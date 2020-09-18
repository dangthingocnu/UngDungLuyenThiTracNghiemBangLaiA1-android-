package com.example.nhom8_udluyenthitracnghiem;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import android.view.MenuItem;

import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.example.nhom8_udluyenthitracnghiem.Adapter.AnswerSheetAdapter;
import com.example.nhom8_udluyenthitracnghiem.Adapter.AnswerSheetHelperAdapter;
import com.example.nhom8_udluyenthitracnghiem.Adapter.QuestionFragmentAdapter;
import com.example.nhom8_udluyenthitracnghiem.Common.Common;
import com.example.nhom8_udluyenthitracnghiem.Common.SpaceDecoration;
import com.example.nhom8_udluyenthitracnghiem.DBHelper.DBHelper;
import com.example.nhom8_udluyenthitracnghiem.Misc.CustomViewPager;

import com.example.nhom8_udluyenthitracnghiem.R;

import com.example.nhom8_udluyenthitracnghiem.model.CurrentQuestion;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CODE_GET_RESULT = 9999;
    int time_play = Common.TOTAL_TIME;
    boolean isAnswerModeView = false;

    TextView txt_right_answer, txt_timer, txt_wrong_answer;

    RecyclerView answer_sheet_view, answer_sheet_helper;
    AnswerSheetAdapter answerSheetAdapter;
    AnswerSheetHelperAdapter answerSheetHelperAdapter;


    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawer;

    //personal modifications
    Button btn_sign_out;
    private boolean isDoItAgainMode = false;

    @Override
    protected void onDestroy() {
        if (Common.countDownTimer != null)
            Common.countDownTimer.cancel();
        super.onDestroy();
    }

    BroadcastReceiver gotoQuestionNum = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_GO_TO_QUESTION)) {
                int question = intent.getIntExtra(Common.KEY_GO_TO_QUESTION, -1);
                if (question != -1)
                    viewPager.setCurrentItem(question); //Got to question
                drawer.closeDrawer(Gravity.START); //Close menu
            }
        }
    };

    Button btn_done;

    MenuItem menu_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selectedCategory.getName());
        setSupportActionBar(toolbar);

        isDoItAgainMode = false;
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        answer_sheet_helper = hView.findViewById(R.id.answer_sheet);
        answer_sheet_helper.setHasFixedSize(true);
        answer_sheet_helper.setLayoutManager(new GridLayoutManager(this, 3));
        answer_sheet_helper.addItemDecoration(new SpaceDecoration(2));
        answerSheetHelperAdapter = new AnswerSheetHelperAdapter(this, Common.answerSheetList);
        answer_sheet_helper.setAdapter(answerSheetHelperAdapter);


        btn_done = hView.findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we will copy code on menu_finish_game
                if (!isAnswerModeView) {
                    new MaterialStyledDialog.Builder(QuestionActivity.this)
                            .setTitle("Kết thúc?")
                            .setIcon(R.drawable.ic_mood_black_24dp)
                            .setDescription("Bạn có thực sự muốn kết thúc?")
                            .setNegativeText("No")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveText("Yes")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    finishGame();
                                    drawer.closeDrawer(Gravity.START);
                                }
                            }).show();
                } else
                    finishGame();
            }
        });


        Common.fragmentsList.clear();
        Common.questionList.clear();
        Common.right_answer_count = 0;
        Common.wrong_answer_count = 0;

        //First we need to take question from DB
        takeQuestion();


    }


    private void finishGame() {
        //For the last question-answer page
        int position = viewPager.getCurrentItem();
        QuestionFragment questionFragment = Common.fragmentsList.get(position);
        CurrentQuestion question_state = questionFragment.getSelectedAnswer();
        if (!isAnswerModeView) {

            Common.answerSheetList.set(position, question_state); // Set question answer for answer sheet
            answerSheetAdapter.notifyDataSetChanged(); //Change color in answer sheet
            answerSheetHelperAdapter.notifyDataSetChanged();
            Common.countDownTimer.cancel();

            countCorrectAnswer();


            txt_right_answer.setText(new StringBuilder(String.format("%d", Common.right_answer_count))
                    .append("/")
                    .append(String.format("%d", Common.questionList.size())).toString());
            txt_wrong_answer.setText(String.valueOf(Common.wrong_answer_count));
        }


        if (question_state != null) {
            if (question_state.getType() == Common.ANSWER_TYPE.NO_ANSWER) {
                questionFragment.showCorrectAnswer();
                questionFragment.disableAnswer();
            }
        }

        //We will navigate to result activity here
        Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
        Common.timer = Common.TOTAL_TIME - time_play;
        Common.no_answer_count = Common.questionList.size() - (Common.wrong_answer_count + Common.right_answer_count);
        Common.data_question = new StringBuilder(new Gson().toJson(Common.answerSheetList));

        startActivityForResult(intent, CODE_GET_RESULT);
    }

    private void countCorrectAnswer() {
        //Reset variable
        Common.right_answer_count = Common.wrong_answer_count = 0;
        for (CurrentQuestion item : Common.answerSheetList) {
            if (item != null) {
                if (item.getType() == Common.ANSWER_TYPE.RIGHT_ANSWER)
                    Common.right_answer_count++;
                else if (item.getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
                    Common.wrong_answer_count++;
            }
        }
    }

    private void genFragmentList() {
        for (int i = 0; i < Common.questionList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            QuestionFragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);

            Common.fragmentsList.add(fragment);

        }
    }


    private void countTimer() {
        if (Common.countDownTimer == null) {
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    time_play -= 1000;

                }


                @Override
                public void onFinish() {
                    //Finish Game
                    Toast.makeText(QuestionActivity.this, "Hết thời gian!", Toast.LENGTH_SHORT).show();
                    finishGame();
                }
            }.start();
        } else {
            Common.countDownTimer.cancel();
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    time_play -= 1000;

                }

                @Override
                public void onFinish() {
                    //Finish Game
                    Toast.makeText(QuestionActivity.this, "Hết thời gian!", Toast.LENGTH_SHORT).show();
                    finishGame();
                }
            }.start();
        }
    }

    private void takeQuestion() {
        Common.questionList.clear();
        Common.selected_values.clear();
//        if(!Common.isOnlineMode), Common.selectedCategory.getClassId()
//        {
        Common.questionList = DBHelper.getInstance(this).getQuestionByCategory(Common.selectedCategory.getId());
        if (Common.questionList.size() == 0) {
            //if no question
            new MaterialStyledDialog.Builder(this)
                    .setTitle("Oops !")
                    .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                    .setDescription("Không có câu hỏi trong đề " + Common.selectedCategory.getName())
                    .setPositiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        } else {

            if (Common.answerSheetList.size() > 0)
                Common.answerSheetList.clear();
            //Gen answerSheet item from question
            //30 question = 30 answer sheet item
            //1 question = 1 answer sheet item
            for (int i = 0; i < Common.questionList.size(); i++) {
                Common.answerSheetList.add(new CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER)); //Default all answer is no answer
            }
//            Common.TOTAL_TIME = 30000 * Common.questionList.size();

        }

        setUpQuestion();
    }

    private void setUpQuestion()
    {
        if(Common.questionList.size() > 0)
        {
            //Show TextView right answer and TextView Timer
            txt_right_answer = findViewById(R.id.txt_answer_right);
            txt_timer = findViewById(R.id.txt_timer);

            txt_timer.setVisibility(View.VISIBLE);
            txt_right_answer.setVisibility(View.VISIBLE);

            txt_right_answer.setText(new StringBuilder(String.format("%d/%d", Common.right_answer_count, Common.questionList.size())));

            countTimer();



            //View
            answer_sheet_view = findViewById(R.id.grid_answer);
            answer_sheet_view.setHasFixedSize(true);
            if(Common.questionList.size() > 5) // If question List have size > 5, we will separate 2 rows
                answer_sheet_view.setLayoutManager(new GridLayoutManager(this, Common.questionList.size()/2));
            answerSheetAdapter = new AnswerSheetAdapter(this, Common.answerSheetList);
            answer_sheet_view.setAdapter(answerSheetAdapter);

            viewPager = findViewById(R.id.viewpager);
            tabLayout = findViewById(R.id.sliding_tabs);

            genFragmentList();


            QuestionFragmentAdapter questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager(),
                    this,
                    Common.fragmentsList);
            viewPager.setAdapter(questionFragmentAdapter);

            tabLayout.setupWithViewPager(viewPager);



            //Event
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
            {
                int SCROLLING_RIGHT = 0;
                int SCROLLING_LEFT = 1;
                int SCROLLING_UNDETERMINED = 2;

                int currentScrollDirection = 2;

                private void setScrollingDirection(float positionOffset)
                {
                    if((1-positionOffset) >= 0.5)
                        this.currentScrollDirection=SCROLLING_RIGHT;
                    else if((1-positionOffset) <= 0.5)
                        this.currentScrollDirection=SCROLLING_LEFT;
                }

                private boolean isScrollDirectionUndetermined()
                {
                    return (currentScrollDirection == SCROLLING_UNDETERMINED);
                }

                private boolean isScrollingRight()
                {
                    return (currentScrollDirection == SCROLLING_RIGHT);
                }

                private boolean isScrollingLeft()
                {
                    return (currentScrollDirection == SCROLLING_LEFT);
                }

                @Override
                public void onPageScrolled(int i, float v, int i1)
                {
                    if(isScrollDirectionUndetermined())
                        setScrollingDirection(v);

                }
                QuestionFragment questionFragment;
                @Override
                public void onPageSelected(int i)
                {

                    int position = 0;
                    if(i>0)
                    {
                        if(isScrollingRight())
                        {
                            //if user scrolls to right, get previous fragment to get result
//                            Toast.makeText(QuestionActivity.this, "isScrollingRight", Toast.LENGTH_SHORT).show();
                            questionFragment = Common.fragmentsList.get(i-1);
                            position = i-1;
//                            Toast.makeText(QuestionActivity.this, "positionR = "+position, Toast.LENGTH_SHORT).show();
                        }
                        else if(isScrollingLeft())
                        {
                            //if user scrolls to left, get next fragment to get result
//                            Toast.makeText(QuestionActivity.this, "isScrollingLeft", Toast.LENGTH_SHORT).show();
                            questionFragment = Common.fragmentsList.get(i+1);
                            position = i+1;
//                            Toast.makeText(QuestionActivity.this, "positionL = "+position, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
//                            Toast.makeText(QuestionActivity.this, "neither Left nor Right", Toast.LENGTH_SHORT).show();
                            questionFragment = Common.fragmentsList.get(position);
//                            Toast.makeText(QuestionActivity.this, "position(else) = "+position, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
//                        Toast.makeText(QuestionActivity.this, "get(0)", Toast.LENGTH_SHORT).show();
                        questionFragment = Common.fragmentsList.get(0);
                        position = 0;
//                        Toast.makeText(QuestionActivity.this, "position(get(0)) = "+position, Toast.LENGTH_SHORT).show();

                    }

                    //If you want to show correct, just call function here

                    CurrentQuestion question_state = questionFragment.getSelectedAnswer();
                    if(!isAnswerModeView)
                    {

                        Common.answerSheetList.set(position, question_state); // Set question answer for answer sheet
                        answerSheetAdapter.notifyDataSetChanged(); //Change color in answer sheet
                        answerSheetHelperAdapter.notifyDataSetChanged();
                        countCorrectAnswer();
                    }


                    if(isAnswerModeView)
                    {
                        questionFragment.showCorrectAnswer();
                        questionFragment.disableAnswer();

                    }
                    if(isDoItAgainMode)
                    {
                        questionFragment.resetQuestion();
                    }

                    txt_right_answer.setText(new StringBuilder(String.format("%d", Common.right_answer_count))
                            .append("/")
                            .append(String.format("%d", Common.questionList.size())).toString());
                    txt_wrong_answer.setText(String.valueOf(Common.wrong_answer_count));





                }

                @Override
                public void onPageScrollStateChanged(int i)
                {
                    if(i == ViewPager.SCROLL_STATE_IDLE)
                        this.currentScrollDirection = SCROLLING_UNDETERMINED;


                    if(!isAnswerModeView && viewPager!=null)
                    {
                        if(viewPager.getCurrentItem() < (Common.answerSheetList.size()-1))
                        {
                            btn_finish.setVisible(false);
                            btn_finish.setEnabled(false);
                        }
                        else if(viewPager.getCurrentItem() == (Common.answerSheetList.size()-1))
                        {
                            btn_finish.setVisible(true);
                            btn_finish.setEnabled(true);
                        }
                    }
                    else
                    {
                        btn_finish.setVisible(true);
                        btn_finish.setEnabled(true);
                    }


                }
            });

        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }
    MenuItem btn_finish;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem item = menu.findItem(R.id.menu_wrong_answer);
        ConstraintLayout constraintLayout = (ConstraintLayout)item.getActionView();
        txt_wrong_answer = constraintLayout.findViewById(R.id.txt_wrong_answer);
        txt_wrong_answer.setText(String.valueOf(Common.wrong_answer_count));


        btn_finish = menu.findItem(R.id.menu_finish_game);
//        menu_sign_out = menu.findItem(R.id.btn_sign_out);

        if(!isAnswerModeView && viewPager!=null)
        {
            if(viewPager.getCurrentItem() < (Common.answerSheetList.size()-1))
            {
                btn_finish.setVisible(false);
                btn_finish.setEnabled(false);
            }
            else if(viewPager.getCurrentItem() == (Common.answerSheetList.size()-1))
            {
                btn_finish.setVisible(true);
                btn_finish.setEnabled(true);
            }
        }
        else
        {
            btn_finish.setVisible(true);
            btn_finish.setEnabled(true);
        }

        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.menu_finish_game)
        {
            if(!isAnswerModeView)
            {
                new MaterialStyledDialog.Builder(this)
                        .setTitle("Kết thúc?")
                        .setIcon(R.drawable.ic_mood_black_24dp)
                        .setDescription("Bạn có thực sự muốn kết thúc?")
                        .setNegativeText("No")
                        .onNegative(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                dialog.dismiss();
                                finishGame();
                            }
                        }).show();
            }
            else
                finishGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_GET_RESULT)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String action = data.getStringExtra("action");
//                Toast.makeText(QuestionActivity.this, "action = "+action, Toast.LENGTH_SHORT).show();
                if(action == null || TextUtils.isEmpty(action))
                {
//                    Toast.makeText(QuestionActivity.this, "Here 2"+action, Toast.LENGTH_SHORT).show();

                    int questionNum = data.getIntExtra(Common.KEY_BACK_FROM_RESULT, -1);
                    viewPager.setCurrentItem(questionNum);

                    isAnswerModeView = true;
                    Common.countDownTimer.cancel();

                    //
                    int position = viewPager.getCurrentItem();
                    QuestionFragment questionFragment = Common.fragmentsList.get(position);
                    CurrentQuestion question_state = questionFragment.getSelectedAnswer();


                    if(question_state!=null)
                    {
                        if (question_state.getType() == Common.ANSWER_TYPE.NO_ANSWER || question_state.getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
                        {
                            questionFragment.showCorrectAnswer();
                            questionFragment.disableAnswer();
                        }
                    }
                    //

//                    viewPager.setEnabled(false);
//                    tabLayout.setTabMode(TabLayout.MODE_FIXED);

                    //Disabling tabLayout and viewPager
                    LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
                    for(int i = 0; i < tabStrip.getChildCount(); i++) {
                        tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                    //            viewPager.setAllowedSwipeDirection(Common.SwipeDirection.none);
                    //


                    txt_wrong_answer.setVisibility(View.GONE);
                    txt_right_answer.setVisibility(View.GONE);
                    txt_timer.setVisibility(View.GONE);

                }
                else
                {

                    if(action.equals("viewquizanswer"))
                    {
                        viewPager.setCurrentItem(0);

                        isAnswerModeView = true;
                        Common.countDownTimer.cancel();

                        txt_wrong_answer.setVisibility(View.GONE);
                        txt_right_answer.setVisibility(View.GONE);
                        txt_timer.setVisibility(View.GONE);


                    }
                    else if(action.equals("doitagain"))
                    {
//                        Toast.makeText(QuestionActivity.this, "Here 4", Toast.LENGTH_SHORT).show();

                        isDoItAgainMode = true;

                        viewPager.setCurrentItem(0);
                        //           viewPager.setAllowedSwipeDirection(Common.SwipeDirection.right);

                        isAnswerModeView = false;
                        Common.right_answer_count = 0;
                        Common.wrong_answer_count = 0;
                        Common.selected_values.clear();
                        countTimer();

                        txt_wrong_answer.setVisibility(View.VISIBLE);
                        txt_right_answer.setVisibility(View.VISIBLE);
                        txt_timer.setVisibility(View.VISIBLE);

                        for(CurrentQuestion item:Common.answerSheetList)
                            item.setType(Common.ANSWER_TYPE.NO_ANSWER); //RESET ALL QUESTIONS
                        answerSheetAdapter.notifyDataSetChanged();
                        answerSheetHelperAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}

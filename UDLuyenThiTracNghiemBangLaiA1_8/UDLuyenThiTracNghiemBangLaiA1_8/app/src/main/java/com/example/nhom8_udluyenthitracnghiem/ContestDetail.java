package com.example.nhom8_udluyenthitracnghiem;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nhom8_udluyenthitracnghiem.Custom_Row_Answer;
import com.example.nhom8_udluyenthitracnghiem.R;
import com.example.nhom8_udluyenthitracnghiem.Question1;
import com.example.nhom8_udluyenthitracnghiem.myResource;

import java.util.ArrayList;
import java.util.Random;



public class ContestDetail extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private ListView listView_Nav,listView_Answer;
    private ActionBarDrawerToggle mToggle;
    private com.example.nhom8_udluyenthitracnghiem.myResource myResource;
    private TextView description;
    private int rowIndexSelected;
    private ImageView imgView;
    private ArrayList<Question1> arrQuestion;
    private Button btnTime, btnSubmit, btnShowResult;
    //    private Button btnPre, btnNext;
    private boolean showRusult;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_detail);
        // Sử dụng vuốt màn hình để chuyển đổi câu hỏi
        // Ánh xạ các đối tượng trong file layout
        initWidget();
        try {
            setMyResource(new myResource(getResources(), R.raw.resource));
            // Random cau hoi
            createRandomQuestion();
            createListViewNavigation();
            createToggle();
            createListViewAnswer(0);
            // Ẩn đáp án
            setShowRusult(false);
            setTime();
            // hide button back
            //btnPre.setVisibility(View.INVISIBLE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // Chạy thời gian
    private void setTime(){
        AsyncTask<Void, String, Void> asyncTask = new AsyncTask<Void, String, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                for (int m=14;m>=0;m--){
                    for (int s=59;s>=0;s--){
                        if (!isShowRusult())
                            this.publishProgress((m<10?("0"+m):m) + ":" + (s<10?("0"+s):s));
                        else
                            return null;
                        SystemClock.sleep(1000);
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                btnTime.setText(values[0]);
                if (values[0].equals("00:00")) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContestDetail.this)
                            .setTitle("Thông báo")
                            .setMessage("Hết giờ làm bài thi !")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SubmitContest();
                                }
                            });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

        };
        asyncTask.execute();
    }
    // Chọn 20 cau hỏi
    private void createRandomQuestion() throws Exception {
        setArrQuestion(new ArrayList<Question1>());
        ArrayList<Integer> arrInterger = new ArrayList<>();

        Random random = new Random();
        // 9 câu khái niệm và quy tắc giao thông
        while (arrInterger.size() < 9){
            boolean flag=false; // Cờ đánh dấu đã tồn tại temp trong arrQuestoin <Tránh lấy các câu hỏi đã lấy>
            int temp = random.nextInt(75);

            for (Integer i:arrInterger){
                if (i==temp){
                    flag = true;
                    break;
                }
            }
            if (!flag) arrInterger.add(temp);
        }

        // 1 câu văn hóa và đạo đức người lái xe
        while (arrInterger.size() < 10){
            boolean flag=false; // Cờ đánh dấu đã tồn tại temp trong arrQuestoin <Tránh lấy các câu hỏi đã lấy>
            int temp = 75 + random.nextInt(5);

            for (Integer i:arrInterger){
                if (i==temp){
                    flag = true;
                    break;
                }
            }
            if (!flag) arrInterger.add(temp);
        }

        // 5 câu hệ thống biển báo
        while (arrInterger.size() < 15){
            boolean flag=false; // Cờ đánh dấu đã tồn tại temp trong arrQuestoin <Tránh lấy các câu hỏi đã lấy>
            int temp = 80 + random.nextInt(36);

            for (Integer i:arrInterger){
                if (i==temp){
                    flag = true;
                    break;
                }
            }
            if (!flag) arrInterger.add(temp);
        }

        // 5 câu giải thích xa hình
        while (arrInterger.size() < 20){
            boolean flag=false; // Cờ đánh dấu đã tồn tại temp trong arrQuestoin <Tránh lấy các câu hỏi đã lấy>
            int temp = 116 + random.nextInt(34);

            for (Integer i:arrInterger){
                if (i==temp){
                    flag = true;
                    break;
                }
            }
            if (!flag) arrInterger.add(temp);
        }

        //Lay cau hoi
        for(Integer i:arrInterger){
            getArrQuestion().add(getMyResource().getIndex(i));
        }
    }

    // Khơi tạo các đối tượng widget với các đối tượng khai báo trong file layout.xml
    private void initWidget(){
        this.listView_Nav = (ListView) findViewById(R.id.layout_learning_lvdrawer);
        this.listView_Answer = (ListView) findViewById(R.id.layout_learning_answer);
        this.description = (TextView) findViewById(R.id.learning_question);
        this.imgView = (ImageView) findViewById(R.id.learning_image_question);
        this.btnTime = (Button) findViewById(R.id.contest_btnTime);
        this.btnSubmit = (Button) findViewById(R.id.contest_btnSubmit);
        this.btnShowResult = (Button) findViewById(R.id.contest_btnShowResult);
//        this.btnPre = (Button) findViewById(R.id.layout_Contest_btnPre);
//        this.btnNext = (Button) findViewById(R.id.layout_Contest_btnNext);
    }

    // Tạo menu chọn câu hỏi
    private void createListViewNavigation() throws Exception{
        ArrayList<String> arrList = new ArrayList<>();
        for (int i=0; i<=19; i++){
            arrList.add("Câu "+(i+1));
        }
        String [] arr = arrList.toArray(new String[arrList.size()]);

        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr);
        listView_Nav.setAdapter(adapter);
        listView_Nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    createListViewAnswer(position);
//                    switch (position){
//                        case 0: btnNext.setVisibility(View.VISIBLE);
//                                btnPre.setVisibility(View.INVISIBLE);
//                            break;
//                        case 19:
//                                btnPre.setVisibility(View.VISIBLE);
//                                btnNext.setVisibility(View.INVISIBLE);
//                            break;
//                        default:
//                            btnPre.setVisibility(View.VISIBLE);
//                            btnNext.setVisibility(View.VISIBLE);
//                            break;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDrawerLayout.closeDrawers();
            }
        });
    }


    // Tạo custom listview các đáp án
    private void createListViewAnswer(final int index) throws Exception{

        // Luu dap an nguoi dung
        ArrayList<Custom_Row_Answer> array = new ArrayList<>();

        // Lay ra 1 cau hoi trong 20 cau cua de thi
        final Question1 question1 = getArrQuestion().get(index);

        for (int i=0;i<question1.getAnswer().size();i++) {
            Custom_Row_Answer answer; //null
            boolean colorBg = false; // Kiểm tra xem có nằm trong đáp án không
            boolean answerTrue = false; // Kiểm tra xem có trả lời đúng không
            if (isShowRusult()) {
                for (Integer temp : question1.getResult()) {
                    if (temp == i) {
                        colorBg = true;
                        break;
                    }
                }

                answer = new Custom_Row_Answer(question1.getAnswer().get(i), colorBg);

                // Xem câu này người dùng có chọn đáp án không. Nếu có đánh dấu lại cho người dùng nhìn để so sánh với đáp án hệ thống.
                for (Integer temp : question1.getUserRsult()) {
                    if (temp == i) {
                        answerTrue = true;
                        break;
                    }
                }

                if (answerTrue) answer.setBit(true);

                array.add(answer);
            } else
                array.add(new Custom_Row_Answer(question1.getAnswer().get(i), false));
        } //end For

        // Kiem tra xem cau hoi nay truoc do nguoi dung co chon dap an chua
        if (question1.getUserRsult() != null){
            for(Integer i: question1.getUserRsult()){
                array.get(i).setBit(true);
            }
        }

        Custom_ListView_Answer adapter = new Custom_ListView_Answer(this,R.layout.custom_listview_learning,array,myResource.getDrawable(getAssets(), "drawable-v24/checked.png"));
        listView_Answer.setAdapter(adapter);

        listView_Answer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setAdapterView(parent);
                // Chỉ chạy đoạn code này khi trong quá trình làm bài thi
                if(!isShowRusult()){
                    Custom_Row_Answer row = (Custom_Row_Answer) parent.getItemAtPosition(position);
                    row.setBit(!row.isBit());
                    Button btn = (Button) view.findViewById(R.id.lear_cus_btn);
                    // Doi mau button
                    btn.setBackgroundResource(row.isBit()?R.color.colorPrimary:R.color.white);
//                      // Thay doi dau tich vao button
//                    if (row.isBit())
//                        btn.setBackground(myResource.getDrawable(getAssets(),"checked.png"));
//                    else
//                        btn.setBackgroundResource(R.color.white);
                    // Cap nhat lai ket qua nguoi dung lua chon
                    question1.setUserRsult(new ArrayList<Integer>());
                    for (int i=0;i<question1.getAnswer().size();i++){
                        row = (Custom_Row_Answer) parent.getItemAtPosition(i);
                        if (row.isBit()){
                            question1.getUserRsult().add(i);
                        }
                    }
                }
            }
        });
        setRowIndexSelected(index);
        // update layout
        updateLayout(index,question1.getDescription());
    }

    // Cập nhật câu hỏi và title trên Actionbar
    private void updateLayout(int index, String des){
        if(!isShowRusult()){
            getSupportActionBar().setTitle("Câu số "+(index+1)+"/20");
        }
        setDescription(des);
        loadImage(index);
    }

    // Tạo Toogle(Button trên ActionBar dùng để đóng mở menu chọn lựa đáp án)
    private void createToggle(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_learning);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Load hình ảnh nếu có
    private void loadImage(int index){
        try{
            String path = getArrQuestion().get(index).getPathImage();
            Drawable drawable = myResource.getDrawable(getAssets(),"image/"+path);
            imgView.setImageDrawable(drawable);
            if (path.equals("")){
                imgView.setMaxHeight(0);
                imgView.setMinimumHeight(0);
            }
            else{
                imgView.setMinimumHeight(600);
                imgView.setMaxHeight(600);
            }
        }catch(Exception e){}
    }

    // Hoàn thành bài thi
    private void SubmitContest(){
        int count=0;
        for (int i=0;i<20;i++){
            Question1 question1 = getArrQuestion().get(i);
            if (myCompare(question1.getUserRsult(),question1.getResult())) count++;
        }
        if(count>=18){
            getSupportActionBar().setTitle("Kết quả: ĐẠT "+count+"/20");
        }else{
            getSupportActionBar().setTitle("Kết quả: Rớt "+count+"/20");
        }
        // Cho phép hiện đáp án
        btnTime.setBackgroundResource(R.color.colorPrimary);
        btnTime.setText("Thi lại");
        setShowRusult(true);
    }

    // kiểm tra đáp án người dùng lựa chọn có đúng không ?
    private boolean myCompare(ArrayList<Integer> arr0, ArrayList<Integer> arr1){
        boolean flag=true; // Cờ kiểm

        /*true: là trùng đáp án.
         * false: không trùng đáp án.
         * */

        // Không trùng nhau vì số lượng đáp án không bằng nhau
        if (arr0.size() != arr1.size()) return false;
            // Đáp án không khớp nhau
        else{
            for (int i=0;i<arr0.size();i++)
                if (arr0.get(i)!=arr1.get(i)) return false;
        }
        return flag;
    }

    // Sự kiện click vào nộp bài
    private void btnSubmit_Click(){
        // Sự kiện sảy ra khi mà người dùng đang làm bài thi và chưa ấn nộp bài
        if (!isShowRusult()){
            boolean flag=false; //kiểm tra xem đã làm hết các câu hỏi chưa

            Question1 question1;
            for(int i=0;i<20;i++){
                question1 = getArrQuestion().get(i);
                if (question1.getUserRsult().size()==0){
                    flag = true;
                    break;
                }
            }

            if (flag){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContestDetail.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn chưa hoàn thành bài thi ! Bạn có thực sự muốn nộp bài ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                btnShowResult.setVisibility(View.VISIBLE);
                                btnSubmit.setVisibility(View.INVISIBLE);
                                SubmitContest();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        }
    }

    // Sự kiện click vào thi lại
    private void btnTime_Click(){
        if (isShowRusult()){
            try {
                createRandomQuestion();
                setShowRusult(false);
                createListViewAnswer(0);
                btnTime.setBackgroundResource(R.color.colorPrimary);
                setTime();
                btnSubmit.setVisibility(View.VISIBLE);
                btnShowResult.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Sự kiện click vào xem đáp án
    private void btnShowResult_Click(){
        try {
            createListViewAnswer(getRowIndexSelected());
            btnShowResult.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(boolean back){
        if (back) super.onBackPressed();
    }

    // ------------------------------- GET - SET------------------------------------------
    public myResource getMyResource() {
        return myResource;
    }

    public void setMyResource(myResource myResource) {
        this.myResource = myResource;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public int getRowIndexSelected() {
        return rowIndexSelected;
    }

    public void setRowIndexSelected(int rowIndexSelected) {
        this.rowIndexSelected = rowIndexSelected;
    }

    public void setAdapterView(AdapterView adapterView) {
        AdapterView adapterView1 = adapterView;
    }

    public ArrayList<Question1> getArrQuestion() {
        return arrQuestion;
    }

    public void setArrQuestion(ArrayList<Question1> arrQuestion) {
        this.arrQuestion = arrQuestion;
    }

    public boolean isShowRusult() {
        return showRusult;
    }

    public void setShowRusult(boolean showRusult) {
        this.showRusult = showRusult;
    }

    // ---------------------------- Override ------------------------------------

    // Sự kiện click vào Toggle trên Actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            createListViewNavigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Sự kiện click vào các view tương ứng
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.contest_btnSubmit: btnSubmit_Click(); break;
            case R.id.contest_btnTime: btnTime_Click(); break;
            case R.id.contest_btnShowResult: btnShowResult_Click();break;
            case R.id.learning_btnBack:
                try {
                    createListViewAnswer(getRowIndexSelected()-1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.learning_btnNext:
                try {
                    createListViewAnswer(getRowIndexSelected()+1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                Toast.makeText(this, "Không tồn tại chức năng này", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ContestDetail.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chưa hoàn thành thi. Bạn có muốn thoát không ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        setShowRusult(true);
                        onBackPressed(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                })
                .setCancelable(false)
                .show();
    }


}

package com.example.nhom8_udluyenthitracnghiem.Common;

import android.os.CountDownTimer;

import com.example.nhom8_udluyenthitracnghiem.QuestionFragment;
import com.example.nhom8_udluyenthitracnghiem.model.Category;
import com.example.nhom8_udluyenthitracnghiem.model.CurrentQuestion;
import com.example.nhom8_udluyenthitracnghiem.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Common
{
    public static final int TOTAL_TIME = 20*60*1000 ; // 60 sec
    public static final String KEY_GO_TO_QUESTION = "GO_TO_QUESTION";
    public static final String KEY_BACK_FROM_RESULT = "BACK_FROM_RESULT";
    public static final String KEY_SAVE_ONLINE_MODE = "ONLINE_MODE";
    public static final String KEY_CLASS_MODE = "CLASS MODE";
    public static List<Question> questionList = new ArrayList<>();
    public static List<CurrentQuestion> answerSheetList = new ArrayList<CurrentQuestion>();
    public static List<CurrentQuestion> answerSheetListFiltered  = new ArrayList<CurrentQuestion>();

    public static Category selectedCategory = new Category();

    public static CountDownTimer countDownTimer;

    public static int timer = 0;
    public static int right_answer_count = 0;
    public static int wrong_answer_count = 0;
    public static int no_answer_count = 0;
    public static StringBuilder data_question = new StringBuilder();
    public static ArrayList<QuestionFragment> fragmentsList = new ArrayList<>();
    public static TreeSet<String> selected_values = new TreeSet<>();
    public static boolean isOnlineMode = false;
    public static int classMode;




    public enum ANSWER_TYPE
    {
        NO_ANSWER,
        WRONG_ANSWER,
        RIGHT_ANSWER
    }
    public enum SwipeDirection
    {
        all, left, right, none ;
    }
}

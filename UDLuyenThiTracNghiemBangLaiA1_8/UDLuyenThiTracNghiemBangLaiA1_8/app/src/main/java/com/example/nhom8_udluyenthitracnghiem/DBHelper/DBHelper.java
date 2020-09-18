package com.example.nhom8_udluyenthitracnghiem.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom8_udluyenthitracnghiem.Common.Common;
import com.example.nhom8_udluyenthitracnghiem.model.Category;
import com.example.nhom8_udluyenthitracnghiem.model.Question;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "TracNgiemBangLai.db";
    private static final int DB_VER=1;
    public  SQLiteDatabase db;
    private static DBHelper instance;


    public static synchronized DBHelper getInstance(Context context)
    {
        if(instance==null)
        {
            instance=new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VER);


    }

    //------------------------lOAI ĐỀ
    /* Getting all categories from the database*/

    public List<Category> getAllCategories()
    {
        SQLiteDatabase db = instance.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM LoaiDe;", null);
        List<Category> categories = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                Category category = new Category(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getString(cursor.getColumnIndex("Image")),
                        Common.selectedCategory.getClassId());
                categories.add(category);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return categories;
    }

    // Get 30 question from db by category

    public List<Question> getQuestionByCategory(int category)
    {
        SQLiteDatabase db = instance.getWritableDatabase();//?getting

        Cursor cursor = db.rawQuery(String.format("SELECT * FROM CauHoi WHERE LoaiDeID = %d ORDER BY RANDOM() LIMIT 20", category), null);
        List<Question> questions = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                Question question = new Question(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("QuestionText")),
                        cursor.getString(cursor.getColumnIndex("QuestionImage")),
                        cursor.getString(cursor.getColumnIndex("AnswerA")),
                        cursor.getString(cursor.getColumnIndex("AnswerB")),
                        cursor.getString(cursor.getColumnIndex("AnswerC")),
                        cursor.getString(cursor.getColumnIndex("AnswerD")),
                        cursor.getString(cursor.getColumnIndex("CorrectAnswer")),
                        cursor.getInt(cursor.getColumnIndex("IsImageQuestion"))==0?Boolean.FALSE:Boolean.TRUE,
                        cursor.getInt(cursor.getColumnIndex("LoaiDeID")),
                        cursor.getInt(cursor.getColumnIndex("LoaiCauHoiID")));
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return questions;
    }


}


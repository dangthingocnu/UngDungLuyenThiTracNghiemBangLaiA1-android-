package com.example.nhom8_udluyenthitracnghiem.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom8_udluyenthitracnghiem.model.BienBaoUtils;
import com.example.nhom8_udluyenthitracnghiem.model.LoaiBienBao;
import com.example.nhom8_udluyenthitracnghiem.model.BienBao;

import java.util.ArrayList;
import java.util.Random;

public class DBHelper1 {
    Context context;
    BienBaoUtils utils;

    String dbName = "FurnitureDB.db";

    public DBHelper1(Context context){
        this.context = context;
        utils = new BienBaoUtils(context);
        //Hàm xóa toàn bộ dữ liệu trên database
        context.deleteDatabase(dbName);
        createTable();
        insertCategories();
        insertFurniture();
    }

    private SQLiteDatabase openDB(){
        return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    }

    private void closeDB(SQLiteDatabase db){
        db.close();
    }

    public void createTable(){
        SQLiteDatabase db = openDB();
        String sqlFurniture = "CREATE TABLE IF NOT EXISTS tblFurniture (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT," +
                "Image TEXT," +
                "Decription TEXT," +
                "CategoriesID INTEGER );";
        String sqlCategories = "CREATE TABLE IF NOT EXISTS tbtCategories (" +
                " CategoriesID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " Name TEXT," +
                "Decription TEXT," +
                " Image TEXT );";
        db.execSQL(sqlFurniture);
        db.execSQL(sqlCategories);
        closeDB(db);
    }

    public ArrayList<BienBao> getALLFurniture(){
        SQLiteDatabase db = openDB();
        ArrayList<BienBao> arr = new ArrayList<>();
        String sql = "select * from tblFurniture";
        Cursor csr = db.rawQuery(sql, null);
        if(csr != null){
            if(csr.moveToFirst()){
                do{
                    int id = csr.getInt(0);
                    String name = csr.getString(1);
                    String image = csr.getString(2);
                    String description = csr.getString(3);
                    int categoriesID = csr.getInt(4);
                    arr.add(new BienBao(name, description, image, categoriesID));
                }while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public ArrayList<LoaiBienBao> getALLCategories(){
        SQLiteDatabase db = openDB();
        ArrayList<LoaiBienBao> arr = new ArrayList<>();
        String sql = "select * from tbtCategories";
        Cursor csr = db.rawQuery(sql, null);
        if(csr != null){
            if(csr.moveToFirst()){
                do{
                    int id = csr.getInt(0);
                    String name = csr.getString(1);
                    String description = csr.getString(2);
                    String image = csr.getString(3);
                    arr.add(new LoaiBienBao(name,description, image, id));
                }while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public LoaiBienBao findByCategoriesID(int id){
        ArrayList<LoaiBienBao> arr = getALLCategories();
        for(LoaiBienBao ca: arr){
            if(ca.getId() == id){
                return ca;
            }
        }
        return null;
    }

    public LoaiBienBao addFurnitureToCategories(int categoriesID){
        LoaiBienBao categories = findByCategoriesID(categoriesID);
        ArrayList<BienBao> arrFurniture = getALLFurniture();

        for(BienBao furniture: arrFurniture){
            if(furniture.getCategoriesID() == categoriesID){
                categories.arrayList.add(furniture);
            }
        }
        return categories;
    }

    public void insertCategories(){
        ArrayList<LoaiBienBao> arrCa = utils.getMockDataCategories();
        SQLiteDatabase db = openDB();

        for (LoaiBienBao ca:arrCa){
            ContentValues cv = new ContentValues();
            cv.put("Name", ca.getName());
            cv.put("Decription",ca.getDescription());
            cv.put("Image", ca.getImageName());
            db.insert("tbtCategories", null, cv);
        }
        closeDB(db);
    }

    public  void insertFurniture(){
        ArrayList<BienBao> arrFurniture = utils.getMockData();
        SQLiteDatabase db = openDB();
        Random random = new Random();

        for (BienBao fur: arrFurniture){
            ContentValues cv = new ContentValues();// hàm ghi dl xuống bảng
            cv.put("Name", fur.getName());
            cv.put("Image", fur.getImageName());
            cv.put("Decription", fur.getDescription());
            cv.put("CategoriesID", fur.getCategoriesID());
            db.insert("tblFurniture", null, cv);
        }
        closeDB(db);
    }

    public void clearDatabase(SQLiteDatabase db){
        clearDatabase(db);
    }
}

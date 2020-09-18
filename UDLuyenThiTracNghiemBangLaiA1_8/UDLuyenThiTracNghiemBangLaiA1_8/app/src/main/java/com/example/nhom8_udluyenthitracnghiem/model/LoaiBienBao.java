package com.example.nhom8_udluyenthitracnghiem.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.nhom8_udluyenthitracnghiem.model.BienBao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoaiBienBao {


  public   String name, imageName;
  public   String description;
    public ArrayList<BienBao> arrayList;
  //  ArrayList<BienBao> arrayList;
    Bitmap image;
    int id;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LoaiBienBao(String name,String description, ArrayList<BienBao> arrayList) {
        this.name = name;
        this.description= description;
        this.arrayList = arrayList;
    }
    public LoaiBienBao(String name, String description, ArrayList<BienBao> arrayList, Bitmap image) {
        this.name = name;
        this.description= description;
        this.arrayList = arrayList;
        this.image = image;
    }

    public LoaiBienBao(String name, String description, ArrayList<BienBao> arrayList, String imageName) {
        this.name = name;
        this.description= description;
        this.imageName = imageName;
        this.arrayList = arrayList;
    }

    public LoaiBienBao(String name,String description, String image, int id) {
        this.name = name;
        this.description= description;
        this.imageName = image;
        this.id = id;
    }

    public LoaiBienBao(String name,String description, String imageName) {
        this.name = name;
        this.description= description;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<BienBao> getArrayList() {
        return arrayList;
    }
    public void setArrayList(ArrayList<BienBao> arrayList) {
        this.arrayList = arrayList;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public static Bitmap convertStringToBitmapFromAccess(Context context, String
            filename){
        AssetManager assetManager = context.getAssets();
        try {
            Log.d("fn",filename);
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return  id;
    }
    public void setId(int id){
        this.id = id;
    }
}

package com.example.nhom8_udluyenthitracnghiem.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class BienBao implements Serializable, Parcelable {
        public String name, imageName;
     public   String description;
        Bitmap image;
        int CategoriesID;
        String dateView;

public int getCategoriesID() {
        return CategoriesID;
        }

public String getDateView() {
        return dateView;
        }

public void setDateView(String dateView) {
        this.dateView = dateView;
        }

public BienBao(String name, String imageName, String description, int categoriesID) {
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        CategoriesID = categoriesID;
        }

public void setCategoriesID(int categoriesID) {
        CategoriesID = categoriesID;
        }

protected BienBao(Parcel in) {
        name = in.readString();
        imageName = in.readString();
        description = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        }

public static final Creator<BienBao> CREATOR = new Creator<BienBao>() {
@Override
public BienBao createFromParcel(Parcel in) {
        return new BienBao(in);
        }

@Override
public BienBao[] newArray(int size) {
        return new BienBao[size];
        }
        };

public String getImageName() {
        return imageName;
        }

public void setImageName(String imageName) {
        this.imageName = imageName;
        }


public BienBao(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        }

public BienBao(String name, String description, Bitmap image) {
        this.name = name;
        this.description = description;
        this.image = image;
        }

public BienBao(String name, String description, String image, Object byCategoriesID, int id) {
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

public Bitmap getImage() {
        return image;
        }

public void setImage(Bitmap image) {
        this.image = image;
        }

public static Bitmap convertStringToBitmapFromAccess(Context context, String
        filename) {
        AssetManager assetManager = context.getAssets();
        try {
        InputStream is = assetManager.open(filename);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
        } catch (IOException e) {
        e.printStackTrace();
        }
        return null;
        }

        @Override
        public int describeContents() {
        return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(imageName);
        parcel.writeString(description);
        parcel.writeParcelable(image, i);
        }

        }

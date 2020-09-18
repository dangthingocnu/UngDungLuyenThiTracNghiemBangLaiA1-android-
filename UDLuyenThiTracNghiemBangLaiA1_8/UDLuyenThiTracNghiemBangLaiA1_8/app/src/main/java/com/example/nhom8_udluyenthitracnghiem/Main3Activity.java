package com.example.nhom8_udluyenthitracnghiem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom8_udluyenthitracnghiem.Adapter.CategoryAdapter;
import com.example.nhom8_udluyenthitracnghiem.Common.SpaceDecoration;
import com.example.nhom8_udluyenthitracnghiem.DBHelper.DBHelper;

public class Main3Activity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thi sát hạch");
        setSupportActionBar(toolbar);

        recycler_category= findViewById(R.id.recyler_category);
        recycler_category.setHasFixedSize(true);
        recycler_category.setLayoutManager(new GridLayoutManager(this,3));

        CategoryAdapter adapter= new CategoryAdapter(Main3Activity.this, DBHelper.getInstance(this).getAllCategories());
        int spaceInPixel= 4;
        recycler_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recycler_category.setAdapter(adapter);
    }
}

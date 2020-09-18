package com.example.nhom8_udluyenthitracnghiem;


import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.PopupMenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom8_udluyenthitracnghiem.Adapter.AdapterRecyclerViewMeoGhiNho;
import com.example.nhom8_udluyenthitracnghiem.DBHelper.MeoGhiNhoDAO;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MeoGhiNhoActivity extends AppCompatActivity {
    RecyclerView rcv_meoGhiNho;
    AdapterRecyclerViewMeoGhiNho adapterRecyclerViewMeoGhiNho;
    MeoGhiNhoDAO meoGhiNhoDAO;
    private StickyHeaderDecoration decor;
    Button btn_menu;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meo_ghi_nho);
        setControl();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("MẸO GHI NHỚ")
    }
    public void setControl(){

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        rcv_meoGhiNho = findViewById(R.id.rcv_meoGhiNho);
        rcv_meoGhiNho.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        meoGhiNhoDAO = new MeoGhiNhoDAO(this);
        adapterRecyclerViewMeoGhiNho = new AdapterRecyclerViewMeoGhiNho(this,meoGhiNhoDAO.getListMeoGhiNho());
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();
        rcv_meoGhiNho.setHasFixedSize(true);
        rcv_meoGhiNho.addItemDecoration(divider);
        rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
        decor = new StickyHeaderDecoration((StickyHeaderAdapter) adapterRecyclerViewMeoGhiNho);
        rcv_meoGhiNho.addItemDecoration(decor, 1);
        OverScrollDecoratorHelper.setUpOverScroll(rcv_meoGhiNho, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu
                        = new PopupMenu(MeoGhiNhoActivity.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_meoghinho,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_tatca:
                                adapterRecyclerViewMeoGhiNho
                                        = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this,meoGhiNhoDAO.getListMeoGhiNho());
                                rcv_meoGhiNho.removeItemDecoration(decor);
                                decor = new StickyHeaderDecoration((StickyHeaderAdapter) adapterRecyclerViewMeoGhiNho);
                                rcv_meoGhiNho.addItemDecoration(decor, 1);
                                rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                                break;
                            case R.id.item_meolythuyet:
                                adapterRecyclerViewMeoGhiNho
                                        = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this,meoGhiNhoDAO.getListMeoLyThuyet());
                                rcv_meoGhiNho.removeItemDecoration(decor);
                                decor = new StickyHeaderDecoration((StickyHeaderAdapter) adapterRecyclerViewMeoGhiNho);
                                rcv_meoGhiNho.addItemDecoration(decor, 1);
                                rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                                break;
                            case R.id.item_meobienbao:
                                adapterRecyclerViewMeoGhiNho
                                        = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this,meoGhiNhoDAO.getListMeoBienBao());
                                rcv_meoGhiNho.removeItemDecoration(decor);
                                decor = new StickyHeaderDecoration((StickyHeaderAdapter) adapterRecyclerViewMeoGhiNho);
                                rcv_meoGhiNho.addItemDecoration(decor, 1);
                                rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                                break;
                            case R.id.item_meosahinh:
                                adapterRecyclerViewMeoGhiNho
                                        = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this,meoGhiNhoDAO.getListMeoSaHinh());
                                rcv_meoGhiNho.removeItemDecoration(decor);
                                decor = new StickyHeaderDecoration((StickyHeaderAdapter) adapterRecyclerViewMeoGhiNho);
                                rcv_meoGhiNho.addItemDecoration(decor, 1);
                                rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);
                popupMenu.show();
            }
        });
    }
}

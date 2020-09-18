package com.example.nhom8_udluyenthitracnghiem;


import android.os.Handler;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BienBaoActivity extends AppCompatActivity {
    BottomNavigationView navView;
    boolean status = false;
    MenuItem menuItem;
    EditText searchView;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);
        navView = findViewById(R.id.nav_view);
        loadFragment(new BienBaoFragment());
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    //getSupportActionBar().setTitle("Dashboard");
                    fragment = new BienBaoFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

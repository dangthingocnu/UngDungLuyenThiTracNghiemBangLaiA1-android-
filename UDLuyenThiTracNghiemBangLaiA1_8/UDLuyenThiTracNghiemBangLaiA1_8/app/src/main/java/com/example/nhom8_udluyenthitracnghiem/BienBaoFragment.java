package com.example.nhom8_udluyenthitracnghiem;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nhom8_udluyenthitracnghiem.DBHelper.DBHelper1;
import com.example.nhom8_udluyenthitracnghiem.model.BienBaoUtils;
import com.example.nhom8_udluyenthitracnghiem.model.LoaiBienBao;

import java.util.ArrayList;


public class BienBaoFragment extends Fragment {
    GridView gridView;
    ArrayList<LoaiBienBao> arrayList;
    LoaiBienBaoAdapter categoriesAdapter;
    BienBaoUtils utils;
    DBHelper1 dbHelper1;
    public BienBaoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        utils = new BienBaoUtils(getContext());
        dbHelper1 = new DBHelper1(getActivity());

        return inflater.inflate(R.layout.fragment_bien_bao, container, false);
    }
    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridView);
        // arrayList = getMockData();
        arrayList = dbHelper1.getALLCategories();
        Log.d("arrSize", arrayList.size() + "");
        categoriesAdapter = new LoaiBienBaoAdapter(getContext(),arrayList);
        gridView.setAdapter(categoriesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, LoaiBienBaoFragment.newInstance(i));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}


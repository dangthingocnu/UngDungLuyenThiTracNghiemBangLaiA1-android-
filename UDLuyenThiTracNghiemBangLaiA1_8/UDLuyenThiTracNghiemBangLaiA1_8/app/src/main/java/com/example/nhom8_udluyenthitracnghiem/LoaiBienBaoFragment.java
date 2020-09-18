package com.example.nhom8_udluyenthitracnghiem;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nhom8_udluyenthitracnghiem.model.BienBao;
import com.example.nhom8_udluyenthitracnghiem.model.BienBaoUtils;

import java.util.ArrayList;



public class LoaiBienBaoFragment extends Fragment {
    ListView listView;
    ArrayList<BienBao> arrayList;
    BienBaoAdapter furnitureAdapter;
    BienBaoUtils utils;
    public LoaiBienBaoFragment() {
        // Required empty public constructor
    }

    public static LoaiBienBaoFragment newInstance(int pos) {

        Bundle bundle = new Bundle();
        bundle.putInt("category", pos);
        LoaiBienBaoFragment categoriesFragment = new LoaiBienBaoFragment();
        categoriesFragment.setArguments(bundle);
        return categoriesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        utils = new BienBaoUtils(getContext());
        return inflater.inflate(R.layout.fragment_loai_bien_bao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        listView = view.findViewById(R.id.listViewCategories);
        arrayList = utils.getFurnitureFromCategories(bundle.getInt("category"));
        furnitureAdapter = new BienBaoAdapter(getContext(),arrayList);
        listView.setAdapter(furnitureAdapter);

    }

}


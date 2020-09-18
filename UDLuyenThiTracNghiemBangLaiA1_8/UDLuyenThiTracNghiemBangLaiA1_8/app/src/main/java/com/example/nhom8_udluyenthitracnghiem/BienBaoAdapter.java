package com.example.nhom8_udluyenthitracnghiem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom8_udluyenthitracnghiem.R;
import com.example.nhom8_udluyenthitracnghiem.model.BienBao;

import java.util.ArrayList;
import java.util.List;

public class BienBaoAdapter extends ArrayAdapter<BienBao> {
    public BienBaoAdapter(Context context, ArrayList<BienBao> Furnitures){
        super(context, 0, (List<BienBao>) Furnitures);
    }
    @Override
    public View getView(int positon, View convertView, ViewGroup parent){
        BienBao furniture = getItem(positon);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_furniture, parent, false);
        ImageView img = convertView.findViewById(R.id.img);
        TextView txtTitle = convertView.findViewById(R.id.tvTitle);
        TextView txtContent = convertView.findViewById(R.id.tvContent);
        txtTitle.setText(furniture.name);
        txtContent.setText(furniture.description);
        if(furniture.getDateView()!=null){
            TextView tvDateView = convertView.findViewById(R.id.tvDateView);
            tvDateView.setText(furniture.getDateView());
        }
        img.setImageBitmap(BienBao.convertStringToBitmapFromAccess(getContext(), furniture.imageName));

        return convertView;
    }
}

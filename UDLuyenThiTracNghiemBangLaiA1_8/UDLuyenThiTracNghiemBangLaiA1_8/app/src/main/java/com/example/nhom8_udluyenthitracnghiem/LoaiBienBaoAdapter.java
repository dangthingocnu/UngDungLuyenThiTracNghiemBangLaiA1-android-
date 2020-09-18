package com.example.nhom8_udluyenthitracnghiem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nhom8_udluyenthitracnghiem.model.LoaiBienBao;

import java.util.ArrayList;

public class LoaiBienBaoAdapter extends ArrayAdapter<LoaiBienBao> {
    //
    public Context context1;
    public LoaiBienBaoAdapter(Context context, ArrayList<LoaiBienBao> Categories){
        super(context, R.layout.item_grid_furniture, Categories);
        //
        this.context1 = context;
    }
    @Override
    public View getView(int positon, View convertView, ViewGroup parent){

       LoaiBienBao categories = getItem(positon);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_furniture, parent, false);
            //    viewHolder.image = convertView.findViewById(R.id.img);
            viewHolder.name = convertView.findViewById(R.id.tvname);
            viewHolder.description=convertView.findViewById(R.id.tvContent1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.image.setImageBitmap(categories.image);
        //////
        viewHolder.name.setText(categories.name);
        viewHolder.description.setText(categories.description);
//        if(categories.getImageName() != null)
//            Log.d("imgName",categories.getImageName());
//        else Log.d("imgName","giá trị null");
//
//        viewHolder.image.setImageBitmap(categories.convertStringToBitmapFromAccess(context1,categories.getImageName()));

        return convertView;
    }
    private static class ViewHolder {
        //  ImageView image;
        TextView name;
        TextView description;
    }
}





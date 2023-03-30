package com.example.anhnvph25971_duanmau.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.example.anhnvph25971_duanmau.object.Sach;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach>{

    private final Context context;
    private final ArrayList<LoaiSach> list;
    private TextView maLoai, tenLoai;


    public LoaiSachSpinnerAdapter(@NonNull Context context,ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_spinner_loaisach, parent, false);
        }

        final LoaiSach loaiSach= list.get(position);
        if (loaiSach != null){
            maLoai = convertView.findViewById(R.id.tv_ma_loaisach);
            tenLoai = convertView.findViewById(R.id.tv_ten_loaisach);

            maLoai.setText(loaiSach.getMaLoai()+"");
            tenLoai.setText(loaiSach.getTenLoai());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_spinner_loaisach, parent, false);
        }

        final LoaiSach loaiSach= list.get(position);
        if (loaiSach != null){
            maLoai = convertView.findViewById(R.id.tv_ma_loaisach);
            tenLoai = convertView.findViewById(R.id.tv_ten_loaisach);

            maLoai.setText(loaiSach.getMaLoai()+"");
            tenLoai.setText(loaiSach.getTenLoai());
        }

        return convertView;
    }
}

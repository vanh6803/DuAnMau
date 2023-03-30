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
import com.example.anhnvph25971_duanmau.object.Sach;
import com.example.anhnvph25971_duanmau.object.ThanhVien;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {

    private final Context context;
    private final ArrayList<ThanhVien> list;
    TextView maTV, tenTV;

    public ThanhVienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if (v==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.layout_spinner_thanhvien, null);
        }

        final ThanhVien thanhVien = list.get(position);
        if (thanhVien != null){
            maTV = v.findViewById(R.id.tv_ma_thanhvien);
            tenTV = v.findViewById(R.id.tv_data_thanhvien);

            maTV.setText(thanhVien.getMaTv()+"");
            tenTV.setText(thanhVien.getTenTv());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if (v==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.layout_spinner_thanhvien, null);
        }

        final ThanhVien thanhVien = list.get(position);
        if (thanhVien != null){
            maTV = v.findViewById(R.id.tv_ma_thanhvien);
            tenTV = v.findViewById(R.id.tv_data_thanhvien);

            maTV.setText(thanhVien.getMaTv()+"");
            tenTV.setText(thanhVien.getTenTv());
        }

        return v;
    }
}

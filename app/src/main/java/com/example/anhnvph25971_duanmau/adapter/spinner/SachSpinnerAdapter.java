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

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {

    private final Context context;
    private final ArrayList<Sach> list;
    private TextView masach, tensach;

    public SachSpinnerAdapter(@NonNull Context context, ArrayList<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if (v==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.layout_spinner_sach, null);
        }

        final Sach sach = list.get(position);
        if (sach != null){
            masach = v.findViewById(R.id.tv_ma_sach);
            tensach = v.findViewById(R.id.tv_data_sach);

            masach.setText(sach.getMaSach()+"");
            tensach.setText(sach.getTenSach());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if (v==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.layout_spinner_sach, null);
        }

        final Sach sach = list.get(position);
        if (sach != null){
            masach = v.findViewById(R.id.tv_ma_sach);
            tensach = v.findViewById(R.id.tv_data_sach);

            masach.setText(sach.getMaSach()+"");
            tensach.setText(sach.getTenSach());
        }

        return v;
    }
}

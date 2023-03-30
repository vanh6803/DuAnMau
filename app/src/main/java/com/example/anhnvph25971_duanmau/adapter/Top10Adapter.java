package com.example.anhnvph25971_duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.object.Top;

import java.util.ArrayList;

public class Top10Adapter extends BaseAdapter{

    private Context context;
    private final ArrayList<Top> list;
    private TextView tenSach, soLuong;

    public Top10Adapter(ArrayList<Top> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;

        if (v == null){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_top10,viewGroup,false);
        }

        tenSach = v.findViewById(R.id.tv_tenSach_top);
        soLuong = v.findViewById(R.id.tv_soLuong);
        Top top = list.get(i);
        tenSach.setText("tên sách: "+ top.getTenSach_top());
        soLuong.setText("số sách: "+ top.getSoLuong_top());

        return v;
    }
}

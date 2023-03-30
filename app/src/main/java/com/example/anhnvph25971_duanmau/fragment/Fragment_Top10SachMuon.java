package com.example.anhnvph25971_duanmau.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.Top10Adapter;
import com.example.anhnvph25971_duanmau.dao.ThongKeDao;
import com.example.anhnvph25971_duanmau.object.Top;

import java.util.ArrayList;


public class Fragment_Top10SachMuon extends Fragment {

    private ListView listView ;
    private ArrayList<Top> list ;
    private Top10Adapter adapter;

    public Fragment_Top10SachMuon() {
        // Required empty public constructor
    }

    public static Fragment_Top10SachMuon newInstance() {
        Fragment_Top10SachMuon fragment = new Fragment_Top10SachMuon();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10_sach_muon, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_top10sach);
        ThongKeDao dao = new ThongKeDao(getContext());
        list = (ArrayList<Top>) dao.getTop();
        adapter = new Top10Adapter(list);
        listView.setAdapter(adapter);
    }
}
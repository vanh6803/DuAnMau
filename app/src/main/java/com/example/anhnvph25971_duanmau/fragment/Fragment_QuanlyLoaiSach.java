package com.example.anhnvph25971_duanmau.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.LoaiSachAdapter;
import com.example.anhnvph25971_duanmau.dao.LoaiSachDao;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Fragment_QuanlyLoaiSach extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<LoaiSach> list = new ArrayList<>();
    private LoaiSachDao dao;
    private LoaiSachAdapter adapter;

    private  EditText ed_name;

    public Fragment_QuanlyLoaiSach() {
        // Required empty public constructor
    }

    public static Fragment_QuanlyLoaiSach newInstance() {
        Fragment_QuanlyLoaiSach fragment = new Fragment_QuanlyLoaiSach();
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
        return inflater.inflate(R.layout.fragment_quan_ly_loai_sach, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ánh xạ
        recyclerView = view.findViewById(R.id.recycle_quanlyloaisach);
        fab = view.findViewById(R.id.fab_quanlyloaisach);

        dao = new LoaiSachDao(getActivity());
        list = dao.selectAll();
        adapter = new LoaiSachAdapter(getActivity(), dao);
        adapter.setData(list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());

                dialog.setContentView(R.layout.layout_item_them_loaisach);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ed_name = dialog.findViewById(R.id.ed_add_tenloaisach);
                Button btn_add = dialog.findViewById(R.id.btn_add_loaisach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_addLS);
                LoaiSach obj = new LoaiSach();

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        obj.setTenLoai(ed_name.getText().toString());
                        if (checlValidate(obj)){
                            dao.insert(obj);
                            list = dao.selectAll();
                            adapter.setData(list);
                            Toast.makeText(getActivity(), "thêm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(), "mời bạn nhập dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private boolean checlValidate(LoaiSach loaiSach){
        return !ed_name.getText().toString().isEmpty(); //k có dữ liệu
// có dữ liệu
    }


    @Override
    public void onResume() {
        super.onResume();
        list = dao.selectAll();
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.ThanhVienAdapter;
import com.example.anhnvph25971_duanmau.dao.ThanhVienDao;
import com.example.anhnvph25971_duanmau.object.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Fragment_QuanLyThanhVien extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ArrayList<ThanhVien> list = new ArrayList<>();
    private ThanhVienDao dao;
    private ThanhVienAdapter adapter;
    private EditText ed_tenTV, ed_namSinh;
    private RadioButton rdo_nam, rdo_nu;
    private Button add, cancel;

    public Fragment_QuanLyThanhVien() {
        // Required empty public constructor
    }

    public static Fragment_QuanLyThanhVien newInstance() {
        Fragment_QuanLyThanhVien fragment = new Fragment_QuanLyThanhVien();
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
        return inflater.inflate(R.layout.fragment_quan_ly_thanh_vien, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_quanlythanhvien);
        fab = view.findViewById(R.id.fab_quanlythanhvien);
        dao  =new ThanhVienDao(getActivity());
        list = dao.selectAll();
        adapter = new ThanhVienAdapter(getActivity(), dao);
        adapter.setData(list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_item_them_thanhvien);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ed_tenTV = dialog.findViewById(R.id.ed_add_tenTv);
                ed_namSinh = dialog.findViewById(R.id.ed_add_namSinh);
                rdo_nam = dialog.findViewById(R.id.rdo_nam);
                rdo_nu = dialog.findViewById(R.id.rdo_nu);
                add = dialog.findViewById(R.id.btn_add_thanhvien);
                cancel = dialog.findViewById(R.id.btn_cancel_thanhvien);

                ThanhVien thanhVien = new ThanhVien();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        thanhVien.setTenTv(ed_tenTV.getText().toString());
                        thanhVien.setNamSinh(Integer.parseInt(ed_namSinh.getText().toString()));
                        if (rdo_nam.isChecked()){
                            thanhVien.setGioiTinh("Nam");
                        }else {
                            thanhVien.setGioiTinh("Nữ");
                        }
                        dao.insert(thanhVien);
                        list = dao.selectAll();
                        adapter.setData(list);
                        Toast.makeText(getActivity(), "thêm sách thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
}
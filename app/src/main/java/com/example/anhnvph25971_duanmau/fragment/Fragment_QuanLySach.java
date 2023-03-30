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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.SachApdapter;
import com.example.anhnvph25971_duanmau.adapter.spinner.LoaiSachSpinnerAdapter;
import com.example.anhnvph25971_duanmau.dao.LoaiSachDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.example.anhnvph25971_duanmau.object.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Fragment_QuanLySach extends Fragment {

    private RecyclerView recyclerView;
    private SachApdapter apdapter;
    private SachDao dao;
    private ArrayList<Sach> list = new ArrayList<>();
    private FloatingActionButton fab;
    private EditText tensach,giaThue;
    private Spinner sploaiSach;
    private Button add,cancel;
    private LoaiSachSpinnerAdapter spinnerAdapter;
    private int mals;
    private ArrayList<LoaiSach> listLS = new ArrayList<>();
    private LoaiSachDao lsdao;

    public Fragment_QuanLySach() {
        // Required empty public constructor
    }

    public static Fragment_QuanLySach newInstance() {
        Fragment_QuanLySach fragment = new Fragment_QuanLySach();
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
        return inflater.inflate(R.layout.fragment_quan_ly_sach, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_quanlysach);
        fab = view.findViewById(R.id.fab_quanlysach);
        dao = new SachDao(getActivity());
        list = dao.selectAll();
        apdapter = new SachApdapter(getActivity(), dao);
        apdapter.setData(list);
        Sach sach = new Sach();
        lsdao = new LoaiSachDao(getActivity());
        listLS = lsdao.selectAll();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(apdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_item_them_sach);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tensach = dialog.findViewById(R.id.ed_add_tenSach);
                giaThue = dialog.findViewById(R.id.ed_add_giaThue);
                sploaiSach = dialog.findViewById(R.id.spn_loaiSach_add);
                add = dialog.findViewById(R.id.btn_add_sach);
                cancel = dialog.findViewById(R.id.btn_cancel_sach);

                spinnerAdapter = new LoaiSachSpinnerAdapter(getActivity(), listLS);
                sploaiSach.setAdapter(spinnerAdapter);

                sploaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mals = listLS.get(i).getMaLoai();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int j = 0; j<listLS.size(); j++){
                    if (sach.getMaLoaiSach() == listLS.get(j).getMaLoai()){
                        sploaiSach.setSelection(j);
                    }
                }



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearForm();
                        dialog.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sach.setTenSach(tensach.getText().toString());
                        sach.setGiaThue(Integer.parseInt(giaThue.getText().toString()));
                        sach.setMaLoaiSach(mals);
                        if (checkValidate()){
                            dao.insert(sach);
                            list = dao.selectAll();
                            apdapter.setData(list);
                            Toast.makeText(getActivity(), "thêm sách thành công", Toast.LENGTH_SHORT).show();
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

    private boolean checkValidate(){
        if (tensach.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "chưa nhập tên sách", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.valueOf(giaThue.getText().toString())== null){
            Toast.makeText(getActivity(), "chưa nhập giá thuê", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearForm(){
        tensach.setText("");
        giaThue.setText("");
    }
}
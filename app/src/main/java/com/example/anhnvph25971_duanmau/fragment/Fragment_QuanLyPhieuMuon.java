package com.example.anhnvph25971_duanmau.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.UFormat;
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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.PhieuMuonAdapter;
import com.example.anhnvph25971_duanmau.adapter.spinner.SachSpinnerAdapter;
import com.example.anhnvph25971_duanmau.adapter.spinner.ThanhVienSpinnerAdapter;
import com.example.anhnvph25971_duanmau.dao.PhieuMuonDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.dao.ThanhVienDao;
import com.example.anhnvph25971_duanmau.object.PhieuMuon;
import com.example.anhnvph25971_duanmau.object.Sach;
import com.example.anhnvph25971_duanmau.object.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Fragment_QuanLyPhieuMuon extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private PhieuMuonDao dao;
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> list = new ArrayList<>();

    //dialog
    private Spinner spinner_thanhVien, spinner_sach;
    private TextView ngaythue;
    private TextView tienThue;
    private CheckBox chk_trangthai;
    private Button add, cancel;

    // thành viên
    private ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    private ThanhVienDao thanhVienDao;
    private ArrayList<ThanhVien> listThanhVien = new ArrayList<>();
    private int maThanhVien;

    // sách
    private SachSpinnerAdapter sachSpinnerAdapter;
    private SachDao sachDao;
    private ArrayList<Sach> listSach = new ArrayList<>();
    private int maSach, giaThue;

    private final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public Fragment_QuanLyPhieuMuon() {
        // Required empty public constructor
    }

    public static Fragment_QuanLyPhieuMuon newInstance() {
        Fragment_QuanLyPhieuMuon fragment = new Fragment_QuanLyPhieuMuon();
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
        return inflater.inflate(R.layout.fragment_quan_ly_phieu_muon, container, false);
    }

    // viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_quanlyphieumuon);
        fab = view.findViewById(R.id.fab_quanlyphieumuon);

        dao = new PhieuMuonDao(getActivity());
        list = dao.selectAll();
        adapter = new PhieuMuonAdapter(getActivity(), dao);
        adapter.setData(list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_item_them_phieumuon);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                spinner_sach = dialog.findViewById(R.id.spn_sach_addpm);
                spinner_thanhVien = dialog.findViewById(R.id.spn_tv_addpm);
                ngaythue = dialog.findViewById(R.id.tv_ngaythue_addpm);
                tienThue = dialog.findViewById(R.id.tv_tienThue_pm_addpm);
                chk_trangthai = dialog.findViewById(R.id.chk_traSach_addpm);
                add = dialog.findViewById(R.id.btn_add_addpm);
                cancel = dialog.findViewById(R.id.btn_cancel_addpm);

                ngaythue.setVisibility(View.GONE);
                PhieuMuon phieuMuon = new PhieuMuon();

                //spinner thành viên
                thanhVienDao = new ThanhVienDao(getActivity());
                listThanhVien = thanhVienDao.selectAll();
                thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getActivity(), listThanhVien);
                spinner_thanhVien.setAdapter(thanhVienSpinnerAdapter);
                spinner_thanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maThanhVien = listThanhVien.get(i).getMaTv();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int j = 0; j < listThanhVien.size(); j++){
                    if (phieuMuon.getMaTV() == listThanhVien.get(j).getMaTv()){
                        spinner_thanhVien.setSelection(j);
                    }
                }

                // spinner sách
                sachDao = new SachDao(getActivity());
                listSach  = sachDao.selectAll();
                sachSpinnerAdapter = new SachSpinnerAdapter(getActivity(), listSach);
                spinner_sach.setAdapter(sachSpinnerAdapter);
                spinner_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        maSach = listSach.get(i).getMaSach();
                        giaThue = listSach.get(i).getGiaThue();
                        tienThue.setText("tiền thuê: "+giaThue+" vnđ");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int j=0; j<listSach.size(); j++){
                    if (phieuMuon.getMaSach() == listSach.get(j).getMaSach()){
                        spinner_sach.setSelection(j);
                    }
                }

                //check trạng thái
                if (phieuMuon.getTraSach() == 1){
                    chk_trangthai.setChecked(true);
                }

                // cancel
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // add
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        phieuMuon.setMaTV(maThanhVien);
                        phieuMuon.setMaSach(maSach);
                        phieuMuon.setTienThue(giaThue);
                        if (chk_trangthai.isChecked()){
                            phieuMuon.setTraSach(1);
                        }else {
                            phieuMuon.setTraSach(0);
                        }
                        phieuMuon.setNgayThue(date.format(new Date()));
                        dao.insert(phieuMuon);
                        list = dao.selectAll();
                        adapter.setData(list);
                        Toast.makeText(getActivity(), "thêm thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

}
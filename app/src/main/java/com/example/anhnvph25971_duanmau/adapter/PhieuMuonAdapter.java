package com.example.anhnvph25971_duanmau.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.spinner.SachSpinnerAdapter;
import com.example.anhnvph25971_duanmau.adapter.spinner.ThanhVienSpinnerAdapter;
import com.example.anhnvph25971_duanmau.dao.PhieuMuonDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.dao.ThanhVienDao;
import com.example.anhnvph25971_duanmau.object.PhieuMuon;
import com.example.anhnvph25971_duanmau.object.Sach;
import com.example.anhnvph25971_duanmau.object.ThanhVien;

import java.text.SimpleDateFormat;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    private final Context context;
    private final PhieuMuonDao dao;
    private ArrayList<PhieuMuon> list;

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

    public PhieuMuonAdapter(Context context, PhieuMuonDao dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<PhieuMuon> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_phieumuon,parent,false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(holder.getAdapterPosition());
        if (phieuMuon == null){
            return;
        }

        holder.maPhieuMuon.setText("mã phiếu mượn: "+ phieuMuon.getMaPM());

        listThanhVien = new ThanhVienDao(context).selectAll();
        ThanhVien thanhVien = new ThanhVienDao(context).getOne(phieuMuon.getMaTV()+"");
        holder.tenNguoiMuon.setText("thành viên: "+thanhVien.getTenTv());

        listSach = new SachDao(context).selectAll();
        Sach sach = new SachDao(context).getOne(phieuMuon.getMaSach()+"");
        holder.tenSachMuon.setText("sách: "+sach.getTenSach());

        holder.tienThue.setText("tiền thuê: "+phieuMuon.getTienThue() +" Vnđ");
        holder.ngaythue.setText("ngày thuê: "+ phieuMuon.getNgayThue());

        if (phieuMuon.getTraSach() == 1){
            holder.trangThai.setText("đã trả sách");
            holder.trangThai.setTextColor(Color.BLUE);
        }else {
            holder.trangThai.setText("Chưa trả sách");
            holder.trangThai.setTextColor(Color.RED);
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(phieuMuon);
                        dao.delete(phieuMuon.getMaSach()+"");
                        notifyDataSetChanged();
                        Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
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


                //spinner thành viên
                listThanhVien = new ThanhVienDao(context).selectAll();
                thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, listThanhVien);
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
                listSach  = new SachDao(context).selectAll();
                sachSpinnerAdapter = new SachSpinnerAdapter(context, listSach);
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

                spinner_sach.setSelection(phieuMuon.getMaSach()-1);
                spinner_thanhVien.setSelection(phieuMuon.getMaTV()-1);
                ngaythue.setText("ngày thuê: "+phieuMuon.getNgayThue());


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
                        dao.update(phieuMuon);
                        notifyDataSetChanged();
                        Toast.makeText(context, "sửa thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{

        private final TextView maPhieuMuon;
        private final TextView tenNguoiMuon;
        private final TextView tenSachMuon;
        private final TextView tienThue;
        private final TextView ngaythue;
        private final TextView trangThai;
        private final ImageView edit;
        private final ImageView delete;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            maPhieuMuon = itemView.findViewById(R.id.tv_maPM);
            tenNguoiMuon = itemView.findViewById(R.id.tv_tennguoimuon);
            tenSachMuon = itemView.findViewById(R.id.tv_tensachmuon);
            tienThue = itemView.findViewById(R.id.tv_tienthue);
            ngaythue = itemView.findViewById(R.id.tv_ngaythue);
            trangThai = itemView.findViewById(R.id.tv_chk_trasach);
            edit = itemView.findViewById(R.id.img_edit_phieumuon);
            delete = itemView.findViewById(R.id.img_delete_phieumuon);
        }
    }

}

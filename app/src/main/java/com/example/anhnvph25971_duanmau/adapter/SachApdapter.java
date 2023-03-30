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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.adapter.spinner.LoaiSachSpinnerAdapter;
import com.example.anhnvph25971_duanmau.dao.LoaiSachDao;
import com.example.anhnvph25971_duanmau.dao.PhieuMuonDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.example.anhnvph25971_duanmau.object.PhieuMuon;
import com.example.anhnvph25971_duanmau.object.Sach;

import java.util.ArrayList;

public class SachApdapter extends RecyclerView.Adapter<SachApdapter.SachViewHolder> {

    private Context context;
    private ArrayList<Sach> list;
    private SachDao dao;
    private ArrayList<LoaiSach> listls;
    int mals;

    public SachApdapter(Context context, SachDao dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Sach> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach obj = list.get(holder.getAdapterPosition());
        if (obj == null){
            return;
        }
        listls = new LoaiSachDao(context).selectAll();
        LoaiSach loaiSach = new LoaiSachDao(context).getOne(obj.getMaLoaiSach()+"");
        holder.maSach.setText("mã sách: "+obj.getMaSach());
        holder.tenSach.setText("tên sách: "+obj.getTenSach());
        holder.giaThue.setText("giá thuê: "+obj.getGiaThue()+"");
        holder.tenloaiSach.setText("tên loại sách: "+loaiSach.getTenLoai());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa ?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkDelete(obj)){
                            list.remove(obj);
                            dao.delete(obj.getMaSach()+"");
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                        }
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
                dialog.setContentView(R.layout.layout_item_them_sach);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText tensach = dialog.findViewById(R.id.ed_add_tenSach);
                EditText giaThue = dialog.findViewById(R.id.ed_add_giaThue);
                Spinner sploaiSach = dialog.findViewById(R.id.spn_loaiSach_add);
                Button add = dialog.findViewById(R.id.btn_add_sach);
                Button cancel = dialog.findViewById(R.id.btn_cancel_sach);

                ArrayList<LoaiSach> listLS = new LoaiSachDao(context).selectAll();

                LoaiSachSpinnerAdapter spinnerAdapter = new LoaiSachSpinnerAdapter(context, listLS);
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
                    if (obj.getMaLoaiSach() == listLS.get(j).getMaLoai()){
                        sploaiSach.setSelection(j);
                    }
                }



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                tensach.setText(obj.getTenSach());
                giaThue.setText(obj.getGiaThue()+"");
                sploaiSach.setSelection(obj.getMaLoaiSach()-1);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        obj.setTenSach(tensach.getText().toString());
                        obj.setGiaThue(Integer.parseInt(giaThue.getText().toString()));
                        obj.setMaLoaiSach(mals);
                        dao.update(obj);
                        notifyDataSetChanged();
                        Toast.makeText(context, "thêm sách thành công", Toast.LENGTH_SHORT).show();
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

    public class SachViewHolder extends RecyclerView.ViewHolder {
        private TextView maSach, tenSach, giaThue, tenloaiSach;
        private ImageView edit, delete;
        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            maSach = itemView.findViewById(R.id.tv_masach);
            tenSach = itemView.findViewById(R.id.tv_tensach);
            giaThue = itemView.findViewById(R.id.tv_giathuesach);
            tenloaiSach = itemView.findViewById(R.id.tv_loaisach);
            edit = itemView.findViewById(R.id.img_edit_sach);
            delete = itemView.findViewById(R.id.img_delete_sach);
        }
    }

    public boolean checkDelete(Sach sach){
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
        ArrayList<PhieuMuon> listPM = phieuMuonDao.selectAll();
        for (int i=0; i<listPM.size(); i++){
            if (sach.getMaSach() == listPM.get(i).getMaSach()){
                return false;
            }
        }
        return true;
    }
}

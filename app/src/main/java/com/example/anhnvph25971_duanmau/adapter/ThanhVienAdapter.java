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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.dao.PhieuMuonDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.dao.ThanhVienDao;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.example.anhnvph25971_duanmau.object.PhieuMuon;
import com.example.anhnvph25971_duanmau.object.Sach;
import com.example.anhnvph25971_duanmau.object.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder>{

    private final Context context;
    private ArrayList<ThanhVien> list;
    private final ThanhVienDao dao;
    private EditText ed_tenTV, ed_namSinh;
    private RadioButton rdo_nam, rdo_nu;
    private Button add, cancel;

    public ThanhVienAdapter(Context context, ThanhVienDao dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<ThanhVien> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thanhvien, parent,false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien obj = list.get(holder.getAdapterPosition());
        if (obj == null){
            return;
        }

        // hiển thị thông tin
        holder.maTV.setText("Mã thành viên: "+obj.getMaTv());
        holder.tenTV.setText("Tên thành viên: "+obj.getTenTv());
        holder.gioiTinh.setText("Giới tính: "+obj.getGioiTinh());
        holder.namSinh.setText("Năm sinh: "+obj.getNamSinh());

        // xóa
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkDelete(obj)){
                            list.remove(obj);
                            dao.delete(obj.getMaTv()+"");
                            notifyDataSetChanged();
                            Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_item_them_thanhvien);

                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ed_tenTV = dialog.findViewById(R.id.ed_add_tenTv);
                ed_namSinh = dialog.findViewById(R.id.ed_add_namSinh);
                rdo_nam = dialog.findViewById(R.id.rdo_nam);
                rdo_nu = dialog.findViewById(R.id.rdo_nu);
                add = dialog.findViewById(R.id.btn_add_thanhvien);
                cancel = dialog.findViewById(R.id.btn_cancel_thanhvien);

                ed_tenTV.setText(obj.getTenTv());
                ed_namSinh.setText(obj.getNamSinh()+"");
                if (obj.getGioiTinh().equalsIgnoreCase("Nam")){
                    rdo_nam.setChecked(true);
                }else {
                    rdo_nu.setChecked(true);
                }

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        obj.setTenTv(ed_tenTV.getText().toString());
                        obj.setNamSinh(Integer.parseInt(ed_namSinh.getText().toString()));
                        if (rdo_nam.isChecked()){
                            obj.setGioiTinh("Nam");
                        }else {
                            obj.setGioiTinh("Nữ");
                        }
                        dao.update(obj);
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

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {

        private final TextView maTV;
        private final TextView tenTV;
        private final TextView gioiTinh;
        private final TextView namSinh;
        private final ImageView edit;
        private final ImageView delete;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            maTV = itemView.findViewById(R.id.tv_mathanhvien);
            tenTV = itemView.findViewById(R.id.tv_tenthanhvien);
            gioiTinh = itemView.findViewById(R.id.tv_gioitinh);
            namSinh = itemView.findViewById(R.id.tv_namsinh);
            edit = itemView.findViewById(R.id.img_edit_thanhvien);
            delete = itemView.findViewById(R.id.img_delete_thanhvien);

        }
    }

    public boolean checkDelete(ThanhVien thanhVien){
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
        ArrayList<PhieuMuon> listPM = phieuMuonDao.selectAll();
        for (int i=0; i<listPM.size(); i++){
            if (thanhVien.getMaTv() == listPM.get(i).getMaTV()){
                return false;
            }
        }
        return true;
    }

}

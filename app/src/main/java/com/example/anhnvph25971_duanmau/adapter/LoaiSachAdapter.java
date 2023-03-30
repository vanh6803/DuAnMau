package com.example.anhnvph25971_duanmau.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.dao.LoaiSachDao;
import com.example.anhnvph25971_duanmau.dao.SachDao;
import com.example.anhnvph25971_duanmau.object.LoaiSach;
import com.example.anhnvph25971_duanmau.object.Sach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {

    private final Context context;
    private final LoaiSachDao dao;
    private ArrayList<LoaiSach> list;

    public LoaiSachAdapter(Context context, LoaiSachDao dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<LoaiSach> obj){
        this.list = obj;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loaisach,parent,false);
        return new LoaiSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {
        LoaiSach obj = list.get(holder.getAdapterPosition());
        if (obj == null){
            return;
        }
        holder.tv_maLoaiSach.setText("mã loại sách: "+obj.getMaLoai());
        holder.tv_tenLoaiSach.setText("tên loại sách: "+obj.getTenLoai());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("bạn muốn xóa "+ obj.getTenLoai()+" ?");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (checkDelete(obj)){
                            list.remove(obj);
                            dao.delete(obj.getMaLoai()+"");
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_item_update_loaisach);

                dialog.setContentView(R.layout.layout_item_them_loaisach);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText ed_name = dialog.findViewById(R.id.ed_add_tenloaisach);
                Button btn_add = dialog.findViewById(R.id.btn_add_loaisach);
                Button btn_cancel = dialog.findViewById(R.id.btn_cancel_addLS);
                LoaiSach obj1 = list.get(position);

                ed_name.setText(obj1.getTenLoai());

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
                        dao.update(obj);
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

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_tenLoaiSach;
        private final TextView tv_maLoaiSach;
        private final ImageView img_edit;
        private final ImageView img_delete;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenLoaiSach = itemView.findViewById(R.id.tv_tenloaisach);
            img_edit = itemView.findViewById(R.id.img_edit_loaisach);
            img_delete = itemView.findViewById(R.id.img_delete_loaisach);
            tv_maLoaiSach = itemView.findViewById(R.id.tv_maloaisach);
        }
    }

    public boolean checkDelete(LoaiSach loaiSach){
        SachDao sachDao = new SachDao(context);
        ArrayList<Sach> listSach = sachDao.selectAll();
        for (int i=0; i<listSach.size(); i++){
            if (loaiSach.getMaLoai() == listSach.get(i).getMaLoaiSach()){
                return false;
            }
        }
        return true;
    }
}

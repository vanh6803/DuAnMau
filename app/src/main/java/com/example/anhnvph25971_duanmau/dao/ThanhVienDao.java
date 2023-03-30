package com.example.anhnvph25971_duanmau.dao;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    private final SQLiteDatabase db;

    public ThanhVienDao (Context context){
        MyDataBase dataBase = new MyDataBase(context);
        db = dataBase.getWritableDatabase();
    }

//    public ArrayList<ThanhVien> getAll(){
//        String sql = "select * from tb_thanhVien";
//        return getData(sql);
//    }
//
//    public ThanhVien getId(String id){
//        String sql = "select * from tb_thanhVien WHERE maTV=?";
//        ArrayList<ThanhVien> list = getData(sql, id);
//        return list.get(0);
//    }

    public ArrayList<ThanhVien> selectAll(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from ThanhVien", null);

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThanhVien thanhVien = new ThanhVien();

                thanhVien.setMaTv(cursor.getInt(0));
                thanhVien.setTenTv(cursor.getString(1));
                thanhVien.setGioiTinh(cursor.getString(2));
                thanhVien.setNamSinh(cursor.getInt(3));

                list.add(thanhVien);

                cursor.moveToNext();
            }
        }
        return list;
    }

    public ThanhVien getOne(String id){
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from ThanhVien where maTv=?", new String[]{id});

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThanhVien thanhVien = new ThanhVien();

                thanhVien.setMaTv(cursor.getInt(0));
                thanhVien.setTenTv(cursor.getString(1));
                thanhVien.setGioiTinh(cursor.getString(2));
                thanhVien.setNamSinh(cursor.getInt(3));
                list.add(thanhVien);
                cursor.moveToNext();
            }
        }
        return list.get(0);
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("tenTv", thanhVien.getTenTv());
        values.put("gioiTinh", thanhVien.getGioiTinh());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.insert("ThanhVien", null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("tenTv", thanhVien.getTenTv());
        values.put("gioiTinh", thanhVien.getGioiTinh());
        values.put("namSinh", thanhVien.getNamSinh());
        return db.update("ThanhVien", values, "maTv=?", new String[] {thanhVien.getMaTv() + ""});
    }

    public int delete(String id) {
        return db.delete("ThanhVien", "maTv=?", new String[] {id});
    }
}

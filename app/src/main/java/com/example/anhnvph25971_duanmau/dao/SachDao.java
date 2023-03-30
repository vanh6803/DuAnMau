package com.example.anhnvph25971_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.Sach;

import java.util.ArrayList;

public class SachDao {
    private final SQLiteDatabase db;

    public SachDao (Context context){
        MyDataBase myDataBase = new MyDataBase(context);
        db = myDataBase.getWritableDatabase();
    }

    public ArrayList<Sach> selectAll(){
        ArrayList<Sach> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Sach", null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoaiSach(cursor.getInt(3));
                list.add(sach);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public Sach getOne(String id){
        ArrayList<Sach> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Sach where maSach=?", new String[]{id});
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaThue(cursor.getInt(2));
                sach.setMaLoaiSach(cursor.getInt(3));
                list.add(sach);
                cursor.moveToNext();
            }
        }
        return list.get(0);
    }

    public long insert(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoaiSach());
        return db.insert("Sach", null, values);
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoai", sach.getMaLoaiSach());
        return db.update("Sach", values, "maSach=?", new String[] {sach.getMaSach() + ""});
    }

    public int delete(String id) {
        return db.delete("Sach", "maSach=?", new String[] {id});
    }
}

package com.example.anhnvph25971_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.LoaiSach;

import java.util.ArrayList;
import java.util.List;


public class LoaiSachDao {

    private final SQLiteDatabase db;

    public LoaiSachDao (Context context){
        MyDataBase myDataBase = new MyDataBase(context);
        db = myDataBase.getWritableDatabase();
    }


    public ArrayList<LoaiSach> selectAll(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from LoaiSach", null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                list.add(loaiSach);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public LoaiSach getOne(String id){
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from LoaiSach Where maLoai=? ", new String[]{id});
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(0));
                loaiSach.setTenLoai(cursor.getString(1));
                list.add(loaiSach);
                cursor.moveToNext();
            }
        }
        return list.get(0);
    }

    public long insert (LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.insert("LoaiSach", null, values);
    }

    public int update (LoaiSach obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(obj.getMaLoai())});
    }

    public int delete(String id){
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

}

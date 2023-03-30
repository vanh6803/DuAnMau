package com.example.anhnvph25971_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDao {
    private final SQLiteDatabase db;

    public PhieuMuonDao (Context context){
        MyDataBase myDataBase = new MyDataBase(context);
        db = myDataBase.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> selectAll(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from PhieuMuon", null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(cursor.getInt(0));
                phieuMuon.setMaTT(cursor.getString(1));
                phieuMuon.setMaTV(cursor.getInt(2));
                phieuMuon.setMaSach(cursor.getInt(3));
                phieuMuon.setTienThue(cursor.getInt(4));
                phieuMuon.setNgayThue(cursor.getString(5));
                phieuMuon.setTraSach(cursor.getInt(6));
                list.add(phieuMuon);
                cursor.moveToNext();
            }
        }
        return list;
    }
    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayThue", phieuMuon.getNgayThue());
        values.put("traSach", phieuMuon.getTraSach());
        return db.insert("PhieuMuon", null, values);
    }

    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("maTT", phieuMuon.getMaTT());
        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach", phieuMuon.getMaSach());
        values.put("tienThue", phieuMuon.getTienThue());
        values.put("ngayThue", phieuMuon.getNgayThue());
        values.put("traSach", phieuMuon.getTraSach());
        return db.update("PhieuMuon", values, "maPM = ?", new String[] {phieuMuon.getMaPM() + ""});
    }

    public int delete(String id) {
        return db.delete("PhieuMuon", "maPM = ?", new String[] {id});
    }
}

package com.example.anhnvph25971_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {

    private final SQLiteDatabase db;

    public ThuThuDao(Context context) {
        MyDataBase dbHelper = new MyDataBase(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<ThuThu> selectAll() {
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from ThuThu", null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTen(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);

                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<ThuThu> getData(String sql, String ...selectionArgs) {
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTen(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);

                cursor.moveToNext();
            }
        }
        return list;
    }

    public ThuThu getOne(String id) {
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from ThuThu where maTT=?", new String[]{id});
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(cursor.getString(0));
                thuThu.setHoTen(cursor.getString(1));
                thuThu.setMatKhau(cursor.getString(2));
                list.add(thuThu);

                cursor.moveToNext();
            }
        }
        return list.get(0);
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getHoTen());
        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        return db.insert("ThuThu", null, values);
    }

    public int update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        return db.update("ThuThu", values, "maTT = ?", new String[] {thuThu.getMaTT()});
    }

    public int checkLogin(String id, String pass){
        String sql = "select * from ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> listtt  = getData(sql, id,pass);
        if (listtt.size() == 0){ // nếu k có user và pass
            return  -1;
        }
        return 1;
    }


}

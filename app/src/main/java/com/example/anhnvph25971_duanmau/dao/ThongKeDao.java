package com.example.anhnvph25971_duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.anhnvph25971_duanmau.database.MyDataBase;
import com.example.anhnvph25971_duanmau.object.Sach;
import com.example.anhnvph25971_duanmau.object.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {
    private final SQLiteDatabase db;
    private final Context context;
    private final SachDao sachDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDao (Context context){
        this.context = context;
        sachDao = new SachDao(context);
        MyDataBase myDataBase = new MyDataBase(context);
        db = myDataBase.getWritableDatabase();
    }

    // thông kê top 10
    public List<Top> getTop(){
        ArrayList<Top> list = new ArrayList<>();
        String sql = "SELECT maSach, count(maSach) as SoLuong FROM PhieuMuon GROUP BY maSach ORDER BY SoLuong DESC LIMIT 10";

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst()){
            while (!c.isAfterLast()){
                Top top = new Top();
                Sach sach = sachDao.getOne(c.getInt(0)+"");
                top.setTenSach_top(sach.getTenSach());
                top.setSoLuong_top(c.getInt(1));
                list.add(top);
                c.moveToNext();
            }
        }
        return list;
    }
}

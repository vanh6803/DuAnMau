package com.example.anhnvph25971_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper {

    static final String db_name = "PNLib";
    static final int db_version = 3;

    public MyDataBase(Context context){
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE ThuThu(" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE LoaiSach(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE ThanhVien(" +
                "maTv INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenTv TEXT NOT NULL," +
                "gioiTinh TEXT NOT NULL, " +
                "namSinh INTEGER NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(sql);

        sql = "CREATE TABLE PhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES ThuThu(maTT), " +
                "maTV INTEGER REFERENCES ThanhVien(maTv), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngayThue TEXT NOT NULL, " +
                "traSach INTEGER NOT NULL)";
        db.execSQL(sql);

        sql = "INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES ('admin', 'Admin', 'admin')";
        db.execSQL(sql);
        sql = "INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES ('vanh', 'Việt Anh', 'vanh123')";
        db.execSQL(sql);


        sql = "INSERT INTO LoaiSach(maLoai, tenLoai) VALUES  (1, 'CNTT')";
        db.execSQL(sql);
        sql = "INSERT INTO LoaiSach(maLoai, tenLoai) VALUES  (2, 'Hành động')";
        db.execSQL(sql);


        sql = "INSERT INTO ThanhVien(maTv, tenTv, gioiTinh, namSinh) " +
                "VALUES  (1, 'Nguyễn Văn A', 'Nam', 2003)";
        db.execSQL(sql);
        sql = "INSERT INTO ThanhVien(maTv, tenTv, gioiTinh, namSinh) " +
                "VALUES  (2, 'Trần Thị B', 'Nữ', 2001)";
        db.execSQL(sql);


        sql = "INSERT INTO Sach(maSach, tenSach, giaThue, maLoai) " +
                "VALUES (1, 'Lập trình Java', 20000, 1)";
        db.execSQL(sql);

        sql = "INSERT INTO Sach(maSach, tenSach, giaThue, maLoai) " +
                "VALUES (2, 'Tru tiên', 12000, 2)";
        db.execSQL(sql);


        sql = "INSERT INTO PhieuMuon(maPM, maTT, maTV, maSach, tienThue, ngayThue, traSach) " +
                "VALUES (1, 'admin', 1, 1, 20000, '2022-10-9', 1)";
        db.execSQL(sql);

        sql = "INSERT INTO PhieuMuon(maPM, maTT, maTV, maSach, tienThue, ngayThue, traSach) " +
                "VALUES (2, 'vanh', 2, 2, 12000, '2022-10-10', 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ThuThu";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS LoaiSach";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS ThanhVien";
        db.execSQL(sql);

        sql = "DROP TABLE IF EXISTS Sach";
        db.execSQL(sql);

        onCreate(db);
    }
}

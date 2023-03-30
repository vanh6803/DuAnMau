package com.example.anhnvph25971_duanmau.object;

public class ThanhVien {
    private int maTv;
    private String tenTv;
    private String gioiTinh;
    private int namSinh;

    public ThanhVien(int maTv, String tenTv, String gioiTinh, int namSinh) {
        this.maTv = maTv;
        this.tenTv = tenTv;
        this.gioiTinh = gioiTinh;
        this.namSinh = namSinh;
    }

    public ThanhVien() {
    }

    public int getMaTv() {
        return maTv;
    }

    public void setMaTv(int maTv) {
        this.maTv = maTv;
    }

    public String getTenTv() {
        return tenTv;
    }

    public void setTenTv(String tenTv) {
        this.tenTv = tenTv;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }
}

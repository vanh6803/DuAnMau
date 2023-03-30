package com.example.anhnvph25971_duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.anhnvph25971_duanmau.dao.ThuThuDao;
import com.example.anhnvph25971_duanmau.fragment.Fragment_DoanhThu;
import com.example.anhnvph25971_duanmau.fragment.Fragment_DoiMatKhau;
import com.example.anhnvph25971_duanmau.fragment.Fragment_QuanLyPhieuMuon;
import com.example.anhnvph25971_duanmau.fragment.Fragment_QuanLySach;
import com.example.anhnvph25971_duanmau.fragment.Fragment_QuanLyThanhVien;
import com.example.anhnvph25971_duanmau.fragment.Fragment_QuanlyLoaiSach;
import com.example.anhnvph25971_duanmau.fragment.Fragment_ThemNguoiDung;
import com.example.anhnvph25971_duanmau.fragment.Fragment_Top10SachMuon;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout ;
    Toolbar toolbar;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ThuThuDao dao;
    String admin;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.id_drawerlayout);
        toolbar  = findViewById(R.id.id_toolbar);
        navigationView = findViewById(R.id.id_nav);
        frameLayout = findViewById(R.id.id_framelayout);

        dao = new ThuThuDao(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(Fragment_QuanLyPhieuMuon.newInstance());
        toolbar.setTitle("Quản lý phiếu mượn");

        intent = getIntent();

        admin = intent.getStringExtra("admin");

        // quyền của admin và user
        navigationView.getMenu().findItem(R.id.item_themnguoidung).setVisible(admin.equalsIgnoreCase("admin"));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id  == R.id.item_quanlyphieumuon){
            replaceFragment(Fragment_QuanLyPhieuMuon.newInstance());
            toolbar.setTitle("Quản lý phiếu mượn");
        }else if (id  == R.id.item_quanlysach){
            replaceFragment(Fragment_QuanLySach.newInstance());
            toolbar.setTitle("Quản lý sách");
        }else if (id  == R.id.item_quanlyloaisach){
            replaceFragment(Fragment_QuanlyLoaiSach.newInstance());
            toolbar.setTitle("Quản lý loại sách");
        }else if (id  == R.id.item_quanlythanhvien){
            replaceFragment(Fragment_QuanLyThanhVien.newInstance());
            toolbar.setTitle("Quản lý thành viên");
        }else if (id  == R.id.item_top10sach){
            replaceFragment(Fragment_Top10SachMuon.newInstance());
            toolbar.setTitle("Top 10 sách mượn nhiều nhất");
        }else if (id  == R.id.item_doanhthu){
            replaceFragment(Fragment_DoanhThu.newInstance());
            toolbar.setTitle("Doanh thu");
        }else if (id  == R.id.item_themnguoidung){
            replaceFragment(Fragment_ThemNguoiDung.newInstance());
            toolbar.setTitle("Thêm người dùng");
        }else if (id  == R.id.item_doimatkhau){
            replaceFragment(Fragment_DoiMatKhau.newInstance());
            toolbar.setTitle("Đổi mật khẩu");
        }else if (id  == R.id.item_dangxuat){
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    // nut back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (drawerLayout.isDrawerOpen(navigationView)){
                drawerLayout.closeDrawer(navigationView);
            }else {
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    // thay thế framelayout bằng fragment tương ứng khi chọn chức năng
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_framelayout, fragment);
        transaction.commit();
    }
}
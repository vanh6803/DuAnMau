package com.example.anhnvph25971_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.dao.ThuThuDao;
import com.example.anhnvph25971_duanmau.object.ThuThu;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layout_chao;
    private Button btn_login;
    private EditText ed_userName, ed_passWord;
    private TextView tv_errorUserName, tv_errorPass;
    private CheckBox chk_remember;
    private SharedPreferences preferences;
    private ThuThuDao dao;
    private String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ
        layout_chao = findViewById(R.id.id_chao);
        btn_login = findViewById(R.id.btn_login);
        ed_userName = findViewById(R.id.ed_login_username);
        ed_passWord = findViewById(R.id.ed_login_password);
        tv_errorUserName = findViewById(R.id.tv_bug_login_user);
        tv_errorPass = findViewById(R.id.tv_bug_login_pass);
        chk_remember = findViewById(R.id.check_nho_pass);

        // màn hình chào
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout_chao.setVisibility(View.INVISIBLE);
            }
        }, 2500);
        
        clearError();

        dao = new ThuThuDao(this);

        // đọc user, pass trong SharedPreferences
        preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        ed_userName.setText(preferences.getString("USERNAME",""));
        ed_passWord.setText(preferences.getString("PASS",""));
        chk_remember.setChecked(preferences.getBoolean("REMEMBER",false));


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    // check trống
    private boolean checkValidate(){
        clearError();
        String user = ed_userName.getText().toString();
        String password = ed_passWord.getText().toString();
        if (user.isEmpty()){
            tv_errorUserName.setText("bạn chưa nhập tài khoản");
            return false;
        }else if (password.isEmpty()){
            tv_errorPass.setText("bạn chưa nhập mật khẩu");
            return false;
        }
        return true;
    }

    // xóa form
    private void clearForm(){
        ed_userName.setText("");
        ed_passWord.setText("");
    }

    //xóa lỗi
    private void clearError(){
        tv_errorUserName.setText("");
        tv_errorPass.setText("");
    }

    // check login và đăng nhập
    private void checkLogin(){
        String username  = ed_userName.getText().toString();
        String pass  = ed_passWord.getText().toString();

        admin = username;

        if (checkValidate()){
            if (dao.checkLogin(username, pass) > 0){
                Toast.makeText(this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                remember(username, pass, chk_remember.isChecked());
                Intent intentLogin = new Intent(MainActivity.this, HomeActivity.class);
                intentLogin.putExtra("admin", admin);
                startActivity(intentLogin);
            }else{
                tv_errorUserName.setText("tên đăng nhập hoặc mật khẩu không đúng");
                tv_errorPass.setText("tên đăng nhập hoặc mật khẩu không đúng");
                Toast.makeText(this, "đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // nhớ mật khẩu
    private void remember(String u, String p, boolean status){
        preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!status){
            editor.clear();
        }else {
            // lưu dữ liệu
            editor.putString("USERNAME",u);
            editor.putString("PASS",p);
            editor.putBoolean("REMEMBER",status);
        }
        // lưu eidt
        editor.commit();
    }
}
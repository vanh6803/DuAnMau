package com.example.anhnvph25971_duanmau.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnvph25971_duanmau.R;
import com.example.anhnvph25971_duanmau.dao.ThuThuDao;
import com.example.anhnvph25971_duanmau.object.ThuThu;


public class Fragment_ThemNguoiDung extends Fragment {
    private EditText edusername, edname, edpass, edrepass;
    private TextView tverrorUserName, tvErrorName, tvErrorpass, tvErrorrepass;
    private Button save, cancel;
    private ThuThuDao dao;

    public Fragment_ThemNguoiDung() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_ThemNguoiDung newInstance() {
        Fragment_ThemNguoiDung fragment = new Fragment_ThemNguoiDung();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__them_nguoi_dung, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edusername = view.findViewById(R.id.ed_add_user);
        edname = view.findViewById(R.id.ed_add_name);
        edpass = view.findViewById(R.id.ed_add_pass);
        edrepass = view.findViewById(R.id.ed_add_re_pass);
        tverrorUserName = view.findViewById(R.id.tv_bug_add_user);
        tvErrorName = view.findViewById(R.id.tv_bug_add_name);
        tvErrorpass = view.findViewById(R.id.tv_bug_add_pass);
        tvErrorrepass = view.findViewById(R.id.tv_bug_add_re_pass);
        save = view.findViewById(R.id.btn_save);
        cancel = view.findViewById(R.id.btn_cancel_tk);

        clearBug();

        dao = new ThuThuDao(getContext());
         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 clearForm();
             }
         });

         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ThuThu thuThu = new ThuThu();
                 thuThu.setMaTT(edusername.getText().toString().trim());
                 thuThu.setHoTen(edname.getText().toString().trim());
                 thuThu.setMatKhau(edpass.getText().toString().trim());
                 if (checkValidate()){
                     dao.insert(thuThu);
                     Toast.makeText(getActivity(), "tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                     clearForm();
                 }
             }
         });

    }

    private boolean checkValidate(){
        clearBug();
        if (edusername.getText().toString().isEmpty()){
            tverrorUserName.setText("nhập tên đăng nhập");
            return false;
        }
        if (edname.getText().toString().isEmpty()){
            tvErrorName.setText("nhập họ tên");
            return false;
        }
        if (edpass.getText().toString().isEmpty()){
            tvErrorpass.setText("nhập password");
            return false;
        }
        if (edpass.getText().toString().length()< 8){
            tvErrorpass.setText("password từ 8 ký tự trở lên");
            return false;
        }
        if (!edpass.getText().toString().equals(edrepass.getText().toString())){
            tvErrorrepass.setText("mật khẩu không khớp");
            return false;
        }
        return true;
    }

    private void clearForm(){
        edusername.setText("");
        edname.setText("");
        edpass.setText("");
        edrepass.setText("");
    }

    private void clearBug(){
        tverrorUserName.setText("");
        tvErrorName.setText("");
        tvErrorpass.setText("");
        tvErrorrepass.setText("");
    }
}
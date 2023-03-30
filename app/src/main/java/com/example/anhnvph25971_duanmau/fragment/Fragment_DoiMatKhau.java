package com.example.anhnvph25971_duanmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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


public class Fragment_DoiMatKhau extends Fragment {

    private ThuThuDao dao;
    private SharedPreferences preferences;

    private EditText ed_matKhauCu, ed_matKhaumoi, ed_nhapLaimMatKhauMoi;
    private TextView error_MkCu, error_MkMoi, error_NhapLaiMKMoi;
    private Button cancel, save;

    public Fragment_DoiMatKhau() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_DoiMatKhau newInstance() {
        Fragment_DoiMatKhau fragment = new Fragment_DoiMatKhau();
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
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    //viết code ở đây
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ánh xạ
        ed_matKhauCu = view.findViewById(R.id.ed_doimk_matkhaucu);
        ed_matKhaumoi = view.findViewById(R.id.ed_doimk_matkhaumoi);
        ed_nhapLaimMatKhauMoi = view.findViewById(R.id.ed_doimk_nhaplaimatkhau);
        error_MkCu = view.findViewById(R.id.tv_error_passcu);
        error_MkMoi = view.findViewById(R.id.tv_error_passnew);
        error_NhapLaiMKMoi = view.findViewById(R.id.tv_error_repassnew);
        cancel = view.findViewById(R.id.btn_doimk_cancel);
        save = view.findViewById(R.id.btn_doimk_save);

        clearError();

        preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        dao = new ThuThuDao(getContext());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
                clearError();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = preferences.getString("USERNAME", null);
                ThuThu thuThu = dao.getOne(username);
                thuThu.setMatKhau(ed_matKhaumoi.getText().toString().trim());
                if (check_validate() == true){
                    dao.update(thuThu);
                    Toast.makeText(getActivity(), "đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    clearForm();
                    clearError();
                }
            }
        });

    }

    public boolean check_validate(){

        String str_mkCu = ed_matKhauCu.getText().toString();
        String str_mkmoi = ed_matKhaumoi.getText().toString();
        String str_mkmoi_nhaplai = ed_nhapLaimMatKhauMoi.getText().toString();
        String str_passCu = preferences.getString("PASS", null);

        if (str_mkCu.isEmpty()){
            error_MkCu.setText("chưa nhập mật khẩu cũ");
            return false;
        }else if (!str_passCu.equals(str_mkCu)) {
            error_MkCu.setText("Nhập sai mật khẩu cũ");
            return false;
        }else {
            error_MkCu.setText("");
        }

        if (str_mkmoi.isEmpty()) {
            error_MkMoi.setText("chưa nhập mật khẩu mới");
            return false;
        } else if (str_mkmoi.equals(str_mkCu)) {
            error_MkMoi.setText("Mật khẩu mới không được trùng mật khẩu cũ");
            return false;
        }else {
            error_MkMoi.setText("");
        }

        if (str_mkmoi_nhaplai.isEmpty()) {
            error_NhapLaiMKMoi.setText("chưa nhập mật khẩu");
            return false;
        }else if (!str_mkmoi_nhaplai.equals(str_mkmoi)) {
            error_NhapLaiMKMoi.setText("Mật khẩu không khớp");
            return false;
        }
        return true;
    }

    private void clearForm(){
        ed_matKhauCu.setText("");
        ed_matKhaumoi.setText("");
        ed_nhapLaimMatKhauMoi.setText("");
    }

    private void clearError(){
        error_MkCu.setText("");
        error_MkMoi.setText("");
        error_NhapLaiMKMoi.setText("");
    }
}
package com.example.luanvanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClickListviewActivity extends AppCompatActivity {
    TextView LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten;
    Button Send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_listview);
        //Set up Textview
        LV_Ma = findViewById(R.id.Lv_Ma);
        LV_Ten = findViewById(R.id.LV_Ten);
        LV_TenTiengAnh = findViewById(R.id.LV_TenTiengAnh);
        SV1_Ten = findViewById(R.id.SV1_Ten);
        MSSV1 = findViewById(R.id.MSSV1);
        SV2_Ten = findViewById(R.id.SV2_Ten);
        MSSV2 = findViewById(R.id.MSSV2);
        GV1_Ten = findViewById(R.id.GV1_Ten);
        GV2_Ten = findViewById(R.id.GV2_Ten);
        Send = findViewById(R.id.btn_send);

        Traluanvandb data;
        data = new Traluanvandb(this);
        //get data from listview onClickListner
        String id = getIntent().getStringExtra(" LV_Ma ");
        data.openDataBase();
        LuanvanModel luanvan = data.getLuanVan(id);

        //Show data
        if (luanvan != null) {
            String Ma_LV = Integer.toString(luanvan.getLV_Ma());
            LV_Ma.setText("Mã luận văn :" + Ma_LV);
            LV_Ten.setText("Tên luận văn :" + luanvan.getLV_Ten());
            LV_TenTiengAnh.setText("Tên luận văn tiếng anh :" + luanvan.getLV_TenTiengAnh());
            SV1_Ten.setText("Tên Sinh viên 1 :" + luanvan.getSV1_Ten());
            MSSV1.setText("MSSV :" + luanvan.getMSSV1());
            SV2_Ten.setText("Tên Sinh viên 2 :" + luanvan.getSV2_Ten());
            MSSV2.setText("MSSV :" + luanvan.getMSSV2());
            GV1_Ten.setText("Giảng vien hướng dẫn :" + luanvan.getGV1_Ten());
            GV2_Ten.setText("Giảng vien hướng dẫn :" + luanvan.getGV2_Ten());
        }
        //Set up to post data
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClickListviewActivity.this, com.example.luanvanapp.Form_thong_tin.class);
                intent.putExtra(" Muon ",id);
                startActivity(intent);
            }
        });
    }


}
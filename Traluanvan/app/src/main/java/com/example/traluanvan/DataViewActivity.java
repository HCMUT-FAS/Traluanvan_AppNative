package com.example.traluanvan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class DataViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
                TextView LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten;
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

                Traluanvandb data;
                data = new Traluanvandb(this);
                //get data from listview onClickListner
                String id = getIntent().getStringExtra(" LV_Ma ");
                data.openDataBase();
                ViewInterface luanvan= data.getLuanVan(id);

                //Show data
                if (luanvan != null) {
                    String Ma_LV = Integer.toString(luanvan.getLV_Ma());
                    LV_Ma.setText("Mã luận văn :"+ Ma_LV);
                    LV_Ten.setText("Tên luận văn :"+luanvan.getLV_Ten());
                    LV_TenTiengAnh.setText("Tên luận văn tiếng anh :"+luanvan.getLV_TenTiengAnh());
                    SV1_Ten.setText("Tên Sinh viên 1 :"+luanvan.getSV1_Ten());
                    MSSV1.setText("MSSV :"+luanvan.getMSSV1());
                    SV2_Ten.setText("Tên Sinh viên 2 :"+luanvan.getSV2_Ten());
                    MSSV2.setText("MSSV :"+luanvan.getMSSV2());
                    GV1_Ten.setText("Giảng vien hướng dẫn :"+luanvan.getGV1_Ten());
                    GV2_Ten.setText("Giảng vien hướng dẫn :"+luanvan.getGV2_Ten());
                }


            }
        }


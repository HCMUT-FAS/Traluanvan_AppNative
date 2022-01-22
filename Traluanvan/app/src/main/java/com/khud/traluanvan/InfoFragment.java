package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InfoFragment extends Fragment {
    String LV_Ma;
    //Set layout
    TextView tenluanvan,
            tensinhvien_1,
            tensinhvien_2,
            tenluanvan_tienganh,
            masinhvien_1,
            masinhvien_2,
            maluanvan,
            gvhuongdan_1,
            gvhuongdan_2;
    //
    Traluanvandb data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_infoluanvan, container, false);
        //Set id
        tenluanvan = view.findViewById(R.id.tenluanvan);
        maluanvan = view.findViewById(R.id.maluanvan);
        gvhuongdan_1 = view.findViewById(R.id.tengiangvien1);
        gvhuongdan_2=view.findViewById(R.id.tengiangvien2);
        tenluanvan_tienganh=view.findViewById(R.id.tenluanvantienganh);
        tensinhvien_1 = view.findViewById(R.id.tensinhvien1);
        masinhvien_1=view.findViewById(R.id.masosinhvien1);
        tensinhvien_2=view.findViewById(R.id.tensinhvien2);
        masinhvien_2=view.findViewById(R.id.masosinhvien2);
        //Get LV_Ma
        Context context=getActivity();
        data=new Traluanvandb(context);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Start
        LV_Ma = getArguments().getString("LV_Ma");

        int viewtype=getArguments().getInt("view_type");
        data.openDataBase();
        LuanvanModel Info = data.getLuanVan(LV_Ma);
        if (Info != null) {
            tenluanvan.setText(Info.getLV_Ten());
            maluanvan.setText(Integer.toString(Info.getLV_Ma()));
            tensinhvien_1.setText(Info.getSV1_Ten());
            masinhvien_1.setText(Info.getMSSV1());

            gvhuongdan_1.setText(Info.getGV1_Ten());
            switch (viewtype) {
                case 2:
                    gvhuongdan_2.setText(Info.getGV2_Ten());
                    break;
                case 3:
                    tensinhvien_2.setText(Info.getSV2_Ten());
                    masinhvien_2.setText(Info.getMSSV2());
                    break;
                case 4:
                    gvhuongdan_2.setText(Info.getGV2_Ten());
                    tensinhvien_2.setText(Info.getSV2_Ten());
                    masinhvien_2.setText(Info.getMSSV2());
                    break;
            }
            if (!Info.getLV_TenTiengAnh().matches("0")){
                tenluanvan_tienganh.setText(Info.getLV_TenTiengAnh());
            }
        }

    }
}
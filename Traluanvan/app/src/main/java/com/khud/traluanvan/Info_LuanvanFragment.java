package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.khud.traluanvan.User.UserViewModel;
import com.khud.traluanvan.databinding.FragmentInfoluanvanBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Info_LuanvanFragment extends Fragment {
    String LV_Ma;
    //Set layout
    TextView tenluanvan,
            tensinhvien_1,
            tensinhvien_2,
//            tenluanvan_tienganh,
            maluanvan,
            gvhuongdan_1,
            gvhuongdan_2;
    //
    Traluanvandb data;
    UserViewModel userViewModel;
    FragmentInfoluanvanBinding InfoluanvanBinding;
    Context mcontext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        InfoluanvanBinding= FragmentInfoluanvanBinding.inflate(inflater, container, false);
        View view=InfoluanvanBinding.getRoot();
        //Set id
        tenluanvan = InfoluanvanBinding.tenluanvan;
        maluanvan = InfoluanvanBinding.maluanvan;
        gvhuongdan_1 = InfoluanvanBinding.tengiangvien1;
        gvhuongdan_2=InfoluanvanBinding.tengiangvien2;
        tensinhvien_1 = InfoluanvanBinding.tensinhvien1;
        tensinhvien_2=InfoluanvanBinding.tensinhvien2;
        //Get LV_Ma
        Context context=getActivity();
        data=new Traluanvandb(context);
        userViewModel=new ViewModelProvider(getActivity()).get(UserViewModel.class);
        mcontext=getActivity();
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
            gvhuongdan_1.setText(Info.getGV1_Ten());
            switch (viewtype) {
                case 2:
                    gvhuongdan_2.setText(Info.getGV2_Ten());
                    break;
                case 3:
                    tensinhvien_2.setText(Info.getSV2_Ten());

                    break;
                case 4:
                    gvhuongdan_2.setText(Info.getGV2_Ten());
                    tensinhvien_2.setText(Info.getSV2_Ten());
                    break;
            }
//            if (!Info.getLV_TenTiengAnh().matches("0")){
//                tenluanvan_tienganh.setText(Info.getLV_TenTiengAnh());
//            }
        }
        if(CheckLoginState()){
            InfoluanvanBinding.LoginCheck.setText("");
        }
        Button muon=InfoluanvanBinding.datebutton;
        muon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckLoginState()){
                    FragmentManager fragmentManager=((MainActivity)mcontext).getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_container, new DateFragment(),"Date").addToBackStack("Date");
                    transaction.commit();
                }
                else{
                    Toast.makeText(mcontext,"Vui lòng đăng nhập để sử dụng tính năng này",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    Boolean CheckLoginState() {
        if (userViewModel.getdata() != null){
            return true;
    }
        return false;
    }
}
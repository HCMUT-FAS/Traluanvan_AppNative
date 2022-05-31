package com.khud.traluanvan.Server;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.khud.traluanvan.User.UserInfoModel;
import com.khud.traluanvan.User.UserViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerController {

    public List<UserInfoModel> FetchUser( JSONObject respone){
         String email;
         int id;
         String name="";
         String email_verified_at;
         String phone;
         String role_id="";
         String created_at;
         String update_at;
         List<UserInfoModel> userdata=new ArrayList<>();
        try {
            email=respone.getString("email");
            id=respone.getInt("id");
            name=respone.getString("name");
            email_verified_at=respone.getString("email_verified_at");
            phone=respone.getString("phone");
            role_id=respone.getString("role_id");
            created_at=respone.getString("created_at");
            update_at=respone.getString("updated_at");
            UserInfoModel userInfoModel =new UserInfoModel(email,id,name,email_verified_at,phone,role_id,created_at,update_at);
            userdata.add(userInfoModel);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    return userdata;
    }
    public void FetchLuanvandata(){

    }

}

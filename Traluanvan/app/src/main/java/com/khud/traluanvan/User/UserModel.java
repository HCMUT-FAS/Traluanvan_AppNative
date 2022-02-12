package com.khud.traluanvan.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.khud.traluanvan.R;


public class UserModel {
    private final String email;
    private final String password;
    private int id;
    private String name;
    private String email_verified_at;
    private String phone;
    private String role_id;
    private String created_at;
    private String update_at;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //Only set

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
    private void SaveUser(Context mcontext,String email,String password){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(mcontext.getString(R.string.SharedPreference),mcontext.MODE_PRIVATE);
        SharedPreferences.Editor user_info=sharedPreferences.edit();
        user_info.putString(mcontext.getString(R.string.User_email),email);
        user_info.putString(mcontext.getString(R.string.User_password),password);
    }
}

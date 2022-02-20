package com.khud.traluanvan.User;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.khud.traluanvan.R;


public class UserInfoModel  {
    private String email;
    private int id;
    private String name;
    private String email_verified_at;
    private String phone;
    private String role_id;
    private String created_at;
    private String update_at;
    public UserInfoModel(String email, int id, String name, String email_verified_at, String phone, String role_id, String created_at, String update_at) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.email_verified_at = email_verified_at;
        this.phone = phone;
        this.role_id = role_id;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

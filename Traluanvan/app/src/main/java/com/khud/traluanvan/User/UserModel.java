package com.khud.traluanvan.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.khud.traluanvan.R;

public class UserModel {
    private final String email;
    private final String password;

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public void SaveUser(Context mcontext, String email, String password){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(mcontext.getString(R.string.SharedPreference),mcontext.MODE_PRIVATE);
        SharedPreferences.Editor user_info=sharedPreferences.edit();
        user_info.putString(mcontext.getString(R.string.User_email),email);
        user_info.putString(mcontext.getString(R.string.User_password),password);
    }
}

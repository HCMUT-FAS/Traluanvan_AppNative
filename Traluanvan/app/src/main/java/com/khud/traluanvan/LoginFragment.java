package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.khud.traluanvan.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding loginBinding;
    TextInputEditText email_input,password_input;
    ConnectServer connectServer;
    Context mcontext;
    Button login;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = loginBinding.getRoot();
        connectServer=new ConnectServer();
        mcontext=getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email_input=loginBinding.loginMailInputEdittext;
        password_input=loginBinding.loginPasswordInputEdittext;
        login= loginBinding.loginbutton;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogin();
            }
        });
    }
    private void setLogin(){
        final String email = email_input.getText().toString().trim();
        final String password = password_input.getText().toString();
        connectServer.Login(mcontext,email,password);
    }
}

package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khud.traluanvan.databinding.FragmentDateBinding;
import com.khud.traluanvan.databinding.FragmentMenuBinding;


public class MenuFragment extends Fragment {
    ConnectServer connectServer;
    FragmentMenuBinding MenuBinding;
    EditText editTextUsername, editTextEmail, editTextPassword,editphone;
    TextView error;
    Button signup;
    Context mcontext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MenuBinding = FragmentMenuBinding.inflate(inflater,container,false);
        View view = MenuBinding.getRoot();
        connectServer=new ConnectServer();
        mcontext=getActivity();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextUsername = MenuBinding.editTextUsername;
        editTextEmail = MenuBinding.editTextEmail;
        editphone= MenuBinding.editphone;
        editTextPassword = MenuBinding.editTextPassword;
        signup=MenuBinding.buttonRegister;
        error=MenuBinding.textViewLogin;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"HI",Toast.LENGTH_SHORT).show();
                regrister();
            }
        });

    }
    private void regrister(){
        final String username = editTextUsername.getText().toString();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString();
        final String phone = editphone.getText().toString();
        connectServer.Signup(mcontext,username,email,phone,password,error);
    }
}
package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.khud.traluanvan.Server.ServerModel;
import com.khud.traluanvan.User.UserViewModel;
import com.khud.traluanvan.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding loginBinding;
    TextInputEditText email_input,password_input;
    ServerModel connectServer;
    Context mcontext;
    Button login;
    UserViewModel userViewModel;
    FragmentManager fragmentManager ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginBinding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = loginBinding.getRoot();
        userViewModel=new ViewModelProvider(getActivity()).get(UserViewModel.class);

        mcontext=getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email_input=loginBinding.loginMailInputEdittext;
        password_input=loginBinding.loginPasswordInputEdittext;
        login= loginBinding.loginbutton;
        fragmentManager= ((MainActivity)mcontext).getSupportFragmentManager();
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
        userViewModel.setLogin(mcontext,email,password);
        UpdateView();

    }
    private void UpdateView(){
        userViewModel.getLoginState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean state) {
                fragmentManager.popBackStack("Date",1);
                if(state){
                    Toast.makeText(mcontext,"Login Sucessfull",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(mcontext, "Login Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

package com.khud.traluanvan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.khud.traluanvan.Server.ServerModel;
import com.khud.traluanvan.User.UserViewModel;
import com.khud.traluanvan.databinding.FragmentMenuBinding;


public class MenuFragment extends Fragment {
    ServerModel connectServer;
    FragmentMenuBinding MenuBinding;
    EditText editTextUsername, editTextEmail, editTextPassword, editphone;
    TextView error;
    Button signup;
    Context mcontext;
    UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MenuBinding = FragmentMenuBinding.inflate(inflater, container, false);
        View view = MenuBinding.getRoot();

        mcontext = getActivity();
        userViewModel =  new ViewModelProvider(getActivity()).get(UserViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextUsername = MenuBinding.editTextUsername;
        editTextEmail = MenuBinding.editTextEmail;
        editphone = MenuBinding.editphone;
        editTextPassword = MenuBinding.editTextPassword;
        signup = MenuBinding.buttonRegister;
        error = MenuBinding.textViewLogin;
        registerLiveDataListenner();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText(userViewModel.getdata().get(0).toString());

            }
        });

    }

    public void registerLiveDataListenner() {
        userViewModel.getLoginState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean state) {
                if (state) {
                    error.setText("Something change");
                }
            }
        });
    }
}
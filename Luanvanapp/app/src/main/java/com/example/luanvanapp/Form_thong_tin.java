package com.example.luanvanapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;

import com.google.android.gms.tasks.Task;
public class Form_thong_tin extends AppCompatActivity implements
        View.OnClickListener  {

    EditText tsv,msv,mlv,sdt;
    TextView email;
    Button Gui;
    DatePicker picker;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SignInGoogle signInGoogle = new SignInGoogle();
    ConnectServer connectServer = new ConnectServer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_thong_tin);
        String id = getIntent().getStringExtra(" Muon ");
        //Set Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("510788274022-dspqaj72j8trg5v5v568j9cl6ur4psun.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END configure_signin]
        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(Form_thong_tin.this, gso);
        // [END build_client]

        //Set id
        email = findViewById(R.id.email);
        tsv = findViewById(R.id.tsv);
        msv =findViewById(R.id.msv);
        mlv=findViewById(R.id.mlv);
        sdt=findViewById(R.id.sdt);
        picker= (DatePicker)findViewById(R.id.date);
        Gui =findViewById(R.id.btn_senddata);
        mlv.setText(id);
        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        findViewById(R.id.btn_senddata).setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        signInGoogle.From_onstart(Form_thong_tin.this,account,email);
    }
    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInGoogle.handleSignInResult(task,account,Form_thong_tin.this,email);
        }
    }
    // [END onActivityResult]
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signInGoogle.signIn(Form_thong_tin.this,mGoogleSignInClient,RC_SIGN_IN,email);
                break;
            case R.id.sign_out_button:
                signInGoogle.signOut(Form_thong_tin.this,mGoogleSignInClient,email);

                break;
            case R.id.btn_senddata:
            try {
                boolean CheckEmail = signInGoogle.CheckEmail(email);
                if (CheckEmail == true) {
                    String date = Getdate();
                    //Code for check string request
                    //Toast.makeText(Form_thong_tin.this,mlv.getText().toString(),Toast.LENGTH_SHORT).show();

                    //Send request to Server
                    connectServer.SendDataToServer(Form_thong_tin.this, email.getText().toString(), tsv.getText().toString(), msv.getText().toString(), mlv.getText().toString(), sdt.getText().toString(), date);
                    //signInGoogle.signOut(Form_thong_tin.this,mGoogleSignInClient,email);
                }
                else {
                Toast.makeText(Form_thong_tin.this,"Bạn không được xài tính năng này",Toast.LENGTH_SHORT).show();
                    signInGoogle.signOut(Form_thong_tin.this,mGoogleSignInClient,email);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(Form_thong_tin.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }
    private String Getdate(){
        int year = picker.getYear();
        int monthn = picker.getMonth()+1;
        int day = picker.getDayOfMonth();
        String date = (year + "-" );
        if (monthn < 10){
           date = (date  + "0"+ monthn + "-" );
        }
        else {
            date = (date  + monthn + "-" );
        }
        if (day < 10){
            date = (date +"0"+ day);
        }
        else{
            date = (date + day);
        }
    return date;
    }
}
package com.example.luanvanapp;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SignInGoogle {

    public void From_onstart(Context context, GoogleSignInAccount account,TextView textview){

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(context);
        updateUI(account,context,textview);
        // [END on_start_sign_in]

    }
    // [START handleSignInResult]
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask, GoogleSignInAccount account,Context context,TextView textview) {
        try {
             account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account,context,textview);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(context, "signInResult:failed code=" + e.getStatusCode(),Toast.LENGTH_SHORT).show();

        }
    }
    // [END handleSignInResult]
    // [START signIn]
    public void signIn(Activity activity, GoogleSignInClient mGoogleSignInClient, int RC_SIGN_IN, TextView textview) {
        try {

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent,RC_SIGN_IN);
        }
        catch (Exception e){
            textview.setText(e.toString());

        }
    }
    // [END signIn]

    // [START signOut]
    public void  signOut(Activity activity,GoogleSignInClient mGoogleSignInClient,TextView textview) {
        try {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener((Activity) activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            updateUI(null, activity,textview);
                            // [END_EXCLUDE]
                        }
                    });
        }
        catch (Exception e){
            textview.setText(e.toString());
        }
    }

    // [END signOut]

    // [START revokeAccess]
    public void revokeAccess(Activity activity,GoogleSignInClient mGoogleSignInClient,TextView textview) {

        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener((Activity) activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]

                        updateUI(null,activity,textview);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    public void updateUI(@Nullable GoogleSignInAccount account,Context context,TextView textview) {
        if (account != null) {
            try {
                String Emailsercurity[] = account.getEmail().split("@");

                if (Emailsercurity[1].matches("hcmut.edu.vn")) {
                    textview.setText(account.getEmail());
                }
                else {
                    Toast.makeText(context,"Vui lòng sử dụng email của Trường Đại học Bách Khoa TpHCM để sử dụng tính năng này",Toast.LENGTH_SHORT).show();
                    textview.setText(account.getEmail());
                }
            } catch (Exception e){
                textview.setText(e.toString());
            }

        } else {
            textview.setText("Bạn chưa đăng nhập ");


        }
    }

    public boolean CheckEmail (TextView email) {
        String Emailsercurity[] = email.getText().toString().split("@");

        if (Emailsercurity[1].matches("hcmut.edu.vn")){
            return true;
        }
        else {
            return false;
        }
    }
}

package com.khud.traluanvan.User;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khud.traluanvan.Server.Listener;
import com.khud.traluanvan.Server.ServerController;
import com.khud.traluanvan.Server.ServerModel;

import org.json.JSONObject;

import java.util.List;

public class UserViewModel extends ViewModel {
   private MutableLiveData<Boolean> Loginstate=new MutableLiveData<>();
   private MutableLiveData<UserInfoModel> userInfoData;
   private List<UserInfoModel> UserData  ;
   //Set up UserViewModel

   public MutableLiveData<Boolean> getLoginState( ){
      if(Loginstate==null) {
         Loginstate.setValue(false);
      }
      return Loginstate;
   }
   public List<UserInfoModel> getdata(){

      return UserData;
   }
   public  void setLogin(Context mcontext, String e, String password){
      ServerModel serverModel=new ServerModel(new Listener() {
         @Override
         public void onDataReceived(List respone) {
            Loginstate.postValue(true);
            UserData=respone;
         }
         @Override
         public void onError(int error) {
         }
      });
      serverModel.Login(mcontext,e,password);
   }
}

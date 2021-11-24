package com.example.viewpapagernavigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConnectServer {
    public ProgressDialog dialog = null;
    //Send data
    public void SendDataToServer(Context context,String e, String tsv, String msv, String mlv,String sdt,String d ) {
        dialog = ProgressDialog.show(context, "", "Please wait...", true);
        // Creating string request with post method.
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/insert-data-form-thong-tin.php";

        serverAPIURL=serverAPIURL+"?e="+e+"&tsv="+tsv+"&msv="+msv+"&mlv="+mlv+"&sdt="+sdt+"&d="+d;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        dialog.dismiss();
                        Toast.makeText(context,ServerResponse , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        dialog.dismiss();
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) ;
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    //Get data
    //Get Available
    public  void Get_LuanvanAvailable_FromServer (Context context, int i) {
        dialog = ProgressDialog.show(context, "", "Please wait...", true);
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/select-data-available.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            dialog.dismiss();
                            //JSONObject obj = new JSONObject(ServerResponse);
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            JSONObject LuanvanObject = jsonArray.getJSONObject(i);
                            Integer LV_Ma = LuanvanObject.getInt("LV_Ma");
                            Integer Available = LuanvanObject.getInt("Available");
                            if (Available==1){
                                Toast.makeText(context, LV_Ma.toString()+": Available", Toast.LENGTH_LONG).show();

                            }
                            else{
                                Toast.makeText(context, LV_Ma.toString()+": Unavailable", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        dialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) ;
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    //Get Luanvan data
    public void Get_LuanvanFromServer (Context context, Traluanvandb database) {
        dialog = ProgressDialog.show(context, "", "Please wait...", true);
        List<LuanvanModel> Luanvan = new ArrayList<>();
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/select-data-luanvan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            dialog.dismiss();
                            //Set Json obj when have more object
                            //JSONObject obj = new JSONObject(ServerResponse);

                            //so here we are getting that json array
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject LuanvanObject = jsonArray.getJSONObject(i);
                                Integer LV_Ma = LuanvanObject.getInt("LV_Ma");
                                String LV_Ten = LuanvanObject.getString("LV_Ten");
                                String LV_TenTiengAnh =  LuanvanObject.getString("LV_TenTiengAnh");
                                String SV1_Ten = LuanvanObject.getString("SV1_Ten");
                                String MSSV1 = LuanvanObject.getString("MSSV1");
                                String SV2_Ten=  LuanvanObject.getString("SV2_Ten");
                                String MSSV2 =  LuanvanObject.getString("MSSV2");
                                String GV1_Ten = LuanvanObject.getString("GV1_Ten");
                                String GV2_Ten =  LuanvanObject.getString("GV2_Ten");
                                //Insert data to database
                                try {

                                    database.InsertData(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);

                                }
                                catch (Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                                }
                                LuanvanModel newview = new LuanvanModel(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
                                Luanvan.add(newview);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        dialog.dismiss();

                        // Showing error message if something goes wrong.
                        dialog.dismiss();
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) ;
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}


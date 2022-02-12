package com.khud.traluanvan;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectServer {
    //Send data
    public void SendDataToServer(Context mcontext,String e, String tsv, String msv, String mlv,String sdt,String d ) {
        // Creating string request with post method.
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/insert-data-form-thong-tin.php";
        serverAPIURL=serverAPIURL+"?e="+e+"&tsv="+tsv+"&msv="+msv+"&mlv="+mlv+"&sdt="+sdt+"&d="+d;
//        Toast.makeText(context,serverAPIURL,Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing Echo Response Message Coming From Server.

                        Toast.makeText(mcontext,ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing error message if something goes wrong.

                        Toast.makeText(mcontext, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) ;
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    //Sign up
    public void Login(Context mcontext, String e, String password) {
        // Creating string request with post method.
        String serverAPIURL = mcontext.getResources().getString(R.string.Server)+mcontext.getResources().getString(R.string.Login_route);

//        Toast.makeText(context,serverAPIURL,Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing Echo Response Message Coming From Server.

                        Toast.makeText(mcontext,ServerResponse,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing error message if something goes wrong.

                        Toast.makeText(mcontext, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams () {
                Map<String, String> params = new HashMap<>();
                params.put("email", e);
                params.put("password", password);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params2 = new HashMap<String, String>();
                params2.put("Accept", "application/json");
                return params2;
            }
        };
            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
    }

    //Sign up
    public void Get_database(Context context, TextView error) {
        // Creating string request with post method.
        String serverAPIURL = context.getResources().getString(R.string.Server)+context.getResources().getString(R.string.Database_route);
        String token=context.getResources().getString(R.string.Token);
//        Toast.makeText(context,serverAPIURL,Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverAPIURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing Echo Response Message Coming From Server.

                        try {
                            JSONArray data=ServerResponse.getJSONArray("data");
//                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject LuanvanObject = data.getJSONObject(0);
                                Integer LV_Ma = LuanvanObject.getInt("id");
                                String LV_Ten = LuanvanObject.getString("nameVN");
                                String LV_TenTiengAnh =  LuanvanObject.getString("nameEN");
                                String SV1_Ten = LuanvanObject.getString("student1");
//                                String MSSV1 = LuanvanObject.getString("MSSV1");
                                String SV2_Ten=  LuanvanObject.getString("student2");
//                                String MSSV2 =  LuanvanObject.getString("MSSV2");
                                String GV1_Ten = LuanvanObject.getString("instructor1");
                                String GV2_Ten =  LuanvanObject.getString("instructor2");
                                //Insert data to database
//                                try {
//                                    database.InsertData(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
//                                }
//                                catch (Exception e) {
//
//                                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
//                                }
//                                LuanvanModel newview = new LuanvanModel(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
//                                Luanvan.add(newview);
//                            }
                            error.setText(LuanvanObject.toString());
                        } catch (JSONException exception) {
                            error.setText(exception.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        //progress

                        // Showing error message if something goes wrong.

                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                        error.setText(volleyError.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Accept", "application/json");
                header.put("Authorization", "Bearer " + token);
                return header;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(jsonObjectRequest);
    }
    //Get data
    //Get Available
    public  void Get_LuanvanAvailable_FromServer (Context context, int i) {
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/select-data-available.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {

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
        List<LuanvanModel> Luanvan = new ArrayList<>();
        String serverAPIURL = "https://traluanvan.herokuapp.com/android/select-data-luanvan.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverAPIURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try {

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

                        Copy_Database_local(context,database);

                        // Showing error message if something goes wrong.
//                        Copy_Database_local(context,database);
                    }
                }) ;
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
 public void Copy_Database_local(Context mcontext, Traluanvandb data){
             File database = mcontext.getApplicationContext().getDatabasePath(data.DB_NAME);
        if (false == database.exists()) {
            data.getReadableDatabase();
            //Copy database
            if (data.copyDataBase(mcontext )) {
                Toast.makeText(mcontext    , "Copy Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mcontext    , "Try again", Toast.LENGTH_SHORT).show();
            }
        }
 }
}


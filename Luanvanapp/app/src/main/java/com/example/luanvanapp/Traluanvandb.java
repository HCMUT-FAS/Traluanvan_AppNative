package com.example.luanvanapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class Traluanvandb extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public static final String TAG = Traluanvandb.class.getSimpleName();
    public static int flag;
    // Exact Name of you db file that you put in assets folder with extension.
    public static String DB_NAME = "Traluanvan.db";
    private final Context myContext;
    String outFileName = "";
    public static String DB_PATH = "/data/data/com.example.luanvanapp/databases/";
    public static final String TABLE_NAME = "luanVan";
    public static final String Col_PRIMARY_KEY = "LV_Ma";
    public static final String COL_2 = "LV_Ten";
    public static final String COL_3 = "LV_TenTiengAnh";
    public static final String COL_4 = "SV1_Ten";
    public static final String COL_5 = "MSSV1";
    public static final String COL_6 = "SV2_Ten";
    public static final String COL_7 = "MSSV2";
    public static final String COL_8 = "GV1_Ten";
    public static final String COL_9 = "GV2_Ten";
    String[] Col_Name = {Col_PRIMARY_KEY, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9};


    public Traluanvandb(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
    public void openDataBase() {
        //Open the database
        String myPath = myContext.getDatabasePath(DB_NAME).getPath();
        if (db != null && db.isOpen()) {
            Log.e(TAG, "openDataBase: Open ");
            return;
        }
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDataBase() {
        if (db != null) {
            db.close();
            return;
        }

    }
    //Get all data from database
    public List<LuanvanModel> getAllData(){
        db = this.getReadableDatabase();
        List<LuanvanModel> result = new ArrayList<>();
        String query = "SELECT * FROM luanVan";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int LV_Ma = cursor.getInt(0);
                String LV_Ten = cursor.getString(1);
                String LV_TenTiengAnh = cursor.getString(2);
                String SV1_Ten = cursor.getString(3);
                String MSSV1 = cursor.getString(4);
                String SV2_Ten = cursor.getString(5);
                String MSSV2 = cursor.getString(6);
                String GV1_Ten = cursor.getString(7);
                String GV2_Ten = cursor.getString(8);
                LuanvanModel newview = new LuanvanModel(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
                result.add(newview);
            } while (cursor.moveToNext());

        } else {
            //do not do anything
        }
        cursor.close();
        db.close();
        return result;
    }
    //Get data request
    public List<LuanvanModel> getData(String Req){
        String QueryRequest= "%"+Req+"%";
        String Seletion = COL_2+"like?";
        String[] SeletionArg = {QueryRequest,QueryRequest,QueryRequest,QueryRequest,QueryRequest,QueryRequest,QueryRequest,QueryRequest,QueryRequest};
        db = this.getReadableDatabase();
        List<LuanvanModel> result = new ArrayList<>();
        String queryRequest = "SELECT * FROM "+TABLE_NAME+" WHERE ("
                + Col_PRIMARY_KEY+" like? or " +
                COL_2+" like? or "+
                COL_3+" like? or "+
                COL_4+" like? or "+
                COL_5+" like? or "+
                COL_6+" like? or "+
                COL_7+" like? or "+
                COL_8+" like? or "+
                COL_9+" like?)";
        Cursor cursor = db.rawQuery(queryRequest,SeletionArg);
        if (cursor.moveToFirst()) {
            do {
                int LV_Ma = cursor.getInt(0);
                String LV_Ten = cursor.getString(1);
                String LV_TenTiengAnh = cursor.getString(2);
                String SV1_Ten = cursor.getString(3);
                String MSSV1 = cursor.getString(4);
                String SV2_Ten = cursor.getString(5);
                String MSSV2 = cursor.getString(6);
                String GV1_Ten = cursor.getString(7);
                String GV2_Ten = cursor.getString(8);
                LuanvanModel newview = new LuanvanModel(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
                result.add(newview);
            } while (cursor.moveToNext());

        } else {
            //do not do anything
        }
        cursor.close();
        db.close();
        return result;
    }
    public LuanvanModel getLuanVan(String id) {
        String[] SeletionArg = { id};
        db = this.getReadableDatabase();
        String queryRequest = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + Col_PRIMARY_KEY + " = ?  ";
        Cursor cursor = db.rawQuery(queryRequest, SeletionArg);
        cursor.moveToFirst();
        int LV_Ma = cursor.getInt(0);
        String LV_Ten = cursor.getString(1);
        String LV_TenTiengAnh = cursor.getString(2);
        String SV1_Ten = cursor.getString(3);
        String MSSV1 = cursor.getString(4);
        String SV2_Ten = cursor.getString(5);
        String MSSV2 = cursor.getString(6);
        String GV1_Ten = cursor.getString(7);
        String GV2_Ten = cursor.getString(8);
        LuanvanModel LV = new LuanvanModel(LV_Ma, LV_Ten, LV_TenTiengAnh, SV1_Ten, MSSV1, SV2_Ten, MSSV2, GV1_Ten, GV2_Ten);
        cursor.close();
        db.close();
        return LV;
    }
}





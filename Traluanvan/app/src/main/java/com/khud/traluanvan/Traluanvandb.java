package com.khud.traluanvan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Traluanvandb extends SQLiteOpenHelper {
    public static SQLiteDatabase db;
    public static final String TAG = Traluanvandb.class.getSimpleName();
    public static int flag;
    // Exact Name of you db file that you put in assets folder with extension.
    public static String DB_NAME = "Traluanvan.db";
    private final Context myContext;
    String outFileName = "";
    public static String DB_PATH = "";
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
    public static final String COL_10 = "Available";
    String[] Col_Name = {Col_PRIMARY_KEY, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9};


    public Traluanvandb(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public static String getDbPath() {

        return DB_PATH=db.getPath();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table.
        String Create = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + Col_PRIMARY_KEY + " INTEGER PRIMARY KEY,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT,"
                + COL_7 + " TEXT,"
                + COL_8 + " TEXT,"
                + COL_9 + " TEXT" +
                ")";
        // Execute script.
        db.execSQL(Create);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public void deleteDataBase(){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
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
    public void InsertData (Integer LV_Ma, String LV_Ten, String LV_TenTiengAnh, String SV1_Ten,String MSSV1,String SV2_Ten,String MSSV2,String GV1_Ten,String GV2_Ten) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_PRIMARY_KEY, LV_Ma);
        contentValues.put(COL_2, LV_Ten);
        contentValues.put(COL_3, LV_TenTiengAnh);
        contentValues.put(COL_4, SV1_Ten);
        contentValues.put(COL_5, MSSV1);
        contentValues.put(COL_6, SV2_Ten);
        contentValues.put(COL_7, MSSV2);
        contentValues.put(COL_8, GV1_Ten);
        contentValues.put(COL_9, GV2_Ten);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
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
        List<LuanvanModel> result = new ArrayList<>();
        String QueryRequest = "%" + Req + "%";
        String Seletion = COL_2 + "like?";
        String[] SeletionArg = {QueryRequest, QueryRequest, QueryRequest, QueryRequest, QueryRequest, QueryRequest, QueryRequest, QueryRequest, QueryRequest};
        db = this.getReadableDatabase();

        String queryRequest = "SELECT * FROM " + TABLE_NAME + " WHERE ("
                + Col_PRIMARY_KEY + " like? or " +
                COL_2 + " like? or " +
                COL_3 + " like? or " +
                COL_4 + " like? or " +
                COL_5 + " like? or " +
                COL_6 + " like? or " +
                COL_7 + " like? or " +
                COL_8 + " like? or " +
                COL_9 + " like?)";
        Cursor cursor = db.rawQuery(queryRequest, SeletionArg);
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
    //Copy database
    public boolean copyDataBase(Context context) {
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = context.getAssets().open(Traluanvandb.DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            String outfifename = Traluanvandb.DB_PATH + Traluanvandb.DB_NAME;
            myOutput = new FileOutputStream(outfifename);

            byte[] buffer = new byte[1024];
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}





package com.example.tra_luan_van;


import android.app.ListActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText search;
        Button btn_search;
        ListView View;

        search = this.findViewById(R.id.search);
        btn_search = findViewById(R.id.btn_search);
        View = (ListView) findViewById(R.id.list) ;

        Traluanvandb data ;
        data = new Traluanvandb(this);
        //check database
        File database = getApplicationContext().getDatabasePath(data.DB_NAME);
        if(false == database.exists())
        {
            data.getReadableDatabase();
            //Copy database
            if (copyDataBase(this))
            {
                Toast.makeText(MainActivity.this,"Copy Success",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Try again",Toast.LENGTH_SHORT).show();
            }
        }


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Req = search.getText().toString();
                data.openDataBase();
                List<ViewInterface>res= data.getAllData(Req);
                ArrayAdapter<ViewInterface> adapter =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,res);
                View.setAdapter(adapter);




                Toast.makeText(MainActivity.this,"Chưa tìm được đâu ",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean copyDataBase(Context context)  {
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = context.getAssets().open(Traluanvandb.DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            String outfifename = Traluanvandb.DB_PATH +Traluanvandb.DB_NAME;
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
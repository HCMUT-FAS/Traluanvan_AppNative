package com.example.test;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up View
        Context context;
        View view;
        EditText search;
        Button btn_search;
        ImageButton Reset;
        TextView Soluong;
        ListView ListView;
        Soluong = findViewById(R.id.soluong);
        search = this.findViewById(R.id.search);
        btn_search = findViewById(R.id.btn_search);
        Reset = findViewById(R.id.reset);
        ListView = findViewById(R.id.list);

        //Toast.makeText(this, "Chúc một ngày tốt lành", Toast.LENGTH_SHORT).show();
        Traluanvandb data;
        data = new Traluanvandb(this);
        //check database
        File database = getApplicationContext().getDatabasePath(data.DB_NAME);
        if (false == database.exists()) {
            data.getReadableDatabase();
            //Copy database
            if (copyDataBase(this)) {
                Toast.makeText(MainActivity.this, "Copy Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }

        //Show Alldata from database
        data.openDataBase();
        List<ViewInterface> Mainscreen = data.getAllData();
        Soluong.setText("Số luận văn : "+Mainscreen.size());
        ArrayAdapter<ViewInterface> AdapterMainscreen =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,Mainscreen);
        //ListView.setAdapter(AdapterMainscreen);

        //To search Request
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Req = search.getText().toString();
                data.openDataBase();
                //Main screen if req = null
                List<ViewInterface>Mainscreen= data.getData(Req);
                ArrayAdapter<ViewInterface> AdapterMainscreen =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,Mainscreen);
                ListView.setAdapter(AdapterMainscreen);
                //Get data request
                List<ViewInterface> result= data.getData(Req);
                Soluong.setText("Số luận văn : "+result.size());
                ArrayAdapter<ViewInterface> Adapterresult =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,result);
                ListView.setAdapter(Adapterresult);
                //Add listview click listener
                ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent ( MainActivity .this ,
                                com.example.test.DataviewActivity. class );
                        ViewInterface luanvan =(ViewInterface) Adapterresult.getItem( position );
                        Toast.makeText(MainActivity.this, Integer.toString(luanvan.getLV_Ma()), Toast.LENGTH_SHORT).show();
                        intent.putExtra (" LV_Ma ", Integer.toString(luanvan.getLV_Ma()));
                        startActivity ( intent );

                    }
                });

            }
        });

        //Back to Main Screen
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.openDataBase();
                List<ViewInterface>Mainscreen= data.getAllData();
                Soluong.setText("Số luận văn : "+Mainscreen.size());
                ArrayAdapter<ViewInterface> AdapterMainscreen =new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,Mainscreen);
                ListView.setAdapter(AdapterMainscreen);

            }
        });
        //Add listview clicklistener
        ListView.setOnItemClickListener (new AdapterView . OnItemClickListener
                (){
            public void onItemClick ( AdapterView <?> arg0 , View arg1 ,
                                      int arg2 , long arg3 ) {
                Intent intent = new Intent ( MainActivity .this ,
                        com.example.test.DataviewActivity. class );
                ViewInterface luanvan =(ViewInterface) AdapterMainscreen.getItem( arg2 );
                intent.putExtra (" LV_Ma ", Integer.toString(luanvan.getLV_Ma()));
                startActivity ( intent );
            }
        });

    }

    //Copy database
    private boolean copyDataBase(Context context) {
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
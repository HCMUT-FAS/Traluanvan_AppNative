package com.example.luanvanapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import View
import java.util.ArrayList;
import android.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
//import Toast
import android.widget.Toast;
//
import androidx.appcompat.app.AppCompatActivity;
//import for copy database
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //Set up parameter
    private int noOfBtns;
    private Button[] btns;
    public int TOTAL_LIST_ITEMS ;
    public int NUM_ITEMS_PAGE ;
    private Context context;
    private View view;
    private EditText search;
    private Button btn_search;
    private ImageButton Reset;
    private TextView Soluong,title;
    private ListView Listview;
    ArrayAdapter<String> sd;
    Traluanvandb data = new Traluanvandb(this);
    ConnectServer connectServer = new ConnectServer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set up Layout
        Soluong = findViewById(R.id.soluong);
        title = findViewById(R.id.title);
        search = this.findViewById(R.id.search);
        btn_search = findViewById(R.id.btn_search);
        Reset = findViewById(R.id.reset);
        Listview = findViewById(R.id.list);

        //Start app
        Toast.makeText(this, "Chúc một ngày tốt lành", Toast.LENGTH_SHORT).show();

        //Check database if exist (Option)
        File database = getApplicationContext().getDatabasePath(data.DB_NAME);
        if (false == database.exists()) {
            data.getReadableDatabase();
            //Copy database
            if (data.copyDataBase(this)) {
                Toast.makeText(MainActivity.this, "Copy Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        }
        //Show Data from database
        //Get All data
        data.openDataBase();
        List<LuanvanModel> Mainscreen = data.getAllData();
        //Get data size
        Soluong.setText("Số luận văn : " + Mainscreen.size());
        TOTAL_LIST_ITEMS = Mainscreen.size();
        NUM_ITEMS_PAGE = 15;
        Caculatepage(TOTAL_LIST_ITEMS,NUM_ITEMS_PAGE);
        Btnfooter(noOfBtns,Mainscreen);
        CheckBtnBackGroud(0,noOfBtns);
        LoadList(0, Mainscreen);
        //To search Request
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Req = search.getText().toString();
                boolean CheckRequest = CheckSearchRequest(Req);
                if (CheckRequest == true) {
                    data.openDataBase();
                    //Get data request
                    List<LuanvanModel> result = data.getData( Req);
                    //Set data request to LoadList
                    Soluong.setText("Số luận văn : " + result.size());

                TOTAL_LIST_ITEMS = result.size();
                Caculatepage(TOTAL_LIST_ITEMS,NUM_ITEMS_PAGE);
                Btnfooter(noOfBtns,result);
                CheckBtnBackGroud(0,noOfBtns);
                LoadList(0, result);}
                else {
                    Toast.makeText(MainActivity.this, "Fill into Search ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Back to Main Screen
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TOTAL_LIST_ITEMS = Mainscreen.size();
                Soluong.setText("Số luận văn : " + TOTAL_LIST_ITEMS);
                Caculatepage(TOTAL_LIST_ITEMS,NUM_ITEMS_PAGE);
                Btnfooter(noOfBtns,Mainscreen);
                CheckBtnBackGroud(0,noOfBtns);
                LoadList(0, Mainscreen);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        connectServer.Get_LuanvanFromServer(MainActivity.this,data);

    }

    @Override
    protected void onDestroy() {
        data.deleteDataBase();
        super.onDestroy();
    }

    //Set up for pagvination
    //Caculate Num of page
    private void Caculatepage (int TOTAL_LIST_ITEMS,int NUM_ITEMS_PAGE)
    {   int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
    }
    //Set up pagvination
    private void Btnfooter(int noOfBtns,List data)
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.btnLay);
        ll.removeAllViews();
        btns    =new Button[noOfBtns];
        for(int i=0;i<noOfBtns;i++)
        {
            btns[i] =   new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);
            final int j = i;
            btns[j].setOnClickListener(new OnClickListener() {
                public void onClick(View v)
                {
                     LoadList(j,data);
                    CheckBtnBackGroud(j,noOfBtns);
                }
            });
        }

    }
    /**
     * Method for Checking Button Backgrounds
     */
    private void CheckBtnBackGroud(int index,int noOfBtns)
    {
        title.setText("Page "+(index+1)+" of "+noOfBtns);
        for(int i=0;i<noOfBtns;i++)
        {
            if(i==index)
            { btns[i].setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }
            else
            {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }
    }
    /**
     * Method for loading data in Listview
     * @param number
     */
    private void LoadList(int number,List data)
    {
        ArrayList<String> sort = new ArrayList<String>();
        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i).toString());
            }
            else
            {
                break;
            }
        }
        sd = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                sort);
        Listview.setAdapter(sd);
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener
                () {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                Intent intent = new Intent(MainActivity.this,
                        com.example.luanvanapp.ClickListviewActivity.class);
                LuanvanModel luanvan = (LuanvanModel) data.get(arg2+start);
                intent.putExtra(" LV_Ma ", Integer.toString(luanvan.getLV_Ma()));
                startActivity(intent);
            }
        });

    }


    private boolean CheckSearchRequest (String Req){
        String ReqArray1 [] = Req.split(" ");
        String ReqArray2 [] = Req.split("%");
        String ReqArray3 [] = Req.split("%20");

        if ((ReqArray1.length == 0) || (ReqArray2.length == 0) ||(ReqArray3.length==0)||(Req.matches(""))) {

            return false;
        }
        else{
            return true;
            }
    }
}
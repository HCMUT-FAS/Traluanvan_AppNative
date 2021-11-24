package com.example.navigation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;

import com.example.navigation.databinding.FragmentDateBinding;
import com.example.navigation.databinding.FragmentHomeBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFragment extends Fragment {
    Button submit;
    EditText datePickerET;
    MaterialDatePicker materialDatePicker;
    Context mcontext;
    FragmentDateBinding DateBinding;
    String Ngay,LV_Ma;
    ConnectServer connectServer;
    String Key="Input";
    ArrayList<String> Input=new ArrayList<String>();


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Input = savedInstanceState.getStringArrayList(Key);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DateBinding = FragmentDateBinding.inflate(inflater,container,false);
        View view = DateBinding.getRoot();
        mcontext=getActivity();
        //create calander
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        datePickerET = DateBinding.calendarInputEdittext;
        final long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        long january = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        long december = calendar.getTimeInMillis();

        //Calendar Constraints
        //lich chi tu thang 1 - 12 cua nam
        //chi dat lich tu ngay hom nay tro di
        CalendarConstraints.Builder constraintBuider = new CalendarConstraints.Builder();
        constraintBuider.setStart(january);
        constraintBuider.setEnd(december);
        constraintBuider.setValidator(DateValidatorPointForward.now());

        // MaterialDatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuider.build());
        materialDatePicker = builder.build();
        //Set layout
        submit=DateBinding.submit;
        //Set ConnectServer
        connectServer=new ConnectServer();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get LV_Ma from Info_Fragment
        Bundle bundle=this.getArguments();
        if(bundle!=null) {
            LV_Ma = bundle.getString("LV_Ma");
            DateBinding.mlvInputEdittext.setText(LV_Ma);
        }
        final Date[] date = new Date[1];
        //Set onclick Datepicker
        datePickerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use getActivity (). getSupportFragmentManager replace getSupportFragmentManager()
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        final SimpleDateFormat[] simpleFormat = new SimpleDateFormat[1];
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
/*
                datePickerET.setText(materialDatePicker.getHeaderText());
*/
                // Get the offset from our timezone and UTC.
                TimeZone timeZoneUTC = TimeZone.getDefault();
                // It will be negative, so that's the -1
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                // Create a date format, then a date object with our offset
                simpleFormat[0] = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                date[0] = new Date(selection + offsetFromUTC);
                datePickerET.setText(simpleFormat[0].format(date[0]));
                simpleFormat[0] = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Ngay=simpleFormat[0].format(date[0]);
            }
        });
        //Set LV_ma
//        DateBinding.mvlInputEdittext.setText(LV_Ma);
        //Set on click submit
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Input.size()>=6) {
                Input.clear();
                }
                Input.add(DateBinding.mailInputEdittext.getText().toString());
                Input.add(DateBinding.nameInputEdittext.getText().toString());
                Input.add(DateBinding.mssvInputEdittext.getText().toString());
                Input.add(DateBinding.mlvInputEdittext.getText().toString());
                Input.add(DateBinding.phoneInputEdittext.getText().toString());
                Input.add(Ngay);
                // use getActivity (). getSupportFragmentManager replace getSupportFragmentManager()
                if (CheckInput(Input)){
                    if (CheckEmail(DateBinding.mailInputEdittext)){
//                        Toast.makeText(getActivity(),Input.get(5), Toast.LENGTH_SHORT).show();
                        connectServer.SendDataToServer(mcontext,
                                Input.get(0),
                                Input.get(1),
                                Input.get(2),
                                Input.get(3),
                                Input.get(4),
                                Input.get(5));
                                }
                    else {
                        Toast.makeText(getActivity(),"Vui lòng xài mail trường", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getActivity(),"Vui lòng kiểm tra lại phần nhập form", Toast.LENGTH_SHORT).show();
                }
                }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        outState.putStringArrayList(Key,Input);
        super.onSaveInstanceState(outState);
    }
    private boolean CheckInput(ArrayList Input){
        for(int i=1;i<Input.size();i++){
            if (Input.get(i).toString().isEmpty()){
                return false;
            }
        }
        return true;
    }
    private boolean CheckEmail (EditText email) {
        String Emailsercurity[] = email.getText().toString().split("@");
        if (Emailsercurity.length == 2) {
            if (Emailsercurity[1].matches("hcmut.edu.vn")) {
                return true;
            }
        }
        return false;
    }

}
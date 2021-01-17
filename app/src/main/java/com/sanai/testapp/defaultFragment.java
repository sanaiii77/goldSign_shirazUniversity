package com.sanai.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import root.converter;

public class defaultFragment extends Fragment {

    TextView textView ;
    Calendar cal = Calendar.getInstance();
    int [] date_shamsi = new int[3];
    String dateForTextview ;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_default,container,false);

        textView = view.findViewById(R.id.defaultTextView);
        //****************************date*********************************


        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date_shamsi = converter.gregorian_to_jalali(year,month,day);
        //*****************************************************************
        dateForTextview = whenIsIt();
        //setTextView(dateForTextview);




        return  view;
    }

    public String whenIsIt (){


        return "expire";
    }

    public void  setTextView (String date){

        if(date == "expire"){
            textView.setText("ترم فعالی وجود ندارد.");
        }
        else if(date == "teachers"){
            textView.setText("زمان انتخاب دانشجو توسط اساتید.");
        }
        else if(date == "student"){
            textView.setText("زمان درخواست و انتخاب اساتید .");
        }

    }
}

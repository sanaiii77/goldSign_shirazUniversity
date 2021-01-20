package com.sanai.testapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import jango.DatetimeRange;
import jango.Django;
import root.CompareTwoDatesTest;
import root.converter;

public class AddDateRangeFragment extends Fragment {
    Spinner startStudent1, startStudent2, startStudent3, finishStudent1, finishStudent2, finishStudent3;
    Button addNewDateRange ;
    //1 = sal 2= mah 3=rooz
    int[] startTimeForStudent ,finishTimeForStudent ;
    String[] days =new String[31];
    String[] months =new String[12];
    String[] years =new String[2];

    String startTime , endTime  = "";

    Calendar cal = Calendar.getInstance();
    int [] date_shamsi = new int[3];

    //*************************************



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_date_range_fragment, container, false);
        addNewDateRange = view.findViewById(R.id.addNewDateRange);
        //_____________________________________________________________________________
        startStudent1 = view.findViewById(R.id.shoroeZamanEntekhabStudent1);
        startStudent2 = view.findViewById(R.id.shoroeZamanEntekhabStudent2);
        startStudent3 = view.findViewById(R.id.shoroeZamanEntekhabStuden3);
        //############################################################
        finishStudent1 = view.findViewById(R.id.payanZamanEntekhabStudent1);
        finishStudent2 = view.findViewById(R.id.payanZamanEntekhabStudent2);
        finishStudent3 = view.findViewById(R.id.payanZamanEntekhabStudent3);

        startTimeForStudent = new int[3];
        finishTimeForStudent = new int[3];

        setSpinner();
        clickOnItemOfSpinners();
        save();
        return view;

    }

    public  void  setSpinner (){

        fillListOfSpinner();
        //_____________________________Student start spinner___________________________________

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        startStudent1.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        startStudent2.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        startStudent3.setAdapter(adapter);

        //_____________________________Student end spinner___________________________________

        adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        finishStudent1.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        finishStudent2.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        finishStudent3.setAdapter(adapter);

    }
    public  void clickOnItemOfSpinners(){

        startStudent1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                startTimeForStudent[0]=(Integer.parseInt((startStudent1.getItemAtPosition(startStudent1.getSelectedItemPosition()).toString())));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //year
        startStudent2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                startTimeForStudent[1]=(Integer.parseInt((startStudent2.getItemAtPosition(startStudent2.getSelectedItemPosition()).toString())));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); // month
        startStudent3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                startTimeForStudent[2]=(Integer.parseInt((startStudent3.getItemAtPosition(startStudent3.getSelectedItemPosition()).toString())));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); // day
        //###################################################################################
        finishStudent1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                finishTimeForStudent[0]=(Integer.parseInt(finishStudent1.getItemAtPosition(finishStudent1.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //year
        finishStudent2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                finishTimeForStudent[1]=(Integer.parseInt(finishStudent2.getItemAtPosition(finishStudent2.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        }); //month
        finishStudent3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                finishTimeForStudent[2]=(Integer.parseInt(finishStudent3.getItemAtPosition(finishStudent3.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //day

        //###################################################################################

    }
    public  void fillListOfSpinner (){

        for(int i=0; i<31 ; i++){
            days[i] = i+1+"";
        }
        for(int i=0; i<12 ; i++){
            months[i] = i+1+"";
        }

        //*********************************************************************
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date_shamsi = converter.gregorian_to_jalali(year,month,day);


        //get persian date and replace
        years[0]= date_shamsi[0] + "";
        years[1]= date_shamsi[0] +1+ "";




        //_____________________________Student start spinner___________________________________




    }
    public  void  save (){
        Django.getDateRangeTimeList();
        addNewDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = CompareTwoDatesTest.convertTimeToString(startTimeForStudent[0],startTimeForStudent[1],startTimeForStudent[2]);
                endTime = CompareTwoDatesTest.convertTimeToString(finishTimeForStudent[0],finishTimeForStudent[1],finishTimeForStudent[2]);
                DatetimeRange datetimeRange = new DatetimeRange();
                datetimeRange.setStart(startTime);
                datetimeRange.setEnd(endTime);
                String when = CompareTwoDatesTest.comparison(startTime,endTime);
                if(when.matches("before") || when.matches("equal")){
                    boolean  bool = isNewTime(datetimeRange);
                    if (bool){

                        dialog(startTime , endTime);
                    }
                    else {
                        Toast.makeText(getActivity(), "بازه زمانی تکراری است", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "زمان پایان نمیتواند قبل از زمان شروع باشد  ", Toast.LENGTH_LONG).show();


                }

            }
        });


    }
    public  boolean isNewTime(DatetimeRange datetimeRange){

        for (int i=0 ; i<Django.dateRangeList.size() ; i++){
            if(Django.dateRangeList.get(i).getStart().matches(datetimeRange.getStart()) &&
                    Django.dateRangeList.get(i).getEnd().matches(datetimeRange.getEnd())){
                return  false;
            }
        }
        return  true;

    }
    public void  dialog (final String startTime , final String endTime){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle( "افزودن بازه زمانی");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                insertDateRange(startTime , endTime , getActivity());
                goToDefaultFargment();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void insertDateRange(String start , String end , final Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("start" , start);
            postData.put("end", end);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"date-range/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getActivity(), response.toString()+"", Toast.LENGTH_LONG).show();
                        System.out.println(response);
                        Django.getDateRangeTimeList();


                    }
                }, new com.android.volley.Response.ErrorListener () {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"error : " + error, Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);


    }
    public void  goToDefaultFargment(){


        //////////////////
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

}
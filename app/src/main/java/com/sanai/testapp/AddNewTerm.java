package com.sanai.testapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jango.DatetimeRange;
import jango.Django;
import jango.Teacher;
import jango.Term;
import root.CompareTwoDatesTest;
import root.converter;


public class AddNewTerm extends Fragment {


    FragmentTransaction transaction;
    //*****************************************************
    Spinner selectDay, selectMonth, selectYear, selectTimeForStudent, selectTimeForTeacher, selectMajor;
    Button addTerm;
    //***********************************************
    String[] days = new String[31];
    String[] months = new String[12];
    ArrayList<String> years;
    static ArrayList<String> dateRange;
    static ArrayList<String> majorList;
    String time = "";
    Calendar cal = Calendar.getInstance();
    int[] date_shamsi = new int[3];

    List<Integer> timeArray;
    String majorSelected, TtimeSelected, StimeSelected;
    int daySelected, monthSelected, yearSelected;
    Term term ;
    static  int termPK ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_term, container, false);

        //_____________________________________________________________________________
        selectDay = view.findViewById(R.id.dayTimeTerm);
        selectMonth = view.findViewById(R.id.monthTimeTerm);
        selectYear = view.findViewById(R.id.yearTimeTerm);
        selectTimeForStudent = view.findViewById(R.id.selectTermTimeForStudent);
        selectTimeForTeacher = view.findViewById(R.id.selectTermTimeForteacher);
        selectMajor = view.findViewById(R.id.selectMajorForTerm);
        addTerm = view.findViewById(R.id.addTerm);
        //_____________________________________________________________________________
        timeArray = new ArrayList<>();
        years = new ArrayList<>();
        dateRange = new ArrayList<>();
        majorList = new ArrayList<>();
        term = new Term();


        //*********************************************************************
        setSpinner();
        clickOnItemOfSpinners();
        saveTerm();
        return view;
    }


    public void goToDefaultFargment() {
        Fragment newFragment = new defaultFragment();
        transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    public void fillListOfSpinner() {

        //Toast.makeText(getActivity(), "size :"+Django.majorList.size()+"  "+Django.dateRangeList.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < Django.dateRangeList.size(); i++) {
            dateRange.add(Django.dateRangeList.get(i).getStart() + "/" + Django.dateRangeList.get(i).getEnd());
        }
        for (int i = 0; i < Django.majorList.size(); i++) {
            majorList.add(Django.majorList.get(i).getMajorTitle());
        }

        for (int i = 0; i < 31; i++) {
            days[i] = i + 1 + "";
        }
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1 + "";
        }

        //*********************************************************************
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        date_shamsi = converter.gregorian_to_jalali(year, month, day);
        date_shamsi[0] -= 5;


        //get persian date and replace
        for (int i = 0; i < 7; i++) {
            years.add(date_shamsi[0] + i + "");
        }

        return;


    }
    public void setSpinner() {
        Django.getDateRangeTimeList();
        Django.getMajorList();
        fillListOfSpinner();
        //_____________________________Student start spinner___________________________________

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectYear.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectMonth.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectDay.setAdapter(adapter);

        //_____________________________Student end spinner___________________________________

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, dateRange);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectTimeForStudent.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, dateRange);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectTimeForTeacher.setAdapter(adapter);
        //*********************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, majorList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        selectMajor.setAdapter(adapter);

    }
    public void saveTerm() {

        addTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatetimeRange std = Django.getDateRange(getDateRangePK(StimeSelected));
                DatetimeRange teach = Django.getDateRange(getDateRangePK(TtimeSelected));
                String when = CompareTwoDatesTest.comparison(std.getEnd(),teach.getStart());//std before teacher start
                if(when.matches("before")){
                    termPK = checkIsNewTerm();
                    if(termPK == -1){
                        // its new
                        dialog1();
                    }
                    else {
                        dialog2();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "بازه زمانی اساتید باید بعد از دانشجویان باشد", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public int checkIsNewTerm() {

        term.setDate(CompareTwoDatesTest.convertTimeToString(yearSelected,monthSelected,daySelected));
        term.setStudent_date_range_PK(getDateRangePK(StimeSelected));
        term.setTeacher_date_range_PK(getDateRangePK(TtimeSelected));

        for(int i=0 ; i<Django.termList.size();i++){
            if(Django.termList.get(i).getDate().matches(term.getDate())
            && Django.termList.get(i).getStudent_date_range_PK() == term.getStudent_date_range_PK()
            && Django.termList.get(i).getTeacher_date_range_PK() == term.getTeacher_date_range_PK()){

                return Django.termList.get(i).getTermPK();
            }

        }
        return  -1;

    }
    public void clickOnItemOfSpinners() {

        selectMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                majorSelected = selectMajor.getItemAtPosition(selectMajor.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //year
        selectTimeForTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                TtimeSelected = selectTimeForTeacher.getItemAtPosition(selectTimeForTeacher.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); // month
        selectTimeForStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                StimeSelected = selectTimeForStudent.getItemAtPosition(selectTimeForStudent.getSelectedItemPosition()).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); // day
        //###################################################################################
        selectDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                daySelected = (Integer.parseInt(selectDay.getItemAtPosition(selectDay.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //year
        selectMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                monthSelected = (Integer.parseInt(selectMonth.getItemAtPosition(selectMonth.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        }); //month
        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                yearSelected = (Integer.parseInt(selectYear.getItemAtPosition(selectYear.getSelectedItemPosition()).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        }); //day

        //###################################################################################

    }
    public int getDateRangePK (String str){
        String[] paths = str.split("/");

        for (int i=0 ; i<Django.dateRangeList.size() ;i++){
            if(Django.dateRangeList.get(i).getStart().matches(paths[0] ) &&
                    Django.dateRangeList.get(i).getEnd().matches(paths[1])){
                return Django.dateRangeList.get(i).getDateRangePK();

            }

        }
        return -1;
    }
    public void  dialog1 (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("افزودن ترم");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                insertTerm(term);
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
    public void  dialog2 (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("افزودن ترم");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                int majorPK = checkIsNewMT(termPK);
                if(majorPK != -1){ // new MT
                    insertMT(termPK , majorPK);

                }
                else {
                    Toast.makeText(getActivity(), "ترم تکراری است", Toast.LENGTH_LONG).show();
                }                dialog.dismiss();
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
    public void insertTerm(Term term){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("date" , term.getDate());
            postData.put("teacher_date_range" , term.getTeacher_date_range_PK());
            postData.put("student_date_range", term.getStudent_date_range_PK());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"term/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getActivity(), response.toString()+"", Toast.LENGTH_LONG).show();
                        System.out.println(response);

                        try {
                            termPK = response.getInt("pk");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Django.getTermList();
                        insertMT(termPK ,getMajorPK());

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
    public void insertMT(int term , int major){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("major" , major);
            postData.put("term", term);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"mt/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "ترم افزوده شد", Toast.LENGTH_LONG).show();
                        System.out.println(response);
                        Django.getMTList();
                        goToDefaultFargment();



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
    public int checkIsNewMT(int term){
        int majorPK = -1 ;

        for(int i=0 ; i<Django.majorList.size();i++){
            if(majorSelected.matches(Django.majorList.get(i).getMajorTitle())){
                majorPK = Django.majorList.get(i).getMajorPK();
            }
        }
        Toast.makeText(getActivity(), "pks "+term+" "+majorPK, Toast.LENGTH_SHORT).show();

        for (int i=0 ; i<Django.MTList.size() ;i++){
            if(Django.MTList.get(i).getMajor_of_mt_PK() == majorPK &&
            Django.MTList.get(i).getTerm_of_mt_PK() == term){
                return  -1;

            }
        }
        return  majorPK;
    }
    public int getMajorPK(){
        for(int i=0 ; i<Django.majorList.size();i++){
            if(majorSelected.matches(Django.majorList.get(i).getMajorTitle())){
                return Django.majorList.get(i).getMajorPK();
            }
        }
        return  -1;

    }

}

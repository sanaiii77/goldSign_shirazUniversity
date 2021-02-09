package com.sanai.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jango.DatetimeRange;
import jango.Django;
import jango.GoldRequest;
import jango.MT;
import jango.Major;
import jango.Student;
import jango.TMT;
import jango.Teacher;
import jango.Term;
import root.CompareTwoDatesTest;


public class ShowTerms extends Fragment {
    public static int mtpk ;
    LinearLayout linearLayout ;
    Button doneTerm ;

    MyStudentRecycleviewAdapter myStudentRecycleviewAdapter;


    //**************************************************
    TextView termText , statusText , stdTimeText , teacherTimeText , stdAvailable , teacherAavlable;
    RecyclerView stdList , teacherList ;

    //______________________________________________________
    MT mt  ;
    Major major  ;
    Term term ;
    DatetimeRange stdDateRange ;
    DatetimeRange teacherDateRange ;
    ArrayList<TMT> tmtListOfTerm = new ArrayList<>() ;
    ArrayList<Student> stdListOfTerm = new ArrayList<>() ;

    ArrayList<TMT> tmts = new ArrayList<>();
    ArrayList<GoldRequest> goldRequests = new ArrayList<>();
    ArrayList<Student> students = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_terms,container,false);
        mtpk = getArguments().getInt("someInt");

        //****************************find*********************************
        termText = view.findViewById(R.id.termInShowTerm);
        statusText = view.findViewById(R.id.statusInShowTerm);
        stdTimeText = view.findViewById(R.id.stdTimeInShowTerm);
        teacherTimeText = view.findViewById(R.id.teacherTimeInShowTerm);
        stdList = view.findViewById(R.id.stdListInShowTerm);
        teacherList = view.findViewById(R.id.teacherListInShowTerm);
        stdAvailable = view.findViewById(R.id.availablityofstd);
        teacherAavlable = view.findViewById(R.id.availablityofteacher);
        linearLayout = view.findViewById(R.id.finishTermLayout);
        doneTerm = view.findViewById(R.id.getTermDone);



        //_________________________________________________________________

        getInfo();
        click();
        return  view;
    }

    public void getInfo(){
        mt = Django.getMT(mtpk);
        major = Django.getMajor(mt.getMajor_of_mt_PK());
        term = Django.getTerm(mt.getTerm_of_mt_PK());
        stdDateRange = Django.getDateRange(term.getStudent_date_range_PK());
        teacherDateRange = Django.getDateRange(term.getTeacher_date_range_PK());
        tmtListOfTerm = getlistOfTMT(mtpk);
        stdListOfTerm = getlistOfStudent();
        //__________________________________________________________
        if(mt.isStatus_of_mt_bool()){ //end
            doneTerm.setVisibility(View.GONE);
        }


        myStudentRecycleviewAdapter = new MyStudentRecycleviewAdapter(stdListOfTerm);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity() );
        stdList.setLayoutManager(mLayoutManager);
        stdList.setAdapter(myStudentRecycleviewAdapter);
        //__________________________________________________________

        MyTeacherRecycleViewAdapter myTeacherRecycleViewAdapter = new MyTeacherRecycleViewAdapter(tmtListOfTerm);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity() );

        teacherList.setLayoutManager(mLayoutManager1);
        teacherList.setAdapter(myTeacherRecycleViewAdapter);


        //__________________________________________________________
        setInfo();

    }
    public void setInfo(){
        termText.setText(term.getDate() + " \n" + major.getMajorTitle());
        stdTimeText.setText("از "+stdDateRange.getStart() + "\n"+"تا "+ stdDateRange.getEnd());
        teacherTimeText.setText("از "+teacherDateRange.getStart() + "\n"+"تا "+ teacherDateRange.getEnd());
        String today = CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2]);
        String time  = CompareTwoDatesTest.comparison( today , teacherDateRange.getEnd());

        if(mt.isStatus_of_mt_bool()){
            statusText.setText("تمام");
            linearLayout.setVisibility(View.GONE);

        }else {
            statusText.setText("نا تمام");
            if (time.matches("after")){
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.GONE);
            }

        }
    }
    public  void click (){
        stdAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stdList.getVisibility() == View.VISIBLE){
                    stdList.setVisibility(View.GONE);

                }else {

                    stdList.setVisibility(View.VISIBLE);
                }

            }
        });

        teacherAavlable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacherList.getVisibility() == View.VISIBLE){
                    teacherList.setVisibility(View.GONE);

                }else {

                    teacherList.setVisibility(View.VISIBLE);
                }

            }
        });

        doneTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goldSign();
                updateStatusOfMT();
                statusText.setText("تمام");
                doneTerm.setVisibility(View.GONE);
                getInfo();

            }
        });
    }
    //base on mt pk
    public  ArrayList<TMT> getlistOfTMT (int mtpk){
        //Toast.makeText(getActivity(), "pk "+ mtpk, Toast.LENGTH_SHORT).show();
        ArrayList<TMT> tmp = new ArrayList<>();
        for (int i=0 ; i<Django.TMTList.size();i++) {
            if(Django.TMTList.get(i).getMt_of_tmt_PK() == mtpk){
                tmp.add(Django.TMTList.get(i));
            }
        }
        return  tmp;
    }
    public  ArrayList<Student> getlistOfStudent (){
        ArrayList<Student> tmp = new ArrayList<>();
        for (int i=0 ; i<Django.studentArrayList.size();i++) {
            if(Django.studentArrayList.get(i).getEntrance_mt_PK() == mtpk){
                tmp.add(Django.studentArrayList.get(i));
                students.add(Django.studentArrayList.get(i));
            }
        }
        return  tmp;
    }
    public void goldSign(){
        tmts = new ArrayList<>();
        tmts = getlistOfTMT(mtpk);
        rankStd(students); // students with ma to min grade
        decreaseCap();



        for(int i=0 ; i<students.size() ; i++){
            Student s = students.get(i);
            if(hasSign(s)){ //has sign
                //Toast.makeText(getContext(), "has sign", Toast.LENGTH_SHORT).show();
                continue;
            }
            goldRequests = new ArrayList<>();
            goldRequests = getRequestOfStd(s.getStudentPK());
            goldRequests = rankRequest(goldRequests); // gold requests max to min priority


            for(int j=0 ; j<goldRequests.size() ; j++){
                int tmt_i_pk = goldRequests.get(j).getTmt_teacher_of_goldreq_PK();
                int index = indexInTMTs(tmt_i_pk);
                if (tmts.get(index).getCap() >0){
                    updateTMTOfStudent(s.getStudentPK() , tmt_i_pk);
                    tmts.get(index).setCap(tmts.get(index).getCap()-1) ;
                    break;
                }



            }




        }
    }
    public ArrayList<GoldRequest> getRequestOfStd(int pk){
        ArrayList<GoldRequest> tmp = new ArrayList<>();
        for (int i=0 ; i < Django.goldReqList.size() ;i++){
            if(Django.goldReqList.get(i).getStudent_of_goldreq_PK() == pk){
                tmp.add(Django.goldReqList.get(i));
            }
        }
        return  tmp;

    }
    public ArrayList<GoldRequest> rankRequest(ArrayList<GoldRequest> array){
        int count = array.size();
        ArrayList<GoldRequest> tmp = new ArrayList<>();
        for (int i=1 ; i <= count ; i++){
            for (int j=0 ; j < count ; j++){
                if (array.get(j).getPriority_of_goldreq_PK() == i){
                    tmp.add(array.get(j));
                    break;
                }

            }

        }
        return  tmp;
    }
    public ArrayList<Student> rankStd(ArrayList<Student> array){
        Student stmp ;

        for (int i=0 ; i <= array.size() ; i++){
            for (int j=i+1 ; j <array.size() ; j++){
                if(array.get(i).getGrade() < array.get(j).getGrade()){
                    stmp = array.get(i);
                    array.set(i ,array.get(j)) ;
                    array.set(j ,stmp) ;
                    break;
                }
            }

        }
        return  array;
    }
    public  int checkTMTCap(int tmtPK){

        for (int i=0 ; i<tmts.size() ; i++){
            if(tmts.get(i).getTmtPK() == tmtPK){
                return i;
            }
        }

        return  -1;


    }
    public void updateTMTOfStudent(final int stdPK , int tmtPK){

        try {
            String URL = Django.URL+ "student/"+ stdPK +"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("selected_tmt", tmtPK);
            jsonBody.put("pk", stdPK);

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getActivity(), "done std: "+stdPK, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

                }
            }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void updateStatusOfMT(){

        try {
            String URL = Django.URL+ "mt/"+ mtpk +"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("status", true);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getActivity(), "پایان ترم", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

                }
            }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public  boolean hasSign(Student std){
        for (int i=0; i<tmts.size() ; i++){
            if(std.getSelected_tmt_PK() == tmts.get(i).getTmtPK()){
                return  true;
            }
        }
        return  false;
    }
    public  void decreaseCap(){
        for(int j=0 ; j<students.size() ; j++){
            for (int i=0; i<tmts.size() ; i++){
                if(students.get(j).getSelected_tmt_PK() == tmts.get(i).getTmtPK()){
                    tmts.get(i).setCap(tmts.get(i).getCap()-1);
                    break;
                }
            }
        }

    }
    public int indexInTMTs(int pk){
        for(int i=0 ;i<tmts.size();i++){
            if(tmts.get(i).getTmtPK() == pk){
                return i;
            }
        }
        return -1;

    }

}

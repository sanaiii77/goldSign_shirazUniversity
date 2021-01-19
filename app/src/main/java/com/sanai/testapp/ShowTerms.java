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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jango.DatetimeRange;
import jango.Django;
import jango.MT;
import jango.Major;
import jango.Student;
import jango.TMT;
import jango.Teacher;
import jango.Term;


public class ShowTerms extends Fragment {
    public static int mtpk ;

    MyStudentRecycleviewAdapter myStudentRecycleviewAdapter;


    //**************************************************
    TextView termText , statusText , stdTimeText , teacherTimeText , stdAvailable , teacherAavlable;
    RecyclerView stdList , teacherList ;

    //______________________________________________________
    MT mt ;
    Major major ;
    Term term ;
    DatetimeRange stdDateRange ;
    DatetimeRange teacherDateRange ;
    ArrayList<TMT> tmtListOfTerm = new ArrayList<>() ;
    ArrayList<Student> stdListOfTerm = new ArrayList<>() ;

    List<String> std = new ArrayList<String>();
    List<String> teacher = new ArrayList<String>();





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_terms,container,false);
        mtpk = getArguments().getInt("someInt");
        Toast.makeText(getActivity(), mtpk+"", Toast.LENGTH_LONG).show();


        //****************************find*********************************
        termText = view.findViewById(R.id.termInShowTerm);
        statusText = view.findViewById(R.id.statusInShowTerm);
        stdTimeText = view.findViewById(R.id.stdTimeInShowTerm);
        teacherTimeText = view.findViewById(R.id.teacherTimeInShowTerm);
        stdList = view.findViewById(R.id.stdListInShowTerm);
        teacherList = view.findViewById(R.id.teacherListInShowTerm);
        stdAvailable = view.findViewById(R.id.availablityofstd);
        teacherAavlable = view.findViewById(R.id.availablityofteacher);

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
        tmtListOfTerm = getlistOfTMT();
        stdListOfTerm = getlistOfStudent();
        //__________________________________________________________


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
        if(mt.isStatus_of_mt_bool()){
            statusText.setText("تمام");

        }else {
            statusText.setText("نا تمام");

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
    }
    //base on mt pk
    public  ArrayList<TMT> getlistOfTMT (){
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
            }
        }
        return  tmp;
    }


}

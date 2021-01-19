package com.sanai.testapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Teacher.Main3Activity;
import jango.DatetimeRange;
import jango.Django;
import jango.MT;
import jango.Major;
import jango.TMT;
import jango.Term;
import root.CheckTime;

import static Teacher.Main3Activity.FINISH__TIME;
import static Teacher.Main3Activity.NOT_START_TIME;
import static Teacher.Main3Activity.STUDENT_Time;
import static Teacher.Main3Activity.TEACHER_TIME;

import static Teacher.Main3Activity.teacher_total_TMT;


public class TMTCalListAdapter extends RecyclerView.Adapter<TMTCalListAdapter.MyTaskViewHolder>  {

    static FragmentActivity mainActivity;

    public TMTCalListAdapter(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmt_calender_row, parent, false);
        MyTaskViewHolder pvh = new MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyTaskViewHolder viewHolder, final int position) {
        TMT tmt = Main3Activity.teacher_total_TMT.get(position);
        MT mt = Django.getMT(tmt.getMt_of_tmt_PK());
        Term term = Django.getTerm(mt.getTerm_of_mt_PK());
        Major major = Django.getMajor(mt.getMajor_of_mt_PK());
        DatetimeRange teacher_datetimeRange = Django.getDateRange(term.getTeacher_date_range_PK());
        DatetimeRange student_datetimeRange = Django.getDateRange(term.getStudent_date_range_PK());
        String time = CheckTime.checkTime(student_datetimeRange , teacher_datetimeRange);
        viewHolder.term.setText(term.getDate()+ "    " + major.getMajorTitle());
        viewHolder.stdtime.setText(student_datetimeRange.getStart() +" \n "+"تا"+" \n " +student_datetimeRange.getEnd());
        viewHolder.teachertime.setText(teacher_datetimeRange.getStart() +"  \n "+"تا"+" \n  " + teacher_datetimeRange.getEnd());

        if (time.matches(TEACHER_TIME)){
            viewHolder.stdtime.setTextColor(mainActivity.getResources().getColor(R.color.red));
            viewHolder.teachertime.setTextColor(mainActivity.getResources().getColor(R.color.green));

        }
        else if(time.matches(STUDENT_Time)){
            viewHolder.stdtime.setTextColor(mainActivity.getResources().getColor(R.color.green));
            viewHolder.teachertime.setTextColor(mainActivity.getResources().getColor(R.color.blue));

        }
        else if(time.matches(NOT_START_TIME)){
            viewHolder.stdtime.setBackgroundColor(Color.WHITE);
            viewHolder.teachertime.setBackgroundColor(Color.WHITE);

        }
        else if(time.matches(FINISH__TIME)){
            viewHolder.stdtime.setTextColor(mainActivity.getResources().getColor(R.color.red));
            viewHolder.teachertime.setTextColor(mainActivity.getResources().getColor(R.color.red));

        }

    }

    @Override
    public int getItemCount() {
        return teacher_total_TMT.size();
    }
    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView term;
        TextView stdtime;
        TextView teachertime;


        MyTaskViewHolder(View itemView) {
            super(itemView);
            term =  itemView.findViewById(R.id.termInCal);
            stdtime =  itemView.findViewById(R.id.stdTimeInCal);
            teachertime =  itemView.findViewById(R.id.teacherTimeInCal);


        }
    }




}

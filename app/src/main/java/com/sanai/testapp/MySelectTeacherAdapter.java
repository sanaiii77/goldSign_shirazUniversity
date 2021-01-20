package com.sanai.testapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import jango.Django;
import jango.Teacher;

public class MySelectTeacherAdapter  extends RecyclerView.Adapter<MySelectTeacherAdapter.MyTaskViewHolder>  {


    Fragment mainActivity;
    String[] teachers ;
    public MySelectTeacherAdapter( Fragment fragment,String[] teachersPK_selected ) {
        this.mainActivity = fragment;
        this.teachers=teachersPK_selected;
    }

    @NonNull
    @Override
    public MySelectTeacherAdapter.MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_select_row, parent, false);
        MySelectTeacherAdapter.MyTaskViewHolder pvh = new MySelectTeacherAdapter.MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MySelectTeacherAdapter.MyTaskViewHolder viewHolder, final int position) {
        viewHolder.number.setText(position +1 + "");

        viewHolder.teacher.setText(teachers[position]);


    }

    @Override
    public int getItemCount() {
        return this.teachers.length;

    }


    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView teacher ;

        MyTaskViewHolder(View itemView) {
            super(itemView);
            number =  itemView.findViewById(R.id.numInSelecTeacherRow);
            teacher = itemView.findViewById(R.id.TeacherRow);

        }
    }



}

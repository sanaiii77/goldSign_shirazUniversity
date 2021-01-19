package com.sanai.testapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jango.Django;
import jango.TMT;
import jango.Teacher;

public class MyTeacherRecycleViewAdapter extends RecyclerView.Adapter<MyTeacherRecycleViewAdapter.MyTaskViewHolder> {


    static List<TMT> list;

    public MyTeacherRecycleViewAdapter(List<TMT> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyTeacherRecycleViewAdapter.MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_row, parent, false);
        MyTeacherRecycleViewAdapter.MyTaskViewHolder pvh = new MyTeacherRecycleViewAdapter.MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyTeacherRecycleViewAdapter.MyTaskViewHolder viewHolder, final int position) {

        Teacher t = Django.getTeacher(list.get(position).getTeacher_of_tmt_PK());
        viewHolder.teacherName.setText(t.getTeacherName() + " " + t.getTeacherFamilyName());
        viewHolder.teacherCap.setText(list.get(position).getCap() + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView teacherName;
        TextView teacherCap;



        MyTaskViewHolder(View itemView) {
            super(itemView);
            teacherName =  itemView.findViewById(R.id.teacherNameRow);
            teacherCap =  itemView.findViewById(R.id.teacherCapRow);



        }
    }



}

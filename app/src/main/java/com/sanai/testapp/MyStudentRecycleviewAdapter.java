package com.sanai.testapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jango.Django;
import jango.MT;
import jango.Student;
import jango.TMT;


public class MyStudentRecycleviewAdapter  extends RecyclerView.Adapter<MyStudentRecycleviewAdapter.MyTaskViewHolder> {


    static List<Student> list;

    public MyStudentRecycleviewAdapter(List<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyStudentRecycleviewAdapter.MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row, parent, false);
        MyStudentRecycleviewAdapter.MyTaskViewHolder pvh = new MyStudentRecycleviewAdapter.MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyStudentRecycleviewAdapter.MyTaskViewHolder viewHolder, final int position) {

        Student s = list.get(position);
        if (s != null){
            viewHolder.name.setText(s.getStudentName() + " " + s.getStudentFamilyname());
            viewHolder.grade.setText(s.getGrade() +"");
            TMT tmp = Django.getTMT(s.getSelected_tmt_PK());
            if(tmp != null){
                String teacherName = Django.getTeacher(tmp.getTeacher_of_tmt_PK()).getTeacherName() + " " +  Django.getTeacher(tmp.getTeacher_of_tmt_PK()).getTeacherFamilyName();
                viewHolder.signgoldTeacher.setText(teacherName+ "");
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView grade;
        TextView signgoldTeacher;



        MyTaskViewHolder(View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.stdNameRow);
            grade =  itemView.findViewById(R.id.stdGradeRow);
            signgoldTeacher =  itemView.findViewById(R.id.stdGoldSignRow);



        }
    }



}

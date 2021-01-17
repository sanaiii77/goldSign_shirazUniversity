package com.sanai.testapp;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import jango.Django;

public class MyTeacherCapacityAdapter extends RecyclerView.Adapter<MyTeacherCapacityAdapter.MyTaskViewHolder>  {


    FragmentActivity mainActivity;

    public MyTeacherCapacityAdapter( Fragment fragment) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_capacity_row, parent, false);
        MyTaskViewHolder pvh = new MyTaskViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyTaskViewHolder viewHolder, final int position) {

        String teacherName ;
        teacherName = Django.teacherArrayList.get(position).getTeacherName() + " " + Django.teacherArrayList.get(position).getTeacherFamilyName();
        viewHolder.name.setText(teacherName);

        viewHolder.cap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!viewHolder.cap.getText().toString().matches("")){
                    AddTMT.capArray[position] = Integer.parseInt(viewHolder.cap.getText().toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return Django.teacherArrayList.size();
    }


    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        EditText cap ;

        MyTaskViewHolder(View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.teachersNameInCapacitySelect);
            cap = itemView.findViewById(R.id.EnterCapacityInCapacitySelect);

        }
    }
}

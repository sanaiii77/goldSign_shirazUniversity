package com.sanai.testapp;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jango.Django;
import jango.MT;
import jango.Major;
import jango.Term;

import static com.sanai.testapp.TermsList.recyclerView;


public class MyTermListAdapter extends RecyclerView.Adapter<MyTermListAdapter.MyTaskViewHolder>  {

    static FragmentActivity mainActivity;


    public MyTermListAdapter( FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout, parent, false);
        MyTaskViewHolder pvh = new MyTaskViewHolder(v);

        return pvh;
    }





    @Override
    public void onBindViewHolder(final MyTaskViewHolder viewHolder, final int position) {
        int majorPK = Django.MTList.get(position).getMajor_of_mt_PK();
        int termPK  = Django.MTList.get(position).getTerm_of_mt_PK();

        for(int i=0 ; i < Django.termList.size();i++){
            if(Django.termList.get(i).getTermPK() == termPK){
                viewHolder.date.setText(Django.termList.get(i).getDate());

            }
        }

        for(int i=0 ; i< Django.majorList.size();i++){
            if(Django.majorList.get(i).getMajorPK() == majorPK){
                viewHolder.major.setText(Django.majorList.get(i).getMajorTitle());

            }
        }

    }

    @Override
    public int getItemCount() {
        return Django.MTList.size();
    }

    public static class MyTaskViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView major;


        MyTaskViewHolder(View itemView) {
            super(itemView);
            date =  itemView.findViewById(R.id.yearsnameInList);
            major =  itemView.findViewById(R.id.majorsnameInList);


        }
    }




}

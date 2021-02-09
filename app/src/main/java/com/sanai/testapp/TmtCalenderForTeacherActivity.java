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

import com.sanai.testapp.R;

import Teacher.Main3Activity;
import jango.Django;

public class TmtCalenderForTeacherActivity extends Fragment {
    RecyclerView recyclerView;
    TMTCalListAdapter tmtCalListAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tmt_calender_for_teacher,container,false);

        recyclerView =view.findViewById(R.id.recycleCal);

        Main3Activity.getListOfTeacherInTmt();

        tmtCalListAdapter = new TMTCalListAdapter(this.getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity() );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(tmtCalListAdapter);


        return  view;
    }
}

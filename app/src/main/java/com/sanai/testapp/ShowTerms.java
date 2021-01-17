package com.sanai.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class ShowTerms extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_terms,container,false);
        String test = "" ;
        test = getArguments().getString("someInt");
        Toast.makeText(getActivity(), test, Toast.LENGTH_LONG).show();


        //****************************date*********************************


        return  view;
    }


}

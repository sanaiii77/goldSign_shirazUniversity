package com.sanai.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import jango.Django;
import jango.MT;


public class TermsList extends Fragment {

     static RecyclerView recyclerView;
    FragmentTransaction transaction;
    MyTermListAdapter myTermListAdapter;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_terms_info,container,false);


        //****************************data of year*********************************
        recyclerView =view.findViewById(R.id.listOfTerms);

        myTermListAdapter = new MyTermListAdapter(this.getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myTermListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              /*  Toast.makeText(getActivity(), "mt pk : "+Django.MTList.get(position).getMtPK()
                        +"\n"+
                        "term pk : "+Django.MTList.get(position).getTerm_of_mt_PK()
                        +"\n" +"major pk : "+Django.MTList.get(position).getMajor_of_mt_PK(), Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        return  view;
    }



    public  void  showSelectedItem(String year){
        Fragment newFragment = new ShowTerms();

        Bundle args = new Bundle();
        args.putString("someInt", year);
        newFragment.setArguments(args);
        transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

}

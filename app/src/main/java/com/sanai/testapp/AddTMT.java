package com.sanai.testapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jango.Django;
import jango.MT;
import jango.Term;


public class AddTMT extends Fragment {

    Spinner mtSpinner ;
    RecyclerView recyclerView ;
    List<String> majors ;
    String mtSelected ;
    List<String> mtString ;
    int spinnerPos =0  ;
    MyTeacherCapacityAdapter customAdapter;
    Button saveTMT ;
    static int[] capArray = new int[Django.teacherArrayList.size()];
    int mtPKselected;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_tmt, container, false);
        Toast.makeText(getActivity(), "tmt start", Toast.LENGTH_SHORT).show();

        mtSpinner = view.findViewById(R.id.selectMajorForTMT);
        saveTMT = view.findViewById(R.id.saveTMT);
        recyclerView = view.findViewById(R.id.recycleTMT);
        recyclerView.setHasFixedSize(true);

        ////////////////////////////////////////////////////////////////////////
        majors = new ArrayList<>();
        mtString = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        //_____________________________________________________
        setSpinner();
        clickOnItemOSpinner(this);
        save();
        return view;

    }



    public  void setSpinner (){
        for (int i=0 ; i< Django.MTList.size() ;i++){
            mtString.add(  getTerm(Django.MTList.get(i).getTerm_of_mt_PK())+ "\n" +
                    getMajorName(Django.MTList.get(i).getMajor_of_mt_PK()) );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,mtString);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mtSpinner.setAdapter(adapter);
    }
    public String getMajorName(int majorPK){
        for(int i=0 ; i<Django.majorList.size();i++){
            if( majorPK == Django.majorList.get(i).getMajorPK() ){
                return Django.majorList.get(i).getMajorTitle();
            }
        }
        return  "";

    }
    public String getTerm(int termPK){
        for(int i=0 ; i<Django.termList.size();i++){
            if( termPK == Django.termList.get(i).getTermPK() ){
                return Django.termList.get(i).getDate();
            }
        }
        return  "";
    }
    public  void clickOnItemOSpinner(final Fragment fragment){
        mtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                mtSelected = mtSpinner.getItemAtPosition(mtSpinner.getSelectedItemPosition()).toString();
                spinnerPos = pos ;
                mtPKselected = Django.MTList.get(pos).getMtPK();
                boolean bool = isTMTNew(Django.MTList.get(pos).getMtPK());
                if (bool == false){
                    Toast.makeText(getActivity(), "ظرفیت اساتید در این ترم تعین شده است  " , Toast.LENGTH_LONG).show();
                    recyclerView.setVisibility(View.INVISIBLE);

                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    customAdapter = new MyTeacherCapacityAdapter(fragment );
                    recyclerView.setAdapter(customAdapter);

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
    public boolean isTMTNew (int MTpk){
        for (int i=0 ; i < Django.TMTList.size() ;i++){
            if(Django.TMTList.get(i).getMt_of_tmt_PK() == MTpk){
                return  false ; //exist
            }
        }
        return  true; //new
    }
    public  void  save (){
        saveTMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean bool = isTMTNew(mtPKselected);
                if (bool == false){
                    Toast.makeText(getActivity(), "ظرفیت اساتید در این ترم تعین شده است  " , Toast.LENGTH_LONG).show();
                    recyclerView.setVisibility(View.INVISIBLE);

                }
                else {
                    dialog();
                }


            }
        });

    }
    public void  dialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("افزودن استاد");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                insertTMT();
                dialog.dismiss();
                goToDefaultFargment();
            }
        });

        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public  void insertTMT(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject postData = new JSONObject();
        for (int i=0 ; i<Django.teacherArrayList.size();i++){
            try {
                postData.put("mt" , Django.MTList.get(spinnerPos).getMtPK());
                postData.put("cap" , capArray[i]);
                postData.put("teacher", Django.teacherArrayList.get(i).getTeacherPK());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.POST, Django.URL+"tmt/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           // Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                            System.out.println(response);
                            Django.getTMTList();

                        }
                    }, new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),"error : " + error, Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    });

            requestQueue.add(jsonObjectRequest);


        }

    }
    public  void  goToDefaultFargment(){

        //////////////////
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }


}



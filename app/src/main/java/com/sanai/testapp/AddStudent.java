package com.sanai.testapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import jango.Django;

public class AddStudent extends Fragment {

    EditText stdName ,stdFamilyName,stdNumber  , stdScore ;
    Button saveStd ;
    Spinner mtspinner ;
    String name, familyName ;
    String stdnumber , score;
    int mtPosition , iscore;
    static  int PK_for_addUserToStudent;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_student,container,false);


        mtspinner =  view.findViewById(R.id.spinner1);
        stdName = view.findViewById(R.id.studentName);
        stdFamilyName = view.findViewById(R.id.studentFamilyName);
        stdNumber = view.findViewById(R.id.stdNumber);
        stdScore = view.findViewById(R.id.stdscore);
        saveStd = view.findViewById(R.id.saveStd);




        //__________________________________________________________________________\\
        attitudeSelection();
        setSpinner ();
        saveStud();

        return  view;
    }
    public  void saveStud (){
        saveStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = stdName.getText().toString();
                familyName = stdFamilyName.getText().toString();
                stdnumber = (stdNumber.getText().toString());
                score = (stdScore.getText().toString());

                if(name.matches("") || familyName.matches("") || stdnumber.matches("")
                || score.matches("") ){

                    Toast.makeText(getActivity(), "خطا در داده", Toast.LENGTH_SHORT).show();
                }
                else{
                    iscore =Integer.parseInt(score);
                    boolean newUser = IsNewUser(stdnumber);
                    if(!newUser){ //false
                        Toast.makeText(getActivity(), "کاربر موجود است", Toast.LENGTH_SHORT).show();
                    }else {
                        dialog ();
                    }
                    //if its new go to save else alarm

                }


                }

                // check its new or not
                //if its new show dialog


        });

    }
    public  void  attitudeSelection (){
        mtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
              mtPosition =pos;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
    public  void  goToDefaultFargment(){
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();


    }
    public  void setSpinner (){
        ArrayList<String> mtStringList = new ArrayList<>();
        for(int i=0 ; i< Django.MTList.size();i++){
            mtStringList.add(getTerm(Django.MTList.get(i).getTerm_of_mt_PK()) + "    " +getMajorName(Django.MTList.get(i).getMajor_of_mt_PK())
                    );
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, mtStringList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mtspinner.setAdapter(adapter);
    }
    public  void  dialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("افزودن دانشجو");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // add this student to backend
                insertUser();
                goToDefaultFargment();
                dialog.dismiss();
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
    public String getTerm(int termPK){
        for(int i=0 ; i<Django.termList.size();i++){
            if( termPK == Django.termList.get(i).getTermPK() ){
                return Django.termList.get(i).getDate();
            }
        }
        return  "";
    }
    public String getMajorName(int majorPK){
        for(int i=0 ; i<Django.majorList.size();i++){
            if( majorPK == Django.majorList.get(i).getMajorPK() ){
                return Django.majorList.get(i).getMajorTitle();
            }
        }
        return  "";

    }
    public boolean IsNewUser(String number){
        for (int i=0 ; i< Django.userArrayList.size() ; i++){
            if (Django.userArrayList.get(i).getUsername().matches(number)){
                return false;
            }
        }
        return  true;

    }
    public void insertUser( ){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("username" ,stdnumber );
            postData.put("password",stdnumber+"std" );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"user/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getActivity(), response.toString()+"", Toast.LENGTH_LONG).show();
                        System.out.println(response);

                        try {
                            PK_for_addUserToStudent = response.getInt("pk");

                            insertStudent();

                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
    public void insertStudent( ){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

       /* Toast.makeText(getActivity(), " pk of std" + PK_for_addUserToStudent, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), name + "\n" +
                familyName + "\n" +
                iscore + " \n" +Django.MTList.get(mtPosition).getMtPK()
                , Toast.LENGTH_SHORT).show();*/

        JSONObject postData = new JSONObject();
        try {
            postData.put("user", PK_for_addUserToStudent);
            postData.put("name" , name);
            postData.put("familyname", familyName);
            postData.put("grade" , iscore);
            postData.put("entrance_mt" , Django.MTList.get(mtPosition).getMtPK());
            postData.put("selected_tmt" ,null);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"student/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "دانشجو افزوده شد", Toast.LENGTH_LONG).show();
                        System.out.println(response);
                        //udate user & teacher list for next oprations
                        Django.getUserListFromJango();
                        Django.getStudentListFromJango();


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

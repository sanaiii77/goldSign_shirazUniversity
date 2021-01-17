package com.sanai.testapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import jango.Django;
import jango.JsonPlaceHolderApi;
import jango.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTeacher extends Fragment {

    public static final String URL =  "http://192.168.1.105:8000/api/";
    EditText tname,tfamilyname,tNumber  ;
    String name, familyName,username ;
    Button saveTeacher ;
    public  static int PK_for_addUserToTeacher = 0 ;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_teacher,container,false);

        //___________________________________________________________________________________
        tname =view.findViewById(R.id.teacherName);
        tfamilyname = view.findViewById(R.id.teacherFamilyName);
        tNumber = view.findViewById(R.id.teacherNumber);
        saveTeacher = view.findViewById(R.id.saveTeacher);
        save ();
        return  view;
    }

    public  void  save(){

        saveTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tname.getText().toString();
                familyName = tfamilyname.getText().toString();
                username = tNumber.getText().toString();

                if(username.matches("") || name.matches("") || familyName.matches("")){
                    Toast.makeText(getActivity(), "خطا در داده", Toast.LENGTH_SHORT).show();

                }else {
                    boolean isNew = IsNewTeacher(username);
                    if (isNew == false){
                        Toast.makeText(getActivity().getApplicationContext(), "کاربر موجود است", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        //addMajor();
                        dialog();
                        //insertUser(tNumber.toString(),"pass2",getActivity().getApplicationContext());
                        //AddUserToTeacher (userPK);
                    }
                }

            }
        });

    }
    public  void  goToDefaultFargment(){
        ///////////////
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    public  void  dialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("افزودن استاد");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                insertUser(username,username+99  ,getActivity().getApplicationContext());
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
    public boolean IsNewTeacher(String number){
        for (int i=0 ; i< Django.userArrayList.size() ; i++){
            if (Django.userArrayList.get(i).getUsername().matches(number)){
                return false;
            }
        }
        return  true;

    }
    public void insertUser(String username , String password , final Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("username" , username);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"user/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            PK_for_addUserToTeacher = response.getInt("pk");

                            insertTeacher( name , familyName , getActivity().getApplicationContext());

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
    public void insertTeacher(String name , String familyName, final Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        Toast.makeText(context, PK_for_addUserToTeacher+"", Toast.LENGTH_SHORT).show();

        JSONObject postData = new JSONObject();
        try {
            postData.put("name" , name);
            postData.put("familyname", familyName);
            postData.put("user", PK_for_addUserToTeacher);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"teacher/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "استاد افزوده شد", Toast.LENGTH_LONG).show();
                        System.out.println(response);
                        //udate user & teacher list for next oprations
                        Django.getUserListFromJango();
                        Django.getTeachersListFromJango();


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

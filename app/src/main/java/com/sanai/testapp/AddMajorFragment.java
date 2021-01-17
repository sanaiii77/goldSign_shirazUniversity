package com.sanai.testapp;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.List;

import jango.Django;
import jango.JsonPlaceHolderApi;
import jango.Major;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMajorFragment extends Fragment {

    EditText majorName ;
    Button addMajor ;
    Spinner majorsSpinner ;
    String newMajor;
    public  static List<String> majors = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_major_fragment, container, false);
        majorName = view.findViewById(R.id.majorName);
        addMajor = view.findViewById(R.id.addMajor);
        majorsSpinner = view.findViewById(R.id.listOfMajor);
        //___________________________________________________________________________________
        getMajorListSetSpinner();
        add();

        return view;
    }
    public  void  add(){
        addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMajor = majorName.getText().toString();
                boolean bool = isNewMajor(newMajor);
                if (bool== true){
                    insertMajor(newMajor);

                }else {
                    Toast.makeText(getActivity(),"گرایش موجود است",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public  boolean isNewMajor(String name){
        for (int i=0 ; i < Django.majorList.size() ;i++){
            if (name.matches(majors.get(i))){
                return  false;
            }
        }
        return true;

    }
    public  void setSpinner (){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item,majors);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        majorsSpinner.setAdapter(adapter);
    }
    public  void  insertMajor(String newMajor){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        JSONObject postData = new JSONObject();
        try {
            postData.put("title" , newMajor);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, Django.URL+"major/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "گرایش افزوده شد", Toast.LENGTH_LONG).show();
                        //udate user & teacher list for next oprations
                        getMajorListSetSpinner();
                        goToDefaultFargment();


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
    public  void  getMajorListSetSpinner(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Django.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Major>> call = jsonPlaceHolderApi.getMajor();
        call.enqueue(new Callback<List<Major>>() {
            @Override
            public void onResponse(Call<List<Major>> call, retrofit2.Response<List<Major>> response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                Django.majorList = response.body() ;
                majors = new ArrayList<>();
                for (int i=0 ; i<Django.majorList.size();i++){
                    majors.add(Django.majorList.get(i).getMajorTitle());
                }
                setSpinner();


            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;

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
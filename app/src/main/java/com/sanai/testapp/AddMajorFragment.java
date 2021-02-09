package com.sanai.testapp;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import jango.Django;
import jango.JsonPlaceHolderApi;
import jango.Major;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sanai.testapp.MainActivity.Bar;

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

        //__________________________________________________________________________________

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
                if(newMajor.matches("")){
                    Toast.makeText(getActivity(),"نام گرایش خالی است",Toast.LENGTH_LONG).show();

                }
                else {
                    boolean bool = isNewMajor(newMajor);
                    if (bool== true){
                        dialog(newMajor);

                    }else {
                        Toast.makeText(getActivity(),"گرایش موجود است",Toast.LENGTH_LONG).show();

                    }

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
                        Django.getMajorList();



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
        majors = new ArrayList<>();
        for (int i=0 ; i<Django.majorList.size();i++){
            majors.add(Django.majorList.get(i).getMajorTitle());
        }
        setSpinner();
        return;

    }
    public  void  goToDefaultFargment(){

        Django.getMajorList();

        //////////////////
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flcontent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        Bar.setVisibility(View.GONE);

        transaction.commit();

    }
    public void  dialog (final String newMajor ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("افزودن گرایش : "+newMajor);
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                insertMajor(newMajor);
                majorName.setText("");
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



}
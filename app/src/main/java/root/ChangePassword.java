package root;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanai.testapp.R;
import com.sanai.testapp.defaultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import jango.Django;


public class ChangePassword extends Fragment {


    EditText password ,reEnterPassword;
    Button cancele , save ;

    String passwordText ,repasswordText ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password,container,false);
        password = view.findViewById(R.id.password);
        reEnterPassword = view.findViewById(R.id.repassword);
        cancele = view.findViewById(R.id.cancelChangePassword);
        save =  view.findViewById(R.id.changePassword);
        //___________________________________________________________________________________\\
        change();
        cancelChangePass();



        return  view;
    }

    public void change(){

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordText = password.getText().toString();
                repasswordText = reEnterPassword.getText().toString();

                if(passwordText.matches("") || repasswordText.matches("")){
                    Toast.makeText(getActivity(), "رمز عبور خالی است", Toast.LENGTH_SHORT).show();

                }else {
                    if (passwordText.equals(repasswordText)) {
                        //backend do change pass
                        if(passwordText.length() >=8){
                            changePass(passwordText);
                            goToDefaultFargment();
                        }else {
                            Toast.makeText(getActivity(), "رمز عبور باید شامل حداقل 8 کاراکتر باشد", Toast.LENGTH_SHORT).show();


                        }




                    } else {

                        password.setText("");
                        reEnterPassword.setText("");
                        Toast.makeText(getActivity(), "رمز عبور مطابقت ندارد", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }

    public  void changePass(String newPassword){

        try {
            String URL =  Django.URL+"user/"+ Django.USER_PK +"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("password", newPassword);

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getActivity(), "رمز عبور تغییر کرد", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }) {

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonOblect);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void cancelChangePass (){

        cancele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToDefaultFargment();

            }
        });

    }

    public  void  goToDefaultFargment(){
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        if(Django.USER_ROLE.matches(Django.STUDENT_STRING)){
            transaction.replace(R.id.flContainerForStudent, newFragment);

        } else if (Django.USER_ROLE.matches(Django.TEACHER_STRING)) {
            transaction.replace(R.id.flContainerForTechear, newFragment);

        }
        else {
            transaction.replace(R.id.flcontent, newFragment);

        }
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

    }
}

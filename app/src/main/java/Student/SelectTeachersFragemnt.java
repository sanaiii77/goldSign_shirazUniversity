package Student;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanai.testapp.R;
import com.sanai.testapp.defaultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jango.Django;
import jango.TMT;


public class SelectTeachersFragemnt extends Fragment {

    ArrayList<String> teachersName = new ArrayList<>();
    Spinner choice1 , choice2 ,choice3 , choice4, choice5 ;
    Button save , cancel ;
    int[] teachersPK_selected = new int[5];
    int[] tmt_to_goldsign = new int[5];
    FrameLayout notAvailable;
    LinearLayout isAvailable;
    TextView textView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_teachers_fragemnt,container,false);

        choice1 = view.findViewById(R.id.selectTeacher1);
        choice2 = view.findViewById(R.id.selectTeacher2);
        choice3 = view.findViewById(R.id.selectTeacher3);
        choice4 = view.findViewById(R.id.selectTeacher4);
        choice5 = view.findViewById(R.id.selectTeacher5);
        save = view.findViewById(R.id.saveChoiceOfTeacher);
        cancel = view.findViewById(R.id.cancelChoiceOfTeacher);
        notAvailable = view.findViewById(R.id.selectionIsNotAvailable);
        isAvailable =view.findViewById(R.id.teacherSelectionIsAvailable);
        textView = view.findViewById(R.id.textviewSelectionIsNotAvailable);

        /*_______________________________________________________________*/
        if(doneOrNot()){
            textView.setText(getStringofTeacherList());
            notAvailable.setVisibility(View.VISIBLE);
            isAvailable.setVisibility(View.GONE);


        }else {
            notAvailable.setVisibility(View.GONE);
            isAvailable.setVisibility(View.VISIBLE);
            setSpinner();
            click();
            selectOnSpinnerItem();

        }


        return  view;
    }

    public String getStringofTeacherList(){
        String[] tmp = new String[5];
        for(int i=0 ; i<Django.goldReqList.size();i++){
            if(Django.goldReqList.get(i).getStudent_of_goldreq_PK()==Django.ROLE_PK){ //done
                int tmtPK = Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK() ;
                Toast.makeText(getActivity(),"teacher name  "+getTeacherName(tmtPK),Toast.LENGTH_LONG).show();
                tmp[Django.goldReqList.get(i).getPriority_of_goldreq_PK() - 1] = getTeacherName(tmtPK);
            }
        }
        String str = "شما اساتید خود را به صورت زیر ثبت کرده اید \n";

        for (int i=0 ; i<tmp.length ;i++){
            str += i +" " + tmp[i] + "\n" ;

        }

        return str;

    }

    public String getTeacherName(int tmtPK){
        int teacherPK =-1 ;

        for (int i=0 ; i<Django.TMTList.size() ; i++){
            if(Django.TMTList.get(i).getTmtPK() == tmtPK){
                teacherPK = Django.TMTList.get(i).getTeacher_of_tmt_PK();
                break;
            }
        }
        for (int i=0 ; i<Django.teacherArrayList.size() ; i++){
            if(Django.teacherArrayList.get(i).getTeacherPK() == teacherPK){
                 return Django.teacherArrayList.get(i).getTeacherName() +   Django.teacherArrayList.get(i).getTeacherFamilyName();
            }
        }




        return  "";

    }
    //***************************************************************************************

    public boolean doneOrNot(){
        for(int i=0 ; i<Django.goldReqList.size();i++){
            if(Django.goldReqList.get(i).getStudent_of_goldreq_PK()==Django.ROLE_PK){ //done
                return  true;
            }
        }
        return  false;
    }
    //***************************************************************************************
    public  void  setSpinner(){
        getTeachersName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        choice1.setAdapter(adapter);
        //****************************************************************************************************
         adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        choice2.setAdapter(adapter);
        //****************************************************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        choice3.setAdapter(adapter);
        //****************************************************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        choice4.setAdapter(adapter);
        //****************************************************************************************************
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        choice5.setAdapter(adapter);
    }
    //***************************************************************************************
    public  void  click(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOnSpinnerItem();
                getListOfTMT();
                dialog();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDefaultFragment();

            }
        });
    }
    //***************************************************************************************
    public  void selectOnSpinnerItem(){
        choice1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                teachersPK_selected[0] = Django.teacherArrayList.get(pos).getTeacherPK();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //******************************************************************************************
        choice2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub

                teachersPK_selected[1] = Django.teacherArrayList.get(pos).getTeacherPK();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //******************************************************************************************
        choice3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                teachersPK_selected[2] = Django.teacherArrayList.get(pos).getTeacherPK();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //******************************************************************************************
        choice4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub

                teachersPK_selected[3] = Django.teacherArrayList.get(pos).getTeacherPK();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //******************************************************************************************
        choice5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                teachersPK_selected[4] = Django.teacherArrayList.get(pos).getTeacherPK();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //******************************************************************************************
    }
    //***************************************************************************************
    public  void  goToDefaultFragment(){
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flContainerForStudent, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    //***************************************************************************************
    public void getTeachersName(){
        for (int i=0 ; i< Django.teacherArrayList.size() ; i++){
            teachersName.add(Django.teacherArrayList.get(i).getTeacherName()+"  " +Django.teacherArrayList.get(i).getTeacherFamilyName() );
        }

    }
    //***************************************************************************************
    public int getTMTPK(int teacherpk){
        int mtPk = Main2Activity.mt.getMtPK();

        for (int i=0 ; i< Django.TMTList.size() ; i++){
            if(Django.TMTList.get(i).getMt_of_tmt_PK() == mtPk && Django.TMTList.get(i).getTeacher_of_tmt_PK() ==teacherpk){
                return Django.TMTList.get(i).getTmtPK();
            }
        }
        return  -1;

    }
    //***************************************************************************************
    public void getListOfTMT(){
        for(int i =0 ; i<5 ;i++){
            tmt_to_goldsign[i] = getTMTPK(teachersPK_selected[i]);
        }
    }
    //***************************************************************************************
    public void insertGoldReq(int tmtPK , int priority ,final Context context){ //Django.Role_pk = student pk
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JSONObject postData = new JSONObject();
        try {
            postData.put("student" , Django.ROLE_PK);
            postData.put("tmt_teacher", tmtPK);
            postData.put("priority", priority);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, "http://192.168.1.105:8000/api/gold-req/create/", postData, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), response.toString()+"", Toast.LENGTH_LONG).show();
                        System.out.println(response);




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
    public  void  dialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("اانتخاب اساتید");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                for(int i=0 , j=1 ; i<5 ;i++ , j++){
                    //Toast.makeText(getActivity(), tmt_to_goldsign[i]+" "+j+" "+Django.ROLE_PK, Toast.LENGTH_LONG).show();
                    insertGoldReq(tmt_to_goldsign[i],j,getActivity());
                }
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

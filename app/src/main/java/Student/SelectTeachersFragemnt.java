package Student;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanai.testapp.MySelectTeacherAdapter;
import com.sanai.testapp.MyStudentRecycleviewAdapter;
import com.sanai.testapp.R;
import com.sanai.testapp.defaultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jango.Django;
import jango.GoldRequest;
import jango.TMT;
import jango.Teacher;


public class SelectTeachersFragemnt extends Fragment {

    static ArrayList<String> teachersName = new ArrayList<>();
    static ArrayList<TMT> TMTArray = new ArrayList<>();
    static ArrayList<Teacher> teachers = new ArrayList<>();// teacher of this mt
    static int[] teachersPK_selected ;// teacher of this mt
    static int[] tmtPK_selected ;// teacher of this mt
    static String[] teachersName_selected ;// teacher of this mt
    static  int len= 0;
    int teacherPs;

    Button save , cancel ;
    FrameLayout notAvailable;
    LinearLayout isAvailable;
    TextView textView ;
    RecyclerView recyclerView;

    EditText priority ;
    Spinner teachersSpiner;
    ImageButton addPriority ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_teachers_fragemnt,container,false);

        save = view.findViewById(R.id.saveChoiceOfTeacher);
        cancel = view.findViewById(R.id.cancelChoiceOfTeacher);
        notAvailable = view.findViewById(R.id.selectionIsNotAvailable);
        isAvailable = view.findViewById(R.id.isAvailable);

        textView = view.findViewById(R.id.textviewSelectionIsNotAvailable);
        recyclerView  = view.findViewById(R.id.selectteacherRecycleview);
        priority = view.findViewById(R.id.priorityOfSelection);
        teachersSpiner = view.findViewById(R.id.teachersNameInSelection);
        addPriority = view.findViewById(R.id.addPriority);

        /*_______________________________________________________________*/
        getTeacherArrayOfMT();
        boolean bool = doneSelection();

        if(bool){ //done
            textView.setText(stringOfTeacher());
            notAvailable.setVisibility(View.VISIBLE);
            isAvailable.setVisibility(View.GONE);


        }else { //not done
            notAvailable.setVisibility(View.GONE);
            isAvailable.setVisibility(View.VISIBLE);

            spinnerSelection();
            click();
        }

        return  view;
    }


    public  void getTeacherArrayOfMT (){
        TMTArray = new ArrayList<>();
        teachers = new ArrayList<>();
        teachersName = new ArrayList<>();
        for(int i=0 ; i <Django.TMTList.size() ;i++){
            if(Django.TMTList.get(i).getMt_of_tmt_PK() == Main2Activity.mt.getMtPK()
            && Django.TMTList.get(i).getCap() != 0){ //in this term and have cap

                TMTArray.add(Django.TMTList.get(i));
                teachers.add(Django.getTeacher(Django.TMTList.get(i).getTeacher_of_tmt_PK()));
            }
        }
        len = teachers.size();
        teachersPK_selected = new int[len];
        teachersName_selected = new String[len];
        tmtPK_selected = new  int[len];
        for(int i=0 ; i<len ; i++){
            teachersName_selected[i] = "";
            teachersPK_selected[i] = -1;
            tmtPK_selected[i] = -1;

        }
        setRecycleView();
        getteachersName();
    }
    public void  getteachersName(){
        for(int i=0 ; i < teachers.size() ; i++){
            teachersName.add(teachers.get(i).getTeacherName() + " "+ teachers.get(i).getTeacherFamilyName());
        }
        setSpinner();

    }
    public  void setSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.spinner_item, teachersName);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        teachersSpiner.setAdapter(adapter);
    }

    public void setRecycleView(){

        MySelectTeacherAdapter myStudentRecycleviewAdapter = new MySelectTeacherAdapter(this,teachersName_selected);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity() );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myStudentRecycleviewAdapter);
    }
    //***************************************************************************************
    public boolean doneSelection(){
        for(int i=0 ; i<Django.goldReqList.size();i++){
            if(Django.goldReqList.get(i).getStudent_of_goldreq_PK()==Django.ROLE_PK){ //done
                return  true;
            }
        }
        return  false;
    }

    //***************************************************************************************
    public String stringOfTeacher(){
        String str= "";
        String[] names = new String[len];

        for(int i=0 ; i<Django.goldReqList.size();i++){
            if(Django.goldReqList.get(i).getStudent_of_goldreq_PK()==Django.ROLE_PK){ //done
                int pos = Django.goldReqList.get(i).getPriority_of_goldreq_PK();
                int tmtPK = Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK();
                TMT tmt = Django.getTMT(tmtPK);
                Teacher teacher = Django.getTeacher(tmt.getTeacher_of_tmt_PK());
                names[pos-1] = pos+"." +" " + teacher.getTeacherName() + " " + teacher.getTeacherFamilyName();
            }
        }
        for (int i=0 ; i<len ; i++){
            str+= names[i] + "\n";
        }
        return  str;

    }
    //***************************************************************************************

    public  void  click(){
        addPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = -1 ;
                if(priority.getText().toString().matches("")){

                }
                else{
                    p = Integer.parseInt(priority.getText().toString());
                    priority.setText("");

                }

                if(p>0 & p <= len){
                    int teacherPK = TMTArray.get(teacherPs).getTeacher_of_tmt_PK();
                    String name = teachersName.get(teacherPs);
                    teachersPK_selected[teacherPs] = teacherPK;
                    tmtPK_selected[teacherPs] = TMTArray.get(teacherPs).getTmtPK();
                    teachersName_selected[p-1] = name;
                    setRecycleView();

                }else{
                    Toast.makeText(getActivity(), "الویت اشتباه است", Toast.LENGTH_SHORT).show();
                }


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool = checkSelection();
                if(bool) {
                    dialog();
                }

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

    public  void  spinnerSelection (){
        teachersSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                teacherPs =pos;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
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
    public  boolean  checkSelection(){
        for (int i=0 ; i<len ; i++){
            if(tmtPK_selected[i] == -1){
                Toast.makeText(getActivity(), "انتخاب استاد کامل نیست", Toast.LENGTH_SHORT).show();
                return false;}
        } // blank check

        for (int i=0 ; i<teachersPK_selected.length ; i++){
            for (int j=i+1 ; j<teachersPK_selected.length ; j++){
                if(teachersPK_selected[i] == teachersPK_selected[j]){
                    Toast.makeText(getActivity(), "انتخاب استاد تکراری مجاز نیست", Toast.LENGTH_SHORT).show();
                    return false;

                }

            }
        } // repitietion cheked


        return  true;
    }
    //***************************************************************************************
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


        builder.setTitle("انتخاب اساتید");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                for (int i=0 ; i<len ; i++){
                    insertGoldReq(tmtPK_selected[i],i+1,getActivity());
                }
                Django.getGoldreqListFromJango();
                goToDefaultFragment();
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

package Teacher;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sanai.testapp.R;
import com.sanai.testapp.TmtCalenderForTeacherActivity;
import com.sanai.testapp.defaultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import jango.Django;
import jango.MT;
import jango.Major;
import jango.Student;
import jango.TMT;
import jango.Term;


public class SelectStudentsFragment extends Fragment {
    Spinner activeTermForSelectGoldSign ;
    TextView textView ;
    ListView listOfTermsStudent;

    ArrayList<String> spiinerData ;
    ArrayList<String> students ;
    ArrayList<Student> studentList ;
    ArrayList<Student> studentist_withFirstChoice ;
    int spinnerPosition;
    static TMT tmt ;
    static  Student selected_std;
    static  String selected_std_name;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_students,container,false);
        activeTermForSelectGoldSign = view.findViewById(R.id.listOfActiveTermForTeacher);
        textView = view.findViewById(R.id.ShowTextOflistOfActiveTMTFrTeacher);
        listOfTermsStudent = view.findViewById(R.id.listOfStudentInActiveTMTFrTeacher);

        generateSpinnerData();
        spinnerSelection();
        listOnClick();


        /*_______________________________________________________________*/

        return  view;
    }

    public void  listOnClick(){
        listOfTermsStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 selected_std = studentList.get(position);
                 selected_std_name = students.get(position);
                 boolean b = hasFirstChoiceOrNot(selected_std);
                 dialog(b);



            }
        });
    }
    public void  spinnerSelection (){
        activeTermForSelectGoldSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                spinnerPosition = pos;
                textView.setText(spiinerData.get(pos));
                tmt = Main3Activity.teacher_active_TMT_but_notdone.get(pos);
                studentList = getStudents(tmt);
                studentist_withFirstChoice = getStudents_withFirstChoice(tmt);
                students = getStudentsStringArray(studentList);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,students);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                listOfTermsStudent.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
    public void generateSpinnerData(){
        spiinerData = new ArrayList<>();

        //Toast.makeText(getActivity(), Main3Activity.teacher_active_TMT_but_notdone.size()+"", Toast.LENGTH_SHORT).show();

        for ( int i=0 ; i<Main3Activity.teacher_active_TMT_but_notdone.size();i++){
            TMT tmt = Main3Activity.teacher_active_TMT_but_notdone.get(i);
            MT mt = Django.getMT(tmt.getMt_of_tmt_PK());
            Term term = Django.getTerm(mt.getTerm_of_mt_PK());
            Major major = Django.getMajor(mt.getMajor_of_mt_PK());
            spiinerData.add(term.getDate() +" \n" + major.getMajorTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,spiinerData);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        activeTermForSelectGoldSign.setAdapter(adapter);
    }
    public static ArrayList<Student> getStudents(TMT tmt){
        ArrayList<Student> std = new ArrayList<>();
        //get All Std in the mt of  TMt
        for(int i = 0; i < Django.goldReqList.size(); i++){
            if( Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK() == tmt.getTmtPK()){
                //stdinTMT.add(Django.goldReqList.get(i));
                std.add(Django.getStudent(Django.goldReqList.get(i).getStudent_of_goldreq_PK()));

            }
        }
        return  std;


    }
    public  ArrayList<Student> getStudents_withFirstChoice(TMT tmt){
        ArrayList<Student> std = new ArrayList<>();
        //get All Std in the mt of  TMt

        for(int i = 0; i < Django.goldReqList.size(); i++){
            if( Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK() == tmt.getTmtPK()){
                if(Django.goldReqList.get(i).getPriority_of_goldreq_PK() == 1) {
                    std.add(Django.getStudent(Django.goldReqList.get(i).getStudent_of_goldreq_PK()));
                }

            }
        }

        return  std;


    }
    public  ArrayList<String> getStudentsStringArray(ArrayList<Student> std){
        //ArrayList<GoldRequest> stdinTMT = new ArrayList<>();
        ArrayList<String> students = new ArrayList<>();
        //get All Std in the mt of  TMt
        for(int i = 0; i < std.size(); i++){
                //stdinTMT.add(Django.goldReqList.get(i));
                students.add(  std.get(i).getStudentName() + " " + std.get(i).getStudentFamilyname()+"\n"+
                        "نمره : "+std.get(i).getGrade()

                );

        }
        return  students;


    }
    public void  goToDefaultFragment(){
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();


        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        /*transaction.replace(R.id.flContainerForTechear, newFragment);
        transaction.addToBackStack(null);


        // Commit the transaction
        transaction.commit();*/

        transaction.replace(R.id.flContainerForTechear,new TmtCalenderForTeacherActivity());
        transaction.commit();

    }
    public  void  dialog (final boolean b){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("امضای طلایی ");
        builder.setMessage("دانشجوی انتخابی شما: "+selected_std_name+"\n"+"آیا از انتخاب این دانشجو اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // add this student to backend
                if(b){
                    update_tmt_teacher(tmt , selected_std);
                    //update  tmt sts
                    update_tmt_sts(tmt);
                    Django.getStudentListFromJango();
                    Django.getTMTList();
                    Main3Activity.getListOfTeacherInTmt();


                }
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
    public void update_tmt_teacher(TMT tmt , Student std){

        try {
            String URL =  Django.URL+"student/" + std.getStudentPK()+"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("selected_tmt", tmt.getTmtPK());

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

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
    public void update_tmt_sts(TMT tmt){

        try {
            String URL =  Django.URL+"tmt/" + tmt.getTmtPK()+"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("sts", true);

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

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
    public  boolean hasFirstChoiceOrNot(Student s){

        for (int i=0 ; i<studentist_withFirstChoice.size() ; i++){
            if(s.getStudentPK() == studentist_withFirstChoice.get(i).getStudentPK()){
                return true;
            }
        }
        return  false;

    }



}

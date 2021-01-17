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
import com.sanai.testapp.defaultFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import jango.Django;
import jango.GoldRequest;
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
    int spinnerPosition;
    static TMT tmt ;
    static  Student selected_std;

    String url ="http://192.168.1.105:8000/api/";


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
                 dialog();
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
                studentList = getStudents(Main3Activity.teacher_active_TMT_but_notdone.get(pos));
                students = getStudentsStringArray(studentList);
                tmt = Main3Activity.teacher_active_TMT_but_notdone.get(pos);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,students);
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
            MT mt = Main3Activity.getMT(tmt.getMt_of_tmt_PK());
            Term term = Main3Activity.getTerm(mt.getTerm_of_mt_PK());
            Major major = Main3Activity.getMAjor(mt.getMajor_of_mt_PK());
            spiinerData.add(term.getDate() +" \n" + major.getMajorTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,spiinerData);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        activeTermForSelectGoldSign.setAdapter(adapter);
    }
    public static ArrayList<Student> getStudents(TMT tmt){
        //ArrayList<GoldRequest> stdinTMT = new ArrayList<>();
        ArrayList<Student> std = new ArrayList<>();
        //get All Std in the mt of  TMt
        for(int i = 0; i < Django.goldReqList.size(); i++){
            if( Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK() == tmt.getMt_of_tmt_PK()){
                //stdinTMT.add(Django.goldReqList.get(i));
                std.add(Main3Activity.getStudent(Django.goldReqList.get(i).getStudent_of_goldreq_PK()));

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
                students.add("نمره : "+std.get(i).getGrade()+ "\n"+
                                std.get(i).getStudentName() + " " + std.get(i).getStudentFamilyname()
                        );

        }
        return  students;


    }
    public void  goToDefaultFragment(){
        Fragment newFragment = new defaultFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.flContainerForTechear, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
    public  void  dialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("افزودن دانشجو");
        builder.setMessage("آیا از صحت اطلاعات اطمینان دارین ؟");

        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // add this student to backend
                update_tmt_teacher(tmt , selected_std);
                Django.getStudentListFromJango();
                Main3Activity.getListOfTeacherInTmt();
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
            String URL =  url+"student/" + std.getStudentPK()+"/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("selected_tmt", tmt.getTmtPK());

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.PATCH, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Toast.makeText(getActivity(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
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



}

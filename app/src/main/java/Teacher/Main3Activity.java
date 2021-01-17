package Teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import jango.DatetimeRange;
import jango.Django;
import jango.GoldRequest;
import jango.MT;
import jango.Major;
import jango.Student;
import jango.TMT;
import jango.Teacher;
import jango.Term;
import root.ChangePassword;

import com.sanai.testapp.MainActivity;
import com.sanai.testapp.R;
import com.sanai.testapp.TmtCalenderForTeacherActivity;

import Student.ResultFragmnet;
import root.CheckTime;
import root.LoginActivity;


import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    public  static String STUDENT_Time ="time_for_student";
    public  static String TEACHER_TIME ="time_for_teacher";
    public  static String NOT_START_TIME ="notStart";
    public  static String FINISH__TIME ="finish";
    //****************************************************
    FrameLayout container ;
    LinearLayout selectTeacher , changePass ,result ;
    FragmentTransaction fragmentTransaction;
    ImageButton logOutTeacher ;
    //*************************************************************
    public static ArrayList<TMT> teacher_active_TMT = new ArrayList<>();
    public static ArrayList<TMT> teacher_active_TMT_but_notdone = new ArrayList<>();
    public static ArrayList<TMT> teacher_active_TMT_but_done = new ArrayList<>();
    public static ArrayList<TMT> teacher_finish_TMT = new ArrayList<>();
    public static ArrayList<TMT> teacher_notstart_TMT = new ArrayList<>();
    public static ArrayList<TMT> teacher_stdToteach_TMT = new ArrayList<>();
    public static ArrayList<TMT> teacher_total_TMT = new ArrayList<>();

    //*************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //**************************************************************
        container = findViewById(R.id.flContainerForTechear);
        selectTeacher = findViewById(R.id.selectStudentByTechearButton);
        changePass = findViewById(R.id.changepasswordByTechearButton);
        result = findViewById(R.id.showResultOfSelectionStudentByTeacherstButton);
        logOutTeacher = findViewById(R.id.logOutTeacher);

        //**************************************************************
        Toast.makeText(getApplicationContext(), Django.today[0]+"-"+Django.today[1]+"-"+Django.today[2]+"-", Toast.LENGTH_SHORT).show();
        getListOfTeacherInTmt();
        clickButton ();

    }

    public void  clickButton (){
        selectTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacher_active_TMT_but_notdone.size() ==0){
                    Toast.makeText(getApplicationContext() , "انجام شده است." ,Toast.LENGTH_LONG).show();
                }
                else {
                    fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flContainerForTechear,new SelectStudentsFragment());
                    fragmentTransaction.commit();

                }


            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //done
                fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerForTechear,new ChangePassword());
                fragmentTransaction.commit();

            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerForTechear,new TmtCalenderForTeacherActivity());
                fragmentTransaction.commit();

            }
        });
        logOutTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Main3Activity.this, LoginActivity.class);
                Main3Activity.this.startActivity(mainIntent);
                Main3Activity.this.finish();

            }
        });
    }
    public static void getListOfTeacherInTmt(){

        teacher_active_TMT = new ArrayList<>();
        teacher_active_TMT_but_done = new ArrayList<>();
        teacher_total_TMT = new ArrayList<>();
        teacher_finish_TMT = new ArrayList<>();
        teacher_active_TMT_but_notdone = new ArrayList<>();
        teacher_notstart_TMT = new ArrayList<>();
        teacher_stdToteach_TMT = new ArrayList<>();


        for (int i=0 ; i <Django.TMTList.size() ; i++){
            if(Django.TMTList.get(i).getTeacher_of_tmt_PK() == Django.ROLE_PK &&
                    Django.TMTList.get(i).getCap() != 0){
                //ostad dr in term cap dashet ro joda mikonim
                teacher_total_TMT.add(Django.TMTList.get(i));

            }
        }

        for (int i=0 ; i < teacher_total_TMT.size() ; i++){
            TMT tmt = teacher_total_TMT.get(i);
            MT mt = getMT(tmt.getMt_of_tmt_PK());
            Term term = getTerm(mt.getTerm_of_mt_PK());
            DatetimeRange teacher_datetimeRange = getDateRange(term.getTeacher_date_range_PK());
            DatetimeRange student_datetimeRange = getDateRange(term.getStudent_date_range_PK());
            String time = CheckTime.checkTime(student_datetimeRange , teacher_datetimeRange);
            if (time.matches(TEACHER_TIME)){
                teacher_active_TMT.add(teacher_total_TMT.get(i));

            }
            else if(time.matches(STUDENT_Time)){
                teacher_stdToteach_TMT .add(teacher_total_TMT.get(i));
            }
            else if(time.matches(NOT_START_TIME)){
                teacher_notstart_TMT.add(teacher_total_TMT.get(i));
            }
            else if(time.matches(FINISH__TIME)){
                teacher_finish_TMT.add(teacher_total_TMT.get(i));
            }

        }

        for (int i=0 ; i<teacher_active_TMT.size();i++){
           // Toast.makeText(this, "active tmt pk : " +teacher_active_TMT.get(i).getTmtPK() , Toast.LENGTH_SHORT).show();
            boolean bool = someOneGetGoldSign(teacher_active_TMT.get(i) );
            if (!bool){ // not done false
                //Toast.makeText(this, "active tmt :"+teacher_active_TMT.get(i).getTmtPK() , Toast.LENGTH_SHORT).show();
                teacher_active_TMT_but_notdone.add(teacher_active_TMT.get(i));

            }
            else{
                teacher_active_TMT_but_done.add(teacher_active_TMT.get(i));
            }

        }

    }
    public static MT getMT (int pk){
        for (int i=0 ; i<Django.MTList.size();i++){
            if(Django.MTList.get(i).getMtPK() == pk){
                return Django.MTList.get(i);
            }
        }
        return null;

    }
    public static Term getTerm (int pk){
        for (int i=0 ; i<Django.termList.size();i++){
            if(Django.termList.get(i).getTermPK() == pk){
                return Django.termList.get(i);
            }
        }
        return null;

    }
    public static Major getMAjor (int pk){
        for (int i=0 ; i<Django.majorList.size();i++){
            if(Django.majorList.get(i).getMajorPK() == pk){
                return Django.majorList.get(i);
            }
        }
        return null;

    }
    public static DatetimeRange getDateRange (int pk){
        for (int i=0 ; i<Django.dateRangeList.size();i++){
            if(Django.dateRangeList.get(i).getDateRangePK() == pk){
                return Django.dateRangeList.get(i);
            }
        }
        return null;

    }
    public static Student getStudent (int pk){
        for (int i=0 ; i<Django.studentArrayList.size();i++){
            if (Django.studentArrayList.get(i).getStudentPK() == pk){
                return Django.studentArrayList.get(i);
            }

        }
        return null;

    }
    //gold req hai k mle in mtt hstnd v prioritt=1 drn bar migrdone
    public static ArrayList<GoldRequest> getgoldRrqofTheTMT_withFirstChoice( TMT tmt ){
        ArrayList<GoldRequest> stdinTMT = getgoldRrqofTheTMT(tmt);
        ArrayList<GoldRequest> tmp = new ArrayList<>();
        //get All Std in the mt of  TMt
        for(int i=0 ; i <stdinTMT.size();i++){
            if( stdinTMT.get(i).getPriority_of_goldreq_PK() == 1){
                tmp.add(Django.goldReqList.get(i));
            }
        }
        return  tmp;


    }
    //hame req hai k k in tmt drn bar migrdone
    public static ArrayList<GoldRequest> getgoldRrqofTheTMT(TMT tmt ){
        ArrayList<GoldRequest> stdinTMT = new ArrayList<>();
        //get All Std in the mt of  TMt
        for(int i=0 ; i <Django.goldReqList.size();i++){
            if( Django.goldReqList.get(i).getTmt_teacher_of_goldreq_PK() == tmt.getMt_of_tmt_PK()){
                stdinTMT.add(Django.goldReqList.get(i));

            }
        }
        return  stdinTMT;


    }
    public  static  boolean someOneGetGoldSign(TMT tmt){

        ArrayList<GoldRequest> goldRequestsOfTMT = getgoldRrqofTheTMT_withFirstChoice(tmt );

        for (int j=0 ; j<goldRequestsOfTMT.size() ; j++){
            Student std = getStudent(goldRequestsOfTMT.get(j).getStudent_of_goldreq_PK());
            if(std.getSelected_tmt_PK() >0) {
                //some onw get gold sign
                return true; //done

            }
        }
        return  false;// not done


    }

}

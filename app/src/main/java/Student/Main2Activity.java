package Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import Teacher.Main3Activity;
import jango.DatetimeRange;
import jango.Django;
import jango.MT;
import jango.Major;
import jango.TMT;
import jango.Term;
import root.ChangePassword;
import com.sanai.testapp.R;

import root.CompareTwoDatesTest;
import root.DateConverter;
import root.LoginActivity;
import root.converter;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main2Activity extends AppCompatActivity {
    //student
    public  static String TIME ="";
    public  static String STUDENT_Time ="time_for_student";
    public  static String TEACHER_TIME ="time_for_teacher";
    public  static String NOT_START_TIME ="notStart";
    public  static String FINISH__TIME ="finish";

    FrameLayout container ;
    LinearLayout selectTeacher , changePass ,result ;
    FragmentTransaction fragmentTransaction;
    public static MT mt ;
    public static TMT tmt ;
    public static Major major ;
    public static Term term;
    public static DatetimeRange student_date_range;
    public static DatetimeRange teacher_date_range;
    public static String name ;
    public static String familyName ;
    public static int grade;

    ImageButton logOutStudent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        container = findViewById(R.id.flContainerForStudent);
        selectTeacher = findViewById(R.id.selectTeachersByStudentButton);
        changePass = findViewById(R.id.changepasswordByStudentButton);
        result = findViewById(R.id.showResultOfSelectionTeacherByStudentButton);
        logOutStudent = findViewById(R.id.logOutStudent);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //*************************Call Methods********************************************

        getinfo();
        Django.getToday();
        clickButton ();
        TIME = checkTime();


        //****************************date*********************************

    }

    public void clickButton (){
        selectTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TIME.matches(STUDENT_Time)){
                    fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flContainerForStudent,new SelectTeachersFragemnt());
                    fragmentTransaction.commit();

                }
                else {
                    Toast.makeText(Main2Activity.this, "فعال نمی باشد", Toast.LENGTH_SHORT).show();

                }


            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerForStudent,new ChangePassword());
                fragmentTransaction.commit();

            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction   =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainerForStudent,new ResultFragmnet());
                fragmentTransaction.commit();

            }
        });
        logOutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Main2Activity.this, LoginActivity.class);
                Main2Activity.this.startActivity(mainIntent);
                Main2Activity.this.finish();
            }
        });
    }
    public void getinfo(){
        name = Django.appStudent.getStudentName();
        familyName = Django.appStudent.getStudentFamilyname();
        grade = Django.appStudent.getGrade();

        //__________________________________________________________________
        for(int i=0 ; i<Django.MTList.size() ; i++){
            if(Django.MTList.get(i).getMtPK() == Django.appStudent.getEntrance_mt_PK()){
                mt = Django.MTList.get(i);
                major = Django.getMajor(mt.getMajor_of_mt_PK());
                term = Django.getTerm(mt.getTerm_of_mt_PK());

                student_date_range = Django.getDateRange(term.getStudent_date_range_PK());
                teacher_date_range = Django.getDateRange(term.getTeacher_date_range_PK());
                /*Toast.makeText(this, "student start time : " + student_date_range.getStart() +
                        "\n"+ "student finish time : " + student_date_range.getEnd() , Toast.LENGTH_LONG).show();*/

            }
        }

        //__________________________________________________________________
        for(int i=0 ; i<Django.TMTList.size() ; i++){
            if(Django.TMTList.get(i).getTmtPK() == Django.appStudent.getSelected_tmt_PK()){
                tmt = Django.TMTList.get(i);
            }
        }
        //__________________________________________________________________
        return;
    }
    public  String checkTime ( ){
        if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after")){

            //Toast.makeText(getApplicationContext(),"notStart" , Toast.LENGTH_LONG).show();
            return NOT_START_TIME ;
        }
        //____________________________________________________________________________________________________________

        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
        && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            //Toast.makeText(getApplicationContext(),"studentActive" , Toast.LENGTH_LONG).show();
            return STUDENT_Time;
        }
        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){

            //Toast.makeText(getApplicationContext(),"studentActive" , Toast.LENGTH_LONG).show();
            return STUDENT_Time;
        }

        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){

            //Toast.makeText(getApplicationContext(),"studentActive" , Toast.LENGTH_LONG).show();
            return STUDENT_Time;
        }
        else if (CompareTwoDatesTest.comparison( student_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( student_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){

            //Toast.makeText(getApplicationContext(),"studentActive" , Toast.LENGTH_LONG).show();
            return STUDENT_Time;
        }
        //________________
        //______________________________________________________________________________________________
        if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            //Toast.makeText(getApplicationContext(),"teacherActive" , Toast.LENGTH_LONG).show();
            return TEACHER_TIME;

        }
        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("after") ){
            //Toast.makeText(getApplicationContext(),"teacherActive" , Toast.LENGTH_LONG).show();
            return TEACHER_TIME;

        }

        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("before")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){
            //Toast.makeText(getApplicationContext(),"teacherActive" , Toast.LENGTH_LONG).show();
            return TEACHER_TIME;

        }
        else if (CompareTwoDatesTest.comparison( teacher_date_range.getStart(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal")
                && CompareTwoDatesTest.comparison( teacher_date_range.getEnd(), CompareTwoDatesTest.convertTimeToString(Django.today[0],Django.today[1],Django.today[2])).matches("equal") ){
            //Toast.makeText(getApplicationContext(),"teacherActive" , Toast.LENGTH_LONG).show();
            return TEACHER_TIME;

        }
        //____________________________________________________________________________________________________________
        else {
           // Toast.makeText(getApplicationContext(),"finish" , Toast.LENGTH_LONG).show();

            return FINISH__TIME;

        }


    }
}

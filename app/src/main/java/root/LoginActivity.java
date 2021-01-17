package root;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sanai.testapp.MainActivity;
import com.sanai.testapp.R;

import java.util.ArrayList;
import java.util.List;

import jango.Django;
import jango.JsonPlaceHolderApi;
import jango.Student;
import jango.Teacher;
import jango.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Teacher.Main3Activity;
import Student.Main2Activity;


public class LoginActivity extends AppCompatActivity {
    Button login ;
    EditText user ,password ;
    String user_str ,password_str;

    public static final String URL =  "http://192.168.1.105:8000/api/";

    public  static    List<User> test = new ArrayList<User>();
    ProgressDialog mProgressDialog;

    // Django.USER_PK
    //Django.today year , month , day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        //****************************************



        //****************************************
        mProgressDialog = new ProgressDialog(getApplicationContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("در حال برقراری");

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Django.getUserListFromJango();
                Django.getStudentListFromJango();
                Django.getTeachersListFromJango();
                Django.getDateRangeTimeList();
                Django.getTermList();
                Django.getMajorList();
                Django.getMTList();
                Django.getTMTList();
                Django.getGoldreqListFromJango();
                Django.getToday();
            }

        });


        //*******************************************************

        click ();


    }
    public  void click (){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(LoginActivity.this, test.size()+" userlist size by test", Toast.LENGTH_SHORT).show();
                user_str = user.getText().toString();
                password_str = password.getText().toString();
                String whoIsUser = Django.check_user_password(user_str ,password_str);

                if (whoIsUser.matches(Django.STUDENT_STRING)){
                    Toast.makeText(LoginActivity.this, "test user role " + Django.USER_ROLE, Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(LoginActivity.this, Main2Activity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();

                }else if(whoIsUser.matches(Django.ADMIN_STRING)){
                    Toast.makeText(LoginActivity.this, "test user role " + Django.USER_ROLE, Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();


                }
                else if(whoIsUser.matches(Django.TEACHER_STRING)){
                    Intent mainIntent = new Intent(LoginActivity.this, Main3Activity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();

                }
                else if (whoIsUser.matches("not exist")){

                    //user.setText("");
                    password.setText("");
                    user_str = "";
                    password_str = "";
                    Toast.makeText(LoginActivity.this, "خطا در اطلاعات یا برقراری ارتباط", Toast.LENGTH_LONG).show();

                }



            }
        });
    }

    //__________________________________________________________________________________\\




    //____________________________________________________________________\\

}










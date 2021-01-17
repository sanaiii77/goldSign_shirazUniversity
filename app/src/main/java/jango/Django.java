package jango;


import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import root.DateConverter;

public class Django {
    public static final String URL =  "http://192.168.1.105:8000/api/";
    public static List<User> userArrayList  = new ArrayList<>();
    public static List<Student> studentArrayList =new ArrayList<>();
    public static List<Teacher> teacherArrayList =new ArrayList<>() ;
    public static List<Major> majorList = new ArrayList<>() ;
    public static List<DatetimeRange> dateRangeList = new ArrayList<>() ;
    public static List<Term> termList = new ArrayList<>() ;
    public static List<MT> MTList = new ArrayList<>() ;
    public static List<TMT> TMTList = new ArrayList<>() ;
    public static List<GoldRequest> goldReqList = new ArrayList<>() ;

    public static final String STUDENT_STRING =  "STUDENT";
    public static final String TEACHER_STRING =  "TEACHER";
    public static final String ADMIN_STRING =  "ADMIN";

    public static String USER_ROLE ;

    public static int USER_PK ;
    public static int ROLE_PK ;
    public static int[] today = new  int[3];

    public static User appUser ;
    public static Student appStudent ;
    public static Teacher appTeacher ;

    //_______________________________________________________________________

    public static void getUserListFromJango(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<User>> call = jsonPlaceHolderApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {

                List<User>  userlist = response.body()  ;
                Django.userArrayList = userlist ;


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
               // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;
    }
    public static void getTeachersListFromJango(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Teacher>> call = jsonPlaceHolderApi.getTeachers();
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                List<Teacher> teacherList = response.body();
                Django.teacherArrayList = teacherList;
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static void getDateRangeTimeList(){

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<DatetimeRange>> call = jsonPlaceHolderApi.getDateRange();
        call.enqueue(new Callback<List<DatetimeRange>>() {
            @Override
            public void onResponse(Call<List<DatetimeRange>> call, Response<List<DatetimeRange>> response) {
                List<DatetimeRange> teacherList = response.body();
                Django.dateRangeList = teacherList;
                return;
            }

            @Override
            public void onFailure(Call<List<DatetimeRange>> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static void getMajorList(){

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

            }

            @Override
            public void onFailure(Call<List<Major>> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;

    }
    public static void getTermList(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Django.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Term>> call = jsonPlaceHolderApi.getTermList();
        call.enqueue(new Callback<List<Term>>() {
            @Override
            public void onResponse(Call<List<Term>> call, retrofit2.Response<List<Term>> response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                Django.termList = response.body() ;

            }

            @Override
            public void onFailure(Call<List<Term>> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;

    }
    public static void getMTList(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Django.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<MT>> call = jsonPlaceHolderApi.getMTList();
        call.enqueue(new Callback<List<MT>>() {
            @Override
            public void onResponse(Call<List<MT>> call, retrofit2.Response<List<MT>> response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                Django.MTList = response.body() ;

            }

            @Override
            public void onFailure(Call<List<MT>> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;

    }
    public static void getTMTList(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Django.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<TMT>> call = jsonPlaceHolderApi.getTMTList();
        call.enqueue(new Callback<List<TMT>>() {
            @Override
            public void onResponse(Call<List<TMT>> call, retrofit2.Response<List<TMT>> response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                Django.TMTList = response.body() ;

            }

            @Override
            public void onFailure(Call<List<TMT>> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();

            }
        });
        return;

    }
    public static void getStudentListFromJango(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Student>> call = jsonPlaceHolderApi.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, retrofit2.Response<List<Student>> response) {

                List<Student>  studentlist = response.body()  ;
                Django.studentArrayList = studentlist;
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                //Toast.makeText(context, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();


            }
        });
        return;
    }
    public static void getGoldreqListFromJango(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<GoldRequest>> call = jsonPlaceHolderApi.getGoldReqList();
        call.enqueue(new Callback<List<GoldRequest>>() {
            @Override
            public void onResponse(Call<List<GoldRequest>> call, retrofit2.Response<List<GoldRequest>> response) {
                List<GoldRequest>  studentlist = response.body()  ;
                Django.goldReqList = studentlist;
            }

            @Override
            public void onFailure(Call<List<GoldRequest>> call, Throwable t) {
                //Toast.makeText(context, "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }



    public static void getToday(){
        Date date= new Date(); // your date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Iran"));
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) +1 ;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DateConverter converter = new DateConverter();
        converter.gregorianToPersian(year, month, day);
        today[0]=converter.getYear();
        today[1]=converter.getMonth();
        today[2]=converter.getDay();
    }
    public static String check_user_password (String username , String password){

        for(int i=0 ; i < Django.userArrayList.size() ;i++){

            if (Django.userArrayList.get(i).getUsername().matches(username) && Django.userArrayList.get(i).getPassword().matches(password)){
                Django.appUser = Django.userArrayList.get(i);
                Django.USER_PK = Django.userArrayList.get(i).getUserPK();
                boolean bool_teacher = isTeacher(Django.userArrayList.get(i).getUserPK());
                boolean bool_student = isStudent(Django.userArrayList.get(i).getUserPK());

                if(bool_teacher == true){
                    Django.USER_ROLE = TEACHER_STRING;
                    return TEACHER_STRING;
                }
                else if(bool_student == true){
                    Django.USER_ROLE = STUDENT_STRING;
                    return STUDENT_STRING;
                }
                else{
                    Django.USER_ROLE = ADMIN_STRING ;
                    return ADMIN_STRING;
                }

            }
        }

        return "not exist";
    }
    public static boolean isStudent(int pk){
        for (int i=0 ; i<Django.studentArrayList.size() ; i++){
            if(Django.studentArrayList.get(i).getUser_of_student_PK() == pk){
                Django.appStudent = Django.studentArrayList.get(i);
                Django.ROLE_PK = Django.studentArrayList.get(i).getStudentPK();
                return true;
            }
        }

        return false;

    }
    public static boolean isTeacher(int pk){
        for (int i=0 ; i<Django.teacherArrayList.size() ; i++){
            if(Django.teacherArrayList.get(i).getTeacherUserPK() == pk){
                Django.appTeacher = Django.teacherArrayList.get(i);
                Django.ROLE_PK = Django.teacherArrayList.get(i).getTeacherPK();
                return true;
            }
        }

        return false;

    }


}
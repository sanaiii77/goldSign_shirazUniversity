package jango;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("user/list/")
    Call<List<User>> getUsers();

    @GET("student/list/")
    Call<List<Student>> getStudents();

    @GET("teacher/list/")
    Call<List<Teacher>> getTeachers();

    @GET("major/list/")
    Call<List<Major>> getMajor();

    @GET("date-range/list/")
    Call<List<DatetimeRange>> getDateRange();

    @GET("term/list/")
    Call<List<Term>> getTermList();

    @GET("mt/list/")
    Call<List<MT>> getMTList();

    @GET("tmt/list/")
    Call<List<TMT>> getTMTList();

    @GET("gold-req/list/")
    Call<List<GoldRequest>> getGoldReqList();



    @GET("user/{pk}")
    Call<User> getUserByPK(@Path("pk") int userPK);


    @GET("student/{pk}")
    Call<Student> getStudentByPK(@Path("pk") int studentPK);

    @GET("mt/{pk}")
    Call<MT> getMTByPK(@Path("pk") int mtPK);

    @GET("major/{pk}")
    Call<Major> getMAjorByPK(@Path("pk") int majorPK);



    @POST("/user/create")
    @FormUrlEncoded
    Call<User> insertUser (@Field("username") String username,
                        @Field("password") String password);


    @POST("/date-range/create")
    @FormUrlEncoded
    Call<DatetimeRange> insertDateRange (@Field("start") String start,
                           @Field("end") String end);


}

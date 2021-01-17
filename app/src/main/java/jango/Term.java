package jango;

import com.google.gson.annotations.SerializedName;

public class Term {
    @SerializedName("pk")
    int termPK ;

    @SerializedName("date")
    String date ;

    @SerializedName("student_date_range")
    int student_date_range_PK;

    @SerializedName("teacher_date_range")
    int teacher_date_range_PK;


    public int getTermPK() {
        return termPK;
    }

    public void setTermPK(int termPK) {
        this.termPK = termPK;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudent_date_range_PK() {
        return student_date_range_PK;
    }

    public void setStudent_date_range_PK(int student_date_range_PK) {
        this.student_date_range_PK = student_date_range_PK;
    }

    public int getTeacher_date_range_PK() {
        return teacher_date_range_PK;
    }

    public void setTeacher_date_range_PK(int teacher_date_range_PK) {
        this.teacher_date_range_PK = teacher_date_range_PK;
    }
}

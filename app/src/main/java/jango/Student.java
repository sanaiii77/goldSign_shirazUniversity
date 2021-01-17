package jango;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("pk")
    int studentPK;
    @SerializedName("user")
    int user_of_student_PK;
    @SerializedName("name")
    String studentName;
    @SerializedName("familyname")
    String studentFamilyname;
    @SerializedName("grade")
    int grade;
    @SerializedName("entrance_mt")
    int entrance_mt_PK; //witch term and witch major
    @SerializedName("selected_tmt")
    int selected_tmt_PK;

    public Student(int studentPK, int user_of_student_PK, String studentName, String studentFamilyname, int grade, int entrance_mt_PK, int selected_tmt_PK) {
        this.studentPK = studentPK;
        this.user_of_student_PK = user_of_student_PK;
        this.studentName = studentName;
        this.studentFamilyname = studentFamilyname;
        this.grade = grade;
        this.entrance_mt_PK = entrance_mt_PK;
        this.selected_tmt_PK = selected_tmt_PK;
    }

    public int getStudentPK() {
        return studentPK;
    }

    public void setStudentPK(int studentPK) {
        this.studentPK = studentPK;
    }

    public int getUser_of_student_PK() {
        return user_of_student_PK;
    }

    public void setUser_of_student_PK(int user_of_student_PK) {
        this.user_of_student_PK = user_of_student_PK;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentFamilyname() {
        return studentFamilyname;
    }

    public void setStudentFamilyname(String studentFamilyname) {
        this.studentFamilyname = studentFamilyname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getEntrance_mt_PK() {
        return entrance_mt_PK;
    }

    public void setEntrance_mt_PK(int entrance_mt_PK) {
        this.entrance_mt_PK = entrance_mt_PK;
    }

    public int getSelected_tmt_PK() {
        return selected_tmt_PK;
    }

    public void setSelected_tmt_PK(int selected_tmt_PK) {
        this.selected_tmt_PK = selected_tmt_PK;
    }
}

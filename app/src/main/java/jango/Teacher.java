package jango;

import com.google.gson.annotations.SerializedName;

public class Teacher {
    @SerializedName("pk")
    int teacherPK;
    @SerializedName("user")
    int teacherUserPK;

    @SerializedName("name")
    String teacherName ;

    @SerializedName("familyname")
    String teacherFamilyName ;


    public Teacher(int teacherPK, int teacherUserPK , String teacherName , String teacherFamilyName) {
        this.teacherPK = teacherPK;
        this.teacherUserPK = teacherUserPK;
        this.teacherFamilyName = teacherFamilyName ;
        this.teacherName = teacherName;
    }

    public int getTeacherPK() {
        return teacherPK;
    }

    public int getTeacherUserPK() {
        return teacherUserPK;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherFamilyName() {
        return teacherFamilyName;
    }
}

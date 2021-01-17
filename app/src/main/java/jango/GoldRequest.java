package jango;

import com.google.gson.annotations.SerializedName;

public class GoldRequest {
    @SerializedName("pk")
    int gold_req_PK;
    @SerializedName("student")
    int student_of_goldreq_PK;
    @SerializedName("tmt_teacher")
    int tmt_teacher_of_goldreq_PK;
    @SerializedName("priority")
    int priority_of_goldreq_PK;


    public int getGold_req_PK() {
        return gold_req_PK;
    }

    public void setGold_req_PK(int gold_req_PK) {
        this.gold_req_PK = gold_req_PK;
    }

    public int getStudent_of_goldreq_PK() {
        return student_of_goldreq_PK;
    }

    public void setStudent_of_goldreq_PK(int student_of_goldreq_PK) {
        this.student_of_goldreq_PK = student_of_goldreq_PK;
    }

    public int getTmt_teacher_of_goldreq_PK() {
        return tmt_teacher_of_goldreq_PK;
    }

    public void setTmt_teacher_of_goldreq_PK(int tmt_teacher_of_goldreq_PK) {
        this.tmt_teacher_of_goldreq_PK = tmt_teacher_of_goldreq_PK;
    }

    public int getPriority_of_goldreq_PK() {
        return priority_of_goldreq_PK;
    }

    public void setPriority_of_goldreq_PK(int priority_of_goldreq_PK) {
        this.priority_of_goldreq_PK = priority_of_goldreq_PK;
    }
}

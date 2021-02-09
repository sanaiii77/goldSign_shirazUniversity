package jango;

import com.google.gson.annotations.SerializedName;

public class TMT {
    @SerializedName("pk")
    int tmtPK ;

    @SerializedName("mt")
    int mt_of_tmt_PK;
    @SerializedName("teacher")
    int teacher_of_tmt_PK;
    @SerializedName("cap")
    int cap;

    @SerializedName("sts")
    Boolean sts;

    public Boolean getSts() {
        return sts;
    }

    public void setSts(Boolean sts) {
        this.sts = sts;
    }

    public int getTmtPK() {
        return tmtPK;
    }

    public void setTmtPK(int tmtPK) {
        this.tmtPK = tmtPK;
    }

    public int getMt_of_tmt_PK() {
        return mt_of_tmt_PK;
    }

    public void setMt_of_tmt_PK(int mt_of_tmt_PK) {
        this.mt_of_tmt_PK = mt_of_tmt_PK;
    }

    public int getTeacher_of_tmt_PK() {
        return teacher_of_tmt_PK;
    }

    public void setTeacher_of_tmt_PK(int teacher_of_tmt_PK) {
        this.teacher_of_tmt_PK = teacher_of_tmt_PK;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }
}

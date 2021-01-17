package jango;

import com.google.gson.annotations.SerializedName;

public class Major {
    @SerializedName("pk")
    int majorPK;
    @SerializedName("title")
    String majorTitle ;

    public int getMajorPK() {
        return majorPK;
    }

    public void setMajorPK(int majorPK) {
        this.majorPK = majorPK;
    }

    public String getMajorTitle() {
        return majorTitle;
    }

    public void setMajorTitle(String majorTitle) {
        this.majorTitle = majorTitle;
    }
}


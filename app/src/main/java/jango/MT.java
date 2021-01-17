package jango;

import com.google.gson.annotations.SerializedName;

public class MT {
    @SerializedName("pk")
    int mtPK;
    @SerializedName("major")
    int major_of_mt_PK;
    @SerializedName("term")
    int term_of_mt_PK;

    public int getMtPK() {
        return mtPK;
    }

    public void setMtPK(int mtPK) {
        this.mtPK = mtPK;
    }

    public int getMajor_of_mt_PK() {
        return major_of_mt_PK;
    }

    public void setMajor_of_mt_PK(int major_of_mt_PK) {
        this.major_of_mt_PK = major_of_mt_PK;
    }

    public int getTerm_of_mt_PK() {
        return term_of_mt_PK;
    }

    public void setTerm_of_mt_PK(int term_of_mt_PK) {
        this.term_of_mt_PK = term_of_mt_PK;
    }
}

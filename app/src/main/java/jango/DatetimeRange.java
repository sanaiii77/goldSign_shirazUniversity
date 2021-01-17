package jango;

import com.google.gson.annotations.SerializedName;

public class DatetimeRange {
    @SerializedName("pk")
    int dateRangePK ;
    @SerializedName("start")
    String start;
    @SerializedName("end")
    String end;


    public int getDateRangePK() {
        return dateRangePK;
    }

    public void setDateRangePK(int dateRangePK) {
        this.dateRangePK = dateRangePK;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

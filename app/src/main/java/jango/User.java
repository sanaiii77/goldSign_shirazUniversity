package jango;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("pk")
    private int userPK ;
    @SerializedName("username")
    private  String username ;
    @SerializedName("password")
    String password ;


    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserPK() {
        return userPK;
    }

    public void setUserPK(int userPK) {
        this.userPK = userPK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

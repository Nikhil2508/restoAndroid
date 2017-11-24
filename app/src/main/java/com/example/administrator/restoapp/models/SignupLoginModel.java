
package com.example.administrator.restoapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupLoginModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("user_email")
    @Expose
    public String userEmail;
    @SerializedName("pic")
    @Expose
    public String pic;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("message")
    @Expose
    public String message;

}
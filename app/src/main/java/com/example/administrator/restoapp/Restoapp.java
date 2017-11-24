package com.example.administrator.restoapp;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Administrator on 11/18/2017.
 */

public class Restoapp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

    }
}

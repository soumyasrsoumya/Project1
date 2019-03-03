package com.example.instagramcloneapp;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7FKovjkM3ZvePCHbShOjwhUlMtrk2kdX8LDMcJxr")
                // if defined
                .clientKey("slHB52qD52cWEuANTjkU0ImG0Lc2ShT5ir2bwDPj")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}

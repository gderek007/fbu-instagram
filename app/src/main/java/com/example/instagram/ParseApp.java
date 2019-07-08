package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final Parse.Configuration configuration= new Parse.Configuration.Builder(this)
            .applicationId("1101234")
            .clientKey("derek1234")
            .server("http://gderek007-fbu-instagram.herokuapp.com/parse")
            .build();


        Parse.initialize(configuration);
    }
}

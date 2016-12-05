package com.example.german.appfirebase;

import android.app.Application;

import com.facebook.FacebookActivity;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by German on 28/11/2016.
 */

public class AppFireBaseApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}

package com.example.fastnumemes;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import java.util.List;

public class RegisterService extends IntentService {


    private int result = Activity.RESULT_CANCELED;

    public static final String username = "username";
    public static final String userpassword = "***";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "service receiver2";
    Login_database db;
    public RegisterService() {
        super("RegisterService");
    }

    // Will be called asynchronously by OS.
    @Override
    protected void onHandleIntent(Intent intent) {
        String user =intent.getStringExtra(username);
        String pass = intent.getStringExtra(userpassword);
        db=new Login_database(this);
        db.InsertUser(new Register(user,pass,0));

        publishResults(0);
    }

    private void publishResults(int result) {
        Intent intent = new Intent(RegisterService.NOTIFICATION);

        intent.putExtra(RESULT, result);

        sendBroadcast(intent);
    }

}





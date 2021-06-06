package com.example.fastnumemes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button login=findViewById(R.id.btn_login);

        final EditText name=findViewById(R.id.username);
        final EditText password=findViewById(R.id.password);
        TextView textView=findViewById(R.id.link_signup);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Login_database db=new Login_database(this);
        List<Register> reg=db.FetchUsers();
int size=reg.size();




        if(reg.size()!=0){
            for(int i=0;i<reg.size();i++) {
                if (reg.get(i).value == 1) {

                        Intent i2 = new Intent(MainActivity.this, ProfileActivity.class);

                    startActivity(i2);
                }
            }

        }


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);

                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginService.class);
                intent.putExtra(LoginService.username, name.getText().toString());
                intent.putExtra(LoginService.userpassword,password.getText().toString());




                startService(intent);

                }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
               LoginService.NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);

    }



    private BroadcastReceiver receiver=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                int resultCode = bundle.getInt(LoginService.RESULT);
                String name=bundle.getString(LoginService.username);
                if (resultCode != -1) {

                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                      i.putExtra("username",name);
                    Login_database db=new Login_database(MainActivity.this);
                    List<Register> reg=db.FetchUsers();
                    int size=reg.size();
                   mFirebaseAnalytics = FirebaseAnalytics.getInstance(MainActivity.this);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, "0");
                    bundle2.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
                    bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "username");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    if(reg.size()!=0){

                            if (reg.get(size-1).value == 0) {
                                db.updateDb(new Register(reg.get(size-1).username,reg.get(size-1).password,1));

                            }

                    }            ;
                    startActivity(i);



                } else {
                    Toast.makeText(MainActivity.this, "Login failed",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, MainActivity.class);

                    startActivity(i);

                }
            }
        }
    };




}
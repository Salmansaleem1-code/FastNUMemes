package com.example.fastnumemes;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {


    Login_database userdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        final EditText name = findViewById(R.id.input_name);
        EditText email = findViewById(R.id.input_email);
        final EditText password = findViewById(R.id.input_password);
        userdb = new Login_database(this);

        Button signup = findViewById(R.id.btn_signup);
        TextView textView = findViewById(R.id.link_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);

                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, RegisterService.class);
                intent.putExtra(RegisterService.username, name.getText().toString());
                intent.putExtra(RegisterService.userpassword,password.getText().toString());




                startService(intent);


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                RegisterService.NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                int resultCode = bundle.getInt(RegisterService.RESULT);

                if (resultCode == 0) {
                    Toast.makeText(RegisterActivity.this, "Registration Successfull",
                            Toast.LENGTH_LONG).show();


                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);

                    startActivity(i);


                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failded",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);

                    startActivity(i);

                }
            }
        }
    };


}

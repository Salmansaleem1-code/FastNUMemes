package com.example.fastnumemes;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import java.util.List;

public class LoginService extends IntentService{


        private int result = Activity.RESULT_CANCELED;

        public static final String username = "username";
        public static final String userpassword = "***";
        public static final String RESULT = "result";
        public static final String NOTIFICATION = "service receiver";
Login_database db;
            public LoginService() {
            super("LoginService");
        }
    String user;
        // Will be called asynchronously by OS.
        @Override
        protected void onHandleIntent(Intent intent) {
         user =intent.getStringExtra(username);
            String pass = intent.getStringExtra(userpassword);
db=new Login_database(this);
 List<Register> reg;
 reg=db.FetchUsers();
 int value=-1;
for(int i=0;i<reg.size();i++) {
System.err.println(reg.get(i).username+"aa"+user);
System.err.println(reg.get(i).password+"bb"+pass);
    if (user.equals(reg.get(i).username ) && pass.equals(reg.get(i).password))  {
System.err.print("dsdfgdhfjg");
        publishResults(0);
        value=0;

    }


}
if(value==-1)
    publishResults(-1);

        }

        private void publishResults(int result) {
            Intent intent = new Intent(LoginService.NOTIFICATION);

            intent.putExtra(RESULT, result);
            intent.putExtra(username,user);

            sendBroadcast(intent);
        }

            }



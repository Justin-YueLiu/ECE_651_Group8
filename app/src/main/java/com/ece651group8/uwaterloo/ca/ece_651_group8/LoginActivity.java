package com.ece651group8.uwaterloo.ca.ece_651_group8;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ece651group8.uwaterloo.ca.ece_651_group8.bean.Config;
import com.ece651group8.uwaterloo.ca.ece_651_group8.dao.TokenDao;
import com.ece651group8.uwaterloo.ca.ece_651_group8.dao.impl.TokenDaoImpl;
import com.ece651group8.uwaterloo.ca.ece_651_group8.util.DBUtils;
import com.ece651group8.uwaterloo.ca.ece_651_group8.util.LoginUtils;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton patientButton;
    private RadioButton relativeButton;
    private EditText mUserName;
    private EditText mPassword;
    private Button signOn;

    private UserLoginTask mAuthTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        DBUtils.init(this);


        mUserName=(EditText)findViewById(R.id.user_name);
        mPassword=(EditText)findViewById(R.id.password);

        mUserName.setText("patient1@gmail.com");
        mPassword.setText("11111111");

        radioGroup=(RadioGroup)findViewById(R.id.radio_group_id);
        patientButton=(RadioButton)findViewById(R.id.patient_id);
        relativeButton=(RadioButton)findViewById(R.id.relative_id);


        patientButton.setChecked(true);

        //Button
        signOn=(Button)findViewById(R.id.sign_on_button);
        signOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();


//          if(user name is right form){
//             connect to the serve;
//             if(vaild username and password){
//                if (patientButton.isChecked()) {
//                    //turn to the patient interface
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this,PatientActivity.class);
//                    startActivity(intent);
//
//                } else if (relativeButton.isChecked()) {
//                    //turn to the relative interface
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, RelativeActivity.class);
//                    startActivity(intent);
//                }else {
//                    //error:you should choose your identity
//                    Toast toast = Toast.makeText(LoginActivity.this,"Please select your identity.",Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();
//                }
                //  }
//             else{
//                  //error: invalid password.
//                Toast toast = Toast.makeText(LoginActivity.this,"Invalid password.",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
////             }
////          }
////          else{
//              //error: user name should be.....
//                Toast toast = Toast.makeText(LoginActivity.this,"Invalid user name.",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
//          }
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        mUserName.setError(null);
        mPassword.setError(null);

        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError("The Password is too short.");
            focusView = mPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            mUserName.setError("Username is required.");
            focusView = mUserName;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else {


            mAuthTask = new UserLoginTask(userName, password);
            mAuthTask.execute((Void) null);
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }



    //
    public class UserLoginTask extends AsyncTask<Void, Void, Map<String,Integer>> {

        private final String userName;
        private final String password;

        UserLoginTask(String u, String p) {
            userName = u;
            password = p;
        }

        SQLiteDatabase db = DBUtils.getDatabase(LoginActivity.this);
        private Config config = new Config();
        String url = "http://"+config.getIp()+":"+config.getPort()+"/api-token-auth/";

        @Override




        //verified the user in the background
        //save and query token from db
        //close db
        protected Map<String,Integer> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
                Map<String,Integer> map;
            try{
                map = LoginUtils.login(db,url,userName,password);

                TokenDao tokenDao = new TokenDaoImpl();
                String token = tokenDao.query(db);
                //Log.i("tokenFromDB",token+"-------------");


                DBUtils.closeDatabase(db);
                return map;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }


        //get Map from doInBackground
        //turn to the user activity depending on their identity
        @Override
        protected void onPostExecute(final Map<String,Integer> map) {
            mAuthTask = null;

            if (map.get("code")==200) {

                if (map.get("type")==2) {
                    //turn to the patient interface
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,PatientActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();

                } else if (map.get("type")==3) {
                    //turn to the relative interface
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, RelativeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }

            } else {
                mPassword.setError("Incorrect Password.");
                mPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

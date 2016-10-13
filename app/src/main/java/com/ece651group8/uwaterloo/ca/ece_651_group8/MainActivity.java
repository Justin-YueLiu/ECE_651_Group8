package com.ece651group8.uwaterloo.ca.ece_651_group8;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton patientButton;
    private RadioButton relativeButton;
    private RadioButton doctorButton;
    private EditText userName;
    private EditText passWord;
    private Button signOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName=(EditText)findViewById(R.id.user_name_edit);
        passWord=(EditText)findViewById(R.id.password_edit);


        radioGroup=(RadioGroup)findViewById(R.id.radio_group_id);
        patientButton=(RadioButton)findViewById(R.id.patient_id);
        relativeButton=(RadioButton)findViewById(R.id.relative_id);
        doctorButton=(RadioButton)findViewById(R.id.doctor_id);


        //Button
        signOn=(Button)findViewById(R.id.sign_on_button);
        signOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          if(user name is right form){
//             connect to the serve;
//             if(vaild username and password){
                if (patientButton.isChecked()) {
                    //turn to the patient interface
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,PatientActivity.class);
                    startActivity(intent);

                } else if (relativeButton.isChecked()) {
                    //turn to the relative interface
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,RelativeActivity.class);
                    startActivity(intent);
                } else if (doctorButton.isChecked()) {
                    //turn to the doctor interface
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,DoctorActivity.class);
                    startActivity(intent);
                } else {
                    //error:you should choose your identity
                    Toast toast = Toast.makeText(MainActivity.this,"Please select your identity.",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();


                }
                //  }
//             else{
//                  //error: invalid password.
//                Toast toast = Toast.makeText(MainActivity.this,"Invalid password.",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
////             }
////          }
////          else{
//              //error: user name should be.....
//                Toast toast = Toast.makeText(MainActivity.this,"Invalid user name.",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
//          }
            }
        });
    }

}

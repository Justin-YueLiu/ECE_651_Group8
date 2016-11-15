package com.ece651group8.uwaterloo.ca.ece_651_group8;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ece651group8.uwaterloo.ca.ece_651_group8.SlidingMenu;
import com.ece651group8.uwaterloo.ca.ece_651_group8.util.AccelerometerSensorEventListener;
import com.ece651group8.uwaterloo.ca.ece_651_group8.util.LocationEventListener;
import com.ece651group8.uwaterloo.ca.ece_651_group8.util.SendSMSMessage;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class PatientActivity extends AppCompatActivity {

    private SlidingMenu mLeftMenu;
    private TextView bloodPressureText;
    private int bloodPressure = 80;

    private Button sendBtn;
    private EditText txtphoneNo;
    private EditText txtMessage;
    private TextView sensorData;
    private String phoneNo;
    private String message;
    private double lat;
    private double lng;

    private Button logOutBtn;
    public String gpsMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_patient);
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);


        bloodPressureText = (TextView) findViewById(R.id.bloodPressure);
        bloodPressureText.setText("Blood Pressure:" + bloodPressure);

        LinearLayout mainComponent = (LinearLayout) findViewById(R.id.mainComponent);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        txtMessage = (EditText) findViewById(R.id.editTextSMS);
        sensorData = (TextView) findViewById(R.id.sensorData);

        txtphoneNo.setText("5197815261");
        txtMessage.setText("lollol");

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        SensorEventListener accelerometerListener = new AccelerometerSensorEventListener(sensorData);
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager locMan = (LocationManager) getSystemService(PatientActivity.LOCATION_SERVICE);
                LocationListener locListener = new LocationEventListener(PatientActivity.this, gpsMsg);

                if (ActivityCompat.checkSelfPermission(PatientActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PatientActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(PatientActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    return;
                }

                Location loc = locMan.getLastKnownLocation(locMan.GPS_PROVIDER);
                if (loc != null) {
                    lat = loc.getLatitude();
                    lng = loc.getLongitude();

                    Log.i("jjjjjjjj", String.valueOf(lat));

                }
                locMan.requestLocationUpdates(locMan.GPS_PROVIDER, 20, 1, locListener);


                String outputMessage = SendSMSMessage.sendSMSMessage(phoneNo,message);
                Toast.makeText(PatientActivity.this,outputMessage,Toast.LENGTH_SHORT).show();
                //sendSMSMessage();
            }
        });

        logOutBtn = (Button) findViewById(R.id.logout);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientActivity.this);
                builder.setTitle("Are you sure to log out?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){
                        Intent intent = new Intent();
                        intent.setClass(PatientActivity.this,LoginActivity.class);
                        PatientActivity.this.startActivity(intent);
                        PatientActivity.this.finish();
                    }
                });

                builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton){

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

//    public void heartBeatSimulation(){
//
//        float[] heartBeat = new float[0];
//        int i = 0;
//
//        while (true){
//
//            while (i >= 72){
//
//                if (i < 32){
//                    heartBeat[0] = (float) (0.08*Math.sin(8* i));
//                }
//                else if (i >32 || i <48){
//                    heartBeat[0] = (float) (0.01*Math.sin(20* i -3.2)+0.046);
//                }
//                else {
//                    heartBeat[0] = (float) (0.065*Math.sin(10* i -4));
//                }
//
//                /*TimerTask task = new TimerTask(){
//                    public void run(){
//
//                    }
//                };
//
//                Timer timer = new Timer();
//                timer.schedule(task, (long) 0.0001);*/
//
//                i = i+1;
//            }
//
//            i = 0;
//
//        }
//    }
    public void toggleMenu(View view){
        mLeftMenu.toggle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(PatientActivity.this);
            builder.setTitle("Are you sure to log out?");
            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton){
                    Intent intent = new Intent();
                    intent.setClass(PatientActivity.this,LoginActivity.class);
                    PatientActivity.this.startActivity(intent);
                    PatientActivity.this.finish();
                }
            });

            builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton){

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
}
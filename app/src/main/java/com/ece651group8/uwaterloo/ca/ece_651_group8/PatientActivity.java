package com.ece651group8.uwaterloo.ca.ece_651_group8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ece651group8.uwaterloo.ca.ece_651_group8.SlidingMenu;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import ca.uwaterloo.sensortoy.LineGraphView;


public class PatientActivity extends AppCompatActivity {

    private SlidingMenu mLeftMenu;
    private TextView bloodPressureText;
    private int bloodPressure = 80;
    private LineGraphView heartRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_patient);
        mLeftMenu = (SlidingMenu)findViewById(R.id.id_menu);

        bloodPressureText = (TextView) findViewById(R.id.bloodPressure);
        bloodPressureText.setText("Blood Pressure:"+bloodPressure);

        LinearLayout mainComponent = (LinearLayout) findViewById(R.id.mainComponent);
        heartRate = new LineGraphView(getApplicationContext(),100, Arrays.asList("heartbeat"));
        mainComponent.addView(heartRate,0);


    }

    public void heartBeatSimulation(){

        float[] heartBeat = new float[0];
        int i = 0;

        while (true){

            while (i >= 72){

                if (i < 32){
                    heartBeat[0] = (float) (0.08*Math.sin(8* i));
                }
                else if (i >32 || i <48){
                    heartBeat[0] = (float) (0.01*Math.sin(20* i -3.2)+0.046);
                }
                else {
                    heartBeat[0] = (float) (0.065*Math.sin(10* i -4));
                }

                heartRate.addPoint(heartBeat);

                /*TimerTask task = new TimerTask(){
                    public void run(){

                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, (long) 0.0001);*/

                i = i+1;
            }

            i = 0;

        }
    }
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
}
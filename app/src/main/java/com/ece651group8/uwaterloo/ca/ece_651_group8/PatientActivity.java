package com.ece651group8.uwaterloo.ca.ece_651_group8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

@SuppressWarnings("ALL")
public class PatientActivity extends AppCompatActivity implements View.OnTouchListener {

    private ViewFlipper Flipper;
    private GestureDetector Detector;

    //private Button DoctorComment;
    //private Button EmergencyCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Flipper = (ViewFlipper) findViewById(R.id.flipper_p);
        Flipper.addView(getImageView(R.mipmap.heartrate1));
        Flipper.addView(getImageView(R.mipmap.heartrate2));
        Flipper.addView(getImageView(R.mipmap.heartrate3));
        Flipper.setOnTouchListener(this);

        Detector = new GestureDetector(new PatientActivity.simpleGestureListener());
    }

    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }

    public boolean onTouch(View v, MotionEvent event) {
        return Detector.onTouchEvent(event);
    }

    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener{
        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;


        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub
            Toast.makeText(PatientActivity.this, "ondown", Toast.LENGTH_SHORT).show();
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Fling left
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                Flipper.setInAnimation(AnimationUtils.loadAnimation(PatientActivity.this,
                        R.anim.push_left_in));
                Flipper.setOutAnimation(AnimationUtils.loadAnimation(PatientActivity.this,
                        R.anim.push_left_out));
                Flipper.showNext();

                Toast.makeText(PatientActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
                Flipper.setInAnimation(AnimationUtils.loadAnimation(PatientActivity.this,
                        R.anim.push_right_in));
                Flipper.setOutAnimation(AnimationUtils.loadAnimation(PatientActivity.this,
                        R.anim.push_right_out));
                Flipper.showPrevious();

                Toast.makeText(PatientActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    //doctor comments


}

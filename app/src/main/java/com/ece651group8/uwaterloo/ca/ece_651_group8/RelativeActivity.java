package com.ece651group8.uwaterloo.ca.ece_651_group8;


import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class RelativeActivity extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener {

    private ViewFlipper viewFlipper;
    private int[] imageId = {R.mipmap.heartrate1, R.mipmap.heartrate2, R.mipmap.heartrate3};
    View lastView;

    //设置小圆点
    private ImageView[] points;
    private int currentIndex = 0;

    GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_relative);
        gestureDetector = new GestureDetector(RelativeActivity.this);
        initViewFlipper();
        initPoints();
    }


    private void initViewFlipper() {
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);



        for (int i = 0; i < imageId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imageId[i]);
            viewFlipper.addView(imageView);
        }

        //另外增加最后一个能点击进入应用的视图
        lastView = View.inflate(this, R.layout.activity_relative, null);
        Button button = (Button) lastView.findViewById(R.id.emergency_contract);
        viewFlipper.addView(lastView);

        //设置最后一个视图上的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RelativeActivity.this, "应用开始！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initPoints() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
        points = new ImageView[4];

        //初始化布局中的小圆点ImageView控件
        for (int i = 0; i < points.length; i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);//遍历LinearLayout下的所有ImageView子节点
            points[i].setEnabled(true);//设置当前状态为允许点击（可点，灰色）
            points[i].setOnClickListener(this);//设置点击监听
            //额外设置一个标识符，以便点击小圆点时跳转对应页面
            points[i].setTag(i);//标识符与圆点顺序一致
        }
        currentIndex = 0;
        points[currentIndex].setEnabled(false);//设置首页为当前页(不可点，黑色)
    }


    @Override
    public void onClick(View v) {
        //点击小点，改变小点的状态，并且改变viewFlipper中子view的显示
        viewFlipper.setDisplayedChild((int) v.getTag());
        points[(int) v.getTag()].setEnabled(false);
        points[currentIndex].setEnabled(true);
        currentIndex = (int) v.getTag();
    }


    //用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    //用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
    //注意和onDown()的区别，强调的是没有松开或者拖动的状态
    @Override
    public void onShowPress(MotionEvent e) {

    }

    //用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    //用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    //用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
    @Override
    public void onLongPress(MotionEvent e) {

    }

    //用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE,1个ACTION_UP触发
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 120) {
            //为了避免能够一直向右滑动
            if (viewFlipper.getDisplayedChild() != 0) {
                //向右滑动，设置滑动动画
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(RelativeActivity.this,
                        R.anim.push_right_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(RelativeActivity.this,
                        R.anim.push_right_out));
                //这个要判断，不然就会跳到第一个
                viewFlipper.showPrevious();

                //当前页卡被选择时,viewFlipper.getDisplayedChild()为当前页数
//                points[viewFlipper.getDisplayedChild()].setEnabled(false);//不可点击
//                points[currentIndex].setEnabled(true);//恢复之前页面状态
//                currentIndex = viewFlipper.getDisplayedChild();
                return true;
            }
        } else if (e2.getX() - e1.getX() < 120) {

            if (viewFlipper.getDisplayedChild() < 4) {

                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(RelativeActivity.this,
                        R.anim.push_left_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(RelativeActivity.this,
                        R.anim.push_left_out));

                viewFlipper.showNext();

//                points[viewFlipper.getDisplayedChild()].setEnabled(false);
//                points[currentIndex].setEnabled(true);
//                currentIndex = viewFlipper.getDisplayedChild()-1;
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

}


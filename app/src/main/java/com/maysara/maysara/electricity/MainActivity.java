package com.maysara.maysara.electricity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView alarmPic ;
    TextView alarmState ;
    FloatingActionButton fab ;
    RelativeLayout mainLayout ;
    Toolbar toolbar ;
    SharedPreferences sharedPreferences ;
    boolean yes = false ;
    SharedPreferences.Editor editor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        alarmPic = (ImageView)findViewById(R.id.alarm_pic);
        alarmState = (TextView)findViewById(R.id.alarm_state);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        sharedPreferences = this.getSharedPreferences("alarmstate",MODE_PRIVATE);
        animate();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getState()){
                    Snackbar.make(view, getResources().getText(R.string.hint), Snackbar.LENGTH_LONG).show();
                }
                setState(!getState());
                animate();
                if (getState())
                    Toast.makeText(MainActivity.this, "تم ضبط المنبه", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "تم ايقاف المنبه", Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.how_to_use)
            showMessageInfoDialog();
        return super.onOptionsItemSelected(item);
    }

    private void setState(boolean s)
    {
        editor = sharedPreferences.edit();
        editor.putBoolean("state",s);
        editor.apply();
        editor.commit();

    }

    public  boolean getState()
    {
        return sharedPreferences.getBoolean("state",false);
    }


    private void animate()
    {

        if(getState())
        {
            alarmState.setText(getBaseContext().getResources().getString(R.string.alarm_on));
            alarmPic.setImageResource(R.drawable.ic_alarm_on_white_24px);
            fab.setImageResource(R.drawable.ic_alarm_off_white_24px);
            final int green = this.getResources().getColor(R.color.green1);
            int red = this.getResources().getColor(R.color.red1);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),red, green);
            colorAnimation.setDuration(250);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mainLayout.setBackgroundColor((int) animator.getAnimatedValue());
                    toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }
        else
        {
            alarmState.setText(getBaseContext().getResources().getString(R.string.alarm_off));
            alarmPic.setImageResource(R.drawable.ic_alarm_off_white_24px);
            fab.setImageResource(R.drawable.ic_alarm_add_white_48dp);
            final int red = this.getResources().getColor(R.color.red1);
            final int green = this.getResources().getColor(R.color.green1);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), green, red);
            colorAnimation.setDuration(250);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mainLayout.setBackgroundColor((int) animator.getAnimatedValue());
                    toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }



    }
    private void showMessageInfoDialog()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.how_to_use);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}

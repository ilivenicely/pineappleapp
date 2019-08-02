package com.paneapple.paneapple;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.paneapple.paneapple.tutor.Login_Home_Screen;

public class Spalsh_Screen extends AppCompatActivity {


    private ProgressBar mProgress;
    private Context context;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh__screen);
        this.context = context;
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        mProgress.setProgressTintList(ColorStateList.valueOf(Color.RED));
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=20) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
                // Timber.e(e.getMessage());
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(Spalsh_Screen.this,Login_Home_Screen.class);
        startActivity(intent);

    }


}

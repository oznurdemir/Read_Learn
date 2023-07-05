package com.oznurdemir.readlearn.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.oznurdemir.readlearn.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }.start();
    }
}
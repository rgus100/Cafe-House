package com.example.cafehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView txtCafe;
    TextView txtHouse;
    RelativeLayout relativeLayout;
    Animation txtAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtCafe = findViewById(R.id.txtCafe);
        txtHouse = findViewById(R.id.txtHouse);
        relativeLayout = findViewById(R.id.relSplash);

        txtAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fall_down);
        layoutAnimation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_to_top);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.setAnimation(layoutAnimation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtCafe.setVisibility(View.VISIBLE);
                        txtHouse.setVisibility(View.VISIBLE);

                        txtCafe.setAnimation(txtAnimation);
                        txtHouse.setAnimation(txtAnimation);
                    }
                },500);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                },5000);

            }
        },1000);

    }
}
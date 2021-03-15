package com.mc.mcfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView iv;
    TextView tv1,tv2;
    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // hide action bar
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        iv = findViewById(R.id.imageView);
        tv1 = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);

        iv.setAnimation(topAnim);
        tv1.setAnimation(bottomAnim);
        tv2.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =new Intent(splash_screen.this,signinActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);

    }

}
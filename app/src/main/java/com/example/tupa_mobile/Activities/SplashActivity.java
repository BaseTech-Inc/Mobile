package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.tupa_mobile.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), OnBoardingActivity.class));
                finish();
            }
        }, 3000);
        final androidx.constraintlayout.widget.ConstraintLayout mask = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.constaint_layout);
        final ImageView logo = (ImageView) findViewById(R.id.logo);
        final ImageView logoFinal = (ImageView) findViewById(R.id.logo_final);

        ObjectAnimator animationLogo = ObjectAnimator.ofFloat(logo, "translationX", 0f);
        animationLogo.setStartDelay(500);
        animationLogo.setDuration(1000);
        animationLogo.setInterpolator(new DecelerateInterpolator());
        animationLogo.start();

        ObjectAnimator animationMask = ObjectAnimator.ofFloat(mask, "translationX", -150f);
        animationMask.setStartDelay(500);
        animationMask.setDuration(1000);
        animationMask.setInterpolator(new DecelerateInterpolator());
        animationMask.start();

        ObjectAnimator animationLogoFinal = ObjectAnimator.ofFloat(logoFinal, "translationX", 490f);
        animationLogoFinal.setStartDelay(600);
        animationLogoFinal.setDuration(1000);
        animationLogoFinal.setInterpolator(new DecelerateInterpolator());
        animationLogoFinal.start();

        ObjectAnimator animationLogoFinalAlpha = ObjectAnimator.ofFloat(logoFinal, View.ALPHA, 1);
        animationLogoFinalAlpha.setStartDelay(0);
        animationLogoFinalAlpha.setDuration(0);
        animationLogoFinalAlpha.setInterpolator(new DecelerateInterpolator());
        animationLogoFinalAlpha.start();
    }
}
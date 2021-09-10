package com.example.tupa_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tupa_mobile.OnBoarding.SliderAdapter;
import com.example.tupa_mobile.OnBoarding.SliderData;
import com.example.tupa_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private LinearLayout dotsLayout;
    private SliderAdapter adapter;
    private List<SliderData> data;
    private Button getStarted, nextPag;
    private TextView skip;
    private ViewPager2 viewPager2;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dotsLayout = findViewById(R.id.dots_layout);
        getStarted = findViewById(R.id.start_btn);
        skip = findViewById(R.id.skip);
        viewPager2 = findViewById(R.id.view_pager2);
        nextPag = findViewById(R.id.next_btn);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 2, true);
            }
        });

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginLayout();
            }
        });

        nextPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
            }
        });

        data = new ArrayList<>();
        data.add(new SliderData("Previsões Meteorológicas", getResources().getString(R.string.OnBoarding), R.drawable.clock_ilustraion_dark_theme));
        data.add(new SliderData("Alertas de Enchentes", getResources().getString(R.string.OnBoarding2), R.drawable.cellphone_ilustraion_dark_theme));
        data.add(new SliderData("Mapa Meteorológico", getResources().getString(R.string.OnBoarding3), R.drawable.map_ilustration_dark_theme));

        adapter = new SliderAdapter(data);
        viewPager2.setAdapter(adapter);

        dots = new ImageView[3];
        createDots();
        selectedDots(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                selectedDots(position);
                if(position==0) {
                    skip.setVisibility(View.VISIBLE);

                }
                else {
                    skip.setVisibility(View.INVISIBLE);
                }

                if (position==2){
                    getStarted.setVisibility(View.VISIBLE);
                    getStarted.setEnabled(true);
                    nextPag.setVisibility(View.INVISIBLE);
                    nextPag.setEnabled(false);
                    dotsLayout.setVisibility(View.INVISIBLE);
                }else{
                    getStarted.setVisibility(View.INVISIBLE);
                    getStarted.setEnabled(false);
                    nextPag.setVisibility(View.VISIBLE);
                    nextPag.setEnabled(true);
                    dotsLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void openLoginLayout(){
        Intent it = new Intent(getBaseContext(), LoginOptionsActivity.class);
        startActivity(it);
        finish();
    }

    private void selectedDots(int position) {
        for (int i=0; i<dots.length; i++){
            if (i==position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selected_dot));
            }
            else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unselected_dot));
            }
        }
    }

    private void createDots(){
        for (int i=0; i<dots.length;i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(6, 0, 6, 0);

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.unselected_dot));
            dots[i].setLayoutParams(params);
            dotsLayout.addView(dots[i]);

        }
    }
}
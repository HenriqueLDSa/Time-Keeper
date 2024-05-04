package com.application.timekeeper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity{

    private TextView mStopwatch;
    private TextView mLap1;
    private TextView mLap2;
    private TextView mLap3;
    private TextView mLap4;
    private boolean isRunning;
    private long startTime = 0L;
    private long msTime = 0L;
    private long timeBuffer = 0L;
    private long currentTime = 0L;
    private Handler timeRunHandler;
    private int iter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stopwatch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.stopwatch_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timeRunHandler = new Handler(Looper.getMainLooper());
        isRunning = false;
        iter = 0;

        Button mStartBtn = findViewById(R.id.btnStart);
        Button mStopBtn = findViewById(R.id.btnStop);
        Button mResetBtn = findViewById(R.id.btnReset);
        Button mLapButton = findViewById(R.id.btnLap);
        mStopwatch = findViewById(R.id.stopwatch);
        mLap1 = findViewById(R.id.lap1);
        mLap2 = findViewById(R.id.lap2);
        mLap3 = findViewById(R.id.lap3);
        mLap4 = findViewById(R.id.lap4);

        mStartBtn.setOnClickListener(v -> updateStopwatch("Start"));
        mStopBtn.setOnClickListener(v -> updateStopwatch("Stop"));
        mResetBtn.setOnClickListener(v -> updateStopwatch("Reset"));
        mLapButton.setOnClickListener(v -> updateStopwatch("Lap"));
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            msTime = SystemClock.uptimeMillis() - startTime;
            currentTime = timeBuffer + msTime;

            int milliseconds = (int) (currentTime % 1000) / 10;
            int seconds = (int) (currentTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            mStopwatch.setText(String.format(Locale.US, "%02d:%02d:%02d", minutes, seconds, milliseconds));
            timeRunHandler.postDelayed(this, 0);
        }
    };

    @SuppressLint("SetTextI18n")
    private void updateStopwatch(String command){
        switch (command) {
            case "Start":
                if (!isRunning) {
                    startTime = SystemClock.uptimeMillis();
                    timeRunHandler.postDelayed(runnable, 0);
                    isRunning = true;
                }
                break;
            case "Stop":
                if (isRunning) {
                    timeBuffer += msTime;
                    timeRunHandler.removeCallbacks(runnable);
                    isRunning = false;
                }
                break;
            case "Reset":
                timeBuffer = 0L;
                msTime = 0L;
                currentTime = 0L;
                startTime = 0L;
                resetComponents();
                isRunning = false;
                timeRunHandler.removeCallbacks(runnable);
                break;
            case "Lap":
                int milliseconds = (int) (currentTime % 1000) / 10;
                int seconds = (int) (currentTime / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if(iter == 0)
                {
                    mLap1.setText(String.format(Locale.US, "Lap 1: " + "%02d:%02d:%02d", minutes, seconds, milliseconds));
                    mLap1.setVisibility(View.VISIBLE);
                    iter++;
                } else if (iter == 1) {
                    mLap2.setText(String.format(Locale.US, "Lap 2: " + "%02d:%02d:%02d", minutes, seconds, milliseconds));
                    mLap2.setVisibility(View.VISIBLE);
                    iter++;
                } else if (iter == 2) {
                    mLap3.setText(String.format(Locale.US, "Lap 3: " + "%02d:%02d:%02d", minutes, seconds, milliseconds));
                    mLap3.setVisibility(View.VISIBLE);
                    iter++;
                } else if (iter == 3) {
                    mLap4.setText(String.format(Locale.US, "Lap 4: " + "%02d:%02d:%02d", minutes, seconds, milliseconds));
                    mLap4.setVisibility(View.VISIBLE);
                    iter++;
                }

                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void resetComponents(){
        mStopwatch.setText("00:00:00");

        mLap1.setText("Lap 1: ");
        mLap1.setVisibility(View.INVISIBLE);

        mLap2.setText("Lap 2: ");
        mLap2.setVisibility(View.INVISIBLE);

        mLap3.setText("Lap 3: ");
        mLap3.setVisibility(View.INVISIBLE);

        mLap4.setText("Lap 4: ");
        mLap4.setVisibility(View.INVISIBLE);

        iter = 0;
    }
}

package com.application.timekeeper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity{

    private LinearLayout mLapContainer;
    private TextView mStopwatch;
    private Button mStartBtn;
    private Button mLapButton;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_stopwatch), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timeRunHandler = new Handler(Looper.getMainLooper());
        isRunning = false;
        iter = 0;

        mLapContainer = findViewById(R.id.lap_container);
        mStartBtn = findViewById(R.id.btnStart);
        mLapButton = findViewById(R.id.btnLap);
        mStopwatch = findViewById(R.id.stopwatch);

        mStartBtn.setOnClickListener(v -> updateStopwatch(mStartBtn));
        mLapButton.setOnClickListener(v -> updateStopwatch(mLapButton));
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
    private void updateStopwatch(Button btn){
        String command = btn.getText().toString();

        switch (command) {
            case "Start":
                if (!isRunning) {
                    startTime = SystemClock.uptimeMillis();
                    timeRunHandler.postDelayed(runnable, 0);
                    isRunning = true;
                    mStartBtn.setText("Stop");
                    mLapButton.setText("Lap");
                }
                break;
            case "Stop":
                if (isRunning) {
                    timeBuffer += msTime;
                    timeRunHandler.removeCallbacks(runnable);
                    isRunning = false;
                    mLapButton.setText("Reset");
                    mStartBtn.setText("Start");
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
                mStartBtn.setText("Start");
                mLapButton.setText("Lap");
                break;
            case "Lap":
                if(!isRunning)
                    break;

                int milliseconds = (int) (currentTime % 1000) / 10;
                int seconds = (int) (currentTime / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                String lapTime = String.format(Locale.US, "%02d:%02d:%02d", minutes, seconds, milliseconds);

                createLapText(lapTime);

                iter++;

                break;
        }
    }

    private void createLapText(String lapTime){
        TextView newLap = new TextView((this));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 10);
        newLap.setLayoutParams(layoutParams);

        String text = "Lap " + iter + ": " + lapTime;
        newLap.setText(text);

        newLap.setTextSize(25);

        newLap.setVisibility(View.VISIBLE);

        mLapContainer.addView(newLap);
    }

    @SuppressLint("SetTextI18n")
    private void resetComponents(){
        mStopwatch.setText("00:00:00");
        mLapContainer.removeAllViews();
        iter = 0;
    }
}

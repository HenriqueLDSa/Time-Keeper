package com.application.timekeeper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CountdownTimer extends AppCompatActivity {
    private TextView countdownTimeTxt;
    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_countdown_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_countdown_timer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        countdownTimeTxt = findViewById(R.id.time_count);

        mBtnStart = findViewById(R.id.btnStart);
        mBtnStart.setOnClickListener(v -> startCountdown());
    }

    private void startCountdown(){
        mBtnStart.setEnabled(false);

        new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                countdownTimeTxt.setText(String.valueOf((millisUntilFinished+500)/1000));
            }

            @Override
            public void onFinish() {
                countdownTimeTxt.setText("0");
                mBtnStart.setEnabled(true);
            }
        }.start();
    }
}

package com.application.clickcounter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class Stopwatch extends AppCompatActivity{

    private Button mStartBtn;
    private Button mStopBtn;
    private Button mResetBtn;
    private TextView mStopwatch;
    private TextView mLap1;
    private TextView mLap2;
    private TextView mLap3;
    private TextView mLap4;

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

        mStartBtn = findViewById(R.id.btnStart);
        mStopBtn = findViewById(R.id.btnStop);
        mResetBtn = findViewById(R.id.btnReset);
        mStopwatch = findViewById(R.id.stopwatch);
        mLap1 = findViewById(R.id.lap1);
        mLap2 = findViewById(R.id.lap2);
        mLap3 = findViewById(R.id.lap3);
        mLap4 = findViewById(R.id.lap4);

        View.OnClickListener buttonListener = view -> {
            Button btn = (Button) view;
            updateStopwatch(btn.getText().toString(), mStopwatch);
        };
    }

    public void updateStopwatch(String btn, TextView mStopwatch){

    }



}

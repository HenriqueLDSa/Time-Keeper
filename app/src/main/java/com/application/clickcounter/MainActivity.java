package com.application.clickcounter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button mMinusButton = findViewById(R.id.minus_button);
        Button mPlusButton = findViewById(R.id.plus_button);
        Button mResetButton = findViewById(R.id.reset_button);
        TextView mCount = findViewById(R.id.count_textview);

        mCount.setText("0");

        View.OnClickListener buttonListener = view -> {
            Button btn = (Button) view;
            updateText(btn.getText().toString(), mCount);
        };

        mMinusButton.setOnClickListener(buttonListener);
        mPlusButton.setOnClickListener(buttonListener);
        mResetButton.setOnClickListener(buttonListener);
    }

    public void updateText(String buttonText, TextView mCount) {
        int num = Integer.parseInt(mCount.getText().toString());

        if(Objects.equals(buttonText, "-") && num > 0)
            num--;
        else if (Objects.equals(buttonText, "+"))
            num++;
        else
            num = 0;

        mCount.setText(String.valueOf(num));
    }
}
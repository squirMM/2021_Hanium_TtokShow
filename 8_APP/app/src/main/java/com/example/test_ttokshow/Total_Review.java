package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

public class Total_Review extends AppCompatActivity {
    Button retBox_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_review);
        retBox_1 = (Button) findViewById(R.id.retBox);
        retBox_1.setOnClickListener(this::onClick) ;
    }
    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retBox:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    public void onClick(View view) {
        BtnOnClickListener onClickListener = new Total_Review.BtnOnClickListener();
        Button retBox = (Button) findViewById(R.id.retBox);
        retBox.setOnClickListener(onClickListener);
    }


}
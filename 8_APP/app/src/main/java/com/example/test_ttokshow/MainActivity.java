package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    //int count=0;
    boolean visible =false;
    Button open_bu;
    Button all_review_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        open_bu = (Button) findViewById(R.id.open);
        open_bu.setOnClickListener(this::onClick) ;
        all_review_1 = (Button) findViewById(R.id.all_review);
        all_review_1.setOnClickListener(this::onClick) ;

    }


    class BtnOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.all_review:
                    Intent intent = new Intent(getApplicationContext(), Total_Review.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    public void onClick(View view) {
        //inflation layout
        LinearLayout inflatedLayout = (LinearLayout)findViewById(R.id.inflatedlayout);
        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(!visible)
        {
            inflater.inflate(R.layout.inflated_layout, inflatedLayout);
            visible = true;
        }
        else{
            inflatedLayout.removeAllViews();
            visible=false;
        }

        //all review
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        Button all_review = (Button) findViewById(R.id.all_review);
        all_review.setOnClickListener(onClickListener);


    }

}

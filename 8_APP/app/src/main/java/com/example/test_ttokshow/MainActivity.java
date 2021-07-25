package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    //int count=0;
    boolean visible =false;
    Button open_bu;
    Button all_review_1;
    Dialog dialog;
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

        dialog = new Dialog(MainActivity.this);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.error_popup); //xml 연결

        findViewById(R.id.test_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(); // 아래 showDialog01() 함수 호출
            }
        });
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
    public void showDialog(){
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Button yesBtn = dialog.findViewById(R.id.yesB);
//        yesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                dialog.dismiss(); // 다이얼로그 닫기
//            }
//        });
//        // 네 버튼
//        dialog.findViewById(R.id.noB).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 원하는 기능 구현
//                finish();           // 앱 종료
//            }
//        });
    }
    //hide
    public void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                // Hide the nav bar and status bar
                 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }

}


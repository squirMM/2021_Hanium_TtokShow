package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {

    boolean visible =false;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //inflation layout
        BtnOnClickListener onClickLis = new BtnOnClickListener();
        Button open_bu = (Button) findViewById(R.id.open);
        open_bu.setOnClickListener(onClickLis);

        //all review
        BtnOnClickListener onClickListener = new BtnOnClickListener();
        Button all_review = (Button) findViewById(R.id.all_review);
        all_review.setOnClickListener(onClickListener);

        dialog = new Dialog(MainActivity.this);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.error_popup); //xml 연결

        //임시 다이얼로그
        findViewById(R.id.test_pop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  showDialog(); // 아래 showDialog01() 함수 호출
            }
//            @Override
//            public boolean onTouchEvent(MotionEvent event) {
//                //바깥레이어 클릭시 안닫히게
//                if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            public void onBackPressed() {
//                //안드로이드 백버튼 막기
//                return;
//            }
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
                case R.id.open:
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
                    break;
            }
        }
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


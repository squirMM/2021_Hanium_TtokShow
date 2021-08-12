package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.test_ttokshow.Scanner.ScannerCaptureActivity;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    TextView product_name;
    ImageButton open_bu;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Text
        product_name=(TextView)findViewById(R.id.name);
        product_name.setSingleLine(true);    // 한줄로 표시하기
        product_name.setEllipsize(TextUtils.TruncateAt.MARQUEE); // 흐르게 만들기
        product_name.setSelected(true);      // 선택하기

        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //inflation layout
        open_bu = (ImageButton) findViewById(R.id.open);
        open_bu.setOnClickListener(onClickListener);

        //all review
        Button all_review = (Button) findViewById(R.id.all_review);
        all_review.setOnClickListener(onClickListener);

        //scanner btn
        ImageButton home = (ImageButton) findViewById(R.id.home_btn);
        home.setOnClickListener(onClickListener);

        //Error Dialog
        dialog = new Dialog(MainActivity.this);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.error_popup); //xml 연결

        //임시 다이얼로그
//        findViewById(R.id.test_pop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                  showDialog(); // 아래 showDialog01() 함수 호출
//            }});
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
                    if(!open_bu.isSelected())
                    {
                        inflater.inflate(R.layout.inflated_layout, inflatedLayout);
                        open_bu.setSelected(true);
                    }
                    else{
                        inflatedLayout.removeAllViews();
                        open_bu.setSelected(false);
                    }
                    break;
                case R.id.home_btn:
                    Intent scan = new Intent(getApplicationContext(), ScannerActivity.class);
                    startActivity(scan);
            }
        }
    }

    public void showDialog(){
        dialog.show();
        //round 맞춰주기
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그 밖 터치했을때 못나가게
        dialog.setCanceledOnTouchOutside(false);
        // 다이얼로그 뒤로가기 버튼 방지
        dialog.setCancelable(false);
        Button yesBtn = dialog.findViewById(R.id.yesB);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //TODO 액티비티 화면 재갱신 시키는 코드
                    Intent intent = getIntent();
                    finish(); //현재 액티비티 종료 실시
                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    startActivity(intent); //현재 액티비티 재실행 실시
                    overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
        dialog.findViewById(R.id.noB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();           // 앱 종료
            }
        });

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
package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test_ttokshow.Recy.ItemData;

import java.util.ArrayList;

public class Zoom_Review extends AppCompatActivity {

    private TextView product_name;
    private TextView content;
    private TextView grade;
    private TextView cite;
    private TextView id;
    private TextView date;
    private ImageView perGrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zoom_review);

        Intent intent=getIntent();
        ItemData list = (ItemData) intent.getSerializableExtra("Item");

        staticItem myApp = (staticItem)getApplicationContext();

        /**Text*/
        product_name = (TextView)findViewById(R.id.name);
        product_name.setText(myApp.getProName());
        content=(TextView)findViewById(R.id.contents);
        content.setText(list.getScontents());
        grade=(TextView)findViewById(R.id.grade_num);
        grade.setText(list.getSgrade());
        cite=(TextView)findViewById(R.id.cite);
        cite.setText(list.getScite());
        id=(TextView)findViewById(R.id.reviewId);
        id.setText(list.getSId());
        date=(TextView)findViewById(R.id.review_date);
        date.setText(list.getSdate());

        /**ImageView*/
        perGrade=(ImageView)findViewById(R.id.per_grade);
        perRank(Integer.parseInt(list.getSgrade()));

        /**Button*/
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //ret Button
        ImageButton retBox = (ImageButton) findViewById(R.id.retButton);
        retBox.setOnClickListener(onClickListener);

        //scanner btn
        ImageButton camera = (ImageButton) findViewById(R.id.cameraBtn);
        camera.setOnClickListener(onClickListener);

        //home btn
        ImageButton home =(ImageButton)findViewById(R.id.home_btn);
        home.setOnClickListener(onClickListener);


    }

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retButton:
                    finish();
                case R.id.cameraBtn:
                    Intent scan = new Intent(getApplicationContext(), ScannerActivity.class);
                    startActivity(scan);
                    break;
            }
        }
    }

    public void perRank(int star){
        Drawable drawable = getResources().getDrawable(R.drawable.aver1);
        switch (star){
            case 5:
                perGrade.setImageResource(R.drawable.aver5);
                break;
            case 4:
                perGrade.setImageResource(R.drawable.aver4);
                break;
            case 3:
                perGrade.setImageResource(R.drawable.aver3);
                break;
            case 2:
                perGrade.setImageResource(R.drawable.aver2);
                break;
            case 1:
                perGrade.setImageResource(R.drawable.aver1);
                break;
        }
    }

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
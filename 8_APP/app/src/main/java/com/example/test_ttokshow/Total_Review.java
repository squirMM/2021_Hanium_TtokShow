package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Total_Review extends AppCompatActivity {
    ImageButton retBox_1;
    private ListView m_oListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_review);
//        retBox_1 = (Button) findViewById(R.id.retBox);
//        retBox_1.setOnClickListener(this::onClick) ;
        // 데이터 1000개 생성--------------------------------.
        String[] strDate = {"2017-01-03", "1965-02-23", "2016-04-13", "2010-01-01", "2017-06-20",
                "2012-07-08", "1980-04-14", "2016-09-26", "2014-10-11", "2010-12-24"};
        int nDatCnt=0;
        ArrayList<ListData> oData = new ArrayList<>();
        for (int i=0; i<1000; ++i)
        {
            ListData oItem = new ListData();
            oItem.StrCite_name = "데이터 " + (i+1);
            oItem.StrContents = (i+1)+"째 내용";
            oItem.StrStar_rank = "1";
            oItem.StrId = "id"+(i+1);
            oItem.StrDate = strDate[nDatCnt++];

            oData.add(oItem);
            if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.list);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);
    }
    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retButton:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    public void onClick(View view) {
        BtnOnClickListener onClickListener = new Total_Review.BtnOnClickListener();
        ImageButton retBox = (ImageButton) findViewById(R.id.retButton);
        retBox.setOnClickListener(onClickListener);
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

package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.test_ttokshow.List.ListAdapter;
import com.example.test_ttokshow.List.ListData;

import java.util.ArrayList;

public class Total_Review extends AppCompatActivity {
    ImageButton retBox_1;
    private ListView m_oListView;
    RecyclerView recyclerView;
    public static String[] out;
    public String[] output = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_review);

        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //ret Button
        ImageButton retBox = (ImageButton) findViewById(R.id.retButton);
        retBox.setOnClickListener(onClickListener);

        /*
        new Thread(){
            public void run(){
                Client.main();
                String[] out = Client.getOutput();
                output = out;
            }
        }.start();
        output[0] = "Already";

        while (output[0] == "Already") {
            continue;
        }
        */

        String[] strDate = new String[(output.length/5)];
        for (int i=0; i<(output.length/5); i++){
            strDate[i] = output[5*i+1];
        }
        int nDatCnt=0;
        ArrayList<ListData> oData = new ArrayList<>();
        for (int i=0; i<(output.length/5); i++)
        {
            ListData oItem = new ListData();
            oItem.StrCite_name = output[5*i+4];
            oItem.StrContents = output[5*i+2];
            oItem.StrStar_rank = output[5*i+3] + "점";
            oItem.StrId = output[5*i];
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

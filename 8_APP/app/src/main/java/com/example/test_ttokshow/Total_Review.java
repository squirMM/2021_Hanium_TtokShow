package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_ttokshow.Recy.Adapter;
import com.example.test_ttokshow.Recy.ItemData;
import com.example.test_ttokshow.Recy.OnReviewItemClickListener;
import com.example.test_ttokshow.Recy.RecyclerDeco;
import com.example.test_ttokshow.Recy.ViewType;
import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;

public class Total_Review extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> list;
    private int num=0;
    private Adapter adapter;
    private TextView person_many;
    private TextView grade_float;
    public static Float averStar;
    static String pm;
    static String rf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_review);

        Intent intent=getIntent();
        list = (ArrayList<ItemData>)intent.getSerializableExtra("Item");

        staticItem myApp = (staticItem)getApplicationContext();

        View grade =(View)findViewById(R.id.grade_total);

        /**Text View*/
        grade_float = (TextView)findViewById(R.id.gradef);
        grade_float.setText(myApp.getAvg()+"/5");
        person_many = (TextView)findViewById(R.id.cnt_per);
        person_many.setText(Integer.toString(myApp.getCnt())+"명");

        /**custom star*/
        RatingBar mRatingBar =grade.findViewById(R.id.ratingBar);
        mRatingBar.setStarCount(5);
        mRatingBar.setStar(myApp.starRating());

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

        /**Recycler View*/
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.scrollToPosition(0);

        RecyclerDeco decoration_Height = new RecyclerDeco(0,0,2,2);
        recyclerView.addItemDecoration(decoration_Height);

        //recyclerView.smoothScrollBy(0, 672);

        adapter = new Adapter(ViewType.large);
        getItem();
        adapter.setOnItemClickListener((v, position) -> {
            // 클릭했을때 원하는데로 처리해주는 부분
            ItemData item = adapter.getItemPos(position);
            System.out.println("click "+item);
            Intent intent_Z=  new Intent(getApplicationContext(), Zoom_Review.class);
            intent_Z.putExtra("Item", item);
            startActivity(intent_Z);
        });
        recyclerView.setAdapter(adapter);


        /**Progress bar + Recycler View*/
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    progressBar.setVisibility(View.VISIBLE);
                    getItem();
                }
            }
        });



    }
    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retButton:
                    finish();
                    break;
                case R.id.cameraBtn:
                    Intent scan = new Intent(getApplicationContext(), ScannerActivity.class);
                    startActivity(scan);
                    break;
                case R.id.home_btn:
                    finish();
                    break;
            }
        }
    }
    private void getItem(){
        int end = Math.min(num + 25, list.size());
        for(int i=num; i<end;i++) {
            ItemData tmp= list.get(i);
            adapter.addItem(tmp);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        },1000);
        num=end;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // hideNavigationBar();
            showSystemUI();
        }
    }
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


}

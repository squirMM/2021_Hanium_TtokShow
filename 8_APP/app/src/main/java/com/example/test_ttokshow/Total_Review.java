package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.test_ttokshow.Recy.Adapter;
import com.example.test_ttokshow.Recy.ItemData;
import com.example.test_ttokshow.Recy.OnReviewItemClickListener;
import com.example.test_ttokshow.Recy.RecyclerDeco;
import com.example.test_ttokshow.Recy.ViewType;
import com.hedgehog.ratingbar.RatingBar;

import java.util.ArrayList;

public class Total_Review extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_total_review);

        Intent intent=getIntent();
        ArrayList<ItemData> list = (ArrayList<ItemData>)intent.getSerializableExtra("Item");

        /**custom star*/
        RatingBar mRatingBar =findViewById(R.id.ratingBar);
        mRatingBar.setStarCount(5);
        mRatingBar.setStar(2.8f);

        /**Button*/
        BtnOnClickListener onClickListener = new BtnOnClickListener();

        //ret Button
        ImageButton retBox = (ImageButton) findViewById(R.id.retButton);
        retBox.setOnClickListener(onClickListener);

        /**Recycler View*/
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.scrollToPosition(0);

        RecyclerDeco decoration_Height = new RecyclerDeco(0,0,2,2);
        recyclerView.addItemDecoration(decoration_Height);

        //recyclerView.smoothScrollBy(0, 672);

        Adapter adapter = new Adapter(ViewType.large,list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnReviewItemClickListener() {
            @Override
            public void onItemClick(Adapter.ViewHolder holder, View view, int position) {
                ItemData item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(),"아이템 선택 " + item.getSId(), Toast.LENGTH_LONG).show(); }
        });

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

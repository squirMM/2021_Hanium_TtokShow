package com.example.test_ttokshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test_ttokshow.Recy.Adapter;
import com.example.test_ttokshow.Recy.OnReviewItemClickListener;
import com.example.test_ttokshow.Recy.RecyclerDeco;
import com.example.test_ttokshow.Recy.ItemData;
import com.example.test_ttokshow.Recy.ViewType;
import com.hedgehog.ratingbar.RatingBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView product_name;
    private ImageButton open_bu;
    private Dialog dialog;
    private ImageView iv_image;
    private ItemData item;
    public ArrayList<ItemData> list_s;
    public ArrayList<ItemData> list;
    private static final String TAG = "MainActivity";
    public String[] output = new String[10];
    public String re = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        list_s = new ArrayList<>();
        list = new ArrayList<>();

        new Thread(){
            @Override
            public void run(){
                Client.main();
                String[] out = Client.getOutput();
                output = out;
                for (int i=1; i<(output.length/5); i++) {
                    item= new ItemData(output[5*i+3],output[5*i+4],output[5*i],output[5*i+1],output[5*i+2]);
                    if(i<10)list_s.add(item);
                    list.add(item);
                }
            }
        }.start();
        output[0] = "Already";

        /**Recycler view*/
        RecyclerView recyclerView = findViewById(R.id.recyclerView_s);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        RecyclerDeco decoration_Width = new RecyclerDeco(20,50,0,0);
        recyclerView.addItemDecoration(decoration_Width);

        Adapter adapter = new Adapter(ViewType.small,list_s);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnReviewItemClickListener() {
            @Override
            public void onItemClick(Adapter.ViewHolder holder, View view, int position) {
                ItemData item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(),"왜 ㅜㅜ", Toast.LENGTH_LONG).show();
            }
        });


        /**custom star*/
        RatingBar mRatingBar =findViewById(R.id.ratingBar);
        mRatingBar.setStarCount(5);
        mRatingBar.setStar(2.8f);

        /**Text*/
        product_name=(TextView)findViewById(R.id.name);
        product_name.setSingleLine(true);    // 한줄로 표시하기
        product_name.setEllipsize(TextUtils.TruncateAt.MARQUEE); // 흐르게 만들기
        product_name.setSelected(true);      // 선택하기

        /**image*/
        iv_image = (ImageView)findViewById(R.id.keywordbox);
        String image_url_con = "https://post-phinf.pstatic.net/MjAxOTA3MDVfNDcg/MDAxNTYyMzA1MTQ0Njc0.04P0QuAk7pRDhmuLYa2Op36kmArY2gO_lwluLr7CE7og.y1dyZeUEudhu9-uTUKSUymLjC3wt8XsuRD7Zx_UoOZAg.JPEG/naver_%ED%95%B4%EB%B0%94%EB%9D%BC%EA%B8%B0_1_pixabay.jpg?type=w1200";
        //"https://drive.google.com/uc?id="+/view~이전에 있는 링크 복붙하면됨
        String image_url=" https://drive.google.com/uc?id=10ce-cbRdeSQynRBRlmBDR94vAdzg0-rA";
        loadImageTask imageTask = new loadImageTask(image_url);
        imageTask.execute();

        /**Button*/
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

        /**Error Dialog*/
        dialog = new Dialog(MainActivity.this);       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.error_popup); //xml 연결
        while (output[0] == "Already") {
            continue;
        }

        //임시 다이얼로그
//        findViewById(R.id.test_pop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                  showDialog(); // 아래 showDialog01() 함수 호출
//            }});

    }

    class BtnOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.all_review:
                    Intent intent = new Intent(getApplicationContext(), Total_Review.class);
                    intent.putExtra("Item", list);
                    startActivity(intent);
                    break;
                case R.id.open:
                    LinearLayout inflatedLayout = (LinearLayout) findViewById(R.id.inflatedlayout);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    if (!open_bu.isSelected()) {
                        inflater.inflate(R.layout.inflated_layout, inflatedLayout);
                        open_bu.setSelected(true);
                    } else {
                        inflatedLayout.removeAllViews();
                        open_bu.setSelected(false);
                    }
                    break;
                case R.id.home_btn:
                    Intent scan = new Intent(getApplicationContext(), ScannerActivity.class);
                    startActivityForResult(scan, 0);

            }
        }

    }
    public class loadImageTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final String url;
        public loadImageTask(String url) { this.url = url; }
        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap imgBitmap = null;
            try {
                URL url1 = new URL(url);
                URLConnection conn = url1.openConnection();
                conn.connect();
                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
                imgBitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imgBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bit) {
            super.onPostExecute(bit);
            iv_image.setImageBitmap(bit);
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
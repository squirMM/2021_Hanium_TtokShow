package com.example.test_ttokshow;

import android.app.Application;

public class staticItem extends Application {
    private String avg;
    private String proName;
    private int cnt;
    @Override
    public void onCreate() {
        //전역 변수 초기화
        avg = "";
        proName="";
        cnt=0;
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setState(String avg, String proName, int cnt){
        this.avg=avg;
        this.proName=proName;
        this.cnt=cnt;
    }

    public String getProName(){
        return proName;
    }

    public String getAvg() {
        return avg;
    }

    public int getCnt() {
        return cnt;
    }
}

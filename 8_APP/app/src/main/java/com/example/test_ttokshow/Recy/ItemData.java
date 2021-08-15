package com.example.test_ttokshow.Recy;

import java.io.Serializable;

public class ItemData implements Serializable {
    //android:id="@+id/grade_num" android:id="@+id/cite" android:id="@+id/reviewId" android:id="@+id/review_date"android:id="@+id/contents"
    private String Sgrade;
    private String Scite;
    private String SId;
    private String Sdate;
    private String Scontents;

    public ItemData(String Sgrade, String Scite, String SId,String Sdate,String Scontents ){
        this.Sgrade=Sgrade;
        this.Scite=Scite;
        this.SId=SId;
        this.Sdate=Sdate;
        this.Scontents=Scontents;
    }

    public String getSgrade() {
        return Sgrade;
    }

    public String getScite() {
        return Scite;
    }

    public String getSId() {
        return SId;
    }

    public String getSdate() {
        return Sdate;
    }

    public String getScontents() {
        return Scontents;
    }

    public void setSgrade(int Sgrade) {
        Sgrade = Sgrade;
    }

    public void setScite(String scite) {
        Scite = scite;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public void setSdate(String sdate) {
        Sdate = sdate;
    }

    public void setScontents(String scontents) {
        Scontents = scontents;
    }
}

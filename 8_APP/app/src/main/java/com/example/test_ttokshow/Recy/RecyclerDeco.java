package com.example.test_ttokshow.Recy;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDeco extends RecyclerView.ItemDecoration {

    private final int divWidthR;
    private final int divWidthL;
    private final int divHeightT;
    private final int divHeightB;

    public RecyclerDeco(int divWidthR, int divWidthL,int divHeightT,int divHeightB)
    {
        this.divWidthR = divWidthR;
        this.divWidthL = divWidthL;
        this.divHeightT=divHeightT;
        this.divHeightB=divHeightB;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidthR;
        outRect.left=divWidthL;
        outRect.top=divHeightT;
        outRect.bottom=divHeightB;
    }
}

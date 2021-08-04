package com.example.test_ttokshow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ListData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ListData> _oData) {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount() {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.review_text_l, parent, false);
        }

        TextView oTextCite = (TextView) convertView.findViewById(R.id.cite);
        TextView oTextDate = (TextView) convertView.findViewById(R.id.review_date);
        TextView oTextContents = (TextView) convertView.findViewById(R.id.contents);
        TextView oTextStar = (TextView) convertView.findViewById(R.id.grade_num);
        TextView oTextId = (TextView) convertView.findViewById(R.id.reviewId);

        oTextCite.setText(m_oData.get(position).StrCite_name);
        oTextDate.setText(m_oData.get(position).StrDate);
        oTextContents.setText(m_oData.get(position).StrContents);
        oTextStar.setText(m_oData.get(position).StrStar_rank);
        oTextId.setText(m_oData.get(position).StrId);

        return convertView;
    }

}

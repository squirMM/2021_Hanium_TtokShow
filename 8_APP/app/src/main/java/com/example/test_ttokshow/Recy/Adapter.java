package com.example.test_ttokshow.Recy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test_ttokshow.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements OnReviewItemClickListener {

    private ArrayList<ItemData> itemData;
    private ViewType sel_type;
    private Intent intent;
    // 리스너 객체 참조를 저장하는 변수
    private OnReviewItemClickListener listener;

    public Adapter(ViewType sel_type,ArrayList<ItemData> itemData) {
        this.sel_type = sel_type;
        this.itemData = itemData;
    }
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnReviewItemClickListener listener) {
        this.listener = listener ;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){ listener.onItemClick(holder,view,position); }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(sel_type==ViewType.small) {
            view = inflater.inflate(R.layout.recycle_s, parent, false);
            return new ViewHolder(view,this);
        }
        else if(sel_type==ViewType.large){
            view = inflater.inflate(R.layout.recycle, parent, false);
            return new ViewHolder(view,this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemData item = itemData.get(position);
        holder.rank.setText(item.getSgrade());
        holder.cite.setText(item.getScite());
        holder.id.setText(item.getSId());
        holder.date.setText(item.getSdate());
        holder.contents.setText(item.getScontents());

        if (Integer.parseInt(item.getSgrade()) > 4){
            holder.imageView.setImageResource(R.drawable.aver5);
        }
        else if (Integer.parseInt(item.getSgrade()) > 3){
            holder.imageView.setImageResource(R.drawable.aver4);
        }
        else if (Integer.parseInt(item.getSgrade()) > 2) {
            holder.imageView.setImageResource(R.drawable.aver3);
        }
        else if (Integer.parseInt(item.getSgrade()) > 1){
            holder.imageView.setImageResource(R.drawable.aver2);
        }
        else if (Integer.parseInt(item.getSgrade()) > 0){
            holder.imageView.setImageResource(R.drawable.aver1);
        }



    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public ItemData getItem(int position){ return itemData.get(position); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contents;
        TextView date;
        TextView id;
        TextView cite;
        TextView rank;
        ImageView imageView;
        public ViewHolder(@NonNull final View itemView, final OnReviewItemClickListener mListener) {
            super(itemView);
            contents = itemView.findViewById(R.id.contents);
            date = itemView.findViewById(R.id.review_date);
            id = itemView.findViewById(R.id.reviewId);
            cite = itemView.findViewById(R.id.cite);
            rank = itemView.findViewById(R.id.grade_num);

            imageView = itemView.findViewById(R.id.per_grade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (mListener != null) {
                        mListener.onItemClick(ViewHolder.this, v, pos);
                    }
                }
            });
        }

    }

}

package com.example.test_ttokshow.Recy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.test_ttokshow.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<ItemData> itemData;
    private ViewType sel_type;
    public Adapter(ViewType sel_type,ArrayList<ItemData> itemData) {
        this.sel_type=sel_type;
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(sel_type==ViewType.small) {
            view = inflater.inflate(R.layout.recycle_s, parent, false);
            return new ViewHolder(view);
        }
        else if(sel_type==ViewType.large){
            view = inflater.inflate(R.layout.recycle, parent, false);
            return new ViewHolder(view);
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

    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contents;
        TextView date;
        TextView id;
        TextView cite;
        TextView rank;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.contents);
            date=itemView.findViewById(R.id.review_date);
            id=itemView.findViewById(R.id.reviewId);
            cite=itemView.findViewById(R.id.cite);
            rank=itemView.findViewById(R.id.grade_num);
        }

    }
}

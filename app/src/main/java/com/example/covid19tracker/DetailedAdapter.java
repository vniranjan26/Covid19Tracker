package com.example.covid19tracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailedAdapter extends RecyclerView.Adapter<DetailedAdapter.DetailedViewHolder>{
    List<Detailedlist> detailedlists;
    Context context;

    public DetailedAdapter(List<Detailedlist> detailedlists, Context context) {
        this.detailedlists = detailedlists;
        this.context = context;
    }
    @NonNull
    @Override
    public DetailedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.confirmed_list,parent,false);
        return new DetailedAdapter.DetailedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedViewHolder holder, int position) {
        if(position %2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")); }
        else
        { holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD")); }
        Detailedlist obj= detailedlists.get(position);
        holder.dateTv.setText(obj.getDate());
        holder.totalconfirmedTv.setText(""+obj.getTotal());
        holder.confirmsTv.setText(""+obj.getToday());
    }

    @Override
    public int getItemCount() { return detailedlists.size(); }

    public class DetailedViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView dateTv,totalconfirmedTv,confirmsTv;
        public DetailedViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv=itemView.findViewById(R.id.dateTv);
            totalconfirmedTv=itemView.findViewById(R.id.totalconfirmedTv);
            confirmsTv=itemView.findViewById(R.id.confirmsTv);
            container=itemView.findViewById(R.id.container);
        }
    }
}

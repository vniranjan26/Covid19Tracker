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

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ActiveViewHolder> {

    List<Activelist> activelist;
    Context context;

    public ActiveAdapter(List<Activelist> activelist, Context context) {
        this.activelist = activelist;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.confirmed_list,parent,false);
        return new ActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveViewHolder holder, int position) {
        if(position %2 == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
            Activelist obj= activelist.get(position);
            holder.dateTv.setText(obj.getDate());
            holder.totalconfirmedTv.setText(""+obj.getActive_case());
            holder.confirmsTv.setText(""+obj.getPercentage()+" %");
    }

    @Override
    public int getItemCount() {
        return activelist.size();
    }

    public  class ActiveViewHolder extends RecyclerView.ViewHolder{
        LinearLayout container;
        TextView dateTv,totalconfirmedTv,confirmsTv;
        public ActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv=itemView.findViewById(R.id.dateTv);
            totalconfirmedTv=itemView.findViewById(R.id.totalconfirmedTv);
            confirmsTv=itemView.findViewById(R.id.confirmsTv);
            container=itemView.findViewById(R.id.container);
        }
    }
}

package com.example.covid19tracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder>{
    List<Statelist> statelists;
    Context context;

    public StateAdapter(List<Statelist> statelists, Context context) {
        this.statelists = statelists;
        this.context = context;
    }
    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        return new StateAdapter.StateViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        if(position %2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")); }
        else
        { holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD")); }
        Statelist obj= statelists.get(position);
        holder.stateTv.setText(obj.getState());
        holder.lastupdatetimeTv.setText(obj.getUpdateTime());
        holder.deltaconfirmedTv.setText(obj.getDeltaconfirm());
        holder.deltarecoveredTv.setText(obj.getDeltarecover());
        holder.deltadeceasedTv.setText(obj.getDeltadeath());
        holder.confirmedTv.setText(""+obj.getConfirm());
        holder.recoveredTv.setText(""+obj.getRecover());
        holder.deceasedTv.setText(""+obj.getDeath());
        holder.activeTv.setText(""+obj.getActive());
    }

    @Override
    public int getItemCount() {
        return statelists.size();
    }

    public class StateViewHolder extends RecyclerView.ViewHolder{
        TextView stateTv,lastupdatetimeTv,confirmedTv,deltaconfirmedTv,activeTv,recoveredTv,deltarecoveredTv,deceasedTv,deltadeceasedTv;
        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            stateTv=itemView.findViewById(R.id.stateTv);
            lastupdatetimeTv=itemView.findViewById(R.id.lastupdatetimeTv);
            confirmedTv=itemView.findViewById(R.id.confirmedTv);
            deltaconfirmedTv=itemView.findViewById(R.id.deltaconfirmedTv);
            activeTv=itemView.findViewById(R.id.activeTv);
            recoveredTv=itemView.findViewById(R.id.recoveredTv);
            deltarecoveredTv=itemView.findViewById(R.id.deltarecoveredTv);
            deceasedTv=itemView.findViewById(R.id.deceasedTv);
            deltadeceasedTv=itemView.findViewById(R.id.deltadeceasedTv);

        }
    }
}

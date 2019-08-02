package com.paneapple.paneapple.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paneapple.paneapple.models.Messsage;
import com.paneapple.paneapple.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Messsage> messsages;

    public MessageAdapter(Activity activity, ArrayList<Messsage> messsages) {
        this.activity = activity;
        this.messsages = messsages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.message_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_send.setText(messsages.get(i).message);
        viewHolder.tv_recive.setText(messsages.get(i).message);
        if(messsages.get(i).getType().equals("1")){
            viewHolder.tv_send.setVisibility(View.VISIBLE);
            viewHolder.tv_recive.setVisibility(View.GONE);
        }else{
            viewHolder.tv_send.setVisibility(View.GONE);
            viewHolder.tv_recive.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messsages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_send;
        public TextView tv_recive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_send = itemView.findViewById(R.id.tv_send);
            tv_recive = itemView.findViewById(R.id.tv_recive);
        }
    }
}

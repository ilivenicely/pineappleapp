package com.paneapple.paneapple.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paneapple.paneapple.tutor.ChatWithTutorActivity;
import com.paneapple.paneapple.tutor.Preferences;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.models.Tutor;

import java.util.ArrayList;

public class StudentChatAdapter extends RecyclerView.Adapter<StudentChatAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Tutor> tutors;

    public StudentChatAdapter(ArrayList<Tutor> tutors, Activity activity) {
        this.tutors = tutors;
        this.activity = activity;

    }

    @NonNull
    @Override
    public StudentChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_chat_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentChatAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_tutor_name.setText(tutors.get(i).username);
        myViewHolder.tv_tutor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*VideoChatViewActivity.token="demoChannel1";
                activity.startActivity(new Intent(activity,VideoChatViewActivity.class));*/

            }
        });


        myViewHolder.linearMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Preferences.writeString(activity,Preferences.Reciever_id,tutors.get(i).getUid());
                Preferences.writeString(activity,Preferences.reciever_name,tutors.get(i).getName());
              activity.startActivity(new Intent(activity, ChatWithTutorActivity.class));
               // activity.startActivity(new Intent(activity, ProceedToPayment.class));


            }
        });
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public void filertdta(ArrayList<Tutor> filter_tutors) {
        tutors = filter_tutors;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tutor_name,tv_tutor_mesage,tv_rate;
        public LinearLayout linearMain;
        public ImageView image;
        public View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tutor_name = (TextView) itemView.findViewById(R.id.tv_tutor_name);
            tv_tutor_mesage = (TextView) itemView.findViewById(R.id.tv_tutor_mesage);
            linearMain = (LinearLayout) itemView.findViewById(R.id.linearMain);
            image = (ImageView) itemView.findViewById(R.id.image);
            view = (View) itemView.findViewById(R.id.view);
        }
    }
}

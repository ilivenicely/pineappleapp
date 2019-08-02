package com.paneapple.paneapple.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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
import com.paneapple.paneapple.students.StudentChatList;

import java.util.ArrayList;

public class TutorChatAdapter extends RecyclerView.Adapter<TutorChatAdapter.ViewHolder> {
    Activity activity;
    ArrayList<StudentChatList> tutors;
    public TutorChatAdapter(ArrayList<StudentChatList> tutors, FragmentActivity activity) {
        this.activity=activity;
        this.tutors=tutors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.student_chat_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, final int i) {
        myViewHolder.tv_tutor_name.setText(tutors.get(i).getStudent_name());
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


                Preferences.writeString(activity,Preferences.Reciever_id,tutors.get(i).getReciever_id());
                Preferences.writeString(activity,Preferences.reciever_name,tutors.get(i).getStudent_name());
                activity.startActivity(new Intent(activity, ChatWithTutorActivity.class));
                // activity.startActivity(new Intent(activity, ProceedToPayment.class));


            }
        });
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public void filertdta(ArrayList<StudentChatList> filter_tutors) {
        tutors = filter_tutors;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tutor_name,tv_tutor_mesage,tv_rate;
        public LinearLayout linearMain;
        public ImageView image;
        public View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tutor_name = (TextView) itemView.findViewById(R.id.tv_tutor_name);
            tv_tutor_mesage = (TextView) itemView.findViewById(R.id.tv_tutor_mesage);
            linearMain = (LinearLayout) itemView.findViewById(R.id.linearMain);
            image = (ImageView) itemView.findViewById(R.id.image);
            view = (View) itemView.findViewById(R.id.view);
        }
    }
}

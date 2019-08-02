package com.paneapple.paneapple.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.paneapple.paneapple.tutor.Find_Tutor;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.models.Tutor_list_Model;

import java.util.List;


public class Tutor_list_Adapter extends RecyclerView.Adapter<Tutor_list_Adapter.MyViewHolder> {
    List<Tutor_list_Model> member_directory_modelList;
    private  Context context;
    List<Image> images = null;

    public Tutor_list_Adapter(Context context, List<Tutor_list_Model> member_directory_modelList) {
        this.member_directory_modelList = member_directory_modelList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Tutor_name;
        ImageView Tutor_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.Tutor_name = (TextView) itemView.findViewById(R.id.tutor_list_name);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular);
            this.Tutor_name.setTypeface(typeface);
            this.Tutor_image = (ImageView) itemView.findViewById(R.id.tutor_list_profile);


            // this.remarks=(TextView)itemView.findViewById(R.id.client_list_remarks);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tutor_list_layout, parent, false);
        view.setOnClickListener(Find_Tutor.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final Tutor_list_Model mylist = member_directory_modelList.get(listPosition);
        holder.Tutor_name.setText(mylist.getmTutor_name());
        Glide.with(context).load(mylist.getmTutor_profile()).into(holder.Tutor_image);
      /*  holder.Call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bundle extras = new Bundle();
                String number=mylist.getmCall();
                extras.putString("ContactNo", mylist.getContact_No());
                Intent intent = new Intent(context, CallScreen.class);
                intent.putExtras(extras);
                context.startActivity(intent);


            }

        });
        holder.Whatsapp_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bundle extras = new Bundle();
                extras.putString("ContactNo", mylist.getmCall());
                Intent intent = new Intent(context, WhatsappScreen.class);
                intent.putExtras(extras);
                context.startActivity(intent);

            }

        });

        holder.Message_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bundle extras = new Bundle();
                extras.putString("ContactNo", mylist.getContact_No());
                Intent intent = new Intent(context, MessageScreen.class);
                intent.putExtras(extras);
                context.startActivity(intent);

            }

        });

        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Member_Directory_Complete_Information.class);
                intent.putExtra("Name",mylist.getFullname());
                intent.putExtra("Business_logo",mylist.getBusiness_logo());
                intent.putExtra("Business_name",mylist.getBusiness_name());
                intent.putExtra("Business_description",mylist.getBusiness_description());
                intent.putExtra("email",mylist.getUsername());
                intent.putExtra("ContactNo",mylist.getContact_No());
                intent.putExtra("WhatsappNo",mylist.getWhatsappNo());
                intent.putExtra("Business_logo_authentication_id",mylist.getBusiness_Authentication_key());
                intent.putExtra("Business_Key",mylist.getBusiness_Key_id());
                //Toast.makeText(context,mylist.getBusiness_Key_id(),Toast.LENGTH_LONG).show();
                context.startActivity(intent);
  }
        });*/


 /*       holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Bundle extras = new Bundle();
                extras.putString("Key",mylist.getmKey());
                extras.putString("Name", mylist.getmName());
                extras.putString("Description",mylist.getmDescription());
                extras.putString("Profile",mylist.getmProfile());
                Intent intent = new Intent(context, Lawyer_Listing_Profile.class);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });*/

                }

@Override
    public int getItemCount() {
        int arr = 0;

        try {
            if (member_directory_modelList.size() == 0) {

                arr = 0;

            } else {
                arr = member_directory_modelList.size();
            }


        } catch (Exception e) {


        }

        return arr;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
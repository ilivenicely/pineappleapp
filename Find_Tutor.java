package com.paneapple.paneapple.tutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.adapters.Tutor_list_Adapter;
import com.paneapple.paneapple.models.Tutor_list_Model;

import java.util.ArrayList;
import java.util.List;

public class Find_Tutor extends AppCompatActivity {
    public static View.OnClickListener myOnClickListener;
    FirebaseDatabase database;
    DatabaseReference mDatabase_reference;
    List<Tutor_list_Model> list;
    RecyclerView mRecyclerView_users_;
    private ProgressDialog progressBar;
    private Toolbar toolbar;
    private Context context;
    private ImageView back_image_view;
    private ShimmerFrameLayout mShimmerViewContainer;
    private EditText SearchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__tutor);
        SearchName=findViewById(R.id.search_name);
        SearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fetch_Clients_Name();
            }
        });
        Tutor_lists();
    }
    private void Tutor_lists(){
        this.context = context;
        ShimmerFrameLayout shimmerContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmerAnimation();
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRecyclerView_users_ = (RecyclerView) findViewById(R.id.recycler_view_tutor_list);
        progressBar = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        mDatabase_reference = database.getReference("All_Tutors");
        mDatabase_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<Tutor_list_Model>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    // progressBar.dismiss();
                    String key=dataSnapshot1.getKey();
                    Tutor_list_Model user_list_model_object = new Tutor_list_Model();
                    user_list_model_object.setmTutor_name(dataSnapshot1.child("FirstName").getValue(String.class));
                    user_list_model_object.setmTutor_profile(dataSnapshot1.child("Tutor_profile").getValue(String.class));
                    Tutor_list_Model model_object = new Tutor_list_Model();
                    String tutor_name = user_list_model_object.getmTutor_name();
                    String tutorprofile = user_list_model_object.getmTutor_profile();

                    model_object.setmTutor_name(tutor_name);
                    model_object.setmTutor_profile(tutorprofile);
                    //  mDatabase_reference.child(id).push().child()
                    progressBar.dismiss();
                    list.add(model_object);
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    Retrieve_Users_Lists();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

    }

    private void Retrieve_Users_Lists() {
       Tutor_list_Adapter recyclerAdapter = new Tutor_list_Adapter(getApplicationContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView_users_.setLayoutManager(layoutManager);
        mRecyclerView_users_.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView_users_.setHasFixedSize(true);
        mRecyclerView_users_.setLayoutManager(layoutManager);
        mRecyclerView_users_.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView_users_.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView_users_.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
        mRecyclerView_users_.setAdapter(recyclerAdapter);
    }

    private void Fetch_Clients_Name() {
        database = FirebaseDatabase.getInstance();
        mDatabase_reference = FirebaseDatabase.getInstance().getReference();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase_reference.child("All_Tutors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<Tutor_list_Model>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    Tutor_list_Model user_list_model_object = new Tutor_list_Model();
                    user_list_model_object.setmTutor_name(areaSnapshot.child("FirstName").getValue(String.class));
                    Tutor_list_Model model_object = new Tutor_list_Model();
                    String areaName=model_object.getmTutor_name();
                    model_object.setmTutor_name(areaName);
                    //Toast.makeText(getApplicationContext(), areaName, Toast.LENGTH_LONG).show();
                    list.add(model_object);
                    // if(list.add(""))
                    // Toast.makeText(getApplicationContext(), "Name Founds ", Toast.LENGTH_LONG).show();

                }
                // list.add(0,"New Client");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.paneapple.paneapple.students;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.adapters.StudentChatAdapter;
import com.paneapple.paneapple.models.Tutor;

import java.util.ArrayList;

public class StudentChat_Activity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference_tutor,databaseReference_chatlist;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Tutor> tutors;
    private RecyclerView rv_tutors;
    private StudentChatAdapter tutorsAdapter;
    private EditText et_search;
    private ProgressBar pb_prgress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat_);
        initview();

    }
    private void initview() {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference_tutor = firebaseDatabase.getReference("All_Tutors");
        rv_tutors = findViewById(R.id.rv_tutors);
        et_search = findViewById(R.id.et_search);
        pb_prgress_bar = findViewById(R.id.pb_prgress_bar);
        tutors = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_tutors.setLayoutManager(linearLayoutManager);
        tutorsAdapter = new StudentChatAdapter(tutors, this);
        rv_tutors.setAdapter(tutorsAdapter);
        pb_prgress_bar.setVisibility(View.VISIBLE);
        getTutors();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterdata(s.toString());

            }
        });

    }   private void filterdata(String query) {
        ArrayList<Tutor> filter_tutors = new ArrayList<>();
        for (Tutor tutor : tutors) {
            if (tutor.username.toLowerCase().contains(query.toLowerCase())) {
                filter_tutors.add(tutor);
            }
        }
        if(query.length()>0){
            tutorsAdapter.filertdta(filter_tutors);
        }else{
            tutorsAdapter.filertdta(tutors);
        }

    }

    private void getTutors() {

        databaseReference_tutor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pb_prgress_bar.setVisibility(View.GONE);
                Log.e("tutors", dataSnapshot + "");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tutor tutorData = snapshot.getValue(Tutor.class);
                    tutors.add(tutorData);
                }
                tutorsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                pb_prgress_bar.setVisibility(View.GONE);
            }
        });
    }
}

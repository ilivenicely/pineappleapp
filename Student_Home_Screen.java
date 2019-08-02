package com.paneapple.paneapple.students;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paneapple.paneapple.tutor.ChatWithTutorActivity;
import com.paneapple.paneapple.tutor.Find_Tutor;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.tutor.Tutor_Login_Screen;
import com.paneapple.paneapple.models.User_Model;

import de.hdodenhof.circleimageview.CircleImageView;


public class Student_Home_Screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private ImageView User_profile;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database;
    private Toolbar toolbar;
    TabLayout tabLayout;
    private CircleImageView mStudent_profile_image;
    private TextView mStudent_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home__screen);
        mStudent_profile_image=findViewById(R.id.student_profile_id_home);
        mStudent_name=findViewById(R.id.student_username_id_home);
        SetupNevigation();
    }
    private void SetupNevigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.close, R.string.open);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setTitle("");
        NavigationView mNavigationView = findViewById(R.id.navigationview_id);
        mNavigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);// you need to set Listener.
        Firebase_CurrentUser_Authentication();
        try {
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User_Model user_list_model_object = new User_Model();
                    user_list_model_object.setUserName(dataSnapshot.child("UserName").getValue(String.class));
                    User_Model model_object = new User_Model();
                    String name = user_list_model_object.getUserName();
                    model_object.setUserName(name);
                    mStudent_name.setText(name);
                    try {
                        String link = dataSnapshot.child("User_profile").getValue(String.class);
                        Glide.with(getApplicationContext()).load(link).into(mStudent_profile_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
            toggle.syncState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_user_profile) {
            Intent member_directory_intent = new Intent(getApplicationContext(),Student_profile.class);
            startActivity(member_directory_intent);
        } else if (id == R.id.item_user_home) {
            Intent intent = new Intent(getApplicationContext(), Student_Home_Screen.class);
            startActivity(intent);
        } else if (id == R.id.item_user_find_tutor) {
            Intent intent = new Intent(getApplicationContext(), Find_Tutor.class);
            startActivity(intent);
        } else if (id == R.id.item_userchat) {
            Intent intent = new Intent(getApplicationContext(), ChatWithTutorActivity.class);
            startActivity(intent);
        }
         else if(id == R.id.item_user_logout_user) {
            Toast.makeText(getApplicationContext(), "Successfully Sign Out !", Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Tutor_Login_Screen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
    private void Firebase_CurrentUser_Authentication() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("All_Users").child(firebaseUser.getUid());
    }
}

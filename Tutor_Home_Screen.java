package com.paneapple.paneapple.tutor;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.models.User_Model;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class Tutor_Home_Screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private TextView Name,UserName;
    private ImageView User_profile;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database;
    private CircleImageView mTutor_image;
    private Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor__home__screen);
        Name=findViewById(R.id.tutor_name_id_home);
        UserName=findViewById(R.id.tutor_username_id_home);
        mTutor_image=findViewById(R.id.tutor_profile_id_home);
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
        NavigationView mNavigationView = findViewById(R.id.navigationview_tutor_id);
        mNavigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);// you need to set Listener.
        final View headerView = LayoutInflater.from(this).inflate(R.layout.navigation_header_user, mNavigationView, false);
        mNavigationView.addHeaderView(headerView);
        Firebase_CurrentUser_Authentication();
        try {
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User_Model user_list_model_object = new User_Model();
                    user_list_model_object.setUserName(dataSnapshot.child("UserName").getValue(String.class));
                    user_list_model_object.setFirstName(dataSnapshot.child("FirstName").getValue(String.class));
                    User_Model model_object = new User_Model();
                    String name=user_list_model_object.getFirstName();
                    model_object.setFirstName(name);
                    Name.setText(name);
                    String username = user_list_model_object.getUserName();
                    model_object.setUserName(username);
                    UserName.setText(username);
                    try {
                        String link = dataSnapshot.child("Tutor_profile").getValue(String.class);
                        Glide.with(getApplicationContext()).load(link).into(mTutor_image);
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
        if (id == R.id.item_tutor_home) {
            Intent member_directory_intent = new Intent(getApplicationContext(), Tutor_Home_Screen.class);
            startActivity(member_directory_intent);
        } else if (id == R.id.item_tutor_request) {
            Toasty.success(getApplicationContext(),"Coming Soon ",Toasty.LENGTH_LONG).show();
        /*    Intent = new Intent(getApplicationContext(), StudentChatFragment.class);
            startActivity(intent);ntent i*/
        } else if (id == R.id.item_tutor_chat) {
            Intent intent = new Intent(getApplicationContext(), ChatWithTutorActivity.class);
            startActivity(intent);
        } else if (id == R.id.item_user_contact_us) {
     /*       Intent intent = new Intent(getApplicationContext(), Show_All_Events.class);
            startActivity(intent);*/
        } /*else if (id == R.id.item_setting_chamber) {
            Intent intent = new Intent(getApplicationContext(), Setting_Chamber
                    .class);
            startActivity(intent);
        }*/
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
        mdatabase = FirebaseDatabase.getInstance().getReference().child("All_Tutors").child(firebaseUser.getUid());
    }
}

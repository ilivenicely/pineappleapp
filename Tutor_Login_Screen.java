package com.paneapple.paneapple.tutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.paneapple.paneapple.R;
import com.paneapple.paneapple.students.Student_SignUp;
import com.paneapple.paneapple.tutor.Tutor_SignUp;

public class Tutor_Login_Screen extends AppCompatActivity {
    TextView mSignUp,mDont_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor__login__screen);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSignUp=(TextView)findViewById(R.id.sign_up);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Tutor_SignUp.class);
                startActivity(intent);
            }
        });
        mDont_have_account=(TextView)findViewById(R.id.donthave_account_id);
        mDont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Student_SignUp.class);
                startActivity(intent);
            }
        });
    }
}

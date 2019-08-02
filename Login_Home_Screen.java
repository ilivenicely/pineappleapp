package com.paneapple.paneapple.tutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.students.Student_Home_Screen;
import com.paneapple.paneapple.students.Student_SignUp;
import com.subhrajyoti.passwordview.PasswordView;

import es.dmoral.toasty.Toasty;

public class Login_Home_Screen extends AppCompatActivity {
    private Button mStudent_login;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;
    public DatabaseReference mdatabase;
    private PasswordView mPassword;
    private EditText mUserName;
    private TextView mSignUp;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home_screen);
        mSignUp=findViewById(R.id.sign_up_id);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Create_Account_Builder();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressDialog(this);
        mUserName = findViewById(R.id.student_login_username_id);
        mPassword = findViewById(R.id.student_login_password_id);
        mStudent_login = findViewById(R.id.login_btn_id);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("Email", "");
        password = sharedPreferences.getString("Password", "");
        mUserName.setText(email);
        mPassword.setText(password);
        mStudent_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student_loginfirebase();
            }
        });
    }

    private void Student_loginfirebase() {
        progressBar.setTitle("Signing In");
        progressBar.setMessage("Please wait a moment");
        progressBar.show();
        final String email = mUserName.getText().toString();
        final String password = mPassword.getText().toString();
        if (email.equals("") && password.equals("")) {
            progressBar.dismiss();
            Toast.makeText(getApplicationContext(), "Please Enter Email and Password", Toast.LENGTH_LONG).show();

        } else if (email.length() > 0 && password.equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
            progressBar.dismiss();

        } else if (email.equals("") && password.length() > 0) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
            progressBar.dismiss();

        } else {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                         Student_Authentication();
                    } else {
                        progressBar.dismiss();
                        Toast.makeText(getApplicationContext(), " Please Enter Correct Email and Password !", Toast.LENGTH_LONG).show();
                    }
                }


            });
        }
    }
    public void saveLoginDetails(String email, String password) {
        new Shared_Prefrance_Manager(getApplicationContext()).saveLoginDetails(email, password);
    }

    public void Create_Account_Builder() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login_Home_Screen.this);
        alertDialog.setTitle(Html.fromHtml("<font color='#000000'>Registration</font>"));
        alertDialog.setMessage(Html.fromHtml("<font color='#000000'>Are You Student or Tutor</font>"));
        alertDialog.setNeutralButton("Student", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent Std_intent = new Intent(Login_Home_Screen.this, Student_SignUp.class);
                startActivity(Std_intent);
            }
        });
        alertDialog.setPositiveButton((Html.fromHtml("<font color='#000000'>Tutor</font>")), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent tutor_intent = new Intent(Login_Home_Screen.this, Tutor_SignUp.class);
                startActivity(tutor_intent);
            }
        });
        final AlertDialog alert = alertDialog.create();
        alert.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, 0xf2f8d6));
        alert.show();
        Button btnPositive = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button btnNegative = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(0xffffff);
            }
        });
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        LinearLayout.LayoutParams layoutParam = (LinearLayout.LayoutParams) btnNegative.getLayoutParams();
        layoutParam.weight = 10;
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
        btnPositive.setBackgroundColor(getResources().getColor(R.color.paneapple_first_color));
        btnNegative.setBackgroundColor(getResources().getColor(R.color.paneapple_first_color));
        btnPositive.setTextColor(Color.WHITE);
        btnNegative.setTextColor(Color.WHITE);
//this dividerID we use for line color below the title but still cannot works
        try {
            int dividerId = alert.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = alert.findViewById(dividerId);
            divider.setBackgroundColor(getResources().getColor(R.color.white_color));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void Student_Authentication() {
        final String email = mUserName.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        try {
            mAuth = FirebaseAuth.getInstance();
            mdatabase = FirebaseDatabase.getInstance().getReference();
            Query query = mdatabase.child("All_Users").orderByChild("Password").equalTo(mPassword.getText().toString().trim());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            Intent intent = new Intent(getApplicationContext(), Student_Home_Screen.class);
                            startActivity(intent);
                            progressBar.dismiss();
                            Toasty.success(getApplicationContext(), "Successfully Authentication!", Toast.LENGTH_LONG, true).show();

                            //Toast.makeText(getApplicationContext(), "Successfully Authentication !", Toast.LENGTH_LONG).show();
                            saveLoginDetails(email, password);
                        }
                    } else {
                        Tutor_Authentication();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            progressBar.dismiss();
            Toast.makeText(getApplicationContext(), "Email Not Found ! ", Toast.LENGTH_LONG).show();
        }
    }
    private void Tutor_Authentication() {
        final String email = mUserName.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mdatabase.child("All_Tutors").orderByChild("Password").equalTo(mPassword.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        Intent intent = new Intent(getApplicationContext(), Tutor_Home_Screen.class);
                        progressBar.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully Authentication !", Toast.LENGTH_LONG).show();
                        saveLoginDetails(email, password);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

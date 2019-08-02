package com.paneapple.paneapple.students;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.paneapple.paneapple.R;
import com.subhrajyoti.passwordview.PasswordView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_SignUp extends AppCompatActivity {
    private EditText mFirst_name, mLast_name, mEmail, mCity, mAge;
    private PasswordView mPassword;
    private TextView mUser_Signup;
    Vibrator vibrator;
    private CircleImageView mUser_profile_id;
    private static final int request_code = 1;
    private StorageReference mStorage;
    private static final int GALLERY_PICK = 1;
    private Uri imageUri;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser firebaseUser;
    HashMap<String, String> users = new HashMap<>();
    private String download_url, user_id;
    Bitmap bmp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__sign_up);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference().child("User_profile_images");
        imageUri=null;
        mUser_profile_id = findViewById(R.id.user_signup_registration_profile);
        mFirst_name = findViewById(R.id.user_signup_registration_first_name_id);
        mLast_name = findViewById(R.id.user_signup_registration_last_name_id);
        mEmail = findViewById(R.id.user_signup_registration_email_id);
        mCity = findViewById(R.id.user_signup_registration_city);
        mPassword = findViewById(R.id.user_signup_registration_password);
        mAge = findViewById(R.id.user_signup_registration_age);
        mUser_Signup = findViewById(R.id.user_signup_registration_textview_id);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        progressBar = new ProgressDialog(this);
        //pick image from gallery and put it imageview to send user profile
        mUser_profile_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, request_code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            imageUri = data.getData();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bmp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                mUser_profile_id.setImageURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StorageReference user_profile = mStorage.child(user_id + ".jpg");
            user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        download_url = task.getResult().getDownloadUrl().toString();
                        users.put("User_profile", download_url);
                    }
                }
            });
        }

        mUser_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation_EditextText();
            }
        });
    }

    private void Validation_EditextText() {
        String mfirstname = mFirst_name.getText().toString();
        String mlastname = mLast_name.getText().toString();
        String memail = mEmail.getText().toString();
        String mpassword = mPassword.getText().toString();
        String mcity = mCity.getText().toString();
        String age = mAge.getText().toString();
        if (TextUtils.isEmpty(mfirstname)) {
            mFirst_name.setError("Please Enter Your First Name");
            mFirst_name.requestFocus();
            vibrator.vibrate(100);
        } else if (TextUtils.isEmpty(mlastname)) {
            mLast_name.setError("Please Enter Your Last Name");
            mLast_name.requestFocus();
            vibrator.vibrate(100);
        } else if (TextUtils.isEmpty(memail)) {
            mEmail.setError("Please Enter Your Email");
            mEmail.requestFocus();
            vibrator.vibrate(100);
        } else if (TextUtils.isEmpty(mpassword)) {
            mPassword.setError("Please Enter Your Password");
            mPassword.requestFocus();
            vibrator.vibrate(100);
        } else if (TextUtils.isEmpty(mcity)) {
            mCity.setError("Please Enter Your City");
            mCity.requestFocus();
            vibrator.vibrate(100);
        } else if (TextUtils.isEmpty(age)) {
            mAge.setError("Please Enter Your Age");
            mAge.requestFocus();
            vibrator.vibrate(100);
        } else {
            Registration_UserFirebase(memail, mpassword);
        }
    }

    private void Registration_UserFirebase(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        progressBar.setTitle("Please wait");
        progressBar.setMessage("While Creating Your Account");
        progressBar.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    users.put("User_profile", download_url);
                    users.put("FirstName", mFirst_name.getText().toString());
                    users.put("LastName", mLast_name.getText().toString());
                    users.put("UserName", mEmail.getText().toString());
                    users.put("Password", mPassword.getText().toString());
                    users.put("City", mCity.getText().toString());
                    users.put("Age", mAge.getText().toString());
                    mDatabase.child("All_Users").child(mAuth.getCurrentUser().getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.dismiss();
                                Toast.makeText(getApplicationContext(), "Your account has been Successfully Created  !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Student_SignUp.this, Student_Home_Screen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.dismiss();
                                Toast.makeText(Student_SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.dismiss();
                    Toast.makeText(Student_SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

package com.paneapple.paneapple.tutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import es.dmoral.toasty.Toasty;

public class Tutor_SignUp extends AppCompatActivity {
    private TextView mNext_Sound_Screen;
    private EditText mTutor_Fname,mTutor_Lname,mTutor_email,mTutor_city,mTutor_age;
    private PasswordView mTutor_password;
    CircleImageView mTutor_profile;
    private  byte[] byteArray;
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
    private Button mTutor_Registration_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor__sign_up);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        imageUri=null;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference().child("Tutor_profile_images");
        mTutor_Fname = findViewById(R.id.tutor_signup_registration_first_name_id);
        mTutor_Lname = findViewById(R.id.tutor_signup_registration_last_name_id);
        mTutor_email = findViewById(R.id.tutor_signup_registration_email_id);
        mTutor_password = findViewById(R.id.tutor_signup_registration_password_login_id);
        mTutor_city = findViewById(R.id.tutor_signup_registration_city_id);
        mTutor_age = findViewById(R.id.tutor_signup_registration_age_id);
        mTutor_profile = findViewById(R.id.tutor_signup_registration_profile_id);
        mTutor_Registration_btn = findViewById(R.id.registration_tutor_btn_id);
        progressBar = new ProgressDialog(this);
        mTutor_Registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration_UserFirebase(mTutor_email.getText().toString(), mTutor_password.getText().toString());
            }
        });
        mNext_Sound_Screen = (TextView) findViewById(R.id.tutor_signup_registration_next_id);
        mNext_Sound_Screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTutor_Fname.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your First Name ",Toast.LENGTH_LONG).show();
                }else if(mTutor_Lname.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Last Name ",Toast.LENGTH_LONG).show();

                }else if(mTutor_email.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Email ",Toast.LENGTH_LONG).show();

                }else if(mTutor_password.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Password ",Toast.LENGTH_LONG).show();

                }else if(mTutor_age.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Age ",Toast.LENGTH_LONG).show();

                }
                else if(mTutor_Lname.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Enter Your Last Name ",Toast.LENGTH_LONG).show();

                }else {
                    mTutor_profile.setDrawingCacheEnabled(true);
                    Bitmap b=mTutor_profile.getDrawingCache();
                    Intent next_intent = new Intent(getApplicationContext(), Sound_Screen_Tutor_Registration.class);
                    next_intent.putExtra("Profile",byteArray);
                    next_intent.putExtra("F_Name",mTutor_Fname.getText().toString());
                    next_intent.putExtra("L_Name",mTutor_Lname.getText().toString());
                    next_intent.putExtra("Email",mTutor_email.getText().toString());
                    next_intent.putExtra("Password",mTutor_password.getText().toString());
                    next_intent.putExtra("City",mTutor_city.getText().toString());
                    next_intent.putExtra("Age",mTutor_age.getText().toString());
                    startActivity(next_intent);
                }
            }
        });
        mTutor_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, request_code);
            }
        });
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
                imageUri = data.getData();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    byteArray = bytes.toByteArray();
                    mTutor_profile.setImageURI(imageUri);
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
                    users.put("Tutor_profile", download_url);
                    users.put("FirstName", mTutor_Fname.getText().toString());
                    users.put("LastName", mTutor_Lname.getText().toString());
                    users.put("UserName", mTutor_email.getText().toString());
                    users.put("Password", mTutor_password.getText().toString());
                    users.put("City", mTutor_city.getText().toString());
                    users.put("Age", mTutor_age.getText().toString());
                    mDatabase.child("All_Tutors").child(mAuth.getCurrentUser().getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.dismiss();
                                Toasty.success(getApplicationContext(), "Your account has been Successfully Created !", Toast.LENGTH_SHORT, true).show();
                                //Toast.makeText(getApplicationContext(), "Your account has been Successfully Created  !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Tutor_SignUp.this, Tutor_Home_Screen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.dismiss();
                                Toast.makeText(Tutor_SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.dismiss();
                    Toast.makeText(Tutor_SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


}
}

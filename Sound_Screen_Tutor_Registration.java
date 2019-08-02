package com.paneapple.paneapple.tutor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.paneapple.paneapple.students.Student_Home_Screen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Sound_Screen_Tutor_Registration extends AppCompatActivity {
    private String Firstname, Lastname, Email, Password, City, Age;
    private ImageView Sound_Recording_image, mTutor_profile_image;
    private CircleImageView mTutor_profile_id;
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
        setContentView(R.layout.activity_sound__screen__tutor__registration);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference().child("Tutor_profile_images");
        progressBar = new ProgressDialog(this);
        imageUri = null;
        mTutor_profile_image = findViewById(R.id.tutor_profile_id);
        Sound_Recording_image = findViewById(R.id.sound_recorder_id);
        mTutor_profile_id = findViewById(R.id.tutor_signup_registration_profile_id);
        Get_Bundles();
        Sound_Recording_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
            }
        });



    }

    private void Send_Tutor_DataFirebase(final String email, final String password) {
        mAuth = FirebaseAuth.getInstance();
        progressBar.setTitle("Please wait");
        progressBar.setMessage("While Creating Your Account");
        progressBar.show();
        mTutor_profile_image.setImageURI(imageUri);
        StorageReference user_profile = mStorage.child(user_id + ".jpg");
        user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    download_url = task.getResult().getDownloadUrl().toString();
                    users.put("Tutor_profile", download_url);
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            users.put("Tutor_profile", download_url);
                            users.put("FirstName", Firstname.toString());
                            users.put("LastName", Lastname.toString());
                            users.put("UserName", Email.toString());
                            users.put("Password", Password.toString());
                            users.put("City", City.toString());
                            users.put("Age", Age.toString());
                            mDatabase.child("All_Tutors").child(mAuth.getCurrentUser().getUid()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.dismiss();
                                        Toasty.success(getApplicationContext(), "Your account has been Successfully Created !", Toast.LENGTH_SHORT, true).show();

                                        //Toast.makeText(getApplicationContext(), "Your account has been Successfully Created  !", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Sound_Screen_Tutor_Registration.this, Student_Home_Screen.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressBar.dismiss();
                                        Toast.makeText(Sound_Screen_Tutor_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            progressBar.dismiss();
                            Toast.makeText(Sound_Screen_Tutor_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
                mTutor_profile_image.setImageURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                }

        }
    }

    private void Get_Bundles() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Firstname = bundle.getString("F_Name");
            Lastname = bundle.getString("L_Name");
            Email = bundle.getString("Email");
            Password = bundle.getString("Password");
            City = bundle.getString("City");
            Age = bundle.getString("Age");
            byte[] byteArray = bundle.getByteArray("Profile");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mTutor_profile_image.setImageBitmap(bmp);

        }
    }
}

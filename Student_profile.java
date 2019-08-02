package com.paneapple.paneapple.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paneapple.paneapple.R;

import es.dmoral.toasty.Toasty;

public class Student_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Toasty.success(getApplicationContext(),"Coming Soon ",Toasty.LENGTH_LONG).show();


    }
    private void Get_Profile(){



   /*     private void Upload_Profile() {
            // FirebaseUser firebaseUser=mAuth.getCurrentUser();
            // final String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
            final User_List_Model user = new User_List_Model();
            //FirebaseUser firebaseUser= FirebaseAuth.getinstance().getCurrentUser();
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                final String id = firebaseUser.getUid();
                progressBar.setTitle("Please Wait");
                progressBar.setMessage("While Loading Your Profile ");
                progressBar.show();
                database = FirebaseDatabase.getInstance();
                mDatabase_reference = database.getReference("All_Lawyers").child(id);
                mDatabase_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        User_List_Model user_model_object = new User_List_Model();
                        user_model_object.setUser_First_Name(dataSnapshot.child("Name").getValue(String.class));
                        user_model_object.setUser_Contact_No(dataSnapshot.child("ContactNo").getValue(String.class));
                        user_model_object.setUser_Description(dataSnapshot.child("Description").getValue(String.class));
                        user_model_object.setUser_Email(dataSnapshot.child("Email").getValue(String.class));
                        user_model_object.setUser_Password(dataSnapshot.child("Password").getValue(String.class));
                        user_model_object.setUser_City(dataSnapshot.child("City").getValue(String.class));
                        user_model_object.setUser_Country(dataSnapshot.child("Country").getValue(String.class));

                        User_List_Model model_object = new User_List_Model();
                        String f_name = user_model_object.getUser_First_Name();
                        String contact_no = user_model_object.getUser_Contact_No();
                        String description=user_model_object.getUser_Description();
                        String email = user_model_object.getUser_Email();
                        String password = user_model_object.getUser_Password();
                        String city = user_model_object.getUser_City();
                        String country = user_model_object.getUser_Country();

                        ///String img=user_list_model_object.getProfile();
                        model_object.setUser_First_Name(f_name);
                        model_object.setUser_Contact_No(contact_no);
                        model_object.setUser_Description(description);
                        model_object.setUser_Email(email);
                        model_object.setUser_Password(password);
                        model_object.setUser_City(city);
                        model_object.setUser_Country(country);

                        mFirstname.setText(f_name);
                        mContactno.setText(contact_no);
                        mDescription.setText(description);
                        mEmail.setText(email);
                        mPassword.setText(password);
                        mCity.setText(city);
                        mCountry.setText(country);
                        if (dataSnapshot.hasChild("Profile")) {
                            download_url = dataSnapshot.child("Profile").getValue(String.class);
                            Picasso.with(getBaseContext()).load(download_url).into(profileImageUpdate);
                        } else {
                            //  download_url = null;
                            Picasso.with(Lawyer_Profile.this)
                                    .load(R.drawable.prifle_icon)
                                    .into(profileImageUpdate);
                        }

                        //  mDatabase_reference.child(id).push().child()
                        progressBar.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }*/

    }
}

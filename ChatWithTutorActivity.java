package com.paneapple.paneapple.tutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paneapple.paneapple.R;
import com.paneapple.paneapple.adapters.MessageAdapter;
import com.paneapple.paneapple.models.Messsage;
import com.paneapple.paneapple.models.Student;
import com.paneapple.paneapple.models.Tutor;
import com.paneapple.paneapple.students.StudentChatList;

import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChatWithTutorActivity extends AppCompatActivity {

    ImageView emoji_btn, img_attachment, camera_btn, btnSend;
    EmojiconEditText editMessage;
    DatabaseReference firebaseDatabase;
    FirebaseAuth firebaseAuth;
    public String reciever_id = "", et_msg = "", login_type = "", sender_name = "",reciever_name="";
    ArrayList<Messsage> messsages;
    public RecyclerView recyclerview1;
    MessageAdapter messageAdapter;
    ImageView emojiButton;
    ImageView submitButton;
    View rootView;
    EmojIconActions emojIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_tutor);
        rootView = findViewById(R.id.root_view);
        emojiButton = (ImageView) findViewById(R.id.emoji_btn);

        reciever_id = Preferences.readString(ChatWithTutorActivity.this, Preferences.Reciever_id, "");
        reciever_name = Preferences.readString(ChatWithTutorActivity.this, Preferences.reciever_name, "");
        login_type = Preferences.readString(ChatWithTutorActivity.this, Preferences.login_type, "");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Message");
        btnSend = findViewById(R.id.btnSend);
        camera_btn = findViewById(R.id.camera_btn);
        img_attachment = findViewById(R.id.img_attachment);
        emoji_btn = findViewById(R.id.emoji_btn);
        editMessage = findViewById(R.id.editMessage);
        emojIcon = new EmojIconActions(this, rootView, editMessage, emojiButton);
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });

        recyclerview1 = findViewById(R.id.recyclerview1);
        if (login_type.equals("student")) {
            FirebaseDatabase.getInstance().getReference("Students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Student student = ds.getValue(Student.class);
                        if (student.getUid().equals(firebaseAuth.getCurrentUser().getUid())) {
                            sender_name = student.getName();
                            Log.e("sender_name", sender_name + " sender_name");

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            FirebaseDatabase.getInstance().getReference("Tutors").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Tutor tutor = ds.getValue(Tutor.class);
                        if (tutor.getUid().equals(firebaseAuth.getCurrentUser().getUid())) {
                            sender_name = tutor.getName();
                            Log.e("sender_name", sender_name + " sender_name");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
       /* editMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ChatWithTutorActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });*/

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_msg = editMessage.getText().toString();
                if (!et_msg.equals("")) {
                    addMessage(et_msg);
                } else {
                    Toast.makeText(ChatWithTutorActivity.this, "Please add a message", Toast.LENGTH_SHORT).show();

                }
            }
        });
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatWithTutorActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        img_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatWithTutorActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
   /*     emoji_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatWithTutorActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
*/
        firebaseDatabase.child(firebaseAuth.getCurrentUser().getUid()).child(reciever_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messsages = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Messsage messsage = ds.getValue(Messsage.class);
                    messsages.add(messsage);
                    Log.e("messsga", ds.getKey() + "");
                }
                messageAdapter = new MessageAdapter(ChatWithTutorActivity.this, messsages);
                recyclerview1.setLayoutManager(new LinearLayoutManager(ChatWithTutorActivity.this));
                recyclerview1.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addMessage(String et_msg) {
        DatabaseReference firebaseDatabase_chatlist = FirebaseDatabase.getInstance().getReference("Chatlist");
        StudentChatList studentChatList = new StudentChatList();
        studentChatList.setStudent_name(reciever_name);
        studentChatList.setReciever_id(reciever_id);
        firebaseDatabase_chatlist.child(firebaseAuth.getCurrentUser().getUid()).child(reciever_id).setValue(studentChatList);
        studentChatList = new StudentChatList();
        studentChatList.setStudent_name(sender_name);
        studentChatList.setReciever_id(firebaseAuth.getCurrentUser().getUid());
        firebaseDatabase_chatlist.child(reciever_id).child(firebaseAuth.getCurrentUser().getUid()).setValue(studentChatList);
        String msg = firebaseDatabase.push().getKey();
        Messsage message_send = new Messsage();
        message_send.setMessage(et_msg);
        message_send.setFile("");
        message_send.setType("1");
        firebaseDatabase.child(firebaseAuth.getCurrentUser().getUid()).child(reciever_id).child(msg).setValue(message_send).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editMessage.setText("");
            }
        });
        Messsage message_reciew = new Messsage();
        message_reciew.setMessage(et_msg);
        message_reciew.setFile("");
        message_reciew.setType("0");
        firebaseDatabase.child(reciever_id).child(firebaseAuth.getCurrentUser().getUid()).child(msg).setValue(message_reciew).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editMessage.setText("");
            }
        });
    }

    public void Back(View view) {
        finish();
    }
}

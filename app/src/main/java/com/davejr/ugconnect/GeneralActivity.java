package com.davejr.ugconnect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ScrollView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GeneralActivity extends AppCompatActivity {

    ImageButton btnSendMsg;
    EditText etMsg;
    TextView txtview;
    ScrollView scroll;

    String UserName, SelectedTopic, user_msg_key;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        btnSendMsg = findViewById(R.id.btnSendMsg);
        etMsg = findViewById(R.id.etMessage);
        txtview = findViewById(R.id.txtvw);
        txtview.setMovementMethod(new ScrollingMovementMethod());
        scroll = findViewById(R.id.skrol);
        UserName = auth.getCurrentUser().getDisplayName();
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        setTitle("Topic : " + SelectedTopic);
        dbr = FirebaseDatabase.getInstance().getReference().child(SelectedTopic);
        txtview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtview.getLayout().getLineTop(txtview.getLineCount()) - txtview.getHeight() > 0)
                    txtview.scrollTo(0,txtview.getLayout().getLineTop(txtview.getLineCount()) - txtview.getHeight());
                else txtview.scrollTo(0,0);
            }
        });
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);


                DatabaseReference dbr2 = dbr.child(user_msg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", etMsg.getText().toString());
                map2.put("user", UserName);
                dbr2.updateChildren(map2);

                etMsg.setText("");

            }
        });

//        dbr.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg, user, conversation;
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    msg = (String) ((DataSnapshot) i.next()).getValue();
                    user = (String) ((DataSnapshot) i.next()).getValue();
                    conversation = user + " : " + msg + "\n";
                    txtview.append(conversation);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String msg, user, conversation;
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    msg = (String) ((DataSnapshot) i.next()).getValue();
                    user = (String) ((DataSnapshot) i.next()).getValue();
                    conversation = user + " : " + msg + "\n";
                    txtview.append(conversation);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
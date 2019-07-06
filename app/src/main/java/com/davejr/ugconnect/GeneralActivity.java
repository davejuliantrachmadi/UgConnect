package com.davejr.ugconnect;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GeneralActivity extends AppCompatActivity {

    ImageButton btnSendMsg;
    ImageButton photoBtn;
//    ImageView imgView;

    EditText etMsg;


    ListView lvDiscussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    String UserName, SelectedTopic, user_msg_key;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference dbr;
    private Uri filePath;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        mFirebaseStorage = FirebaseStorage.getInstance();
        btnSendMsg = (ImageButton) findViewById(R.id.btnSendMsg);
        etMsg = (EditText) findViewById(R.id.etMessage);
        etMsg.setText("");
        lvDiscussion = (ListView) findViewById(R.id.IvConversation);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listConversation);
        lvDiscussion.setAdapter(arrayAdpt);
        photoBtn = (ImageButton) findViewById(R.id.photoPickerButton);
//        imgView = (ImageView) findViewById(R.id.imageView);



        UserName = auth.getCurrentUser().getDisplayName();
        SelectedTopic = getIntent().getExtras().get("selected_topic").toString();
        setTitle("Topic : " + SelectedTopic);

        dbr = FirebaseDatabase.getInstance().getReference().child(SelectedTopic);

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbr2 = dbr.child("user_msg_key");
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", etMsg.getText().toString());
                map2.put("user", UserName);
                dbr2.updateChildren(map2);

                etMsg.setText("");
            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                pickFromGallery();

            }
        });

        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateConversation(dataSnapshot);
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




    public void updateConversation(DataSnapshot dataSnapshot){
        String msg, user, conversation;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            msg = (String) ((DataSnapshot)i.next()).getValue();
            user = (String) ((DataSnapshot)i.next()).getValue();

            conversation = user + " : " + msg;
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        intent.putExtra("message","testing123");
        // Launching the Intent
        startActivityForResult(intent,71);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 71 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imgView.setImageBitmap(bitmap);
                StorageReference photoRef = mChatPhotosStorageReference.child(filePath.getLastPathSegment());
                photoRef.putFile(filePath).addOnSuccessListener
                        (this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                dbr.updateChildren("usr_");
                            }
                        });

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
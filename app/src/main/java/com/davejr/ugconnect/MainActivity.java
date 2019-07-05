package com.davejr.ugconnect;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;



public class MainActivity extends AppCompatActivity {

    ListView IvGeneral;
    ArrayList<String> listofGeneral = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    String UserName;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().getRoot();

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "MainActivity";

    private void setupFirebaseListener() {
        Log.d(TAG, "setupFirebaseListener : setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    Log.d(TAG,"onAuthStateChanged: sign_in " + user.getUid());

                }else {
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                    Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener !=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IvGeneral = (ListView) findViewById(R.id.IvGeneral);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listofGeneral);
        IvGeneral.setAdapter(arrayAdpt);

        setupFirebaseListener();

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.sign_out);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Link.class);
                startActivity(intent);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick : attempting to sign out the user.");
                FirebaseAuth.getInstance().signOut();
            }

        });






        getUserName();

        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());

                }

                arrayAdpt.clear();
                arrayAdpt.addAll(set);
                arrayAdpt.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        IvGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), GeneralActivity.class);
                i.putExtra("selected_topic", ((TextView)view).getText().toString());
                i.putExtra("user_name", UserName);
                startActivity(i);


            }
        });

    }

    private void getUserName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText userName = new EditText (this);

        builder.setView(userName);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserName = userName.getText().toString();

            }
        });



        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUserName();
            }
        });
        builder.show();
    }

}








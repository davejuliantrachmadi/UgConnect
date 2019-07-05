package com.davejr.ugconnect;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

public class Link extends AppCompatActivity {

    android.support.v7.widget.GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        android.support.v7.widget.GridLayout mainGrid = (android.support.v7.widget.GridLayout)findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);
        //setToggleEvent(mainGrid);
    }

    private void setToggleEvent(android.support.v7.widget.GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(Link.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(Link.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setSingleEvent(android.support.v7.widget.GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI == 0)
                    {
                        Intent intent = new Intent(Link.this, BAAK.class);
                        startActivity(intent);
                    }
                    else if (finalI == 1)
                    {
                        Intent intent = new Intent(Link.this, Studentsite.class);
                        startActivity(intent);
                    }
                    else if (finalI == 2)
                    {
                        Intent intent = new Intent(Link.this, Vclass.class);
                        startActivity(intent);
                    }
                    else if (finalI == 3)
                    {
                        Intent intent = new Intent(Link.this, Staffsite.class);
                        startActivity(intent);
                    }
                    else if (finalI == 4)
                    {
                        Intent intent = new Intent(Link.this, Library.class);
                        startActivity(intent);
                    }
                    else if (finalI == 5)
                    {
                        Intent intent = new Intent(Link.this, elearning.class);
                        startActivity(intent);
                    }
                    else if (finalI == 6)
                    {
                        Intent intent = new Intent(Link.this, BAPSI.class);
                        startActivity(intent);
                    }
                    else if (finalI == 7)
                    {
                        Intent intent = new Intent(Link.this, SAP.class);
                        startActivity(intent);
                    }

                }
            });
        }
    }
}

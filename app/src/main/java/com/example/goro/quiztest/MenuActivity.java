package com.example.goro.quiztest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Goro on 03.10.2017.
 */

public class MenuActivity extends Activity {
    Context context = this;
    DataBaseHelper myDbHelper;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmenu);
        TextView text1 = (TextView) findViewById(R.id.textView8);
        TextView text2 = (TextView) findViewById(R.id.textView6);
        TextView text3 = (TextView) findViewById(R.id.textView7);


        Typeface face = Typeface.createFromAsset(getAssets(), "font/Appetite.ttf");

        text1.setTypeface(face);
        text2.setTypeface(face);
        text3.setTypeface(face);
        myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        SQLiteDatabase mydb1 = myDbHelper.getReadableDatabase();
        Cursor info = mydb1.rawQuery("SELECT * FROM info WHERE _id=" + 1 + "", null);
        info.moveToFirst();
        i = info.getInt(info.getColumnIndex("countlearn"));
        TextView text;


        text = (TextView) findViewById(R.id.count);
        text.setTypeface(face);
        text.setText("" + (i - 1) + "");


        RelativeLayout b2 = (RelativeLayout) findViewById(R.id.button1);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        LearnActivity.class));

            }
        });


        RelativeLayout b3 = (RelativeLayout) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        TestActivity.class));

            }
        });


        RelativeLayout b1 = (RelativeLayout) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        NoteActivity.class));
            }
        });



       /* TextView tv = (TextView) findViewById(R.id.imengg);
        Typeface face = Typeface.createFromAsset(getAssets(), "font/snap itc.ttf");
        tv.setTypeface(face);
        Button b2 = (Button) findViewById(R.id.but2);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        TestActivity.class));

            }
        });


        Button b3 = (Button) findViewById(R.id.but3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        NoteActivity.class));

            }
        });


        Button b1 = (Button) findViewById(R.id.but1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MenuActivity.this,
                        LearnActivity.class));
            }
        });

        */
    }


    @Override
    protected void onResume() {
        super.onResume();

        Typeface face = Typeface.createFromAsset(getAssets(), "font/Appetite.ttf");

        myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


        SQLiteDatabase mydb1 = myDbHelper.getReadableDatabase();
        Cursor info = mydb1.rawQuery("SELECT * FROM info WHERE _id=" + 1 + "", null);
        info.moveToFirst();
        i = info.getInt(info.getColumnIndex("counttest"));
        TextView text1;


        text1 = (TextView) findViewById(R.id.count);
        text1.setTypeface(face);
        text1.setText("" + (i - 1) + "");

    }



}
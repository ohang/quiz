package com.example.goro.quiztest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Goro on 04.10.2017.
 */

public class LearnActivity extends Activity {
    Context context=this;
    DataBaseHelper myDbHelper;
    int i;










    @Override
    protected void onPause() {
        super.onPause();
        int f= 1;
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("countlearn",i); //These Fields should be your String values of actual column names

        db.update("info", cv, "_id="+f, null);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        TextView text;
        final Button update1;
        Button back1;
        update1 = (Button) findViewById(R.id.updatequestion);


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
        i=info.getInt(info.getColumnIndex("countlearn"));






        text = (TextView) findViewById(R.id.textView2);
        text.setText("" + i + "/500");


        //  final Cursor res = myDbHelper.getAllData();

        final TextView t1 = (TextView) findViewById(R.id.eng);
        final TextView t2 = (TextView) findViewById(R.id.rus);
        ImageButton bt = (ImageButton) findViewById(R.id.button2);
        ImageButton bt1 = (ImageButton) findViewById(R.id.button1);
        final RelativeLayout rl1 = findViewById(R.id.learn1);

        back1 = (Button) findViewById(R.id.backstart1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });

      /*  update1.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override

            public void onClick(View v) {

                res.moveToFirst();
                i=1;

                rl1.setBackground(getResources().getDrawable(fff[i]));
                String rus = res.getString(res.getColumnIndex("russian"));
                String str = res.getString(res.getColumnIndex("english"));
                t2.setText(rus);
                t1.setText(str);
                TextView text = (TextView) findViewById(R.id.textView2);
                text.setText("" + i + "/500");


            }


        });

*/
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
        c.moveToFirst();
        if (c != null && c.getCount() != 0) {
            //   b2.setText(c.getString(c.getColumnIndex("russian")));

            t1.setText(c.getString(c.getColumnIndex("english")));
            t2.setText(c.getString(c.getColumnIndex("russian")));
            int id = getResources().getIdentifier((c.getString(c.getColumnIndex("img"))), "drawable", this.getPackageName());
            rl1.setBackground(getResources().getDrawable(id));

        }


        bt.setOnClickListener(new View.OnClickListener() {
            // int i =0;
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
             if(i<500) {

                 nextword();
             }


            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            // int i =0;
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                if(i>1) {
                    previous();
                }


            }
        });
        update1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override

            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                } else {
                    alertDialogBuilder = new AlertDialog.Builder(context);
                }

                // set title
                alertDialogBuilder.setTitle("Вы хотите начать сначала?");

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Да",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                update();
                            }
                        })
                        .setNegativeButton("Нет",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



            }


        });


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void nextword() {
        i++;
        TextView text = (TextView) findViewById(R.id.textView2);
        text.setText("" + i + "/500");

        final TextView t1 = (TextView) findViewById(R.id.eng);
        final TextView t2 = (TextView) findViewById(R.id.rus);
        ImageButton bt = (ImageButton) findViewById(R.id.button2);
        ImageButton bt1 = (ImageButton) findViewById(R.id.button1);
        final RelativeLayout rl1 = findViewById(R.id.learn1);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
        c.moveToFirst();
        if (c != null && c.getCount() != 0) {
            //   b2.setText(c.getString(c.getColumnIndex("russian")));

            t1.setText(c.getString(c.getColumnIndex("english")));
            t2.setText(c.getString(c.getColumnIndex("russian")));
            int id = getResources().getIdentifier((c.getString(c.getColumnIndex("img"))), "drawable", this.getPackageName());
            rl1.setBackground(getResources().getDrawable(id));

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void previous() {
        i--;
        TextView text = (TextView) findViewById(R.id.textView2);
        text.setText("" + i + "/500");
        final TextView t1 = (TextView) findViewById(R.id.eng);
        final TextView t2 = (TextView) findViewById(R.id.rus);
        ImageButton bt = (ImageButton) findViewById(R.id.button2);
        ImageButton bt1 = (ImageButton) findViewById(R.id.button1);
        final RelativeLayout rl1 = findViewById(R.id.learn1);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
        c.moveToFirst();
        if (c != null && c.getCount() != 0) {
            //   b2.setText(c.getString(c.getColumnIndex("russian")));

            t1.setText(c.getString(c.getColumnIndex("english")));
            t2.setText(c.getString(c.getColumnIndex("russian")));
            int id = getResources().getIdentifier((c.getString(c.getColumnIndex("img"))), "drawable", this.getPackageName());
            rl1.setBackground(getResources().getDrawable(id));

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void update() {
        i=1;
        TextView text = (TextView) findViewById(R.id.textView2);
        text.setText("" + i + "/500");
        final TextView t1 = (TextView) findViewById(R.id.eng);
        final TextView t2 = (TextView) findViewById(R.id.rus);
        ImageButton bt = (ImageButton) findViewById(R.id.button2);
        ImageButton bt1 = (ImageButton) findViewById(R.id.button1);
        final RelativeLayout rl1 = findViewById(R.id.learn1);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
        c.moveToFirst();
        if (c != null && c.getCount() != 0) {
            //   b2.setText(c.getString(c.getColumnIndex("russian")));

            t1.setText(c.getString(c.getColumnIndex("english")));
            t2.setText(c.getString(c.getColumnIndex("russian")));
            int id = getResources().getIdentifier((c.getString(c.getColumnIndex("img"))), "drawable", this.getPackageName());
            rl1.setBackground(getResources().getDrawable(id));

        }


    }


}

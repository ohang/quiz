package com.example.goro.quiztest;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

public class TestActivity extends AppCompatActivity {
    Context context=this;
    DataBaseHelper myDbHelper;
     int i;




    @Override
    protected void onPause() {
        super.onPause();
        int f= 1;
        i--;
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("counttest",i); //These Fields should be your String values of actual column names

        db.update("info", cv, "_id="+f, null);



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.start));

        final Button b1;
        final Button b2;
        final Button b3;
        final Button b4;
        final Button back;
        final Button update;
        final TextView tx1;
        final TextView tx2;
        RelativeLayout img = (RelativeLayout) findViewById(R.id.relativeLayout);

        myDbHelper = new DataBaseHelper(this);
        SQLiteDatabase mydb1 = myDbHelper.getReadableDatabase();
        Cursor info = mydb1.rawQuery("SELECT * FROM info WHERE _id=" + 1 + "", null);
        info.moveToFirst();
        i=info.getInt(info.getColumnIndex("counttest"));

        TextView text = (TextView)findViewById(R.id.textView2);
        text.setText(""+i+"/500");


        back = (Button) findViewById(R.id.backstart);





        update = (Button) findViewById(R.id.updatequestion);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);

        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);


        tx1 = (TextView)findViewById(R.id.textView);
        tx2 = (TextView)findViewById(R.id.textView1);

        List<String> answers = new ArrayList<String>();






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









        SQLiteDatabase db = myDbHelper.getWritableDatabase();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    //    .setMessage("Вы хотите начать сначала?")
                        .setCancelable(false)
                        .setPositiveButton("Да",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                               updatequestion();
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




        Random random = new Random();
        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
     //   c.moveToFirst();

        quiz();




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b1.getText().equals(tx1.getText())) {
                    righttoast();
                    new android.os.Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                            quiz();
                        }
                    }, 1200);
                    //quiz();


                } else {
                    wrongtoast();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b2.getText().equals(tx1.getText())) {
                    righttoast();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                            quiz();
                        }
                    }, 1200);
                    // quiz();


                }else {
                    wrongtoast();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b3.getText().equals(tx1.getText())){
                    righttoast();
                    new android.os.Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                            quiz();
                        }
                    }, 1200);

                   // quiz();


                }else {
                    wrongtoast();
                }
            }
        });



        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b4.getText().equals(tx1.getText())){

                    righttoast();
                    new android.os.Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                            quiz();
                        }
                    }, 1200);



                }else {
                    wrongtoast();
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void quiz(){

        TextView text = (TextView)findViewById(R.id.textView2);
        text.setText(""+i+"/500");
        List<String> answers = new ArrayList<String>();
        final Button b1;
        final Button b2;
        final Button b3;
        final Button b4;
        final TextView tx1;
        final TextView tx2;



        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        RelativeLayout img = (RelativeLayout) findViewById(R.id.relativeLayout);


        tx1 = (TextView)findViewById(R.id.textView);
        tx2 = (TextView)findViewById(R.id.textView1);



        Random random = new Random();

        SQLiteDatabase db = myDbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM top500 WHERE _id=" + i + "", null);
       c.moveToFirst();
        if (c != null && c.getCount() != 0) {
            //   b2.setText(c.getString(c.getColumnIndex("russian")));
            answers.add(c.getString(c.getColumnIndex("russian")));
            tx1.setText(c.getString(c.getColumnIndex("russian")));
            tx2.setText(c.getString(c.getColumnIndex("english")));
            int id =  getResources().getIdentifier((c.getString(c.getColumnIndex("img"))), "drawable", this.getPackageName());
            img.setBackground(getResources().getDrawable(id));

        }


        int rand1 = random.nextInt(499)+1;


        Cursor c1 = db.rawQuery("SELECT * FROM top500 WHERE _id=" +rand1+ "", null);
      c1.moveToFirst();
        if (c1 != null && c1.getCount() != 0) {
            answers.add(c1.getString(c1.getColumnIndex("russian")));
        }

        int rand2 = random.nextInt(499)+1;
        Cursor c2 = db.rawQuery("SELECT * FROM top500 WHERE _id=" +rand2+"", null);
      c2.moveToFirst();
        if (c2 != null && c2.getCount() != 0) {
            answers.add(c2.getString(c1.getColumnIndex("russian")));
        }


        int rand3 = random.nextInt(499)+1;
        Cursor c3 = db.rawQuery("SELECT * FROM top500 WHERE _id=" +rand3+"", null);
        c3.moveToFirst();
        if (c3 != null && c3.getCount() != 0) {
            answers.add(c3.getString(c1.getColumnIndex("russian")));
        }


        Random tii = new Random();
        int kk = tii.nextInt(4);
        b1.setText(answers.get(kk));
        answers.remove(kk);

        Random tiv = new Random();
        int k = tiv.nextInt(3);
        b2.setText(answers.get(k));
        answers.remove(k);

        Random ti = new Random();
        int p = ti.nextInt(2);
        b3.setText(answers.get(p));
        answers.remove(p);

        b4.setText(answers.get(0));
        i++;



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public  void updatequestion(){
        i=1;
        quiz();

    }
    public  void righttoast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.right,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
       // toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                toast.cancel();
            }
        }, 800);

    }
    public  void wrongtoast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.wrong,
                (ViewGroup) findViewById(R.id.toast_layout_root1));

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                toast.cancel();
            }
        }, 600);

    }


    }




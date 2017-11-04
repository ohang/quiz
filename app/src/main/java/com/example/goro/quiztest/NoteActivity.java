package com.example.goro.quiztest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Goro on 04.10.2017.
 */

public class NoteActivity extends Activity {
    Context context = this;
    final String LOG_TAG = "myLogs";
     final String TABLE_NAME ="mywords";
    DataBaseHelper mydb;
    Button but1;
    EditText edt1;
    EditText edt2;
    final ArrayList<Note> not = new ArrayList<Note>();




    //


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BoxAdapter boxAdapter = new BoxAdapter(this, not);
        setContentView(R.layout.activity_note);
        TextView t2 = (TextView) findViewById(R.id.textView3);
        Button back1;



        back1 = (Button) findViewById(R.id.backstart1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });


        mydb = new DataBaseHelper(this);
        try {

            mydb.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            mydb.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        but1 = (Button) findViewById(R.id.but2);
        SQLiteDatabase DB = mydb.getWritableDatabase();



        Cursor res = DB.rawQuery("Select * from " +"mywords",null );


        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {

                Note note = new Note(res.getString(1), res.getString(2));
                note.setId(res.getInt(0));
                not.add(note);
                //   Toast.makeText(this,note.getEnglish(),Toast.LENGTH_SHORT).show();

            }
            //Toast.makeText(this,"hjhkh",Toast.LENGTH_SHORT).show();
        }
        final ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);


        // final  MyAdapter myadapter = new MyAdapter(not,getApplicationContext());
        //lvMain.setAdapter(myadapter);



        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder alertDialogBuilder ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                } else {
                    alertDialogBuilder = new AlertDialog.Builder(context);
                }

                // set title
                alertDialogBuilder.setTitle("Удалить выбранный элемент?");

                // set dialog message
                alertDialogBuilder
                        //    .setMessage("Вы хотите начать сначала?")
                        .setCancelable(false)
                        .setPositiveButton("Да",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity

                                Toast.makeText(getApplicationContext(),
                                        "DELETED", Toast.LENGTH_LONG)
                                        .show();

                                Note nt = not.get(position);
                                int idn = nt.getId();
                                SQLiteDatabase db = mydb.getWritableDatabase();
                                String where = "_id = " + (idn);
                                db.delete("mywords", where, null);

                                Update();
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



                return false;
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNote();


            }
        });




    }

    public void Update() {
        not.clear();

        mydb = new DataBaseHelper(this);
        but1 = (Button) findViewById(R.id.but2);
        SQLiteDatabase DB = mydb.getWritableDatabase();

        Cursor res = DB.rawQuery("Select * from " +"mywords",null );
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {


                Note note = new Note(res.getString(1), res.getString(2));
                note.setId(res.getInt(0));
                not.add(note);
                //   Toast.makeText(this,note.getEnglish(),Toast.LENGTH_SHORT).show();

            }
            //Toast.makeText(this,"hjhkh",Toast.LENGTH_SHORT).show();
        }
        //
        //
        final BoxAdapter boxAdapter = new BoxAdapter(this, not);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);

        // настраиваем список

        //    lvMain.setAdapter(boxAdapter);
        // final ListView lvMain = (ListView) findViewById(R.id.lvMain);
        //final  MyAdapter myadapter = new MyAdapter(not,getApplicationContext());
        //   lvMain.setAdapter(myadapter);

    }


    public void addNote() {
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);

        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String russian = edt1.getText().toString();
        String english = edt2.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = mydb.getWritableDatabase();


        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("russian", russian);
        cv.put("english", english);


        // вставляем запись и получаем ее ID
        long rowID = db.insert("mywords", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);


        Update();


    }





}

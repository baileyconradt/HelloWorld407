package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {


    int noteid = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = findViewById(R.id.editTextTextMultiLine);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if(noteid != -1){
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }

    }

    public void saveMethod(View view){
        EditText editText = findViewById(R.id.editTextTextMultiLine);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid == -1){
            //add note
            title = "NOTE_"+ (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(date,username,title,content);
        }
        else{
            title = "NOTE_"+ (noteid + 1);
            dbHelper.updateNotes(date,username,title,content);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", username);
        startActivity(intent);



    }
}
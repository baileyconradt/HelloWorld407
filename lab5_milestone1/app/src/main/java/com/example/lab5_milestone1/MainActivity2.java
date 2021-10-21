package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView textView2;


    static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        textView2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        textView2.setText("Welcome " + str + "!");

        //read notes for the username
        notes  = dbHelper.readNotes(str);
        Log.i("HELLO", notes.toString());
        Log.i("name", str);

        ArrayList<String> displayNotes = new ArrayList<>();
        for(Note note : notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu, menu);
       return true;

    }

    public void goToActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreferences.edit().remove("username").apply();
                goToActivity1();
                return true;
            case R.id.note:
                Intent intent = new Intent(this, MainActivity3.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
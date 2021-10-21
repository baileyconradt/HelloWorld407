package com.example.lab5_milestone1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper {

    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase){
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes " +
                "(id INTEGER PRIMARY KEY, username TEXT, date TEXT, title TEXT, content TEXT, src TEXT)");

    }

    public ArrayList<Note> readNotes(String username){
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from notes where username like '%s'", username), null);

        int dateIndex = c.getColumnIndex("date");
        int titleIndex = c.getColumnIndex("title");
        int contentIndex = c.getColumnIndex("content");

        c.moveToFirst();

        ArrayList<Note> notesList = new ArrayList<>();
//        Note note2 = new Note(new Date().toString(), "alex", "test", "test");
//        notesList.add(note2);

        while(!c.isAfterLast()){

            String title = c.getString(titleIndex);
            String date = c.getString(dateIndex);
            String content = c.getString(contentIndex);

            Note note = new Note(date, username, title, content);
            notesList.add(note);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return notesList;

    }

    public void saveNotes(String date, String username, String title, String content) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO notes (username, date, title, content) VALUES ('%s', '%s', '%s', '%s')", username, date, title, content));
    }

    public void updateNotes(String date, String username, String title, String content) {
        createTable();
        sqLiteDatabase.execSQL(String.format("UPDATE notes SET content = '%s', date = '%s' where title = '%s' and username = '%s'", content, date, title, username));
    }
}

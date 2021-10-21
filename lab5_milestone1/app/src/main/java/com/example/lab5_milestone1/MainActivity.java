package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){
        EditText myTextField = (EditText) findViewById(R.id.myTextField);
        String str = myTextField.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", str).apply();

        //Toast.makeText(MainActivity.this, myTextField.getText().toString(), Toast.LENGTH_LONG).show();
        goToActivity2(str);
    }

    public void goToActivity2(String s){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", s);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5_milestone1", Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey,"").equals("")){
            //logged in previously
            goToActivity2(sharedPreferences.getString(usernameKey,""));
        }else{
            //not logged in
            setContentView(R.layout.activity_main);
        }



    }
}
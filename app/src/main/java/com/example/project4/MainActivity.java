package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(e->{
            Intent startGame = new Intent(this, GameActivity.class);
            startActivity(startGame);
        });
    }
}
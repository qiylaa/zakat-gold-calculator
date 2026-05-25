package com.example.zakatgoldcalculator;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    TextView aboutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutText = findViewById(R.id.aboutText);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
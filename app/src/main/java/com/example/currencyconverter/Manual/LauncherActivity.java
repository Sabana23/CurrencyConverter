package com.example.currencyconverter.Manual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyconverter.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        findViewById(R.id.convert_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent postIntent = new Intent(LauncherActivity.this, ManualActivity.class);
            startActivity(postIntent);
            }
        });

    }
}

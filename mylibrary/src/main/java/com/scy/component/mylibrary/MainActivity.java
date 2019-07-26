package com.scy.component.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private android.widget.TextView tvtest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.tvtest = (TextView) findViewById(R.id.test);
//        tvtest.setOnClickListener(v -> {
//
//        });
    }
}

package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final TextView mTextView = (TextView) findViewById(R.id.textView_result);
        String result= getIntent().getStringExtra("RESULT_OPERATION");
        mTextView.setText(result);
    }
}

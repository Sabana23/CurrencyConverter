package com.example.currencyconverter;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Main1 extends AppCompatActivity {
    float a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    void convertUsdToInr()
    {  EditText usd= (EditText) findViewById(R.id.usd);
        usd.setInputType(InputType.TYPE_CLASS_NUMBER);

        EditText  inr=(EditText) findViewById(R.id.inr);
        inr.setInputType(InputType.TYPE_CLASS_NUMBER);

        double a=Integer.parseInt(usd.getText().toString());
        double result=a*74.03;
        inr.setText(String.valueOf(result));

    }

    void convertInrToUsd()
    {  EditText usd= (EditText) findViewById(R.id.usd);
        usd.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText  inr=(EditText) findViewById(R.id.inr);
        inr.setInputType(InputType.TYPE_CLASS_NUMBER);

        double a=Integer.parseInt(inr.getText().toString());
        double result=a/74.03;
        usd.setText(String.valueOf(result));
    }

    public void click(View view)
    {
        convertUsdToInr();
        Button b2=(Button) findViewById(R.id.submit2);
        b2.setEnabled(false);
    }

    public void reset(View view)
    {
        Button b1=(Button) findViewById(R.id.submit);
        b1.setEnabled(true);
        Button b2=(Button) findViewById(R.id.submit2);
        b2.setEnabled(true);
        EditText usd= (EditText) findViewById(R.id.usd);
        EditText inr=(EditText) findViewById(R.id.inr);
        usd.setText("");
        inr.setText("");

    }

    public void click2(View view)
    {
        Button b1=(Button) findViewById(R.id.submit);
        b1.setEnabled(false);
        convertInrToUsd();
    }



}
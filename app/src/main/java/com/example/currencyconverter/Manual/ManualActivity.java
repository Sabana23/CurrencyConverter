package com.example.currencyconverter.Manual;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.currencyconverter.R;
import com.example.currencyconverter.Retrofit.RetrofitBuilder;
import com.example.currencyconverter.Retrofit.RetrofitInterface;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManualActivity extends AppCompatActivity {
    Button button;
    EditText currencyToBeConverted,currencyConverted;
    Spinner convertToDropdown,convertFromDropdown;
    private Object List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        currencyToBeConverted = findViewById(R.id.currency_to_be_converted);
        currencyConverted = findViewById(R.id.currency_converted);
        convertFromDropdown = findViewById(R.id.convert_from);
        convertToDropdown = findViewById(R.id.convert_to);
        button = findViewById(R.id.button);

        //Adding Functionality
        String[] dropdownList = {"USD","AED","AFN","ALL","AMD","ANG","AOA",
                "ARS","AUD","AWG", "AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD",
                "BND","BOB","BRL","BSD","BTN","BWP","BYN","BZD","CAD",
                "CDF","CHF","CLP","CNY","COP","CRC","CUC","CUP","CVE",
                "CZK","DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR",
                "FJD","FKP","FOK","GBP","GEL","GGP","GHS","GIP","GMD","GNF",
                "GTQ","GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS",
                "IMP","INR","IQD","IRR","ISK","JMD","JOD","JPY","KES",
                "KGS","KHR","KID","KMF","KRW","KWD","KYD","KZT","LAK",
                "LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA","MKD",
                "MMK","MNT","MOP","MRU","MUR","MVR","MWK","MXN","MYR",
                "MZN","NAD","NGN","NIO","NOK","NPR","NZD","OMR","PAB",
                "PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD",
                "RUB","RWF","SAR","SBD","SCR","SDG","SEK","SGD","SHP",
                "SLL","SOS","SRD","SSP","STN","SYP","SZL","THB","TJS",
                "TMT","TND","TOP","TRY","TTD","TVD","TWD","TZS","UAH",
                "UGX","UYU","UZS","VES","VND","VUV","WST","XAF",
                "XCD","XDR","XOF","XPF","YER","ZAR","ZMW"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dropdownList);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                      JsonObject res = response.body();
                      JsonObject rates = res.getAsJsonObject("rates");
                      double currency = Double.valueOf(currencyToBeConverted.getText().toString());
                      double multiplier = Double.valueOf(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                      double result = currency * multiplier;
                      currencyConverted.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });
    }
}

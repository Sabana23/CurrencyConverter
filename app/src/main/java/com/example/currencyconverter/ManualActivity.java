package com.example.currencyconverter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        currencyToBeConverted = findViewById(R.id.currency_to_be_converted);
        currencyConverted = findViewById(R.id.currency_converted);
        convertFromDropdown = findViewById(R.id.convert_from);
        convertToDropdown = findViewById(R.id.convert_to);
        button = findViewById(R.id.button);

        //Adding Functionality
        List<String> states = Arrays.asList("USD","INR","EUR","NZD","AFN","ARS","AOA");
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),R.layout.my_selected_item,states);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
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

package com.example.currencyconverter.Country;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencyconverter.R;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class CurrencyActivity extends AppCompatActivity {
    TextView convertFromDropdownTextView,convertToDropdownTextView,conversionRateText;
    EditText  amountToConvert;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    Button convertButton;
    String convertFromValue,convertToValue,conversionValue;
    String[] country= {"USD","AED","AFN","ALL","AMD","ANG","AOA",
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
            "XCD","XDR","XOF","XPF","YER","ZAR","ZMW"}; //1000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        convertFromDropdownTextView = findViewById(R.id.convert_from_dropdown_menu);
        convertToDropdownTextView = findViewById(R.id.convert_to_dropdown_menu);
        convertButton = findViewById(R.id.conversionButton);
        conversionRateText = findViewById(R.id.conversionRateText);
        amountToConvert = findViewById(R.id.amountToConvertValueEditText);

        arrayList = new ArrayList<>();
        for(String i:country){
            arrayList.add(i);
        }
        convertFromDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDialog = new Dialog(CurrencyActivity.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();
                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyActivity.this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertFromDropdownTextView.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(position);

                    }
                });
            }
        });

        convertToDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDialog = new Dialog(CurrencyActivity.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650,800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text);
                ListView listView = toDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyActivity.this,android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convertToDropdownTextView.setText(adapter.getItem(position));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                   Double amountToConvert = Double.valueOf(CurrencyActivity.this.amountToConvert.getText().toString());
                   getConversionRate(convertFromValue,convertToValue,amountToConvert);

                }
                catch(Exception e){

                }
            }
        });
    }

    public String getConversionRate(String convertFrom, String convertTo,Double amountToConvert) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://v6.exchangerate-api.com/v6/"+convertFrom+"_"+convertTo+"5a916dd688a2e019cbd53ecb";
        StringRequest  stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double conversionRateValue = round(((Double) jsonObject.get(convertFrom + "_" + convertTo)), 2);
                    conversionValue = ""+round((conversionRateValue*amountToConvert),2);
                    conversionRateText.setText(conversionValue);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        return null;
    }

    public  static double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);bd.setScale(places,RoundingMode.HALF_UP);
        return  bd.doubleValue();

    }
}
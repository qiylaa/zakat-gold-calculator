package com.example.zakatgoldcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText weightInput, priceInput;
    RadioGroup typeGroup;
    RadioButton keepRadio, wearRadio;
    Button calculateBtn;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weightInput = findViewById(R.id.weightInput);
        priceInput = findViewById(R.id.priceInput);
        typeGroup = findViewById(R.id.typeGroup);
        keepRadio = findViewById(R.id.keepRadio);
        wearRadio = findViewById(R.id.wearRadio);
        calculateBtn = findViewById(R.id.calculateBtn);
        resultText = findViewById(R.id.resultText);

        calculateBtn.setOnClickListener(v -> calculateZakat());
    }

    private void calculateZakat() {
        String weightStr = weightInput.getText().toString();
        String priceStr = priceInput.getText().toString();

        if (weightStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please enter all required values", Toast.LENGTH_SHORT).show();
            return;
        }

        if (typeGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gold type", Toast.LENGTH_SHORT).show();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double price = Double.parseDouble(priceStr);

        double uruf;

        if (keepRadio.isChecked()) {
            uruf = 85;
        } else {
            uruf = 200;
        }

        double totalGoldValue = weight * price;
        double payableWeight = weight - uruf;

        double zakatPayable;

        if (payableWeight <= 0) {
            zakatPayable = 0;
        } else {
            zakatPayable = payableWeight * price;
        }

        double totalZakat = zakatPayable * 0.025;

        String result = "Total Gold Value: RM " + String.format("%.2f", totalGoldValue) +
                "\nGold Weight Minus Uruf: " + String.format("%.2f", payableWeight) + " g" +
                "\nZakat Payable Value: RM " + String.format("%.2f", zakatPayable) +
                "\nTotal Zakat: RM " + String.format("%.2f", totalZakat);

        resultText.setText(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Gold Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Check out my Zakat Gold Calculator app: https://github.com/qiylaa/zakat-gold-calculator");
            startActivity(Intent.createChooser(shareIntent, "Share application using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
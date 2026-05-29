package com.example.zakatgoldcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout weightInputLayout, priceInputLayout;
    TextInputEditText weightInput, priceInput;
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

        weightInputLayout = findViewById(R.id.weightInputLayout);
        priceInputLayout = findViewById(R.id.priceInputLayout);
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
        // Reset errors
        weightInputLayout.setError(null);
        priceInputLayout.setError(null);

        String weightStr = weightInput.getText() != null ? weightInput.getText().toString() : "";
        String priceStr = priceInput.getText() != null ? priceInput.getText().toString() : "";

        boolean hasError = false;

        if (weightStr.isEmpty()) {
            weightInputLayout.setError(getString(R.string.error_weight_empty));
            hasError = true;
        }

        if (priceStr.isEmpty()) {
            priceInputLayout.setError(getString(R.string.error_price_empty));
            hasError = true;
        }

        if (typeGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getString(R.string.error_type_not_selected), Toast.LENGTH_SHORT).show();
            hasError = true;
        }

        if (hasError) return;

        double weight = Double.parseDouble(weightStr);
        double price = Double.parseDouble(priceStr);

        double uruf = keepRadio.isChecked() ? 85 : 200;

        double totalGoldValue = weight * price;
        double payableWeight = weight - uruf;
        
        double zakatPayableValue;
        if (payableWeight <= 0) {
            zakatPayableValue = 0;
        } else {
            zakatPayableValue = payableWeight * price;
        }

        double totalZakat = zakatPayableValue * 0.025;

        String result = "Total Gold Value: RM " + String.format("%.2f", totalGoldValue) +
                "\nUruf Threshold: " + String.format("%.0f", uruf) + " g" +
                "\nGold Weight Minus Uruf: " + String.format("%.2f", payableWeight) + " g" +
                "\n------------------------------------------" +
                "\nZakat Payable: RM " + String.format("%.2f", zakatPayableValue) +
                "\nTOTAL ZAKAT: RM " + String.format("%.2f", totalZakat);

        resultText.setText(result);
    }

    private void resetFields() {
        weightInput.setText("");
        priceInput.setText("");
        typeGroup.clearCheck();
        resultText.setText(getString(R.string.result_placeholder));
        weightInputLayout.setError(null);
        priceInputLayout.setError(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetFields();
            return true;
        }

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
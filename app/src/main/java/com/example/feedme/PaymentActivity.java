package com.example.feedme;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private TextView mtv_payment;
    private EditText met_money;
    private Button mbtn_payment_confirm;
    private Button mbtn_payment_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_payment);
        mtv_payment = findViewById(R.id.payment);
        //met_money = findViewById(R.id.money);
        mbtn_payment_confirm = findViewById(R.id.btn_payment_confirm);
        mbtn_payment_cancel = findViewById(R.id.btn_payment_back);
        mbtn_payment_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "pay successfully", Toast.LENGTH_SHORT).show();
            }
        });
        mbtn_payment_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }
}

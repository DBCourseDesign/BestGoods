package com.example.feedme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Button mBtn_Register;
    private Button mBtn_Cancel;
    private EditText et_username, et_email;
    private EditText et_password, et_confirm_pwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;

    private String identityChoice = "No choice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);


        mBtn_Register = findViewById(R.id.btn_register);
        mBtn_Cancel = findViewById(R.id.btn_cancel);
        mBtn_Register.setOnClickListener(new ButtonListen());
        mBtn_Cancel.setOnClickListener(new ButtonListen());
        radioGroup = findViewById(R.id.radioGroup);
        chooseIdentity();
        //et_username = findViewById(R.id.et_username);
        //et_password = findViewById(R.id.et_password);
        //et_confirm_pwd = findViewById(R.id.et_confirm_pwd);
        //et_email = findViewById(R.id.et_email);

        progressBar = new ProgressBar(this);

    }

    private void registerUser(){
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String confirm_pwd = et_confirm_pwd.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String identity = identityChoice;

        // Checking email validity
        String regex_email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern_email = Pattern.compile(regex_email);
        Matcher matcher_email = pattern_email.matcher(email);

        // Checking pwd validity
        String regex_pwd = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,16}$";
        Pattern pattern_pwd = Pattern.compile(regex_pwd);
        Matcher matcher_pwd = pattern_pwd.matcher(password);

        if(username.length() == 0)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Username shouldn't be blank",
                    Toast.LENGTH_LONG ).show();
        }
        else if(password.length() < 8 || password.length() > 16)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Password should be 8 ~ 16 bits",
                    Toast.LENGTH_LONG ).show();
        }
        else if (!matcher_pwd.matches()){
            Toast.makeText(
                    getApplicationContext(),
                    "Password should contain uppercase and lowercase letters, number, and special character",
                    Toast.LENGTH_LONG ).show();
        }
        else if (!confirm_pwd.equals(password)) {
            Toast.makeText(
                    getApplicationContext(),
                    "Passwords entered twice are inconsistent",
                    Toast.LENGTH_LONG ).show();
        }
        else if(!matcher_email.matches()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Invalid email pattern",
                    Toast.LENGTH_LONG ).show();
        }
        else {
            SharedPreferences mySharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("my_email", email);
            editor.putString("my_username", username);
            editor.commit();

            // progressBar
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // progressBar
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (!jsonObject.getBoolean("error")) {
                                    startActivity(new Intent(RegisterActivity.this, RegisterSuccessActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // progressBar
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("email", email);
                    params.put("identity", identity);
                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private class ButtonListen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register:
                    registerUser();
                    break;

                case R.id.btn_cancel:
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    break;
            }
        }
    }

    private void chooseIdentity(){
        radioGroup .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.radioButtonS:
                        identityChoice = "student".trim();
                        break;
                    case R.id.radioButtonR:
                        identityChoice = "restaurant".trim();
                        break;
                    case R.id.radioButtonD:
                        identityChoice = "delivery".trim();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }

        return super.onKeyDown(keyCode, event);
    }


}

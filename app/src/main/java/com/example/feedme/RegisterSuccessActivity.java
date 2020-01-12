package com.example.feedme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterSuccessActivity extends AppCompatActivity {

    private Integer id = 0;
    private TextView tv_username, tv_id;
    private Button btn_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= getSharedPreferences("user", Activity.MODE_PRIVATE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register_success);
        getUserId();
        String username = sharedPreferences.getString("my_username", "");

        tv_username = findViewById(R.id.success_tv_username);
        tv_username.setText(username);
        tv_id = findViewById(R.id.success_tv_id);
        tv_id.setText(id.toString().trim());
        btn_success = findViewById(R.id.success_btn);
        btn_success.setOnClickListener(new ButtonListen());
    }

    private void getUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        final String email = sharedPreferences.getString("my_email", "");
        // progressBar

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETUSERID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressBar
                        SharedPreferences mySharedPreferences=  getSharedPreferences("user", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                editor.putInt("id", obj.getInt("id"));
                                editor.commit();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG ).show();
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
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;

            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        id = sharedPreferences.getInt("id", 1) + 1;
    }

    private class ButtonListen implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.success_btn:
                    startActivity(new Intent(RegisterSuccessActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(RegisterSuccessActivity.this, MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
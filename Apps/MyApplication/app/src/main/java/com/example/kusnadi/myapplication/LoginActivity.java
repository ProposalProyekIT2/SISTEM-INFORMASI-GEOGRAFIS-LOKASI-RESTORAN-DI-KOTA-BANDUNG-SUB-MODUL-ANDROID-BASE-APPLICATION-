package com.example.kusnadi.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kusnadi on 9/23/2016.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = LoginActivity.this;

        pDialog = new ProgressDialog(context);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
    }
    private void login() {
        final String username = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains(Koneksi.LOGIN_SUCCESS)){
                    //Buatkan sebuah shared preference
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Koneksi.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Buatkan Sebuah variabel Editor Untuk penyimpanan Nilai shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Tambahkan Nilai ke Editor
                    editor.putBoolean(Koneksi.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Koneksi.EMAIL_SHARED_PREF, username);
                    //Simpan Nilai ke Variabel editor
                    editor.commit();
                    hideDialog();
                    gotoMainActivity();
                }else{
                    hideDialog();
                    Toast.makeText(context, "Username atau Password Salah", Toast.LENGTH_LONG).show();
                }
            }
        },
        new Response.ErrorListener(){
         @Override
            public void onErrorResponse(VolleyError error) {
             hideDialog();
             Toast.makeText(context, "The Server Unreachable", Toast.LENGTH_LONG).show();
         }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Koneksi.KEY_USERS, username);
                params.put(Koneksi.KEY_PASSWORD, password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void gotoMainActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showDialog(){
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog(){
        if(pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onClickDaftar (View view) {
        Intent intentDaftar = new Intent (LoginActivity.this, RegisterActivity.class);
        startActivity(intentDaftar);
    }
}

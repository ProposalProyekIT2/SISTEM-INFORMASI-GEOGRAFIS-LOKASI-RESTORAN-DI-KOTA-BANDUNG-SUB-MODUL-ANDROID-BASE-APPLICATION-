package com.example.kusnadi.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Kusnadi on 9/22/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUsername;
    private EditText editPassword;
    private EditText editTextNama;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    private void TambahDataUsers(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final String nama = editTextNama.getText().toString().trim();

        class TambahDataUsers extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Proses Kirim Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v){
                HashMap<String,String> params = new HashMap<>();
                params.put(Koneksi.KEY_USER_USERNAME,username);
                params.put(Koneksi.KEY_USER_PASSWORD,password);
                params.put(Koneksi.KEY_USER_NAMA,nama);

                RequestHandlerUsers rhu = new RequestHandlerUsers();
                String res = rhu.sendPostRequest(Koneksi.URL_ADD, params);
                return res;
            }
        }
        TambahDataUsers ae = new TambahDataUsers();
        ae.execute();
    }
    @Override
    public void onClick(View v){
        if(v == buttonRegister){
            TambahDataUsers();
        }
    }
}

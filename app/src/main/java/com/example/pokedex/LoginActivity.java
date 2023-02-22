package com.example.pokedex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEdt, senhaEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void efetuarLogin() {
        Intent it = new Intent(this, Dashboard.class);
        startActivity(it);
    }

    public void validarLogin(View view){
        loginEdt = findViewById(R.id.editLogin);
        senhaEdt = findViewById(R.id.editSenha);

        if (loginEdt.getText().toString().isEmpty() && senhaEdt.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor informe login e senha", Toast.LENGTH_SHORT).show();
            return;
        }
        login(loginEdt.getText().toString(), senhaEdt.getText().toString());

    }

    private void login(String login, String senha) {
        String url = "http://192.168.15.5/pokedexApi/public/api/login";

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Integer.parseInt(response) == 1) {
                    Toast.makeText(getApplicationContext(), "Logado!.", Toast.LENGTH_LONG).show();
                    efetuarLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "Credenciais inválidas!.", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erro de conexão com a api.", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("login", login);
                params.put("password", senha);

                return params;
            }
        };
        queue.add(request);
    }
}
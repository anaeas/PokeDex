package com.example.pokedex;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private TextView quantPok;
    private ListView listaTipo,listaHab;
    private String url_quant = "http://192.168.15.5/pokedexApi/public/api/quantpokemons";
    private String url_tipo = "http://192.168.15.5/pokedexApi/public/api/top3tipos";
    private String url_hab = "http://192.168.15.5/pokedexApi/public/api/top3habilidades";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        quantPok = findViewById(R.id.totalCadastrados);
        listaTipo = findViewById(R.id.tiposCadastrados);
        listaHab = findViewById(R.id.habilidadesCadastradas);

        getDataQuant();
        getDataTipo();
        getDataHab();
    }

    private void getDataQuant() {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url_quant, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//                Log.i(TAG, "linha :" + response);
                quantPok.setText("Pokémons cadastrados: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                Toast.makeText(getApplicationContext(), "Erro de conexão com a api.", Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void getDataTipo() {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url_tipo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    JSONObject data = new JSONObject(response);
                    JSONArray subjects = data.getJSONArray("tipos");
                    String[] subjectsArray = new String[subjects.length()];
                    for(int i = 0; i < subjects.length(); i++){
                        subjectsArray[i] = subjects.getString(i);
                    }
                    ArrayAdapter<String> array = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            subjectsArray);
                    listaTipo.setAdapter(array);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                Toast.makeText(getApplicationContext(), "Erro de conexão com a api.", Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void getDataHab() {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url_hab, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    JSONObject data = new JSONObject(response);
                    JSONArray subjects = data.getJSONArray("habilidades");
                    String[] subjectsArray = new String[subjects.length()];
                    for(int i = 0; i < subjects.length(); i++){
                        subjectsArray[i] = subjects.getString(i);
                    }
                    ArrayAdapter<String> array = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            subjectsArray);
                    listaHab.setAdapter(array);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error :" + error.toString());
                Toast.makeText(getApplicationContext(), "Erro de conexão com a api.", Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cadastro:
                Intent it = new Intent(Dashboard.this, Cadastrar.class);
                startActivity(it);
                return true;
            case R.id.listar:
                it = new Intent(Dashboard.this, ListarTodos.class);
                startActivity(it);
                return true;
            case R.id.ptipo:
                it = new Intent(Dashboard.this, PesquisarTipo.class);
                startActivity(it);
                return true;
            case R.id.phabilidade:
                it = new Intent(Dashboard.this, PesquisarHabilidade.class);
                startActivity(it);
                return true;
            case R.id.sair:
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setTitle("Sair irá encerrar a aplicação");
                builder.setMessage("Tem certeza que deseja sair?");
                builder.setCancelable(true);

                builder.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(1);
                    }
                });

                builder.setPositiveButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
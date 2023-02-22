package com.example.pokedex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

public class PesquisarTipo extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    ListView lvTipo;
    private String url = "http://192.168.15.5/pokedexApi/public/api/pesquisatipo";
    private String url2;

    private Spinner tipoSpinner;
    private ArrayAdapter<CharSequence> tipoAdapter;
    private String selectedTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_tipo);

        tipoSpinner = findViewById(R.id.spinnerTipo);
        Button submitButton;
        submitButton = findViewById(R.id.botaoPesquisar);
        lvTipo = findViewById(R.id.listViewTipo);

        //Cria ArrayAdapter usando um string array e um spinnerlayout
        tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipo_poke, R.layout.spinner_layout);

        //Seleciona o layout
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Seta o adapter ao spinner para popular o spinner
        tipoSpinner.setAdapter(tipoAdapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTipo = tipoSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitButton.setOnClickListener(v -> {
            //valida se o spinner esta preenchido corretamente
            if (selectedTipo.equals("Selecione um tipo")) {
                Toast.makeText(PesquisarTipo.this, "Selecione um tipo", Toast.LENGTH_LONG).show();

            } else {
                //Caso tudo ok  PENDENTE
                String tipoString = tipoSpinner.getSelectedItem().toString();
//                Toast.makeText(PesquisarTipo.this, "Pesquisa ok", Toast.LENGTH_LONG).show();
                url2 = url+"/?tipo="+tipoString;
                Log.i(TAG, "Url :" + url2);
                getDataTipo();
            }
        });
    }

    private void getDataTipo() {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    JSONObject data = new JSONObject(response);
                    JSONArray subjects = data.getJSONArray("nomes");
                    String[] subjectsArray = new String[subjects.length()];
                    for(int i = 0; i < subjects.length(); i++){
                        subjectsArray[i] = subjects.getString(i);
                    }
                    ArrayAdapter<String> array = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            subjectsArray);
                    lvTipo.setAdapter(array);

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
}
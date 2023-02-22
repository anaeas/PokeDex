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

public class PesquisarHabilidade extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    ListView lvHabilidade;
    private String url = "http://192.168.15.5/pokedexApi/public/api/pesquisahabilidade";
    private String url2;

    private Spinner habilidadeSpinner;
    private ArrayAdapter<CharSequence> habilidadeAdapter;
    private String selectedHabilidade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_habilidade);

        habilidadeSpinner = findViewById(R.id.spinnerHabilidade);
        Button submitButton;
        submitButton = findViewById(R.id.botaoPesquisar);
        lvHabilidade = findViewById(R.id.listViewPesquisar);

        //Cria ArrayAdapter usando um string array e um spinnerlayout
        habilidadeAdapter = ArrayAdapter.createFromResource(this, R.array.habilidade_poke, R.layout.spinner_layout);

        //Seleciona o layout
        habilidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Seta o adapter ao spinner para popular o spinner
        habilidadeSpinner.setAdapter(habilidadeAdapter);
        //atribui o item selecionado para fazer validação
        habilidadeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHabilidade = habilidadeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //valida se o spinner esta preenchido corretamente
        submitButton.setOnClickListener(v -> {
            if (selectedHabilidade.equals("Selecione uma habilidade")) {
                Toast.makeText(PesquisarHabilidade.this, "Selecione uma habilidade", Toast.LENGTH_LONG).show();

            } else {
                //Caso tudo ok  PENDENTE
                String tipoString = habilidadeSpinner.getSelectedItem().toString();
//                Toast.makeText(PesquisarHabilidade.this, "Pesquisa ok", Toast.LENGTH_LONG).show();
                url2 = url+"/?habilidade="+tipoString;
                getDataHab();
                Log.i(TAG, "Url :" + url2);
            }
        });
    }

    private void getDataHab() {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    JSONObject data = new JSONObject(response);
                    JSONArray subjects = data.getJSONArray("pokemons");
                    String[] subjectsArray = new String[subjects.length()];
                    for(int i = 0; i < subjects.length(); i++){
                        subjectsArray[i] = subjects.getString(i);
                    }
                    ArrayAdapter<String> array = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            subjectsArray);
                    lvHabilidade.setAdapter(array);

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
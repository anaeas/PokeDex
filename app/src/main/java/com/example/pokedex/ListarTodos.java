package com.example.pokedex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.Arrays;
import java.util.List;

public class ListarTodos extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.15.5/pokedexApi/public/api/listaNomeImagem";

    private ListView listView;
    private String nomePokemon[] = {};
    private Integer idImagem[] = {};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        getData( this);

    }

    private void getData(Context context) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                    List<String> dados = Arrays.asList(subjectsArray);

                    JSONArray imagensJson = data.getJSONArray("imagens");
                    String[] imagensArray = new String[imagensJson.length()];
                    for(int i = 0; i < imagensJson.length(); i++){
                        imagensArray[i] = imagensJson.getString(i);
                    }
                    List<String> imagens = Arrays.asList(imagensArray);

                    JSONArray idsJson = data.getJSONArray("ids");
                    String[] idsArray = new String[idsJson.length()];
                    for(int i = 0; i < idsJson.length(); i++){
                        idsArray[i] = idsJson.getString(i);
                    }
                    List<String> ids = Arrays.asList(idsArray);



                    RecyclerView rvMain = findViewById(R.id.rvMain);
                    final MainRecyclerAdapter adapter = new MainRecyclerAdapter(dados,imagens,ids, context);
                    rvMain.setAdapter(adapter);
                    rvMain.setHasFixedSize(true);
                    rvMain.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

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
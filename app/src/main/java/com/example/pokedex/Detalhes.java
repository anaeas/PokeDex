package com.example.pokedex;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Detalhes extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://192.168.15.5/pokedexApi/public/api/detalhesPokemon";
    private TextView nome, tipo, cat;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        nome = findViewById(R.id.textViewNome);
        tipo = findViewById(R.id.textViewTipo);
        cat = findViewById(R.id.textViewCat);
        image = findViewById(R.id.logo);

        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        url = url+"?id="+id;
        getData(url);

    }


    private void getData(String url) {
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    JSONObject data = new JSONObject(response);

                    byte[] decodedString = Base64.decode(data.getString("image"), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    image.setImageBitmap(decodedByte);


                    nome.setText("Nome: "+data.getString("nome"));
                    tipo.setText("Tipo: "+data.getString("tipo"));

                    String h1 = data.getString("habilidade_1");
                    String h2 = data.getString("habilidade_2");
                    String h3 = data.getString("habilidade_3");

                    if (h1.equals("null")){
                        h1="";
                    }
                    if (h2.equals("null")){
                        h2="";
                    }
                    if (h3.equals("null")){
                        h3="";
                    }

                    cat.setText("Habilidades:\n"+h1+"\n"+h2+"\n"+h3);

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
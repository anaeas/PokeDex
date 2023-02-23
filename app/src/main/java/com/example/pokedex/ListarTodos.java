package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListarTodos extends ListActivity {
    private ListView listView;
    private String nomePokemon[] = {};
    private Integer idImagem[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        ListView listView = findViewById(R.id.list);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(),"Nome"+nomePokemon[position-1],Toast.LENGTH_SHORT).show();

//                Intent it = new Intent(ListarTodos.this, Detalhes.class);
//                startActivity(it);

            }
        });



    }
}
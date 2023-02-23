package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class ListarTodos extends AppCompatActivity {
    private ListView listView;
    private String nomePokemon[] = {};
    private Integer idImagem[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        RecyclerView rvMain = findViewById(R.id.rvMain);

        List<String> dados = Arrays.asList( "Produção Geral", "Perdas", "Contagem Por Máquina", "OEE", "Paradas de Máquina");
        final MainRecyclerAdapter adapter = new MainRecyclerAdapter(dados, this);
        rvMain.setAdapter(adapter);
        rvMain.setHasFixedSize(true);
        rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));




//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(getApplicationContext(),"Nome"+nomePokemon[position-1],Toast.LENGTH_SHORT).show();
//
////                Intent it = new Intent(ListarTodos.this, Detalhes.class);
////                startActivity(it);
//
//            }
//        });



    }
}
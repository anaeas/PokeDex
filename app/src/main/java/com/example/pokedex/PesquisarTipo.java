package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PesquisarTipo extends AppCompatActivity {

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
        ListView lvTipo = findViewById(R.id.listViewTipo);

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
                Toast.makeText(PesquisarTipo.this, "Pesquisa ok", Toast.LENGTH_LONG).show();
            }
        });
    }
}
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

public class PesquisarHabilidade extends AppCompatActivity {

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
        ListView lvHabilidade = findViewById(R.id.listViewPesquisar);

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
                Toast.makeText(PesquisarHabilidade.this, "Pesquisa ok", Toast.LENGTH_LONG).show();
            }
        });
    }

}
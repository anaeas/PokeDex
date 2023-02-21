package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
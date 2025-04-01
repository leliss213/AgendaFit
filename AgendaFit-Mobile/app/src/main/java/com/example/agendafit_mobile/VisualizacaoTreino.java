package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import controller.ConexaoController;
import controller.InformacoesApp;
import adapter.TreinoAdapter;
import modelDominio.Treino;

public class VisualizacaoTreino extends AppCompatActivity {
    RecyclerView rvVisualizacaoTreino;
    TreinoAdapter treinoAdapter;
    ArrayList<Treino> listaTreinos;
    InformacoesApp informacoesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvVisualizacaoTreino = findViewById(R.id.rvVisualizacaoTreino);
        informacoesApp = (InformacoesApp) getApplicationContext();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Visualização dos Treinos");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                listaTreinos = ccont.listaTreinos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(informacoesApp, "dentro da thread", Toast.LENGTH_SHORT).show();
                    }
                });

                if(listaTreinos != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            treinoAdapter = new TreinoAdapter(listaTreinos,trataCliqueItem);
                            rvVisualizacaoTreino.setLayoutManager(new LinearLayoutManager(informacoesApp));
                            rvVisualizacaoTreino.setItemAnimator(new DefaultItemAnimator());
                            rvVisualizacaoTreino.setAdapter(treinoAdapter);
                        }
                    });
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(informacoesApp, "Não foi possível receber a lista de Treinos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();
    }

    TreinoAdapter.TreinoOnClickListener trataCliqueItem = new TreinoAdapter.TreinoOnClickListener() {
        @Override
        public void onClickTreino(View view, int position) {
            Treino treino =listaTreinos.get(position);
            Intent it = new Intent(VisualizacaoTreino.this,VisualizacaoDetalhadaTreino.class);
            it.putExtra("treino",treino);
            it.putExtra("codTreino", treino.getCodTreino());
            startActivity(it);
        }
    };

}

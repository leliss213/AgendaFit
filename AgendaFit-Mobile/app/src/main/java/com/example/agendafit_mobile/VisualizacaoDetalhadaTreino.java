package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import controller.ConexaoController;
import controller.InformacoesApp;
import controller.SpinnerMultiSelecionavel;
import modelDominio.Exercicio;
import modelDominio.Treino;

public class VisualizacaoDetalhadaTreino extends AppCompatActivity {
    TextView tvVisualizacaoDetalhadaTituloTreino,tvVisualizacaoDetalhadaDescricaoTreino,tvVisualizacaoDetalhadaData,tvVisualizacaoDetalhadaHora,tvVisualizacaoDetalhadaTipoTreino;
    SpinnerMultiSelecionavel spMultiExerciciosCadastrados;
    ArrayList<Exercicio> listaExercicios;
    InformacoesApp informacoesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_detalhada_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent it = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Treino treino = (Treino) it.getSerializableExtra("treino");
        spMultiExerciciosCadastrados = findViewById(R.id.spMultiExerciciosCadastrados);
        informacoesApp = (InformacoesApp) getApplicationContext();
        tvVisualizacaoDetalhadaTituloTreino = findViewById(R.id.tvVisualizacaoDetalhadaTituloTreino);
        tvVisualizacaoDetalhadaDescricaoTreino = findViewById(R.id.tvVisualizacaoDetalhadaDescricaoTreino);
        tvVisualizacaoDetalhadaData = findViewById(R.id.tvVisualizacaoDetalhadaData);
        tvVisualizacaoDetalhadaHora = findViewById(R.id.tvVisualizacaoDetalhadaHora);
        tvVisualizacaoDetalhadaTipoTreino = findViewById(R.id.tvVisualizacaoDetalhadaTipoTreino);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                listaExercicios = ccont.listaExerciciosFiltro(treino.getCodTreino());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(listaExercicios!= null){
                            spMultiExerciciosCadastrados.setItems(listaExercicios);
                        }

                    }
                });

            }
        });
        thread.start();
        if(it != null){
            tvVisualizacaoDetalhadaTituloTreino.setText("Título: "+treino.getNomeTreino());
            tvVisualizacaoDetalhadaDescricaoTreino.setText("Descrição: "+treino.getDescricao());
            tvVisualizacaoDetalhadaData.setText("Data: "+treino.getData());
            tvVisualizacaoDetalhadaHora.setText(String.valueOf("Hora: "+treino.getHora()));
            String treinoLiteral="";
            if(treino.getTipo() == 1){
                treinoLiteral = "Peito";
            } else if(treino.getTipo() == 2){
                treinoLiteral = "Ombro";
            } else if(treino.getTipo() == 3){
                treinoLiteral = "Braço";
            } else if(treino.getTipo() == 4){
                treinoLiteral = "Costas";
            } else if(treino.getTipo() == 5){
                treinoLiteral = "Perna";
            }
            tvVisualizacaoDetalhadaTipoTreino.setText("Tipo: "+treinoLiteral);
        }
    }

}

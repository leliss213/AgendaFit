package com.example.agendafit_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    Button bVisualizacaoDetalhadaTreinoExcluir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_detalhada_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent it = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Treino treino = (Treino) it.getSerializableExtra("treino");
        bVisualizacaoDetalhadaTreinoExcluir = findViewById(R.id.bVisualizacaoDetalhadaTreinoExcluir);
        spMultiExerciciosCadastrados = findViewById(R.id.spMultiExerciciosCadastrados);
        informacoesApp = (InformacoesApp) getApplicationContext();
        tvVisualizacaoDetalhadaTituloTreino = findViewById(R.id.tvVisualizacaoDetalhadaTituloTreino);
        tvVisualizacaoDetalhadaDescricaoTreino = findViewById(R.id.tvVisualizacaoDetalhadaDescricaoTreino);
        tvVisualizacaoDetalhadaData = findViewById(R.id.tvVisualizacaoDetalhadaData);
        tvVisualizacaoDetalhadaHora = findViewById(R.id.tvVisualizacaoDetalhadaHora);
        tvVisualizacaoDetalhadaTipoTreino = findViewById(R.id.tvVisualizacaoDetalhadaTipoTreino);
        final Thread thread = new Thread(new Runnable() {
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
            tvVisualizacaoDetalhadaHora.setText("Hora: "+treino.getHora());
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

        bVisualizacaoDetalhadaTreinoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mensagem = new AlertDialog.Builder(VisualizacaoDetalhadaTreino.this);
                mensagem.setTitle("Excluir Treino");
                mensagem.setIcon(android.R.drawable.ic_delete);
                mensagem.setMessage("Deseja excluir esse treino?");
                mensagem.setPositiveButtonIcon(getDrawable(R.drawable.ic_sim));
                mensagem.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Thread thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String situacao = null;
                                ConexaoController ccont = new ConexaoController(informacoesApp);
                                situacao = ccont.deletaTreino(treino);
                                if(situacao.equals("ok")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(informacoesApp, "Treino Excluido com sucesso", Toast.LENGTH_SHORT).show();
                                            Intent it = new Intent(VisualizacaoDetalhadaTreino.this, VisualizacaoTreino.class);
                                            startActivity(it);
                                        }
                                    });
                                }else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(informacoesApp, "Erro", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        thread1.start();
                    }

                });
                mensagem.setNegativeButton("não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mensagem.show();

            }
        });
    }

}

package com.example.agendafit_mobile;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import modelDominio.Exercicio;

public class VisualizacaoExercicio extends AppCompatActivity {
    RecyclerView rvVisualizacaoExercicio;
    ExercicioAdapter exercicioAdapter;

    ArrayList<Exercicio> listaExercicios;

    InformacoesApp informacoesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_exercicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvVisualizacaoExercicio = findViewById(R.id.rvVisualizacaoExercicio);
        informacoesApp = (InformacoesApp) getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Visualização dos Exercícios");


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                ConexaoController ccont = new ConexaoController(informacoesApp);
                listaExercicios = ccont.listaExercicios();

                if(listaExercicios != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            exercicioAdapter = new ExercicioAdapter(listaExercicios,trataCliqueItem);
                            rvVisualizacaoExercicio.setLayoutManager(new LinearLayoutManager(informacoesApp));
                            rvVisualizacaoExercicio.setItemAnimator(new DefaultItemAnimator());
                            rvVisualizacaoExercicio.setAdapter(exercicioAdapter);
                        }
                    });
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(informacoesApp, "Nao foi possivel obter a lista de exercicios", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        thread.start();
    }

    ExercicioAdapter.ExercicioOnClickListener trataCliqueItem = new ExercicioAdapter.ExercicioOnClickListener() {
        @Override
        public void onClickExercicio(View view, int position) {
            final Exercicio exercicio = listaExercicios.get(position);
            AlertDialog.Builder mensagem = new AlertDialog.Builder(VisualizacaoExercicio.this);
            mensagem.setTitle("Excluir Exercício");
            mensagem.setIcon(android.R.drawable.ic_delete);
            mensagem.setMessage("Deseja excluir esse exercício?");
            mensagem.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ConexaoController ccont = new ConexaoController(informacoesApp);
                            String situacao = ccont.deletaExercicio(exercicio);
                            if (situacao.equals("ok")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(informacoesApp, "Exercicio Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(informacoesApp, "ERRO:", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                    thread.start();
                }
            });
            mensagem.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            mensagem.show();
        }
    };

}

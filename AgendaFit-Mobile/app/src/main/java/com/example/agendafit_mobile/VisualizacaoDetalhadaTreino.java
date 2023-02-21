package com.example.agendafit_mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class VisualizacaoDetalhadaTreino extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText tvVisualizacaoDetalhadaTituloTreino,tvVisualizacaoDetalhadaDescricaoTreino,tvVisualizacaoDetalhadaData,tvVisualizacaoDetalhadaHora,tvVisualizacaoDetalhadaTipoTreinoString, tvVisualizacaoDetalhadaExerciciosTreino;
    Spinner spCadastroTipoTreino;
    TextView tvVisualizacaoDetalhadaTexto, tvVisualizacaoDetalhadaTexto2;
    SpinnerMultiSelecionavel spMultiExerciciosCadastrados;
    ArrayList<Exercicio> listaExercicios;
    InformacoesApp informacoesApp;
    Button bVisualizacaoDetalhadaTreinoExcluir, bVisualizacaoDetalhadaTreinoAlterar, bVisualizacaoDetalhadaTreinoSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_detalhada_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent it = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Treino treino = (Treino) it.getSerializableExtra("treino");
        spCadastroTipoTreino = findViewById(R.id.spCadastroTipoTreino);
        tvVisualizacaoDetalhadaTexto2 = findViewById(R.id.tvVisualizacaoDetalhadaTexto2);
        tvVisualizacaoDetalhadaTexto = findViewById(R.id.tvVisualizacaoDetalhadaTexto);
        bVisualizacaoDetalhadaTreinoExcluir = findViewById(R.id.bVisualizacaoDetalhadaTreinoExcluir);
        bVisualizacaoDetalhadaTreinoAlterar = findViewById(R.id.bVisualizacaoDetalhadaTreinoAlterar);
        bVisualizacaoDetalhadaTreinoSalvar = findViewById(R.id.bVisualizacaoDetalhadaTreinoSalvar);
        spMultiExerciciosCadastrados = findViewById(R.id.spMultiExerciciosCadastrados);
        informacoesApp = (InformacoesApp) getApplicationContext();
        tvVisualizacaoDetalhadaTituloTreino = findViewById(R.id.tvVisualizacaoDetalhadaTituloTreino);
        tvVisualizacaoDetalhadaDescricaoTreino = findViewById(R.id.tvVisualizacaoDetalhadaDescricaoTreino);
        tvVisualizacaoDetalhadaData = findViewById(R.id.tvVisualizacaoDetalhadaData);
        tvVisualizacaoDetalhadaHora = findViewById(R.id.tvVisualizacaoDetalhadaHora);
        tvVisualizacaoDetalhadaTipoTreinoString = findViewById(R.id.tvVisualizacaoDetalhadaTipoTreinoString);
        tvVisualizacaoDetalhadaExerciciosTreino = findViewById(R.id.tvVisualizacaoDetalhadaExerciciosTreino);

        //carregando o spinner do tipo de treino ex: peito, ombro, costas, braço e perna
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_treino, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCadastroTipoTreino.setAdapter(adapter);
        spCadastroTipoTreino.setOnItemSelectedListener(this);

        //thread para pegar a lista de exercicios que estão no treino e colocar no spinner e no editText
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
                            tvVisualizacaoDetalhadaExerciciosTreino.setText("Exercícios: "+listaExercicios.toString());
                        }

                    }
                });

            }
        });
        thread.start();

        //pegando a intent mandada da tela de visualizacao de treinos e setando os editText
        if(it != null){
            tvVisualizacaoDetalhadaTituloTreino.setText("Título: "+treino.getNomeTreino());
            tvVisualizacaoDetalhadaDescricaoTreino.setText("Descrição: "+treino.getDescricao());
            tvVisualizacaoDetalhadaData.setText("Data: "+treino.getData());
            tvVisualizacaoDetalhadaHora.setText("Hora: "+treino.getHora());
            tvVisualizacaoDetalhadaTipoTreinoString.setText("Tipo: "+treino.tipoLiteral());
            desabilitarHabilitar(false);
            spMultiExerciciosCadastrados.setVisibility(View.GONE);
            tvVisualizacaoDetalhadaTexto.setVisibility(View.GONE);
            tvVisualizacaoDetalhadaTexto2.setVisibility(View.GONE);
            spCadastroTipoTreino.setVisibility(View.GONE);
        }

        //verificando os campos apos o click do botão salvar
        bVisualizacaoDetalhadaTreinoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tvVisualizacaoDetalhadaTituloTreino.getText().toString().equals("")){
                    if(!tvVisualizacaoDetalhadaDescricaoTreino.getText().toString().equals("")){
                        if(!tvVisualizacaoDetalhadaData.getText().toString().equals("")){
                            if(!tvVisualizacaoDetalhadaHora.getText().toString().equals("")){

                            }else{
                                tvVisualizacaoDetalhadaHora.requestFocus();
                                Toast.makeText(informacoesApp, "Insira a Hora do Treino!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            tvVisualizacaoDetalhadaData.requestFocus();
                            Toast.makeText(informacoesApp, "Insira a Data do Treino!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        tvVisualizacaoDetalhadaDescricaoTreino.requestFocus();
                        Toast.makeText(informacoesApp, "Insira a Descrição do Treino!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    tvVisualizacaoDetalhadaTituloTreino.requestFocus();
                    Toast.makeText(informacoesApp, "Insira o Nome do Treino!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //tratando o clique do botao alterar e habilitando os campos para edição
        bVisualizacaoDetalhadaTreinoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvVisualizacaoDetalhadaTituloTreino.setText(treino.getNomeTreino());
                tvVisualizacaoDetalhadaDescricaoTreino.setText(treino.getDescricao());
                tvVisualizacaoDetalhadaData.setText(treino.getData());
                tvVisualizacaoDetalhadaHora.setText(""+treino.getHora());
                tvVisualizacaoDetalhadaTipoTreinoString.setText(treino.tipoLiteral());
                spMultiExerciciosCadastrados.setVisibility(View.VISIBLE);
                tvVisualizacaoDetalhadaTexto.setVisibility(View.VISIBLE);
                tvVisualizacaoDetalhadaExerciciosTreino.setVisibility(View.GONE);
                tvVisualizacaoDetalhadaTexto2.setVisibility(View.VISIBLE);
                spCadastroTipoTreino.setVisibility(View.VISIBLE);
                desabilitarHabilitar(true);
                tvVisualizacaoDetalhadaTipoTreinoString.setVisibility(View.GONE);
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ConexaoController ccont = new ConexaoController(informacoesApp);
                        listaExercicios = ccont.listaExercicios();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spMultiExerciciosCadastrados.setItems(listaExercicios);
                            }
                        });
                    }
                });
                thread1.start();
            }
        });

        //botão excluir
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

    //função para desabilitar e habilitar os campos dependendo do botão clicado
    public void desabilitarHabilitar(boolean situacao){
        tvVisualizacaoDetalhadaTituloTreino.setEnabled(situacao);
        tvVisualizacaoDetalhadaDescricaoTreino.setEnabled(situacao);
        tvVisualizacaoDetalhadaData.setEnabled(situacao);
        tvVisualizacaoDetalhadaHora.setEnabled(situacao);
        tvVisualizacaoDetalhadaTipoTreinoString.setEnabled(situacao);
        tvVisualizacaoDetalhadaExerciciosTreino.setEnabled(situacao);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

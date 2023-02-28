package com.example.agendafit_mobile;

import android.os.Bundle;
import android.print.PrintJob;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import controller.ConexaoController;
import controller.InformacoesApp;
import controller.SpinnerMultiSelecionavel;
import modelDominio.Exercicio;
import modelDominio.Treino;
import utils.DataCalendario;


public class CadastroTreino extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerTipoTreino;
    EditText etCadastroNomeTreino, etCadastroDescricao, etCadastroData, etCadastroHora;
    Button bCadastroCadastrar, bCadastroCancelar;
    InformacoesApp informacoesApp;
    ImageButton ibCadastroTreinoCalendario;
    SpinnerMultiSelecionavel spinnerMultiSelecionavel;
    //recebe a lista do banco
    ArrayList<Exercicio> listExercicios = new ArrayList<>();
    ArrayList<Exercicio> listExerciciosSelecionados = new ArrayList<>();
    //recebe somente a lista de nomes dos exercicios vindos do banco
    ArrayList<String> listExerciciosNomes = new ArrayList<>();
    DataCalendario dataCalendario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        informacoesApp = (InformacoesApp) getApplicationContext();

        spinnerTipoTreino = findViewById(R.id.spCadastroTipoTreino);
        spinnerMultiSelecionavel = findViewById(R.id.spMultiExercicios);
        etCadastroNomeTreino = findViewById(R.id.etCadastroNomeTreino);
        etCadastroDescricao = findViewById(R.id.etCadastroDescricao);
        etCadastroData = findViewById(R.id.etCadastroData);
        etCadastroHora = findViewById(R.id.etCadastroHora);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_treino, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoTreino.setAdapter(adapter);
        spinnerTipoTreino.setOnItemSelectedListener(this);

        bCadastroCadastrar = findViewById(R.id.bCadastroCadastrar);
        bCadastroCancelar = findViewById(R.id.bCadastroCancelar);

        ibCadastroTreinoCalendario = findViewById(R.id.ibCadastroTreinoCalendario);

        spinnerMultiSelecionavel.setEnabled(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                listExercicios = ccont.listaExercicios();
                for(int x = 0; x<listExercicios.size();x++){
                    String nome = listExercicios.get(x).getNomeExercicio();
                    listExerciciosNomes.add(nome);
                }
                spinnerMultiSelecionavel.setItems(listExercicios);

            }
        });
        thread.start();

        bCadastroCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etCadastroNomeTreino.getText().toString().isEmpty()){
                    if(!etCadastroDescricao.getText().toString().isEmpty()){
                        if(!etCadastroData.getText().toString().isEmpty()){
                            if(!etCadastroHora.getText().toString().isEmpty()){
                                if(spinnerTipoTreino.getSelectedItemPosition()!=0){
                                    if(spinnerMultiSelecionavel.getSelectedSize()>0){
                                        String nomeTreino = etCadastroNomeTreino.getText().toString();
                                        String descricao = etCadastroDescricao.getText().toString();
                                        String data = etCadastroData.getText().toString();
                                        float hora = Float.parseFloat(etCadastroHora.getText().toString());
                                        int tipoTreino = spinnerTipoTreino.getSelectedItemPosition();
                                        listExerciciosSelecionados = spinnerMultiSelecionavel.getSelectedItems();
                                        final Treino treino = new Treino(nomeTreino,descricao,data,hora,listExerciciosSelecionados,tipoTreino);
                                        //Toast.makeText(informacoesApp, "IRU", Toast.LENGTH_SHORT).show();
                                        Thread thread1 = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConexaoController ccont = new ConexaoController(informacoesApp);
                                                String msgRecebida = ccont.cadastroTreino(treino);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(informacoesApp, "certo", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        });
                                        thread1.start();
                                    }else {
                                        Toast.makeText(informacoesApp, "Insira os Exercícios!", Toast.LENGTH_SHORT).show();
                                        spinnerMultiSelecionavel.requestFocus();
                                    }

                                } else{
                                    Toast.makeText(CadastroTreino.this, "Insira o Tipo do Treino!", Toast.LENGTH_SHORT).show();
                                    spinnerTipoTreino.requestFocus();
                                }
                            } else{
                                Toast.makeText(CadastroTreino.this, "Insira a Hora do Treino!", Toast.LENGTH_SHORT).show();
                                etCadastroHora.requestFocus();
                            }
                        } else{
                            Toast.makeText(CadastroTreino.this, "Insira a Data do Treino!", Toast.LENGTH_SHORT).show();
                            etCadastroData.requestFocus();
                        }
                    } else{
                        Toast.makeText(CadastroTreino.this, "Insira a Descrição do Treino!", Toast.LENGTH_SHORT).show();
                        etCadastroDescricao.requestFocus();
                    }
                } else{
                    Toast.makeText(CadastroTreino.this, "Insira o Nome do Treino!", Toast.LENGTH_SHORT).show();
                    etCadastroNomeTreino.requestFocus();
                }
            }
        });

        bCadastroCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });

        ibCadastroTreinoCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataCalendario = new DataCalendario();
                dataCalendario.setEditText(etCadastroData);
                dataCalendario.show(getSupportFragmentManager(), "Calendário");
            }
        });
    }
    //teste

    public void limpaCampos(){
        etCadastroNomeTreino.setText(null);
        etCadastroDescricao.setText(null);
        etCadastroData.setText(null);
        etCadastroHora.setText(null);
        etCadastroNomeTreino.requestFocus();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

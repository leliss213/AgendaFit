package com.example.agendafit_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import controller.ConexaoController;
import controller.InformacoesApp;
import modelDominio.Exercicio;

public class CadastroExercicios extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spCadastroTipoTreino;
    EditText etCadastroExerciciosNomeExercicio;
    Button bCadastroCancelar,bCadastroCadastrar;
    InformacoesApp informacoesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_exercicios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        informacoesApp = (InformacoesApp) getApplicationContext();
        etCadastroExerciciosNomeExercicio = findViewById(R.id.etCadastroExerciciosNomeExercicio);

        bCadastroCadastrar = findViewById(R.id.bCadastroCadastrar);
        bCadastroCancelar = findViewById(R.id.bCadastroCancelar);

        spCadastroTipoTreino = findViewById(R.id.spCadastroTipoTreino);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_treino, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCadastroTipoTreino.setAdapter(adapter);
        spCadastroTipoTreino.setOnItemSelectedListener(this);

        bCadastroCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etCadastroExerciciosNomeExercicio.getText().toString().isEmpty()){
                    if(!spCadastroTipoTreino.getSelectedItem().toString().equals("Nenhum")){

                        String nomeExercicio = etCadastroExerciciosNomeExercicio.getText().toString();
                        int tipoExercicio = spCadastroTipoTreino.getSelectedItemPosition();
                       // Toast.makeText(CadastroExercicios.this, "Tipo Exercicio: "+tipoExercicio+" Nome Exercicio: "+nomeExercicio, Toast.LENGTH_SHORT).show();
                        final Exercicio exercicio = new Exercicio(nomeExercicio, tipoExercicio);

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ConexaoController ccont = new ConexaoController(informacoesApp);
                                String msgRecebida = ccont.cadastraExercicio(exercicio);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(informacoesApp, "Exercício cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        thread.start();

                    } else{
                        Toast.makeText(CadastroExercicios.this, "Informe o Tipo do Exercício", Toast.LENGTH_SHORT).show();
                        spCadastroTipoTreino.requestFocus();
                    }
                } else{
                    Toast.makeText(CadastroExercicios.this, "Informe o Nome do Exercício", Toast.LENGTH_SHORT).show();
                    etCadastroExerciciosNomeExercicio.requestFocus();
                }
            }
        });
        bCadastroCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });
    }

    public void limpaCampos(){
        etCadastroExerciciosNomeExercicio.setText(null);
        etCadastroExerciciosNomeExercicio.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

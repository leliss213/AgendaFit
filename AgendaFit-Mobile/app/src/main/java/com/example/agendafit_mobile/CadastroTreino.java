package com.example.agendafit_mobile;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroTreino extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerTipoTreino;
    EditText etCadastroNomeTreino, etCadastroDescricao, etCadastroData, etCadastroHora;
    Button bCadastroCadastrar, bCadastroCancelar;

    //teste
    MaterialCardView selecionarExercicios;
    boolean [] select;
    ArrayList<Integer> listExercicios = new ArrayList<>();
    String [] nomesExercicios = {"Supino", "LegPress","ElevacaoLateral"};
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_treino);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selecionarExercicios = findViewById(R.id.mcvCadastro);
        select = new boolean[nomesExercicios.length];

        etCadastroNomeTreino = findViewById(R.id.etCadastroNomeTreino);
        etCadastroDescricao = findViewById(R.id.etCadastroDescricao);
        etCadastroData = findViewById(R.id.etCadastroData);
        etCadastroHora = findViewById(R.id.etCadastroHora);

        spinnerTipoTreino = findViewById(R.id.spCadastroTipoTreino);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_treino, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoTreino.setAdapter(adapter);
        spinnerTipoTreino.setOnItemSelectedListener(this);

        bCadastroCadastrar = findViewById(R.id.bCadastroCadastrar);
        bCadastroCancelar = findViewById(R.id.bCadastroCancelar);

        bCadastroCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etCadastroNomeTreino.getText().toString().isEmpty()){
                    if(!etCadastroDescricao.getText().toString().isEmpty()){
                        if(!etCadastroData.getText().toString().isEmpty()){
                            if(!etCadastroHora.getText().toString().isEmpty()){
                                if(spinnerTipoTreino.getSelectedItemPosition()!=0){


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

        selecionarExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCousesDialog();
            }
        });
    }
    //teste
    private void showCousesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroTreino.this);
        builder.setTitle("Selecionar Exercicios");
        builder.setCancelable(false);
        builder.setMultiChoiceItems(nomesExercicios, select, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    listExercicios.add(i);
                }else{
                    listExercicios.remove(i);
                }
            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();
                for(int x =0; x<listExercicios.size();x++){
                    stringBuilder.append(nomesExercicios[listExercicios.get(x)]);

                    if(x != listExercicios.size()-1 ){
                        stringBuilder.append(", ");
                    }
                }
            }
        }).setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNeutralButton("limpar todos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for(int x =0; x<select.length;x++){
                    select[x] = false;
                    listExercicios.clear();
                }
            }
        });
        builder.show();
    }

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

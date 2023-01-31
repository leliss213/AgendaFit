package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import modelDominio.Treino;

public class VisualizacaoDetalhadaTreino extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_detalhada_exercicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent it = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(it != null){
            Treino treino = (Treino) it.getSerializableExtra("treino");

        }
    }

}

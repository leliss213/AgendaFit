package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    Button bMenuCadastrarTreino, bMenuVisualizarTreino, bMenuCadastrarExercicio, bMenuVisualizarExercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bMenuCadastrarTreino = findViewById(R.id.bMenuCadastrarTreino);
        bMenuVisualizarTreino = findViewById(R.id.bMenuVisualizarTreino);
        bMenuCadastrarExercicio = findViewById(R.id.bMenuCadastrarExercicio);
        bMenuVisualizarExercicio = findViewById(R.id.bMenuVisualizarExercicio);

        bottomNavigationView = findViewById(R.id.bnMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        bMenuCadastrarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, CadastroTreino.class);
                startActivity(it);
            }
        });
        bMenuVisualizarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, VisualizacaoTreino.class);
                startActivity(it);
            }
        });
        bMenuCadastrarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, CadastroExercicios.class);
                startActivity(it);
            }
        });
        bMenuVisualizarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, VisualizacaoExercicio.class);
                startActivity(it);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.home:
                Intent it = new Intent(this, MenuActivity.class);
                startActivity(it);
                return true;

            case R.id.visualizar:
                it = new Intent(this, VisualizacaoTreino.class);
                startActivity(it);
                return true;

            case R.id.adicionar:
                it = new Intent(this, CadastroTreino.class);
                startActivity(it);
                return true;

            case R.id.perfil:
                it = new Intent(this, PerfilActivity.class);
                startActivity(it);
                return true;
        }
        return false;
    }
}

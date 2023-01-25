package com.example.agendafit_mobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import controller.InformacoesApp;
import modelDominio.Usuario;

public class PerfilActivity extends AppCompatActivity {
    TextView tvPerfilNomeUsuario, tvPerfilAlturaUsuario, tvPerfilPesoUsuario;
    InformacoesApp informacoesApp;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        informacoesApp = (InformacoesApp) getApplicationContext();
        tvPerfilNomeUsuario = findViewById(R.id.tvPerfilNomeUsuario);
        tvPerfilAlturaUsuario = findViewById(R.id.tvPerfilAlturaUsuario);
        tvPerfilPesoUsuario = findViewById(R.id.tvPerfilPesoUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = informacoesApp.getUsuarioLogado();
        tvPerfilNomeUsuario.setText("Bem vindo - "+usuario.getNomeUsuario());
        tvPerfilAlturaUsuario.setText("Altura: "+usuario.getAltura()+"cm");
        tvPerfilPesoUsuario.setText("Peso: "+(usuario.getPeso())+"kg");
    }

}

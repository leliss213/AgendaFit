package com.example.agendafit_mobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import controller.ConexaoController;
import controller.InformacoesApp;
import modelDominio.Exercicio;
import modelDominio.Usuario;

public class PerfilActivity extends AppCompatActivity {
    TextView tvPerfilNomeUsuario, tvPerfilAlturaUsuario, tvPerfilPesoUsuario, tvPerfilOmbroexe, tvPerfilPeitoexe,tvPerfilBracoexe,tvPerfilCostasexe,tvPerfilPernaexe;
    ArrayList<Exercicio> listaExercicios;
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

        tvPerfilOmbroexe = findViewById(R.id.tvPerfilOmbroexe);
        tvPerfilPeitoexe = findViewById(R.id.tvPerfilPeitoexe);
        tvPerfilBracoexe = findViewById(R.id.tvPerfilBracoexe);
        tvPerfilCostasexe = findViewById(R.id.tvPerfilCostasexe);
        tvPerfilPernaexe = findViewById(R.id.tvPerfilPernaexe);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                listaExercicios = ccont.listaExercicios();
                int peito=0,ombro=0,braco=0,costas=0,perna=0;
                for (int x = 0 ; x<listaExercicios.size(); x++){
                    Exercicio exercicio = listaExercicios.get(x);
                    if(exercicio.getTipo() == 1){
                        peito++;
                    } else if(exercicio.getTipo() == 2){
                        ombro++;
                    } else if(exercicio.getTipo() == 3){
                        braco++;
                    } else if(exercicio.getTipo() == 4){
                        costas++;
                    } else if(exercicio.getTipo() == 5){
                        perna++;
                    }
                }
                tvPerfilPeitoexe.setText(String.valueOf(peito));
                tvPerfilOmbroexe.setText(String.valueOf(ombro));
                tvPerfilBracoexe.setText(String.valueOf(braco));
                tvPerfilCostasexe.setText(String.valueOf(costas));
                tvPerfilPernaexe.setText(String.valueOf(perna));
            }
        });
        thread.start();
    }

}

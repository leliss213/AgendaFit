package com.example.agendafit_mobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import controller.ConexaoController;
import controller.InformacoesApp;
import modelDominio.Exercicio;
import modelDominio.Treino;
import modelDominio.Usuario;

public class PerfilActivity extends AppCompatActivity {
    TextView tvPerfilNomeUsuario, tvPerfilAlturaUsuario, tvPerfilPesoUsuario;
    ArrayList<Exercicio> listaExercicios;
    ArrayList<Treino> listaTreinos;
    InformacoesApp informacoesApp;
    Usuario usuario;
    PieChart pcPerfilTreinos, pcPerfilExercicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AGENDA FIT");
        actionBar.hide();
        informacoesApp = (InformacoesApp) getApplicationContext();
        tvPerfilNomeUsuario = findViewById(R.id.tvPerfilNomeUsuario);
        tvPerfilAlturaUsuario = findViewById(R.id.tvPerfilAlturaUsuario);
        tvPerfilPesoUsuario = findViewById(R.id.tvPerfilPesoUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = informacoesApp.getUsuarioLogado();
        tvPerfilNomeUsuario.setText("Bem vindo - "+usuario.getNomeUsuario());
        tvPerfilAlturaUsuario.setText("Altura: "+usuario.getAltura()+"cm");
        tvPerfilPesoUsuario.setText("Peso: "+(usuario.getPeso())+"kg");

        pcPerfilExercicios = findViewById(R.id.pcPerfilExercicios);
        pcPerfilTreinos = findViewById(R.id.pcPerfilTreinos);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                listaExercicios = ccont.listaExercicios();
                listaTreinos = ccont.listaTreinos();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        graficoTreinos(listaTreinos);
                        graficoExercicio(listaExercicios);
                    }
                });
            }
        });
        thread.start();
    }
    public void graficoTreinos(ArrayList<Treino> listaTreinos){
        ArrayList<PieEntry> entradas=new ArrayList<>();
        float peito=0,ombro=0,costas=0,braco=0,perna=0;
        for (int x = 0 ; x<listaTreinos.size(); x++){
            Treino treino= listaTreinos.get(x);
            if(treino.getTipo() == 1){
                peito++;
            } else if(treino.getTipo() == 2){
                ombro++;
            } else if(treino.getTipo() == 3){
                braco++;
            } else if(treino.getTipo() == 4){
                costas++;
            } else if(treino.getTipo() == 5){
                perna++;
            }
        }
        PieEntry entradaPeito = new PieEntry(peito,"Peito");
        entradas.add(entradaPeito);

        PieEntry entradaOmbro = new PieEntry(ombro,"Ombro");
        entradas.add(entradaOmbro);

        PieEntry entradaBraco = new PieEntry(braco,"Braco");
        entradas.add(entradaBraco);

        PieEntry entradaCostas = new PieEntry(costas,"Costas");
        entradas.add(entradaCostas);

        PieEntry entradaPerna = new PieEntry(perna,"Perna");
        entradas.add(entradaPerna);

        PieDataSet dadosTorta = new PieDataSet(entradas,"");
        dadosTorta.setValueTextSize(12f);
        dadosTorta.setColors(ColorTemplate.JOYFUL_COLORS);
        pcPerfilTreinos.setData(new PieData(dadosTorta));
        pcPerfilTreinos.animateXY(2000,2000);
        pcPerfilTreinos.getDescription().setEnabled(false);
        pcPerfilTreinos.setRotationEnabled(false);
        PieData pieData =new PieData(dadosTorta);
        pcPerfilTreinos.setData(pieData);
    }

    public void graficoExercicio(ArrayList<Exercicio> listaExercicios){
        ArrayList<PieEntry> entradas=new ArrayList<>();
        float peito=0,ombro=0,costas=0,braco=0,perna=0;
        for (int x = 0 ; x<listaExercicios.size(); x++){
            Exercicio exercicio= listaExercicios.get(x);
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
        PieEntry entradaPeito = new PieEntry(peito,"Peito");
        entradas.add(entradaPeito);

        PieEntry entradaOmbro = new PieEntry(ombro,"Ombro");
        entradas.add(entradaOmbro);

        PieEntry entradaBraco = new PieEntry(braco,"Braco");
        entradas.add(entradaBraco);

        PieEntry entradaCostas = new PieEntry(costas,"Costas");
        entradas.add(entradaCostas);

        PieEntry entradaPerna = new PieEntry(perna,"Perna");
        entradas.add(entradaPerna);

        PieDataSet dadosTorta = new PieDataSet(entradas,"");
        dadosTorta.setValueTextSize(12f);
        dadosTorta.setColors(ColorTemplate.JOYFUL_COLORS);
        pcPerfilExercicios.setData(new PieData(dadosTorta));
        pcPerfilExercicios.animateXY(2000,2000);
        pcPerfilExercicios.getDescription().setEnabled(false);
        pcPerfilExercicios.setRotationEnabled(false);
        PieData pieData =new PieData(dadosTorta);
        pcPerfilExercicios.setData(pieData);
    }

}

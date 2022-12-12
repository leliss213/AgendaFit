package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bMainEntrar, bMainCancelar;
    EditText etMainUsuario, etMainSenha;
    InformacoesApp informacoesApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        informacoesApp = (InformacoesApp) getApplicationContext();
        bMainEntrar = findViewById(R.id.bMainEntrar);
        bMainCancelar = findViewById(R.id.bMainCancelar);

        etMainUsuario = findViewById(R.id.etMainUsuario);
        etMainSenha = findViewById(R.id.etMainSenha);

        bMainEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etMainUsuario.getText().toString().isEmpty()){
                    if(!etMainSenha.getText().toString().isEmpty()){

                        //String usuario = etMainUsuario.getText().toString();
                        //String senha = etMainSenha.getText().toString();
                        Intent it = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(it);
                    }else{
                        Toast.makeText(informacoesApp, "Insira a Senha!", Toast.LENGTH_SHORT).show();
                        etMainSenha.requestFocus();
                    }
                }else{
                    Toast.makeText(informacoesApp, "Insira o Usuário!", Toast.LENGTH_SHORT).show();
                    etMainUsuario.requestFocus();
                }
            }
        });

        bMainCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });
    }

    public void limpaCampos(){
        etMainUsuario.setText("");
        etMainSenha.setText("");
    }

}
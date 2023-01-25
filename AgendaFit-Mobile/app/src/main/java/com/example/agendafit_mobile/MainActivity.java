package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import controller.ConexaoController;
import controller.InformacoesApp;
import modelDominio.Usuario;

public class MainActivity extends AppCompatActivity {
    Button bMainEntrar, bMainCancelar;
    EditText etMainUsuario, etMainSenha;
    InformacoesApp informacoesApp;
    Boolean resultadoConexao;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        informacoesApp = (InformacoesApp) getApplicationContext();
        bMainEntrar = findViewById(R.id.bMainEntrar);
        bMainCancelar = findViewById(R.id.bMainCancelar);

        etMainUsuario = findViewById(R.id.etMainUsuario);
        etMainSenha = findViewById(R.id.etMainSenha);


        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ConexaoController ccont = new ConexaoController(informacoesApp);
                resultadoConexao = ccont.criaConexao();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultadoConexao == true) {
                            Toast.makeText(informacoesApp, "SEXOOOOOO", Toast.LENGTH_SHORT).show();

                        } else if (resultadoConexao == false) {
                            Toast.makeText(informacoesApp, "ANTI SEXOOOOOOO", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        thread.start();


        bMainEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMainUsuario.getText().toString().isEmpty()) {
                    if (!etMainSenha.getText().toString().isEmpty()) {

                        String nomeUsuario = etMainUsuario.getText().toString();
                        final String senha = etMainSenha.getText().toString();

                        usuario = new Usuario(nomeUsuario, senha);

                        Thread thread1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ConexaoController ccont = new ConexaoController(informacoesApp);
                                usuario = ccont.login(usuario);

                                if (usuario != null) {
                                    informacoesApp.setUsuarioLogado(usuario);
                                    Intent it = new Intent(MainActivity.this, MenuActivity.class);
                                    startActivity(it);

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(informacoesApp, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        thread1.start();

                    } else {
                        Toast.makeText(informacoesApp, "Insira a Senha!", Toast.LENGTH_SHORT).show();
                        etMainSenha.requestFocus();
                    }
                } else {
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

    public void limpaCampos() {
        etMainUsuario.setText("");
        etMainSenha.setText("");
    }

}
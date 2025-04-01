package com.example.agendafit_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import controller.AuthService;
import controller.ConexaoController;
import controller.DTO.LoginRequestDTO;
import controller.DTO.LoginResponseDTO;
import controller.InformacoesApp;
import controller.RetrofitClient;
import modelDominio.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Hash;

public class MainActivity extends AppCompatActivity {
    private Button bMainEntrar;
    private EditText etMainUsuario, etMainSenha;
    private InformacoesApp informacoesApp;
    private Usuario usuario;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        informacoesApp = (InformacoesApp) getApplicationContext();
        bMainEntrar = findViewById(R.id.bMainEntrar);
        etMainUsuario = findViewById(R.id.etMainUsuario);
        etMainSenha = findViewById(R.id.etMainSenha);

        authService = RetrofitClient.getInstance().create(AuthService.class);

        bMainEntrar.setOnClickListener(v -> performLogin());
    }

    public void performLogin(){
        String user = etMainUsuario.getText().toString().trim();
        String password = etMainSenha.getText().toString().trim();

        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequestDTO requestDTO = new LoginRequestDTO(user, password);
        Call<LoginResponseDTO> call = authService.login(requestDTO);

        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Falha no response", Toast.LENGTH_SHORT).show();
                }

                UUID tokenUser = response.body().getToken();
                Log.d("Login", "Token recebido: " + tokenUser);

                Intent it = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(it);
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na conexao", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void limpaCampos() {
        etMainUsuario.setText("");
        etMainSenha.setText("");
    }

}
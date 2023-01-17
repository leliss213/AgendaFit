package controller;

import com.example.agendafit_mobile.InformacoesApp;

import java.io.IOException;

import modelDominio.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexaoController {
    InformacoesApp informacoesApp;

    public ConexaoController(InformacoesApp informacoesApp) {
        this.informacoesApp = informacoesApp;
    }

    public boolean criaConexao() {
        boolean resultado;
        try {
            informacoesApp.socket = new Socket("10.0.2.2", 1234);
            informacoesApp.out = new ObjectOutputStream(informacoesApp.socket.getOutputStream());
            informacoesApp.in = new ObjectInputStream(informacoesApp.socket.getInputStream());

            resultado = true;

        } catch (IOException ioe) {
            ioe.printStackTrace();
            resultado = false;
        }
        return resultado;
    }

    public Usuario login(Usuario user) {
        Usuario userLogado = null;
        String msgRecebida;
        try {
            informacoesApp.out.writeObject("efetuarLogin");
            msgRecebida = (String) informacoesApp.in.readObject();

            if (msgRecebida.equals("ok")) {
                informacoesApp.out.writeObject(user);
                userLogado = (Usuario) informacoesApp.in.readObject();
                return userLogado;
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException classe) {
            classe.printStackTrace();
            return null;
        }
        return userLogado;
    }
}
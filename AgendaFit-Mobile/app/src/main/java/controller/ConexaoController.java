package controller;

import com.example.agendafit_mobile.InformacoesApp;

import java.io.IOException;

import modelDominio.Exercicio;
import modelDominio.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

    public String cadastraExercicio(Exercicio exercicio){
        String msgRecebida ="";
        try{
            informacoesApp.out.writeObject("cadastroExercicio");
            msgRecebida = (String) informacoesApp.in.readObject();
            if(msgRecebida.equals("ok")){
                informacoesApp.out.writeObject(exercicio);
                msgRecebida = (String) informacoesApp.in.readObject();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (ClassNotFoundException classe){
            classe.printStackTrace();
        }
        return msgRecebida;
    }

    public ArrayList<Exercicio> listaExercicios(){
        ArrayList<Exercicio> listaExercicios;
        try {
            informacoesApp.out.writeObject("listaExercicios");
            listaExercicios = (ArrayList<Exercicio>) informacoesApp.in.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            listaExercicios = null;
        } catch (ClassNotFoundException classe) {
            classe.printStackTrace();
            listaExercicios = null;
        }
        return listaExercicios;
    }
}

package controller;

import java.io.IOException;

import modelDominio.Exercicio;
import modelDominio.Treino;
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
            informacoesApp.socket = new Socket("10.0.2.2", 12345);
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

    public ArrayList<Treino> listaTreinos(){
        ArrayList<Treino> listaTreinos;
        try {
            informacoesApp.out.writeObject("listaTreinos");
            listaTreinos = (ArrayList<Treino>) informacoesApp.in.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            listaTreinos = null;
        } catch (ClassNotFoundException classe) {
            classe.printStackTrace();
            listaTreinos = null;
        }
        return listaTreinos;
    }

    public String deletaExercicio(Exercicio exercicio){
        String msgRecebida="";
        try{
            informacoesApp.out.writeObject("deletarExercicio");
            msgRecebida = (String) informacoesApp.in.readObject();
            if(msgRecebida.equals("ok")){
                informacoesApp.out.writeObject(exercicio);
                msgRecebida = (String) informacoesApp.in.readObject();
            }
            return msgRecebida;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return msgRecebida;
        }
    }

    public String cadastroTreino(Treino treino){
        String msgRecebida ="";
        try{
            informacoesApp.out.writeObject("cadastroTreino");
            msgRecebida = (String) informacoesApp.in.readObject();
            if(msgRecebida.equals("ok")){
                informacoesApp.out.writeObject(treino);
                msgRecebida = (String) informacoesApp.in.readObject();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch (ClassNotFoundException classe){
            classe.printStackTrace();
        }
        return msgRecebida;
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

}

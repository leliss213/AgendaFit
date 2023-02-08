package controller;

import java.io.IOException;

import modelDominio.Exercicio;
import modelDominio.Usuario;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import modelDominio.Treino;

public class ConexaoController {
    InformacoesApp informacoesApp;

    public ConexaoController(InformacoesApp infoApp) {
        informacoesApp = infoApp;
    }
    //conexão é apenas para o mobile
    public boolean criaConexao() {
        boolean resultado;
        try {
            informacoesApp.socket = new Socket("127.0.0.1", 12345);
            informacoesApp.out = new ObjectOutputStream(informacoesApp.socket.getOutputStream());
            informacoesApp.in = new ObjectInputStream(informacoesApp.socket.getInputStream());

            resultado = true;

        } catch (IOException ioe) {
            ioe.printStackTrace();
            resultado = false;
        }
        return resultado;
    }

    public Usuario login(Usuario user) throws IOException, ClassNotFoundException {
        Usuario userLogado = null;
        String msgRecebida;
        informacoesApp.out.writeObject("efetuarLogin");
        msgRecebida = (String) informacoesApp.in.readObject();
        if (msgRecebida.equals("ok")) {
            informacoesApp.out.writeObject(user);
            userLogado = (Usuario) informacoesApp.in.readObject();
            return userLogado;
        }
        return userLogado;
    }

    public String cadastraExercicio(Exercicio exercicio) throws IOException, ClassNotFoundException{
        String msgRecebida ="";
        informacoesApp.out.writeObject("cadastroExercicio");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(exercicio);
            msgRecebida = (String) informacoesApp.in.readObject();
        }
        return msgRecebida;
    }

    public ArrayList<Exercicio> listaExercicios() throws IOException, ClassNotFoundException{
        ArrayList<Exercicio> listaExercicios;
        informacoesApp.out.writeObject("listaExercicios");
        listaExercicios = (ArrayList<Exercicio>) informacoesApp.in.readObject();
        return listaExercicios;
    }

    public String deletaExercicio(Exercicio exercicio) throws IOException, ClassNotFoundException{
        String msgRecebida="";
        informacoesApp.out.writeObject("deletarExercicio");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(exercicio);
        }
        return msgRecebida;
    }
    
    public String cadastraUsuario(Usuario usuario) throws IOException, ClassNotFoundException{
        String msgRecebida ="";
        informacoesApp.out.writeObject("cadastroUsuario");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(usuario);
            msgRecebida = (String) informacoesApp.in.readObject();
        }
        return msgRecebida;
    }

    public ArrayList<Usuario> listaUsuario() throws IOException, ClassNotFoundException{
        ArrayList<Usuario> listaUsuarios;
        informacoesApp.out.writeObject("listaUsuarios");
        listaUsuarios = (ArrayList<Usuario>) informacoesApp.in.readObject();
        return listaUsuarios;
    }

    public String deletaUsuario(Usuario usuario) throws IOException, ClassNotFoundException{
        String msgRecebida="";
        informacoesApp.out.writeObject("deletarUsuario");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(usuario);
        }
        return msgRecebida;
    }
    
    public String cadastraTreino(Treino treino) throws IOException, ClassNotFoundException{
        String msgRecebida ="";
        informacoesApp.out.writeObject("cadastroTreino");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(treino);
            msgRecebida = (String) informacoesApp.in.readObject();
        }
        return msgRecebida;
    }

    public ArrayList<Treino> listaTreinos() throws IOException, ClassNotFoundException{
        ArrayList<Treino> listaTreinos;
        informacoesApp.out.writeObject("listaTreinos");
        listaTreinos = (ArrayList<Treino>) informacoesApp.in.readObject();
        return listaTreinos;
    }

    public String deletaTreino(Treino treino) throws IOException, ClassNotFoundException{
        String msgRecebida="";
        informacoesApp.out.writeObject("deletarTreino");
        msgRecebida = (String) informacoesApp.in.readObject();
        if(msgRecebida.equals("ok")){
            informacoesApp.out.writeObject(treino);
        }
        return msgRecebida;
    }
}
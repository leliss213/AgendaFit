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

    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;

    //conexão é apenas para o mobile
    public boolean criaConexao() {
        boolean resultado;
        try {
            socket = new Socket("127.0.0.1", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

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
        out.writeObject("efetuarLogin");
        msgRecebida = (String) in.readObject();
        if (msgRecebida.equals("ok")) {
            out.writeObject(user);
            userLogado = (Usuario) in.readObject();
            return userLogado;
        }
        return userLogado;
    }

    public String cadastraExercicio(Exercicio exercicio) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("cadastroExercicio");
        out.writeObject(exercicio);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public String alteraExercicio(Exercicio exercicio) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("alteraExercicio");
        out.writeObject(exercicio);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public ArrayList<Exercicio> listaExercicios() throws IOException, ClassNotFoundException {
        ArrayList<Exercicio> listaExercicios;
        out.writeObject("listaExercicios");
        listaExercicios = (ArrayList<Exercicio>) in.readObject();
        return listaExercicios;
    }

    public String deletaExercicio(Exercicio exercicio) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("deletarExercicio");
        out.writeObject(exercicio);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public String cadastraUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("cadastroUsuario");
        out.writeObject(usuario);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public String alteraUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("alteraUsuario");
        out.writeObject(usuario);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public ArrayList<Usuario> listaUsuario() throws IOException, ClassNotFoundException {
        ArrayList<Usuario> listaUsuarios;
        out.writeObject("listaUsuarios");
        listaUsuarios = (ArrayList<Usuario>) in.readObject();
        return listaUsuarios;
    }

    public String deletaUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("deletarUsuario");
        out.writeObject(usuario);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public String cadastraTreino(Treino treino) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("cadastroTreino");
        out.writeObject(treino);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public String alteraTreino(Treino treino) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("alteraTreino");
        out.writeObject(treino);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }

    public ArrayList<Treino> listaTreinos() throws IOException, ClassNotFoundException {
        ArrayList<Treino> listaTreinos;
        out.writeObject("listaTreinos");
        listaTreinos = (ArrayList<Treino>) in.readObject();
        return listaTreinos;
    }

    public String deletaTreino(Treino treino) throws IOException, ClassNotFoundException {
        String msgRecebida = "";
        out.writeObject("deletarTreino");
        out.writeObject(treino);
        msgRecebida = (String) in.readObject();

        return msgRecebida;
    }
}

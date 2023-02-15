/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.ExercicioDao;
import model.TreinoDao;
import model.UsuarioDao;
import modelDominio.Exercicio;
import modelDominio.Treino;
import modelDominio.Usuario;

/** guilherme
/**
 *
 * @author Leandro
 */
public class TrataClienteController extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int idUnico;
//aqui
    public TrataClienteController(ObjectInputStream in, ObjectOutputStream out, Socket s, int idUnico) {
        this.in = in;
        this.out = out;
        this.s = s;
        this.idUnico = idUnico;
    }

    @Override
    public void run() {
        String acao;
        System.out.println("Aguardando Ação");
        //ArrayList<Exercicio> listaExercicios = new ArrayList<>();
        try{
            acao = (String) in.readObject();
            while(!acao.equalsIgnoreCase("fim")) {
                System.out.println("Cliente " + idUnico + " enviou o comando: "+ acao);
                
                if(acao.equalsIgnoreCase("efetuarLogin")){
                    out.writeObject("ok");
                    Usuario usuario = (Usuario) in.readObject();
                    
                    UsuarioDao dao = new UsuarioDao();
                    Usuario userSelected = dao.efetuarLogin(usuario);
                    out.writeObject(userSelected);
                    //userSelected.equals(out);
                } else if(acao.equalsIgnoreCase("cadastroTreino")){
                    Treino treino = (Treino) in.readObject();
                    
                    TreinoDao dao = new TreinoDao();
                    if(dao.inserir(treino) == -1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }
                } else if(acao.equalsIgnoreCase("alteraTreino")){
                    Treino treino = (Treino) in.readObject();
                    
                    TreinoDao dao = new TreinoDao();
                    if(dao.alterar(treino) == -1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }
                } else if(acao.equalsIgnoreCase("listaTreinos")){
                    TreinoDao dao = new TreinoDao();
                    out.writeObject(dao.getLista());
                } else if(acao.equalsIgnoreCase("deletarTreino")){
                    Treino treino = (Treino) in.readObject();
                    TreinoDao dao = new TreinoDao();
                    
                    if(dao.excluir(treino)==-1){
                        out.writeObject("ok");
                    }else{
                        out.writeObject("nok");
                    }
                } else if(acao.equalsIgnoreCase("cadastroExercicio")){
                    Exercicio exercicio = (Exercicio) in.readObject();
                    ExercicioDao dao = new ExercicioDao();
                    if(dao.inserir(exercicio)==-1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }      
                } else if(acao.equalsIgnoreCase("alteraExercicio")){
                    Exercicio exercicio = (Exercicio) in.readObject();
                    ExercicioDao dao = new ExercicioDao();
                    if(dao.alterar(exercicio)==-1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }      
                } else if(acao.equalsIgnoreCase("listaExercicios")){
                    ExercicioDao dao = new ExercicioDao();
                    out.writeObject(dao.getLista());
                } else if(acao.equalsIgnoreCase("deletarExercicio")){
                    Exercicio exercicio = (Exercicio) in.readObject();
                    ExercicioDao dao = new ExercicioDao();
                    
                    if(dao.excluir(exercicio)==-1){
                        out.writeObject("ok");
                    }else{
                        out.writeObject("nok");
                    }
                } else if(acao.equalsIgnoreCase("cadastroUsuario")){
                    Usuario usuario = (Usuario) in.readObject();
                    UsuarioDao dao = new UsuarioDao();
                    if(dao.inserir(usuario)==-1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }      
                } else if(acao.equalsIgnoreCase("alteraUsuario")){
                    Usuario usuario = (Usuario) in.readObject();
                    UsuarioDao dao = new UsuarioDao();
                    if(dao.alterar(usuario)==-1){
                        out.writeObject("ok");
                    } else{
                        out.writeObject("nok");
                    }      
                } else if(acao.equalsIgnoreCase("listaUsuarios")){                    
                    UsuarioDao dao = new UsuarioDao();
                    out.writeObject(dao.getLista());
                } else if(acao.equalsIgnoreCase("deletarUsuario")){
                    Usuario usuario = (Usuario) in.readObject();
                    UsuarioDao dao = new UsuarioDao();
                    
                    if(dao.excluir(usuario)==-1){
                        out.writeObject("ok");
                    }else{
                        out.writeObject("nok");
                    }
                } else if(acao.equalsIgnoreCase("listaExerciciosFiltro")){
                    out.writeObject("ok");
                    ExercicioDao dao = new ExercicioDao();
                    int codTreino = (int) in.readObject();                    
                    out.writeObject(dao.getFiltroLista(codTreino));
                }
                
                acao = (String) in.readObject();
            }  
            
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            // Fechando recursos ocupados pelo cliente.
            System.out.println("Cliente " + idUnico + " finalizou a conexão");
            this.in.close();
            this.out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}

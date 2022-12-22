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
import model.UsuarioDao;
import modelDominio.Usuario;

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

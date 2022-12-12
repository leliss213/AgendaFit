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

/**
 *
 * @author Leandro
 */
public class TrataClienteController extends Thread{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int idUnico;

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
        
        //try{
            
        //}catch(IOException | ClassNotFoundException e){

        //}
        
    }
}

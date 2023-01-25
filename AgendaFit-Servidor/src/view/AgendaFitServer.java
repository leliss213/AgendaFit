/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.TrataClienteController;
import factory.Conector;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

/**
 *
 * @author Leandro
 */
public class AgendaFitServer {
    public static void main(String[] args) {
        Connection con;
        con = Conector.getConnection();

        try {
            // iniciando servidor na porta 1234
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor inicializado. Aguardando conexão...");
            ConectaServidor s1 = new ConectaServidor(servidor, con);
            s1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ConectaServidor extends Thread {

    private ServerSocket servidor;
    private int idUnico; // Nº para identificar o cliente que está conectando...
    private Connection con;

    public ConectaServidor(ServerSocket servidor, Connection con) {
        this.servidor = servidor;
        this.con = con;
    }

    public void run() {
        try {
            while (true) {
                Socket cliente = this.servidor.accept();
                System.out.println("Um novo cliente conectou : " + cliente);

                ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
                idUnico++; 
                System.out.println("Iniciando uma nova Thread para o Cliente " + idUnico);
                TrataClienteController tratacliente = new TrataClienteController(in, out, cliente, idUnico);
                tratacliente.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

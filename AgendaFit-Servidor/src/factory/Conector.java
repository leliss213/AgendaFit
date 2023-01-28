/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Leandro
 */
public class Conector {
    
    private static Connection con;
    
    public static Connection getConnection(){
        try{
          // String que armazena a url de conexao com o banco
            String url = "jdbc:mysql://localhost:3306/"; // Caminho do banco e porta
            String banco = "academia";                  // Nome da base
            String usuario = "root";                     // Usuário para acesso
            String senha = "";                           // Senha para acesso

            con = DriverManager.getConnection(url + banco, usuario, senha);
            return con;  
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Verifique se o MySL (XAMPP) está ativado!");
            e.printStackTrace();
            return null;
        }
    }
    
}

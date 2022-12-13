/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import factory.Conector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelDominio.Usuario;

/**
 *
 * @author Leandro
 */
public class UsuarioDao {
    private Connection con;
    
    public UsuarioDao() {
        con = Conector.getConnection();
    }
    
    public Usuario efetuarLogin(Usuario user) {
        PreparedStatement stmt = null; // usado para rodar SQL
        Usuario userselecionado = null;
        
        try{
            String sql = "select * from usuario" + " where login = ? and senha = ? ";
            con.prepareStatement(sql);
            
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getSenha());
            
            ResultSet res = stmt.executeQuery();
            
            while(res.next()){
                user = new Usuario(res.getInt("codUsuario"),
                            res.getString("nomeUsuario"),
                            res.getString("login"),
                            res.getString("senha"),
                            res.getString("email"),
                            res.getFloat("peso"),
                            res.getFloat("altura"));
            }
            res.close();
            stmt.close();
            con.close();
            return user;
            
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return null;
        } 
    }
}

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
import java.util.ArrayList;
import modelDominio.Exercicio;
import modelDominio.Usuario;
import utils.Util;

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
            String sql = "select * from usuario" + 
                    " where login = ? and senha = ? ";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getSenha());
            
            ResultSet res = stmt.executeQuery();
            
            while(res.next()){
                userselecionado = new Usuario(res.getInt("codUsuario"),
                            res.getString("nomeUsuario"),
                            res.getString("login"),
                            res.getString("senha"),
                            res.getFloat("altura"),
                            res.getFloat("peso"),
                            res.getString("email"));
            }
            res.close();
            stmt.close();
            con.close();
            return userselecionado;
            
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return null;
        } 
    }
    
    public int inserir(Usuario usuario){
        PreparedStatement stmt = null;
        
        try{
            try{
                con.setAutoCommit(false);
                String sql = "insert into usuarios (nomeUsuario , login, senha, email, peso, altura) values (?,?,?,?,?,?);";
                stmt = con.prepareStatement(sql);
                
                String password = Util.encryptPassword(usuario.getSenha());
                
                stmt.setString(1, usuario.getNomeUsuario());
                stmt.setString(2, usuario.getLogin());
                stmt.setString(3, password);
                stmt.setString(4, usuario.getEmail());
                stmt.setFloat(5, usuario.getPeso());
                stmt.setFloat(6, usuario.getAltura());
                
                stmt.execute();
                stmt.close();
                return -1;
            }catch(SQLException e){
                try {
                    con.rollback(); // cancelando a transação 
                    return e.getErrorCode(); // devolvendo o erro
                } catch (SQLException ex) {
                    return ex.getErrorCode();
                }
            }
            
        }finally{
            try {
                stmt.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                return e.getErrorCode();
            }
        }
    }
    
    public ArrayList<Usuario> getLista(){
        Statement stmt = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from exercicios order by tipo");
            
            while (res.next()){
                Usuario usuario = new Usuario(res.getInt("codUsuario"),
                            res.getString("nomeUsuario"),
                            res.getString("login"),
                            res.getString("senha"),
                            res.getFloat("altura"),
                            res.getFloat("peso"),
                            res.getString("email"));
                
                listaUsuarios.add(usuario);
            }
            return listaUsuarios;
            
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + " - "
                    + e.getMessage());
            return null;   
        }
    }
    
    public int excluir(Usuario usuario){
        PreparedStatement stmt = null;
        try{
            try{
                con.setAutoCommit(false);
                String sql = "delete from exercicios where codUsuario = ?";
                stmt = con.prepareStatement(sql);
                
                stmt.setInt(1, usuario.getCodUsuario());
                stmt.execute();
                con.commit();
                return -1;
            }catch(SQLException e){
                try {
                    con.rollback(); // cancelando a transação 
                    return e.getErrorCode(); // devolvendo o erro
                } catch (SQLException ex) {
                    return ex.getErrorCode();
                }
            } 
        } finally{
            try {
                stmt.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                return e.getErrorCode();
            }
        }
    }
}

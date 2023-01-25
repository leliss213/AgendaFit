/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import modelDominio.Exercicio;

/**
 *
 * @author Leandro
 */
public class ExercicioDao {
    private Connection con;
    
    public ExercicioDao() {
        con = Conector.getConnection();
    }
    
    public int inserir(Exercicio exercicio){
        PreparedStatement stmt = null;
        
        try{
            try{
                con.setAutoCommit(false);
                String sql = "insert into exercicios (nomeExercicio, tipo) values (?,?);";
                stmt = con.prepareStatement(sql);
                
                stmt.setString(1, exercicio.getNomeExercicio());
                stmt.setInt(2, exercicio.getTipo());
                
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
    
    public ArrayList<Exercicio> getLista(){
        Statement stmt = null;
        ArrayList<Exercicio> listaExercicios = new ArrayList<>();
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from exercicios order by tipo");
            
            while (res.next()){
                Exercicio exercicio = new Exercicio(res.getInt("codExercicio"),res.getString("nomeExercicio"),res.getInt("tipo"));
                
                listaExercicios.add(exercicio);
            }
            return listaExercicios;
            
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + " - "
                    + e.getMessage());
            return null;   
        }
    }
    
    public int excluir(Exercicio exercicio){
        PreparedStatement stmt = null;
        try{
            try{
                con.setAutoCommit(false);
                String sql = "delete from exercicios where codExercicio = ?";
                stmt = con.prepareStatement(sql);
                
                stmt.setInt(1, exercicio.getCodExercicio());
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

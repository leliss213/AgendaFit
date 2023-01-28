/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.jdi.connect.Connector;
import factory.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelDominio.Exercicio;
import modelDominio.Treino;

/**
 *
 * @author Leandro
 */
public class TreinoDao {
    private Connection con;

    public TreinoDao() {
        con = Conector.getConnection();
    }
    
    public int inserir(Treino treino){
        PreparedStatement stmt = null;
        try {
            try {
                con.setAutoCommit(false);
                String sql = "insert into treinos (nomeTreino, descricao, data, hora, tipoTreino) values (?,?,?,?,?);";
                stmt = con.prepareStatement(sql);
                
                stmt.setString(1, treino.getNomeTreino());
                stmt.setString(2, treino.getDescricao());
                stmt.setString(3, treino.getData());
                stmt.setFloat(4, treino.getHora());
                stmt.setInt(5, treino.getTipo());
                
                stmt.execute();
                stmt.close();
                return -1;
            } catch(SQLException e){
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
    
    public ArrayList<Treino> getLista(){
        Statement stmt = null;
        ArrayList<Treino> listaTreinos = new ArrayList<>();
        try{
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from treinos order by tipo");
            
            while (res.next()){
                Treino treino = new Treino(res.getInt("codTreino"),res.getString("nomeTreino"),res.getString("descricao"),res.getString("data"),res.getFloat("hora"), res.getInt("tipo"));
                
                listaTreinos.add(treino);
            }
            return listaTreinos;
            
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + " - "
                    + e.getMessage());
            return null;   
        }
    }
    
    public int excluir(Treino treino){
        PreparedStatement stmt = null;
        try{
            try{
                con.setAutoCommit(false);
                String sql = "delete from treinos where codTreino = ?";
                stmt = con.prepareStatement(sql);
                
                stmt.setInt(1, treino.getCodTreino());
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

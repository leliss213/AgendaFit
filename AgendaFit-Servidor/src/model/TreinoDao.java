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
import java.sql.SQLException;
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
                String sql = "insert into treinos (nomeTreino, descricao, data, hora, exercicio_codExercicio, tipoTreino) values (?,?,?,?,?,?);";
                stmt = con.prepareStatement(sql);
                
                stmt.setString(1, treino.getNomeTreino());
                stmt.setString(2, treino.getDescricao());
                stmt.setString(3, treino.getData());
                stmt.setFloat(4, treino.getHora());
                stmt.setInt(5, treino.getExercicio().getCodExercicio());
                stmt.setInt(6, treino.getTipo());
                
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


}

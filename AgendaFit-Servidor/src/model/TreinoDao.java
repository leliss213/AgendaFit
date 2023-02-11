
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

    public int inserir(Treino treino) {
        PreparedStatement stmt = null;
        try {
            try {
                con.setAutoCommit(false);
                //primeiro insere o treino 
                String sql = "insert into treinos (nomeTreino, descricao, data, hora, tipoTreino) values (?,?,?,?,?);";
                stmt = con.prepareStatement(sql);

                stmt.setString(1, treino.getNomeTreino());
                stmt.setString(2, treino.getDescricao());
                stmt.setString(3, treino.getData());
                stmt.setFloat(4, treino.getHora());
                stmt.setInt(5, treino.getTipo());
                //executa o stmt e faz o commit
                stmt.execute();
                con.commit();
                //cria o stmt da tabela intermediária entre treino e exercício
                PreparedStatement stmt2 = null;
                //cria um select e armazena o último ID de treinos que foi adicionado
                PreparedStatement stmt3 = con.prepareStatement("SELECT last_insert_id() FROM treinos");
                ResultSet rs = null;
                rs = stmt3.executeQuery();
                rs.next();
                //lastID armazena o ID arrecem inserido do treino
                int lastID = Integer.valueOf(rs.getString(1));
                //for percorrendo os exercicios e inserindo na tabela intermediária
                for (Exercicio exercicio : treino.getExercicio()) {
                    String sql2 = "insert into TreinoExercicio(treinos_codTreino,exercicios_codExercicio) values (?,?);";

                    stmt2 = con.prepareStatement(sql2);

                    stmt2.setInt(1, lastID);
                    stmt2.setInt(2, exercicio.getCodExercicio());
                    stmt2.execute();
                }
                con.commit();
                stmt.close();
                stmt2.close();

                return -1;
            } catch (SQLException e) {
                try {
                    con.rollback(); // cancelando a transação 
                    return e.getErrorCode(); // devolvendo o erro
                } catch (SQLException ex) {
                    return ex.getErrorCode();
                }
            }
        } finally {
            try {
                stmt.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                return e.getErrorCode();
            }
        }
    }
    
    public int alterar(Treino treino) {
        PreparedStatement stmt = null;
        try {
            try {
                con.setAutoCommit(false);
                //primeiro insere o treino 
                String sql = "UPDATE treinos SET nomeTreino = ?, descricao = ?, data = ?, hora = ?, tipoTreino= ? WHERE codTreino = ?;";
                stmt = con.prepareStatement(sql);

                stmt.setString(1, treino.getNomeTreino());
                stmt.setString(2, treino.getDescricao());
                stmt.setString(3, treino.getData());
                stmt.setFloat(4, treino.getHora());
                stmt.setInt(5, treino.getTipo());
                stmt.setInt(6, treino.getCodTreino());
                //executa o stmt e faz o commit
                stmt.executeUpdate();
                con.commit();
                //cria o stmt da tabela intermediária entre treino e exercício
                PreparedStatement stmt2 = null;
                //cria um delete para remover todos os exercicios para aquele treino 
                PreparedStatement stmt3 = con.prepareStatement("DELETE FROM TreinoExercicio WHERE treinos_codTreino = ?");
                stmt3.setInt(1, treino.getCodTreino());
                stmt3.execute();
                stmt3.close();
                //for percorrendo os exercicios e inserindo na tabela intermediária
                for (Exercicio exercicio : treino.getExercicio()) {
                    String sql2 = "insert into TreinoExercicio(treinos_codTreino,exercicios_codExercicio) values (?,?);";

                    stmt2 = con.prepareStatement(sql2);

                    stmt2.setInt(1, treino.getCodTreino());
                    stmt2.setInt(2, exercicio.getCodExercicio());
                    stmt2.executeUpdate();
                }
                con.commit();
                stmt.close();
                stmt2.close();

                return -1;
            } catch (SQLException e) {
                try {
                    con.rollback(); // cancelando a transação 
                    return e.getErrorCode(); // devolvendo o erro
                } catch (SQLException ex) {
                    return ex.getErrorCode();
                }
            }
        } finally {
            try {
                stmt.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                return e.getErrorCode();
            }
        }
    }

    public ArrayList<Treino> getLista() {
        Statement stmt = null;
        ArrayList<Treino> listaTreinos = new ArrayList<>();
        try {
            stmt = con.createStatement();
            //select ordenando a lista pelo tipo do treino
            ResultSet res = stmt.executeQuery("select * from treinos order by tipoTreino");
            //while percorrendo os treinos e adicionando na lista para retornar
            while (res.next()) {
                Treino treino = new Treino(res.getInt("codTreino"), res.getString("nomeTreino"), res.getString("descricao"), res.getString("data"), res.getFloat("hora"), res.getInt("tipoTreino"));

                listaTreinos.add(treino);
            }
            return listaTreinos;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " - "
                    + e.getMessage());
            return null;
        }
    }

    public int excluir(Treino treino) {
        //Statement stmt = null;
        Statement stmt2 = null;
        try {
            try {

                int codTreino = treino.getCodTreino();

                stmt2 = con.createStatement();

                String query = String.format("delete from treinoexercicio where treinoexercicio.treinos_codTreino = %d ", codTreino);
                stmt2.executeUpdate(query);
                String query2 = String.format("delete from treinos where codTreino = %d", codTreino);
                stmt2.executeUpdate(query2);
            } catch (SQLException e) {
                try {
                    con.rollback(); // cancelando a transação 
                    return e.getErrorCode(); // devolvendo o erro
                } catch (SQLException ex) {
                    return ex.getErrorCode();
                }
            }
        } finally {
            try {
                stmt2.close();
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                return e.getErrorCode();
            }
        }
        return -1;
    }

}

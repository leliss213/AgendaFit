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
import modelDominio.Treino;
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

        try {
            String sql = "select * from usuario where login = ? and senha = ?";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getSenha());

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
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

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return null;
        }
    }

    public int inserir(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            try {
                con.setAutoCommit(false);
                String sql = "insert into usuario (nomeUsuario, login, senha, email, peso, altura) values (?,?,?,?,?,?);";
                stmt = con.prepareStatement(sql);

                stmt.setString(1, usuario.getNomeUsuario());
                stmt.setString(2, usuario.getLogin());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getEmail());
                stmt.setFloat(5, usuario.getPeso());
                stmt.setFloat(6, usuario.getAltura());

                //executa o stmt e faz o commit
                stmt.execute();
                con.commit();
                //cria o stmt da tabela intermediária entre treino e exercício
                PreparedStatement stmt2 = null;
                //cria um select e armazena o último ID de treinos que foi adicionado
                PreparedStatement stmt3 = con.prepareStatement("SELECT last_insert_id() FROM usuario");
                ResultSet rs = null;
                rs = stmt3.executeQuery();
                rs.next();
                //lastID armazena o ID arrecem inserido do treino
                int lastID = Integer.valueOf(rs.getString(1));
                //for percorrendo os exercicios e inserindo na tabela intermediária
                for (Treino treino : usuario.getTreinos()) {
                    String sql2 = "insert into TreinoUsuario(usuarios_codUsuario,treinos_codTreino) values (?,?);";

                    stmt2 = con.prepareStatement(sql2);

                    stmt2.setInt(1, lastID);
                    stmt2.setInt(2, treino.getCodTreino());
                    stmt2.execute();
                }
                con.commit();
                stmt.close();
                stmt2.close();

                return -1;
            } catch (SQLException e) {
                try {
                    System.out.println(e.getMessage());
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

    public int alterar(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            try {
                con.setAutoCommit(false);
                String sql = "UPDATE usuario SET nomeUsuario = ?, login = ?, senha = ?, email = ?, peso = ?, altura = ? WHERE codUsuario = ?;";
                stmt = con.prepareStatement(sql);

                stmt.setString(1, usuario.getNomeUsuario());
                stmt.setString(2, usuario.getLogin());
                stmt.setString(3, usuario.getSenha());
                stmt.setString(4, usuario.getEmail());
                stmt.setFloat(5, usuario.getPeso());
                stmt.setFloat(6, usuario.getAltura());
                stmt.setFloat(7, usuario.getCodUsuario());

                //executa o stmt e faz o commit
                stmt.executeUpdate();
                con.commit();
                //cria o stmt da tabela intermediária entre treino e usuario
                PreparedStatement stmt2 = null;
                //cria um delete para remover todos os exercicios para aquele treino 
                PreparedStatement stmt3 = con.prepareStatement("DELETE FROM TreinoUsuario WHERE usuarios_codUsuario = ?");
                stmt3.setInt(1, usuario.getCodUsuario());
                stmt3.executeUpdate();
                stmt3.close();
                //for percorrendo os exercicios e inserindo na tabela intermediária
                for (Treino treino : usuario.getTreinos()) {
                    String sql2 = "insert into TreinoUsuario(usuarios_codUsuario,treinos_codTreino) values (?,?);";

                    stmt2 = con.prepareStatement(sql2);

                    stmt2.setInt(1, usuario.getCodUsuario());
                    stmt2.setInt(2, treino.getCodTreino());
                    stmt2.execute();
                }
                con.commit();
                stmt.close();
                if(stmt2 != null) {
                    stmt2.close();
                }

                return -1;
            } catch (SQLException e) {
                try {
                    System.out.println(e.getMessage());
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

    public ArrayList<Usuario> getLista() {
        Statement stmt = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from usuario order by codUsuario desc");

            while (res.next()) {
                Usuario usuario = new Usuario(res.getInt("codUsuario"),
                        res.getString("nomeUsuario"),
                        res.getString("login"),
                        res.getString("senha"),
                        res.getFloat("altura"),
                        res.getFloat("peso"),
                        res.getString("email"));

                ArrayList<Treino> treinos = new ArrayList<>();
                PreparedStatement stmt3 = con.prepareStatement("SELECT * FROM treinousuario LEFT JOIN treinos ON treinos.codTreino = treinos_codTreino WHERE usuarios_codUsuario = ?");
                stmt3.setInt(1, usuario.getCodUsuario());
                ResultSet res3 = stmt3.executeQuery();
                while (res3.next()) {
                    Treino treino = new Treino(res3.getInt("codTreino"),
                            res3.getString("nomeTreino"),
                            res3.getString("descricao"),
                            res3.getString("data"),
                            res3.getFloat("hora"),
                            res3.getInt("tipoTreino"));

                    treinos.add(treino);
                }
                usuario.setTreinos(treinos);

                listaUsuarios.add(usuario);
            }
            return listaUsuarios;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " - "
                    + e.getMessage());
            return null;
        }
    }

    public int excluir(Usuario usuario) {
        PreparedStatement stmt = null;
        try {
            try {
                con.setAutoCommit(false);
                String sql = "delete from usuario where codUsuario = ?";
                stmt = con.prepareStatement(sql);

                stmt.setInt(1, usuario.getCodUsuario());
                stmt.execute();

                stmt = con.prepareStatement("DELETE FROM treinousuario WHERE usuarios_codUsuario = ?");

                stmt.setInt(1, usuario.getCodUsuario());
                stmt.execute();
                con.commit();
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
}

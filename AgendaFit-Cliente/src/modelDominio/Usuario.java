/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDominio;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Leandro
 */
public class Usuario implements Serializable {

    int codUsuario;
    String nomeUsuario;
    String login;
    String senha;
    float peso;
    float altura;
    String email;
    ArrayList<Treino> treinos;

    public Usuario(int codUsuario, String nomeUsuario, String login, String senha, float peso, float altura, String email) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
    }

    public Usuario(int codUsuario, String nomeUsuario, String login, String senha, float peso, float altura, String email, ArrayList<Treino> treinos) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
        this.treinos = treinos;
    }

    public Usuario(String nomeUsuario, String email, String login, String senha, float peso, float altura, ArrayList<Treino> treinos) {
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
        this.treinos = treinos;
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(ArrayList<Treino> treinos) {
        this.treinos = treinos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codUsuario=" + codUsuario + ", nomeUsuario=" + nomeUsuario + ", login=" + login + ", senha=" + senha + ", peso=" + peso + ", altura=" + altura + ", email=" + email + '}';
    }
}

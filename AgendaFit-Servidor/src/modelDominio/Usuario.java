/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDominio;

import java.io.Serializable;

/**
 *
 * @author Leandro
 */
public class Usuario implements Serializable{
    int codUsuario;
    String nomeUsuario;
    String login;
    String senha;
    float peso;
    float altura;
    String email;

    public Usuario(int codUsuario, String nomeUsuario, String login, String senha, String email, float peso, float altura) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.peso = peso;
        this.altura = altura;
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

    @Override
    public String toString() {
        return "Usuario{" + "codUsuario=" + codUsuario + ", nomeUsuario=" + nomeUsuario + ", login=" + login + ", senha=" + senha + ", peso=" + peso + ", altura=" + altura + ", email=" + email + '}';
    }
}

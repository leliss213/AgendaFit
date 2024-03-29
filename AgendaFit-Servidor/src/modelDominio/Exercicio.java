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
public class Exercicio implements Serializable{
    private int codExercicio;
    private String nomeExercicio;
    private int tipo;

    public Exercicio(String nomeExercicio, int tipo) {
        this.nomeExercicio = nomeExercicio;
        this.tipo = tipo;
    }

    public Exercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }


    public Exercicio(int codExercicio, String nomeExercicio, int tipo) {
        this.codExercicio = codExercicio;
        this.nomeExercicio = nomeExercicio;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCodExercicio() {
        return codExercicio;
    }

    public void setCodExercicio(int codExercicio) {
        this.codExercicio = codExercicio;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    @Override
    public String toString() {
        return nomeExercicio + " - " + this.tipoLiteral();
    }
    public String tipoLiteral(){
        String retorno = "";
        if(tipo == 1){
            retorno = "Peito";
        }else if(tipo == 2){
            retorno = "Ombro";
        }else if(tipo == 3){
            retorno = "Braço";
        }else if(tipo == 4){
            retorno = "Costas";
        }else if(tipo == 5){
            retorno = "Perna";
        }
        return retorno;
    }
}

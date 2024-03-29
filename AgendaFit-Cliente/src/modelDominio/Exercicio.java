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
public class Exercicio implements Serializable {

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

    public String tipoLiteral() {
        if (this.getTipo() == 1) {
            return "Peito";
        } else if (this.getTipo() == 2) {
            return "Ombro";
        } else if (this.getTipo() == 3) {
            return "Braço";
        } else if (this.getTipo() == 4) {
            return "Costas";
        } else if (this.getTipo() == 5) {
            return "Perna";
        }
        return "";
    }
}

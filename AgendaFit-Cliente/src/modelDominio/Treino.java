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
public class Treino implements Serializable{
    private int codTreino;
    private String nomeTreino;
    private String descricao;
    private String data;
    private float hora;
    private ArrayList<Exercicio> listaExercicios;
    private Exercicio exercicio;
    private int tipo;

    public Treino(int codTreino, String nomeTreino, String descricao, String data, float hora, Exercicio exercicio, int tipo) {
        this.codTreino = codTreino;
        this.nomeTreino = nomeTreino;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.exercicio = exercicio;
        this.tipo = tipo;
    }

    public Treino(String nomeTreino, String descricao, String data, float hora, ArrayList<Exercicio> listaExercicios, int tipo) {
        this.nomeTreino = nomeTreino;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.listaExercicios = listaExercicios;
        this.tipo = tipo;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCodTreino() {
        return codTreino;
    }

    public void setCodTreino(int codTreino) {
        this.codTreino = codTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getHora() {
        return hora;
    }

    public void setHora(float hora) {
        this.hora = hora;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    @Override
    public String toString() {
        return "Treino{" + "codTreino=" + codTreino + ", nomeTreino=" + nomeTreino + ", descricao=" + descricao + ", data=" + data + ", hora=" + hora + ", exercicio=" + exercicio + ", tipo=" + tipo + '}';
    }

    public ArrayList<Exercicio> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(ArrayList<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }
}

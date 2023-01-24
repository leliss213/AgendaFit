package controller;

import modelDominio.Exercicio;

public class ItemExercicio {
    private Exercicio exercicio;
    private Boolean value;

    public ItemExercicio(Exercicio exercicio, Boolean value) {
        this.exercicio = exercicio;
        this.value = false;
    }

    public ItemExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
        this.value = false;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}

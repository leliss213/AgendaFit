/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modelDominio.Treino;

/**
 *
 * @author guilh
 */
public class TabelaTreinoModel extends AbstractTableModel {

    private final ArrayList<Treino> treinos;
    private final String[] columns = {"Nome", "Data", "Hora", "Tipo"};

    public TabelaTreinoModel(ArrayList<Treino> treinos) {
        this.treinos = treinos;
    }

    @Override
    public int getRowCount() {
        return treinos.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Treino treino = treinos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return treino.getNomeTreino();
            case 1:
                return treino.getData();
            case 2:
                return treino.getHora();
            case 3:
                return treino.tipoLiteral();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Treino getRowObject(int row) {
        return treinos.get(row);
    }
}

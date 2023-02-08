/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modelDominio.Exercicio;

/**
 *
 * @author guilh
 */
public class TabelaExercicioModel extends AbstractTableModel {
    private final ArrayList<Exercicio> exercicios;
    private final String[] columns = {"Nome", "Tipo"};

    public TabelaExercicioModel(ArrayList<Exercicio> exercicios) {
      this.exercicios = exercicios;
    }

    @Override
    public int getRowCount() {
      return exercicios.size();
    }

    @Override
    public int getColumnCount() {
      return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      Exercicio exercicio = exercicios.get(rowIndex);
      switch (columnIndex) {
        case 0:
          return exercicio.getNomeExercicio();
        case 1:
          return exercicio.getTipo();
        default:
          return null;
      }
    }

    @Override
    public String getColumnName(int column) {
      return columns[column];
    }
    
    public Exercicio getRowObject(int row) {
        return exercicios.get(row);
    }
    
}

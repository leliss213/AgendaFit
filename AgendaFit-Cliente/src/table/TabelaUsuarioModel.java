/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import modelDominio.Usuario;

/**
 *
 * @author guilh
 */
public class TabelaUsuarioModel extends AbstractTableModel {

    private final ArrayList<Usuario> usuarios;
    private final String[] columns = {"Nome", "Login", "Email"};

    public TabelaUsuarioModel(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return usuario.getNomeUsuario();
            case 1:
                return usuario.getLogin();
            case 2:
                return usuario.getEmail();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Usuario getRowObject(int row) {
        return usuarios.get(row);
    }
}

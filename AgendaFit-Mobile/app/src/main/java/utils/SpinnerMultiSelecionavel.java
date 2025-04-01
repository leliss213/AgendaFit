package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;


import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;

import controller.ItemExercicio;
import modelDominio.Exercicio;

public class SpinnerMultiSelecionavel extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {
    ArrayList<ItemExercicio> items = null;
    ArrayAdapter adapter;

    // utiliza esse construtor
    public SpinnerMultiSelecionavel(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
        Log.d("Teste","Construtor");
    }

    @Override
    public void onClick(DialogInterface dialog, int idx, boolean isChecked) {
        if (items != null && idx < items.size()) {
            items.get(idx).setValue(isChecked);
            adapter.clear();
            adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "'idx' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Log.d( "Teste", "Perform");
        String[] itemNames = new String[items.size()];
        boolean[] itemSelection = new boolean[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemNames[i] = items.get(i).getExercicio().getNomeExercicio();
            itemSelection[i] = items.get(i).getValue();
        }
        builder.setMultiChoiceItems(itemNames, itemSelection, this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                // Do nothing
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(ArrayList<Exercicio> listaExercicios) {
        this.items = new ArrayList<ItemExercicio>();
        for (int x = 0; x < listaExercicios.size(); x++) {
            ItemExercicio itemExercicio = new ItemExercicio(listaExercicios.get(x));
            this.items.add(itemExercicio);
            Log.d("Teste","Gerando os ItemExercicio para - : " + items.get(x).getExercicio().getNomeExercicio());
        }
        adapter.clear();
        adapter.add("");
    }

    public ArrayList<Exercicio> getSelectedItems() {
        ArrayList<Exercicio> exerciciosSelecionados = new ArrayList<>();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getValue()) { // ou if (items.get(i).isValue()) {..}
                exerciciosSelecionados.add(items.get(i).getExercicio());
            }
        }

        return exerciciosSelecionados;
    }

    public int getSelectedSize() {
        int size = 0;
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getValue()) {
                size++;
            }
        }

        return size;
    }
    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getValue()) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(items.get(i).getExercicio().getNomeExercicio());
            }
        }
        return sb.toString();
    }

}

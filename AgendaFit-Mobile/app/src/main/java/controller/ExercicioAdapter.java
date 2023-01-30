package controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.agendafit_mobile.R;

import java.util.List;

import modelDominio.Exercicio;

public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.MyViewHolder> {
    private List<Exercicio> listaExercicio;
    private ExercicioOnClickListener exercicioOnClickListener;

    public ExercicioAdapter(List<Exercicio> listaExercicio, ExercicioOnClickListener exercicioOnClickListener) {
        this.listaExercicio = listaExercicio;
        this.exercicioOnClickListener = exercicioOnClickListener;
    }

    @Override
    public ExercicioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row_exercicio, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExercicioAdapter.MyViewHolder holder, final int position) {
        Exercicio exercicio= listaExercicio.get(position);
        String exercicioLiteral="";
        if(exercicio.getTipo() == 1){
            exercicioLiteral = "Peito";
        } else if(exercicio.getTipo() == 2){
            exercicioLiteral = "Ombro";
        } else if(exercicio.getTipo() == 3){
            exercicioLiteral = "Braço";
        } else if(exercicio.getTipo() == 4){
            exercicioLiteral = "Costas";
        } else if(exercicio.getTipo() == 5){
            exercicioLiteral = "Perna";
        }
        holder.tvExercicioNome.setText(exercicio.getNomeExercicio());
        holder.tvExercicioTipo.setText(exercicioLiteral);
        /* CUIDADO: .setText() precisa sempre de String. Se for outro tipo de dado (sem concatenação), deve ser feita a conversão com o String.valueOf() */

        // clique no item do cliente
        if (exercicioOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exercicioOnClickListener.onClickExercicio(holder.itemView,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listaExercicio.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvExercicioNome, tvExercicioTipo;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvExercicioNome = itemView.findViewById(R.id.tvExercicioNome);
            tvExercicioTipo = itemView.findViewById(R.id.tvExercicioTipo);


        }
    }

    public interface ExercicioOnClickListener {
        public void onClickExercicio(View view, int position);
    }

}

package adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendafit_mobile.R;

import java.util.List;

import modelDominio.Treino;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.MyViewHolder> {
    private final List<Treino> listaTreinos;
    private final TreinoAdapter.TreinoOnClickListener treinoOnClickListener;

    public TreinoAdapter(List<Treino> listaTreinos, TreinoOnClickListener treinoOnClickListener) {
        this.listaTreinos = listaTreinos;
        this.treinoOnClickListener = treinoOnClickListener;
    }

    @NonNull
    @Override
    public TreinoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row_treino, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final TreinoAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Treino treino= listaTreinos.get(position);
        String treinoLiteral="";
        if(treino.getTipo() == 1){
            treinoLiteral = "Peito";
        } else if(treino.getTipo() == 2){
            treinoLiteral = "Ombro";
        } else if(treino.getTipo() == 3){
            treinoLiteral = "Braço";
        } else if(treino.getTipo() == 4){
            treinoLiteral = "Costas";
        } else if(treino.getTipo() == 5){
            treinoLiteral = "Perna";
        }
        holder.tvTreinoNome.setText(treino.getNomeTreino());
        holder.tvTreinoTipo.setText(treinoLiteral);
        holder.tvTreinoData.setText("Data: "+treino.getData());
        holder.tvTreinoHora.setText("Hora: " +String.valueOf(treino.getHora())+"hrs");
        if(treino.getDescricao().length() <=40){
            holder.tvTreinoDescricao.setText("Descrição: "+treino.getDescricao());
        }else{
            String descricaoMenor = treino.getDescricao().substring(0,20)+"...";
            holder.tvTreinoDescricao.setText("Descrição: "+descricaoMenor);
        }

        // clique no item do cliente
        if (treinoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    treinoOnClickListener.onClickTreino(holder.itemView,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listaTreinos.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTreinoNome, tvTreinoTipo,tvTreinoDescricao,tvTreinoHora,tvTreinoData;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTreinoNome = itemView.findViewById(R.id.tvTreinoNome);
            tvTreinoTipo = itemView.findViewById(R.id.tvTreinoTipo);
            tvTreinoDescricao = itemView.findViewById(R.id.tvTreinoDescricao);
            tvTreinoHora = itemView.findViewById(R.id.tvTreinoHora);
            tvTreinoData = itemView.findViewById(R.id.tvTreinoData);
        }
    }

    public interface TreinoOnClickListener {
        public void onClickTreino(View view, int position);
    }

}

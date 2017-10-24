package br.com.exemplosms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.exemplosms.model.Contato;
import br.com.exemplosms.provasms.R;

/**
 * Created by Rafael on 20/10/2017.
 */

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>  {
  EditText edtTelefone;
    List<Contato> contatos;
    public ContatoAdapter(List<Contato> contatos, EditText edtTelefone){
        this.contatos = contatos;
        this.edtTelefone = edtTelefone;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        ContatoViewHolder pvh = new ContatoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ContatoViewHolder holder, int position) {
        holder.nomeView.setText(contatos.get(position).getNomeContato());
        holder.telefoneView.setText(contatos.get(position).getTelContato());

        holder.nomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = holder.telefoneView.getText().toString().replace("(","")
                        .replace(")","")
                        .replace(" ","")
                        .replace("-","")
                        .trim();
                edtTelefone.setText(telefone);
            }
        });

        holder.telefoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = holder.telefoneView.getText().toString().replace("(","")
                        .replace(")","")
                        .replace(" ","")
                        .replace("-","")
                        .trim();
                edtTelefone.setText(telefone);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }


    public static class ContatoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeView;
        TextView telefoneView;



       ContatoViewHolder(View itemView) {
            super(itemView);
            nomeView = (TextView)itemView.findViewById(R.id.txtNome);
            telefoneView = (TextView)itemView.findViewById(R.id.txtTelefone);

        }
    }
}

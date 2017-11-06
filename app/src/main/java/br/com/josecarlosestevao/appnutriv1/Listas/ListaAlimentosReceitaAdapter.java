package br.com.josecarlosestevao.appnutriv1.Listas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;

/**
 * Created by Dell on 09/12/2016.
 */
public class ListaAlimentosReceitaAdapter extends ArrayAdapter<Receita> {

    private final List<Receita> alimentosConsumidos;
    private final Activity activity;

    public ListaAlimentosReceitaAdapter(Activity activity, int textViewResourceld,
                                        List<Receita> alimentosConsumidos) {
        super(activity, textViewResourceld, alimentosConsumidos);
        this.activity = activity;
        this.alimentosConsumidos = alimentosConsumidos;
    }

    public View getView(int position, View convetView, ViewGroup paret) {
        Receita consumo = alimentosConsumidos.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.item_alimento, null);

        TextView alimento = (TextView) view.findViewById(R.id.txtNome);
        alimento.setText(consumo.getAlimento().toString());


        //   TextView data = (TextView) view.findViewById(R.id.txtvalor);
        // data.setText(consumo.getCarboidrato().toString());


        //  setText(dia + "/" + (mes + 1) + "/" + ano);
        //     final int pos=position;
        return view;
    }

    public long getItemId(int position) {
        return alimentosConsumidos.get(position).getId();
    }

    public int getCount() {
        return super.getCount();
    }

    public Receita getItem(int position) {
        return alimentosConsumidos.get(position);
    }
}

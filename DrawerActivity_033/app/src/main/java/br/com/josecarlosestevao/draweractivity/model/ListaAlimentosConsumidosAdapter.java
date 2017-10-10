package br.com.josecarlosestevao.draweractivity.model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.josecarlosestevao.draweractivity.R;

/**
 * Created by Dell on 09/12/2016.
 */
public class ListaAlimentosConsumidosAdapter extends ArrayAdapter<AlimentoConsumido> {

    private final List<AlimentoConsumido> alimentosConsumidos;
    private final Activity activity;

    public ListaAlimentosConsumidosAdapter(Activity activity, int textViewResourceld,
                                           List<AlimentoConsumido> alimentosConsumidos) {
        super(activity, textViewResourceld, alimentosConsumidos);
        this.activity = activity;
        this.alimentosConsumidos = alimentosConsumidos;
    }

    public View getView(int position, View convetView, ViewGroup paret) {
        AlimentoConsumido alimentoConsumido = alimentosConsumidos.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.item_alimento, null);

        TextView alimento = (TextView) view.findViewById(R.id.txtNome);
        alimento.setText(alimentoConsumido.getAlimento().toString());


        TextView data = (TextView) view.findViewById(R.id.txtdatarecebe);
        data.setText(alimentoConsumido.getData().toString());


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

    public AlimentoConsumido getItem(int position) {
        return alimentosConsumidos.get(position);
    }
}

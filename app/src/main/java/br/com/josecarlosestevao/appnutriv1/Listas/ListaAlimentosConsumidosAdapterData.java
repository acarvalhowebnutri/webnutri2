package br.com.josecarlosestevao.appnutriv1.Listas;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;

/**
 * Created by Dell on 09/12/2016.
 */
public class ListaAlimentosConsumidosAdapterData extends ArrayAdapter<Consumo> {

    private final List<Consumo> alimentosConsumidos;
    private final Activity activity;

    public ListaAlimentosConsumidosAdapterData(Activity activity, int textViewResourceld,
                                               List<Consumo> alimentosConsumidos) {
        super(activity, textViewResourceld, alimentosConsumidos);
        this.activity = activity;
        this.alimentosConsumidos = alimentosConsumidos;
    }

    public View getView(int position, View convetView, ViewGroup paret) {
        Consumo consumo = alimentosConsumidos.get(position);

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

    public Consumo getItem(int position) {
        return alimentosConsumidos.get(position);
    }
}

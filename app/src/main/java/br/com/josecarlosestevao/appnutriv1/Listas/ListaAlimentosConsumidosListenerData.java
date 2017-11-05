package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import br.com.josecarlosestevao.appnutriv1.Activiy.CadastrarAlimentoActivity;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;

/**
 * Created by Dell on 11/07/2017.
 */
public class ListaAlimentosConsumidosListenerData implements AdapterView.OnItemClickListener{

    ListaAlimentosDietaNutricionistaDataFragment activity;
    // private final ListaAlimentosConsumidosActivity activity;


    public ListaAlimentosConsumidosListenerData(ListaAlimentosDietaNutricionistaDataFragment activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long idPosicao) {
        Intent i = new Intent(activity.getContext(),CadastrarAlimentoActivity.class);
        i.putExtra("itemSelecionadoParaEdicao", (Consumo) activity.getListaAlimentosDietaNutricionistaDataFragment().getItemAtPosition(posicao));
        activity.startActivity(i);



    }

}


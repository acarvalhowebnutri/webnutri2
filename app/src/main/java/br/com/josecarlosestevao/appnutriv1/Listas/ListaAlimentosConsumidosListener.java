package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import br.com.josecarlosestevao.appnutriv1.Activiy.CadastrarAlimentoActivity;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;

/**
 * Created by Dell on 07/01/2017.
 */
public class ListaAlimentosConsumidosListener implements AdapterView.OnItemClickListener {

     ListaAlimentosConsumidosFragment activity;
   // private final ListaAlimentosConsumidosActivity activity;




    public ListaAlimentosConsumidosListener(ListaAlimentosConsumidosFragment activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long idPosicao) {
        Intent i = new Intent(activity.getContext(),CadastrarAlimentoActivity.class);
        i.putExtra("itemSelecionadoParaEdicao",(Consumo)activity.getListaAlimentosConsumidos().getItemAtPosition(posicao));
        activity.startActivity(i);



    }


}

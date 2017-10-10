package br.com.josecarlosestevao.draweractivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;

/**
 * Created by Dell on 07/01/2017.
 */
public class ListaAlimentosConsumidosListener implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final ListaAlimentosConsumidosActivity activity;

    public ListaAlimentosConsumidosListener(ListaAlimentosConsumidosActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long idPosicao) {
        Intent i = new Intent(activity,CadastrarAlimentoActivity.class);
        i.putExtra("itemSelecionadoParaEdicao",(AlimentoConsumido)activity.getListaAlimentosConsumidos().getItemAtPosition(posicao));
        activity.startActivity(i);
    }

    @Override
    public void onClick(View v) {

    }
}

package br.com.josecarlosestevao.appnutriv1.Listas;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import br.com.josecarlosestevao.appnutriv1.Nutricionista.ListaPacientesFragment;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 07/01/2017.
 */
public class ListaPacientesListener implements AdapterView.OnItemClickListener {

    ListaPacientesFragment activity;
    // private final ListaAlimentosConsumidosActivity activity;


    public ListaPacientesListener(ListaPacientesFragment activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long idPosicao) {
        Intent i = new Intent(activity.getContext(), ListaAlimentosConsumidosFragment.class);
        i.putExtra("itemSelecionadoParaEdicao", (Usuario) activity.getListapacientes().getItemAtPosition(posicao));
        activity.startActivity(i);


    }


}

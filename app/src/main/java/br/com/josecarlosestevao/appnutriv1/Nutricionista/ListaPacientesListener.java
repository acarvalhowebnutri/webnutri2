package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Dell on 07/01/2017.
 */
public class ListaPacientesListener extends Activity implements AdapterView.OnItemClickListener {

    private static final int MENU_APAGAR = Menu.FIRST;
    // private final ListaAlimentosConsumidosActivity activity;
    private static final int MENU_RECEITAR = Menu.NONE;
     //ListaAlimentosConsumidosFragment activity;
    ListaPacientesFragment activity;


    public ListaPacientesListener(ListaPacientesFragment activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long idPosicao) {
  /*
        Intent i = new Intent(activity.getContext(),CadastrarAlimentoActivity.class);
        i.putExtra("itemSelecionadoParaEdicao",(Usuario)activity.getListapacientes().getItemAtPosition(posicao));
        activity.startActivity(i);


*/
    }


}

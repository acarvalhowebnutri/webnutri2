package br.com.josecarlosestevao.draweractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;
import br.com.josecarlosestevao.draweractivity.model.ListaAlimentosConsumidosAdapter;

public class ListaAlimentosConsumidosActivity extends FragmentActivity implements AdapterView.OnItemClickListener{

    private ListView listaAlimentosConsumidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alimentos_consumidos);

        listaAlimentosConsumidos=(ListView)findViewById(R.id.lista_alimentos_consumidos);

    }

    @Override
    protected void onResume() {
        super.onResume();

        AlimentoConsumidoDAO dao=new AlimentoConsumidoDAO(getApplicationContext());
        final List<AlimentoConsumido> alimentoConsumidos = dao.listaAlimentos();
        ArrayAdapter<AlimentoConsumido> adapter=new ListaAlimentosConsumidosAdapter(this,android.R.layout.simple_list_item_1,alimentoConsumidos);
        listaAlimentosConsumidos.setAdapter(adapter);
        // listaAlimentosConsumidos.setOnClickListener(new ListaAlimentosConsumidosListener(this));

    }

    public ListView getListaAlimentosConsumidos(){
        return listaAlimentosConsumidos;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,CadastrarAlimentoActivity.class);
        i.putExtra("itemSelecionadoParaEdicao",(AlimentoConsumido)this.getListaAlimentosConsumidos().getItemAtPosition(position));
        this.startActivity(i);
    }
}

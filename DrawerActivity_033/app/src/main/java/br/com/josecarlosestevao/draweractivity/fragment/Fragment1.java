package br.com.josecarlosestevao.draweractivity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;
import br.com.josecarlosestevao.draweractivity.model.ListaAlimentosConsumidosAdapter;
import br.com.josecarlosestevao.draweractivity.R;

/**
 * Created by Dell on 25/12/2016.
 */
public class Fragment1 extends Fragment {


    private ListView listaAlimentosConsumidos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_frag1, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText("Fragment 1");

        listaAlimentosConsumidos = (ListView) view.findViewById(R.id.list);


        return(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        AlimentoConsumidoDAO dao = new AlimentoConsumidoDAO(getContext());

        final List<AlimentoConsumido> alimentosConsumidos = dao.listaAlimentos();
        ArrayAdapter<AlimentoConsumido> adapter = new ListaAlimentosConsumidosAdapter(getActivity(),
                android.R.layout.simple_list_item_1, alimentosConsumidos);
        listaAlimentosConsumidos.setAdapter(adapter);

ArrayList<AlimentoConsumido> alimento = new ArrayList<>();


    }
}


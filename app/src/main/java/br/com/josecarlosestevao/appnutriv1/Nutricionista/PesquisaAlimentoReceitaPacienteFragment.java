package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorConsumo;
import br.com.josecarlosestevao.appnutriv1.Constantes.SelectDateFragment;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaAlimentoReceitaPacienteFragment extends Fragment {

    public TextView txtdata;
    ListView lv;
    SearchView sv;
    ArrayList<Consumo> alimento = new ArrayList<>();
    //   ConsumoDAO adapter1;
    AdaptadorConsumo adapter;
    Consumo consumo;
    Usuario u;
    ConsumoDAO alimentoRepo;
    Cursor cursor;
    //   private ConsumoDAO dao;
    //  List<Consumo> alimentoConsumidos = dao.listaTodos();
    private int ano, mes, dia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag3, null);

        lv = (ListView) view.findViewById(R.id.listfrag);
        sv = (SearchView) view.findViewById(R.id.search);
        txtdata = (TextView) view.findViewById(R.id.txtData);


        adapter = new AdaptadorConsumo(getContext(), alimento);
        consumo = new Consumo();
        //   lv.setOnItemClickListener(new ItemList());
        //  lv.setAdapter(adapter);


        //   if(cursor==null) insertDummy();


        txtdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");


            }
        });


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    lv.clearTextFilter();
                } else {
                    buscaAlimento(newText);

                    //mListView.setFilterText(newText.toString());
                }
                return false;
            }
        });


        return (view);
    }


    public void buscaAlimento(String newText) {

        alimento.clear();

        ConsumoDAO db = new ConsumoDAO(getContext());
        db.openDB();
        Consumo p = null;
        Cursor c = db.recuperar(newText);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(1);
            String valor = c.getString(2);
            String pro = c.getString(3);

            p = new Consumo();
            //p.setId(id);
            p.setAlimento(name);
            p.setCarboidrato(valor);
            p.setProteina(pro);

            alimento.add(p);
        }

        db.closeDB();
      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);


    }


    public ListView getListaAlimentoConsumidos() {

        return lv;
    }


}



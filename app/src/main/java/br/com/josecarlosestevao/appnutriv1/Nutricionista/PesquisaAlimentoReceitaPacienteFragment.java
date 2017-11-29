package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Receita.AdaptadorReceita;
import br.com.josecarlosestevao.appnutriv1.Receita.Receita;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaAlimentoReceitaPacienteFragment extends Fragment {

    public TextView txtdata;
    ListView lv;
    SearchView sv;
    ArrayList<Receita> alimento = new ArrayList<>();
    //   ConsumoDAO adapter1;
    AdaptadorReceita adapter;
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
        View view = inflater.inflate(R.layout.layout_pesquisa_alimento, null);

        lv = (ListView) view.findViewById(R.id.listfrag);
        sv = (SearchView) view.findViewById(R.id.search);
        txtdata = (TextView) view.findViewById(R.id.txtData);


        adapter = new AdaptadorReceita(getContext(), alimento);
        consumo = new Consumo();
        //   lv.setOnItemClickListener(new ItemList());
        //  lv.setAdapter(adapter);


        //   if(cursor==null) insertDummy();

        Bundle bundle = getArguments();
        final String crnpaciente = bundle.getString("crn");
        final String data = bundle.getString("link");
        final String nome = bundle.getString("nome");
        final String idpaciente = bundle.getString("idpaciente");

        if (u == null) {
            u = new Usuario();
        }
        u.setNome(nome);
        u.setCrn(crnpaciente);
        u.setId(idpaciente);

        if (consumo == null) {
            consumo = new Consumo();
        }
        consumo.setData(data);
     /*   txtdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");


            }
        });
*/

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
        Bundle bundle = getArguments();
        //  String nomepaciente = bundle.getString("nome");
        String data = bundle.getString("link");
        String tipo = bundle.getString("tipo");
        ConsumoDAO db = new ConsumoDAO(getContext());
        db.openDB();
        Receita p = null;
        Cursor c = db.recuperar(newText);
        while (c.moveToNext()) {
            long id = c.getInt(0);
            String name = c.getString(1);
            String energia = c.getString(2);
            //String valor = c.getString(2);
            //String pro = c.getString(3);

            p = new Receita();
            //p.setId(id);
            p.setAlimento(name);
            p.setEnergia(energia);

            p.setId(id);
            // p.setCarboidrato(valor);
            //p.setProteina(pro);
            p.setUsuario(u);
            p.setData(data);
            p.setTipo(tipo);


            alimento.add(p);
        }

        db.closeDB();
        c.close();

      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);


    }


    public ListView getListaAlimentoConsumidos() {

        return lv;
    }


}



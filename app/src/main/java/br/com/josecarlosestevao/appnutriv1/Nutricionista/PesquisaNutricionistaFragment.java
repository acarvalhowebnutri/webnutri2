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

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorNutricionista;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaFragment extends Fragment {

    public TextView txtdata;
    ListView lv;
    SearchView sv;
    ArrayList<Nutricionista> alimento = new ArrayList<>();

    //   ConsumoDAO adapter1;
    AdaptadorNutricionista adapter;
    Nutricionista consumo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_pesq_nutri_cad, null);

        lv = (ListView) view.findViewById(R.id.listnutrifrag);
        sv = (SearchView) view.findViewById(R.id.searchnutri);
        txtdata = (TextView) view.findViewById(R.id.txtMensagem);


        adapter = new AdaptadorNutricionista(getContext(), alimento);
        consumo = new Nutricionista();


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
                    buscaNutricionista(newText);

                    //mListView.setFilterText(newText.toString());
                }
                return false;
            }
        });


        return (view);
    }


    public void buscaNutricionista(String newText) {

        alimento.clear();

        UsuarioDAO db = new UsuarioDAO(getContext());
        db.open();
        Nutricionista p = null;
        Cursor cn = db.recuperarNutri(newText);
        while (cn.moveToNext()) {
            int id = cn.getInt(0);
            String name = cn.getString(1);
            String senha = cn.getString(2);
            String email = cn.getString(3);
            String crn = cn.getString(4);
            //String pro=c.getString(3);

            p = new Nutricionista();
            //p.setId(id);
            p.setNome(name);
            p.setSenha(senha);
            p.setEmail(email);
            p.setCrn(crn);
            //p.setProteina(pro);

            alimento.add(p);
        }

        db.close();
      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);


    }


    public ListView getListaAlimentoConsumidos() {

        return lv;
    }


}



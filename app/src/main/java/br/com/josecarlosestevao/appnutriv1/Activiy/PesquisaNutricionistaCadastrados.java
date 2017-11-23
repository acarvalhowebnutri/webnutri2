package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorNutricionista;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.Nutricionista.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaCadastrados extends Activity {

    public TextView txtdata;
    ListView lv;
    SearchView sv;
    ArrayList<Nutricionista> alimento = new ArrayList<>();
    //   ConsumoDAO adapter1;
    AdaptadorNutricionista adapter;
    Nutricionista consumo;
    Nutricionista u;
    ConsumoDAO alimentoRepo;
    Cursor cursor;
    Button btnVoltarPerfil;
    //   private ConsumoDAO dao;
    //  List<Consumo> alimentoConsumidos = dao.listaTodos();
    private int ano, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pesq_nutri_cad);

        lv = (ListView) findViewById(R.id.listfrag);
        sv = (SearchView) findViewById(R.id.searchnutri);
        txtdata = (TextView) findViewById(R.id.txtData);
        btnVoltarPerfil = (Button) findViewById(R.id.criarContaBtn);


        adapter = new AdaptadorNutricionista(getApplication(), alimento);
        consumo = new Nutricionista();
        //   lv.setOnItemClickListener(new ItemList());
        //  lv.setAdapter(adapter);


        //   if(cursor==null) insertDummy();


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

                    // Toast.makeText(getApplicationContext(), "Dados alterados", Toast.LENGTH_LONG).show();

                    //mListView.setFilterText(newText.toString());
                }
                return false;
            }


        });

        /*

        btnVoltarPerfil.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent voltar = new Intent(getApplication(), PerfilActivity.class);
                startActivity(voltar);

            }
        });
*/
        //Intent voltar = new Intent(getApplicationContext(), PerfilActivityOld.class);
        //startActivity(voltar);


        //   return(view);
    }


    public void buscaNutricionista(String newText) {

        alimento.clear();

        UsuarioDAO db = new UsuarioDAO(getApplicationContext());
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



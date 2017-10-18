package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorNutricionista;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class PesquisaNutricionistaCadastrados extends AppCompatActivity {

    ListView lv;
    SearchView sv;
    ArrayList<Nutricionista> alimento=new ArrayList<>();
 //   ConsumoDAO adapter1;
    AdaptadorNutricionista adapter;
     Consumo consumo;
    Nutricionista u;
 //   private ConsumoDAO dao;
  //  List<Consumo> alimentoConsumidos = dao.listaTodos();
     private int ano, mes, dia;
    public TextView txtdata;
    ConsumoDAO alimentoRepo ;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pesq_nutri_cad);

        lv= (ListView)findViewById(R.id.listfrag);
        sv= (SearchView) findViewById(R.id.search);
        txtdata = (TextView) findViewById(R.id.txtData);


        adapter=new AdaptadorNutricionista(getApplication(),alimento);
        consumo = new Consumo();
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

        Intent voltar = new Intent(getApplicationContext(), PerfilFragment.class);
        startActivity(voltar);




     //   return(view);
    }





    public void buscaNutricionista(String newText) {

        alimento.clear();

        UsuarioDAO db=new UsuarioDAO(getApplicationContext());
        db.open();
        Nutricionista p=null;
        Cursor c=db.recuperar(newText);
        while (c.moveToNext())
        {
             int id=c.getInt(0);
            String name=c.getString(1);
            String crn=c.getString(2);
            //String pro=c.getString(3);

            p=new Nutricionista();
            //p.setId(id);
            p.setNome(name);
            p.setCrn(crn);
            //p.setProteina(pro);

            alimento.add(p);
        }

        db.close();
      /*  ArrayAdapter<Consumo> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);


    }


    public ListView getListaAlimentoConsumidos(){

        return lv;
    }



}



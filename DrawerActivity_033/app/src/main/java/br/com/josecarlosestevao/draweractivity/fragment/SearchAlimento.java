package br.com.josecarlosestevao.draweractivity.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.josecarlosestevao.draweractivity.R;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class SearchAlimento extends AppCompatActivity {

    ListView lv;
    SearchView sv;
    ArrayList<AlimentoConsumido> alimento=new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frag3);
        lv= (ListView) findViewById(R.id.list);
        sv= (SearchView) findViewById(R.id.search);
        adapter=new CustomAdapter(this,alimento);

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

                 //   Intent intent = getIntent();
                   // String message = intent.getStringExtra("message");
                   getAlimento(newText);
                   // AlimentoConsumidoDAO db=new AlimentoConsumidoDAO(getApplicationContext());
                    //db.buscaAlimento(newText);

                    //mListView.setFilterText(newText.toString());
                }
                return false;
            }
        });


    }


    public ListView getListaAlimentosConsumidos() {
        return lv;
    }
/*
    protected void onResume() {
        super.onResume();


        AlimentoConsumidoDAO dao = new AlimentoConsumidoDAO(getApplicationContext());

        final List<AlimentoConsumido> alimentosConsumidos = dao.listaTodos();
        ArrayAdapter<AlimentoConsumido> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimentosConsumidos);
        lv.setAdapter(adapter);
    }
*/






    private void getAlimento(String newText) {

        alimento.clear();

        AlimentoConsumidoDAO db=new AlimentoConsumidoDAO(this);
       db.openDB();
        AlimentoConsumido p=null;
        Cursor c=db.recuperar(newText);
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);

            p=new AlimentoConsumido();
           // p.setId(id);
            p.setAlimento(name);
          //  p.setData(data);

            alimento.add(p);
        }

        db.closeDB();
      /*  ArrayAdapter<AlimentoConsumido> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);



    }





}

package br.com.josecarlosestevao.draweractivity.fragment;

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

import br.com.josecarlosestevao.draweractivity.R;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;

/**
 * Created by Dell on 06/01/2017.
 */
public class Fragment3 extends Fragment {

    ListView lv;
    SearchView sv;
    ArrayList<AlimentoConsumido> alimento=new ArrayList<>();
 //   AlimentoConsumidoDAO adapter1;
    CustomAdapter adapter;
    private AlimentoConsumido alimentoConsumido;
 //   private AlimentoConsumidoDAO dao;
  //  List<AlimentoConsumido> alimentoConsumidos = dao.listaTodos();
     private int ano, mes, dia;
    public TextView txtdata;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag3, null);

        lv= (ListView) view.findViewById(R.id.listfrag);
        sv= (SearchView) view.findViewById(R.id.search);
        txtdata = (TextView) view.findViewById(R.id.txtData);


        adapter=new CustomAdapter(getContext(),alimento);
        alimentoConsumido = new AlimentoConsumido();
     //   lv.setOnItemClickListener(new ItemList());
      //  lv.setAdapter(adapter);
/*
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        dataGasto = (TextView) view.findViewById(R.id.txtData);
      dataGasto.setText(dia + "/" + (mes+1) + "/" + ano);
*/
      //  txtdata.setText(dia + "/" + (mes+1) + "/" + ano);


        txtdata.setOnClickListener(new View.OnClickListener(){

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




        return(view);
    }





    public void buscaAlimento(String newText) {

        alimento.clear();

        AlimentoConsumidoDAO db=new AlimentoConsumidoDAO(getContext());
        db.openDB();
        AlimentoConsumido p=null;
        Cursor c=db.recuperar(newText);
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);

            p=new AlimentoConsumido();
            //p.setId(id);
            p.setAlimento(name);

            alimento.add(p);
        }

        db.closeDB();
      /*  ArrayAdapter<AlimentoConsumido> adapter = new ListaAlimentosConsumidosAdapter(this,
                android.R.layout.simple_list_item_1, alimento);*/
        lv.setAdapter(adapter);



    }

    /*    public void save(String name) {

        AlimentoConsumidoDAO db=new AlimentoConsumidoDAO(getContext());
        //  DBAdapter db=new DBAdapter(this);
        db.openDB();
        //db.add(name);

        if(db.add(name)==true)
        {
            Toast.makeText(getActivity(),name + " adicionado", Toast.LENGTH_SHORT).show();
            db.closeDB();

            //nameEditText.setText("");
        }else {
            // Toast.makeText(this, "Unable To Save", Toast.LENGTH_SHORT).show();
        }


            db.closeDB();

       this.buscaAlimento(null);
    }

*/
    public ListView getListaAlimentoConsumidos(){

        return lv;
    }


}



//*****************************************************************************************************************************
// lv.setClickable(true);
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                String name = String.valueOf(lv.getItemAtPosition(position));
              // = alimento.get(position).buscaAlimento();
                save(name);

            }
        });
*/
    /*    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text or do whatever you need.
                String name = String.valueOf(lv.getItemAtPosition(position));
                // = alimento.get(position).buscaAlimento();
                save(name);

            }
        });*/

/*
        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parentView, View childView,
                                       int position, long id) {
                //String name = String.valueOf(lv.getItemAtPosition(position));
              // String str =(String)parentView.getSelectedItem();
           //     save(str);
               String name  = alimento.get(position).buscaAlimento();
              save(name);


            }

            public void onNothingSelected(AdapterView parentView) {
                lv.clearTextFilter();

            }
        });*/
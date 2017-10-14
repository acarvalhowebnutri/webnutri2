package br.com.josecarlosestevao.draweractivity.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.josecarlosestevao.draweractivity.ListaAlimentosConsumidosActivity;
import br.com.josecarlosestevao.draweractivity.R;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;
import br.com.josecarlosestevao.draweractivity.model.DatabaseHelper;

/**
 * Created by Oclemmy on 5/2/2016 for ProgrammingWizards Channel and http://www.Camposha.com.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<AlimentoConsumido> planets;
    LayoutInflater inflater;
    AlimentoConsumido alimentoConsumido = new AlimentoConsumido();
    ListaAlimentosConsumidosActivity activity;
    AlimentoConsumidoDAO dao = new AlimentoConsumidoDAO(c);
    Fragment3 save;
    private DatabaseHelper dbHelper;
    private int ano, mes, dia;
    private TextView dataGasto;


    SQLiteDatabase db;


    public CustomAdapter(Context c, ArrayList<AlimentoConsumido> planets) {
        this.c = c;
        this.planets = planets;
    }

    @Override
    public int getCount() {
        return planets.size();
    }

    @Override
    public AlimentoConsumido getItem(int position) {
        return planets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_alimento, parent, false);
        }

        TextView nameTxt = (TextView) convertView.findViewById(R.id.txtNome);
        TextView dataTxt = (TextView) convertView.findViewById(R.id.txtdatarecebe);


        nameTxt.setText(planets.get(position).getAlimento());
        dataTxt.setText(planets.get(position).getData().toString());

        final String name = planets.get(position).getAlimento();
        final String data = planets.get(position).getData().toString();
       // alimentoConsumido.setAlimento(planets.get(position).getAlimento());
        //alimentoConsumido.setData(planets.get(position).getData());
       // final String data = "teste";
       // alimentoConsumido.setData(data);


        final int pos = position;
        //   alimentoConsumido = new AlimentoConsumido();

        // save = new Fragment3();
        //save(name);

        final Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => "+calendar.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(calendar.getTime());
        // formattedDate have current date/time





        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alimentoConsumido.setAlimento(name);
                alimentoConsumido.setData(data);
                //Toast.makeText(c, name + "adicionado com sucesso", Toast.LENGTH_SHORT).show();
                //Toast.makeText(c, formattedDate, Toast.LENGTH_SHORT).show();
                Toast.makeText(c, planets.get(position).getData() + " :data", Toast.LENGTH_SHORT).show();
                //Toast.makeText(c, planets.get(position).getAlimento() + " :Alimento", Toast.LENGTH_SHORT).show();
                AlimentoConsumidoDAO db = new AlimentoConsumidoDAO(c);
                db.consumido(alimentoConsumido);


            }

        });


        return convertView;
    }
/*
    public void adiciona(AlimentoConsumido alimento) {
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getAlimento());
        values.put("data", System.currentTimeMillis());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert("alimento_consumido", null, values);
        alimento.setId(id);
        db.close();

    }

    private void save(String name, String data) {
        AlimentoConsumidoDAO db = new AlimentoConsumidoDAO(c);
        //  DBAdapter db=new DBAdapter(this);
        db.openDB();
        //db.add(name);
        if (db.add(name) == true && db.add(data)) {
            // Toast.makeText((),name + " adicionado", Toast.LENGTH_SHORT).show();
            db.closeDB();

            //nameEditText.setText("");
        } else {
            // Toast.makeText(this, "Unable To Save", Toast.LENGTH_SHORT).show();
        }


        db.closeDB();

        // this.getAlimento(null);

    }
*/

}


/*public void selecionarData(View view) {
        activity.showDialog(view.getId());
    }


    protected Dialog onCreateDialog(int id) {
        if (R.id.txtData == id) {
            return new DatePickerDialog(c, listener, ano, mes, dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataGasto.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

    */
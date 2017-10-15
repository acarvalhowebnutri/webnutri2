package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;

public class CadastrarAlimentoActivity extends AppCompatActivity {

    //private DatabaseHelper helper;
    private EditText alimento, quantidadePessoas, orcamento;
    private Button gravarbanco;
    private Consumo consumo;
    private int ano, mes, dia;
    private TextView dataGasto;
    private DatePicker dataConsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_alimento);

// recuperando novas views
        alimento = (EditText) findViewById(R.id.alimento);
        gravarbanco = (Button) findViewById(R.id.btnGravar);
        dataConsumo = (DatePicker)findViewById(R.id.data_consumo);
 /*
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        dataGasto = (TextView) findViewById(R.id.txtData);
        dataGasto.setText(dia + "/" + (mes + 1) + "/" + ano);

      final Calendar calendar1 = Calendar.getInstance();
        System.out.println("Current time => "+calendar1.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      final String formattedDate = df.format(calendar1.getTime());
*/
// prepara acesso ao banco de dados
        //helper = new DatabaseHelper(this);
        consumo = new Consumo();

        gravarbanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumo.setAlimento(alimento.getText().toString());
               //consumo.setData(alimento.getText().toString());


                String data = ( dataConsumo.getDayOfMonth()+
                        "/"+(dataConsumo.getMonth()+1)+"/"
                        +dataConsumo.getYear());


                //consumo.setData((dataConsumo.getDayOfMonth()+"/"+(dataConsumo.getMonth()+1)+"/" +dataConsumo.getYear()).toString());

                ConsumoDAO dao = new ConsumoDAO(getApplicationContext());
                dao.adiciona(consumo);
              //  String teste = consumo.getData().toString();
                Toast.makeText(getApplicationContext(), data+" "+getString(R.string.registro_salvo),
                        Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }


    protected Dialog onCreateDialog(int id) {
        if (R.id.txtData == id) {
            return new DatePickerDialog(this, listener, ano, mes, dia);
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
}
/* ********************************************************************************************************************8
    public void GravarBanco(View view) {



        consumo.setAlimento(alimento.getText().toString());

        ConsumoDAO dao = new ConsumoDAO(getApplicationContext());
        dao.adiciona(consumo);
        Toast.makeText(this, getString(R.string.registro_salvo),
                Toast.LENGTH_SHORT).show();
                }*/
      /*  SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alimento", alimento.getText().toString());

        long resultado = db.insert("alimento", null, values);
        if (resultado != -1) {
            Toast.makeText(this, getString(R.string.registro_salvo),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.erro_salvar),
                    Toast.LENGTH_SHORT).show();
        }*/


/*
    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
*/


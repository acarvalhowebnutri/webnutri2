package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;

import java.util.Calendar;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.ListaHistoricoConsumidosFragmentFirebase;


public class HistoricoFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    FragmentManager fm = getFragmentManager();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        //   populateSetDate(yy, mm+1, dd);


       //TextView txtdata = (TextView) getActivity().findViewById(R.id.textview1);
        String stringOfDate = dd + "/" + (mm + 1) + "/" + yy;
        //txtdata.setText(stringOfDate);

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        //  ListaAlimentosConsumidosFragmentPorData mainFragment = new ListaAlimentosConsumidosFragmentPorData();
        ListaHistoricoConsumidosFragmentFirebase mainFragment = new ListaHistoricoConsumidosFragmentFirebase();


// Passando um link
        Bundle bundle = new Bundle();
        bundle.putString("link", stringOfDate);
        mainFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_direito, mainFragment, "layout_frag4")
        .addToBackStack("layout_frag4").commit();
  /*     ListaAlimentosConsumidosFragmentPorData frag3 = (ListaAlimentosConsumidosFragmentPorData) fm.findFragmentByTag("layout_frag4");

        if (frag3 == null) {
            frag3 = new ListaAlimentosConsumidosFragmentPorData();
        }
        ft.replace(R.id.layout_direito, frag3, "layout_frag4");
        ft.commit();

      /*  Intent one = new Intent(getActivity(), ListaAlimentosConsumidosFragmentPorData.class);
        one.putExtra("databaseUrl", stringOfDate);
        startActivity(one);
        */
    }

}

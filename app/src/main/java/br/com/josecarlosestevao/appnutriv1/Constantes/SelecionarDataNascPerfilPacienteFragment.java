package br.com.josecarlosestevao.appnutriv1.Constantes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import br.com.josecarlosestevao.appnutriv1.R;

/**
 * Created by Dell on 17/01/2017.
 */
public class SelecionarDataNascPerfilPacienteFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
   // private TextView txtdata =(TextView) getActivity().findViewById(R.id.txtData);
    private int ano, mes, dia;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
       populateSetDate(yy, mm+1, dd);
        String stringOfDate = dd + "/" + (mm + 1) + "/" + yy;
        SelecionarDataNascPerfilPacienteFragment mainFragment = new SelecionarDataNascPerfilPacienteFragment();

// Passando um link
        Bundle bundle = new Bundle();
        bundle.putString("link", stringOfDate);
        mainFragment.setArguments(bundle);


    }
    public void populateSetDate(int year, int month, int day) {
        TextView tv = (TextView) getActivity().findViewById(R.id.editDataNascimento);
        tv.setText(month+"/"+day+"/"+year);
    }




/*
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


        }
    };

*/
}

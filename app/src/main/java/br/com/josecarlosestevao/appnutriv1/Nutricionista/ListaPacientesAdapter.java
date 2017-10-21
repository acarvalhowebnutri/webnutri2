package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 09/12/2016.
 */
public class ListaPacientesAdapter extends ArrayAdapter<Usuario> {

    private final List<Usuario> pacientes;
    private final Activity activity;

    public ListaPacientesAdapter(Activity activity, int textViewResourceld,
                                 List<Usuario> pacientes) {
        super(activity, textViewResourceld, pacientes);
        this.activity = activity;
        this.pacientes = pacientes;
    }

    public View getView(int position, View convetView, ViewGroup paret) {
        Usuario pac = pacientes.get(position);

        View view = activity.getLayoutInflater().inflate(R.layout.item_usuario, null);

        TextView nome = (TextView) view.findViewById(R.id.txtNome);
        nome.setText(pac.getNome().toString());

        TextView senha = (TextView) view.findViewById(R.id.txtemail);
        senha.setText(pac.getSenha().toString());

     //   TextView data = (TextView) view.findViewById(R.id.txtvalor);
       // data.setText(consumo.getCarboidrato().toString());


      //  setText(dia + "/" + (mes + 1) + "/" + ano);
        //     final int pos=position;
        return view;
    }

    public long getItemId(int position) {
        return pacientes.get(position).getId();
    }

    public int getCount() {
        return super.getCount();
    }

    public Usuario getItem(int position) {
        return pacientes.get(position);
    }
}

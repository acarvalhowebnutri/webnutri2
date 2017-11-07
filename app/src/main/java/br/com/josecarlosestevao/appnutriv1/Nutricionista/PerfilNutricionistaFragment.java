package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;

/**
 * Created by Dell on 06/01/2017.
 */
public class PerfilNutricionistaFragment extends Fragment {

    NutricionistaDao loginAdapter;
    TextView tvnome, tvcrn, tvemail;
    Button salvar, deletar, alterarNutri;
    SessionManager session;
    Nutricionista nutricionista;
    FragmentManager fm = getActivity().getSupportFragmentManager();
    private ImageView campoFotoNutricionista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_perfil_nutricionista, null);


        loginAdapter = new NutricionistaDao(getContext());
        //    loginAdapter = loginAdapter.open();


        final Intent intent = new Intent();
        Bundle params = intent.getExtras();

        tvnome = (TextView) view.findViewById(R.id.textViewExibeNOME);
        tvcrn = (TextView) view.findViewById(R.id.textViewExibeCRN);
        tvemail = (TextView) view.findViewById(R.id.textViewExibeEMAIL);
        alterarNutri = (Button) view.findViewById(R.id.buttonAlterar);
        campoFotoNutricionista = (ImageView) view.findViewById(R.id.imageViewFotoNutricionista);


        carregarDadosNutricionistas();


        alterarNutri.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                FragmentTransaction ft = fm.beginTransaction();


                PerfilNutricionistaFragment frag_editar_perfil_nutri = (PerfilNutricionistaFragment) fm.findFragmentByTag("frag_editar_perfil_nutri");

                if (frag_editar_perfil_nutri == null) {
                    frag_editar_perfil_nutri = new PerfilNutricionistaFragment();
                }
                ft.replace(R.id.layout_direito, frag_editar_perfil_nutri, "frag_editar_perfil_nutri");


            }
        });


        return (view);
    }

    private void carregarDadosNutricionistas() {

        session = new SessionManager(getContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        if (name != null) {
            //String userName = params.getString("nome");

            nutricionista = loginAdapter.ler(name);
            tvnome.setText(nutricionista.getNome());
            tvcrn.setText(nutricionista.getCrn());
            tvemail.setText(nutricionista.getEmail());


            if (nutricionista.getFoto() != null)

                campoFotoNutricionista.setImageBitmap(ConversorImagem.converteByteArrayPraBitmap(nutricionista.getFoto()));


        }

    }


}



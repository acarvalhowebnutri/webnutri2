package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.josecarlosestevao.appnutriv1.Constantes.AdaptadorConsumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.Consumo;
import br.com.josecarlosestevao.appnutriv1.Consumo.ConsumoDAO;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 06/01/2017.
 */
public class MenuReceitaFragment extends Fragment {

    public TextView textViewNomePaciente, texViewCafedaManha, texViewAlmoco, texViewJantar, texViewLanche, textViewDataReceita;
    public ImageButton imageButtonCafe, imageButtonAlmoço, imageButtonJantar, imageButtonLanche;
    ListView lv;
    SearchView sv;
    ArrayList<Consumo> alimento = new ArrayList<>();
    //   ConsumoDAO adapter1;
    AdaptadorConsumo adapter;
    Consumo consumo;
    Usuario u;
    ConsumoDAO alimentoRepo;
    Cursor cursor;
    //   private ConsumoDAO dao;
    //  List<Consumo> alimentoConsumidos = dao.listaTodos();
    private int ano, mes, dia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu_receita, null);


        textViewNomePaciente = (TextView) view.findViewById(R.id.textViewNomePaciente);
        textViewDataReceita = (TextView) view.findViewById(R.id.textViewDataReceita);
        texViewCafedaManha = (TextView) view.findViewById(R.id.texViewCafedaManha);
        texViewAlmoco = (TextView) view.findViewById(R.id.texViewAlmoco);
        texViewJantar = (TextView) view.findViewById(R.id.texViewJantar);
        texViewLanche = (TextView) view.findViewById(R.id.texViewLanche);

        imageButtonCafe = (ImageButton) view.findViewById(R.id.imageButtonCafe);
        imageButtonAlmoço = (ImageButton) view.findViewById(R.id.imageButtonAlmoço);
        imageButtonJantar = (ImageButton) view.findViewById(R.id.imageButtonJanta);
        imageButtonLanche = (ImageButton) view.findViewById(R.id.imageButtonLanche);


        Bundle bundlerecebe = getArguments();
        final String nomepaciente = bundlerecebe.getString("nome");
        final String data = bundlerecebe.getString("link");

        textViewNomePaciente.setText("Paciente: " + nomepaciente);
        textViewDataReceita.setText("Data escolhida: " + data);


        imageButtonCafe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tipo = "café da manha";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", data);
                bundle.putString("nome", nomepaciente);
                bundle.putString("tipo", tipo);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });
        imageButtonAlmoço.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tipo = "almoço";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", data);
                bundle.putString("nome", nomepaciente);
                bundle.putString("tipo", tipo);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });
        imageButtonJantar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tipo = "jantar";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", data);
                bundle.putString("nome", nomepaciente);
                bundle.putString("tipo", tipo);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });
        imageButtonLanche.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tipo = "lanche";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", data);
                bundle.putString("nome", nomepaciente);
                bundle.putString("tipo", tipo);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });


        return (view);
    }


}



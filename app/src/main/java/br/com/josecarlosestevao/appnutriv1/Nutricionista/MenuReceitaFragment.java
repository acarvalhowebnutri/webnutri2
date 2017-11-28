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
    public ImageButton imageButtonCafe, imageButtonAlmoco, imageButtonJantar, imageButtonLanche;
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
        imageButtonAlmoco = (ImageButton) view.findViewById(R.id.imageButtonAlmoço);
        imageButtonJantar = (ImageButton) view.findViewById(R.id.imageButtonJanta);
        imageButtonLanche = (ImageButton) view.findViewById(R.id.imageButtonLanche);


        Bundle bundlerecebe = getArguments();
        String crnpaciente = bundlerecebe.getString("crn");
        String data = bundlerecebe.getString("link");
       String nome = bundlerecebe.getString("nome");
        String idpaciente = bundlerecebe.getString("idpaciente");
        //String idpaciente2 = bundlerecebe.getString("idpaciente");

        if (savedInstanceState != null) {
            data = (String) savedInstanceState.get("link");
            nome = (String) savedInstanceState.get("nome");
            idpaciente = (String) savedInstanceState.get("idpaciente");
            crnpaciente = (String) savedInstanceState.get("crn");

        }


        final String datarecebe = data;
        final String crnrecebe = crnpaciente;

        final String nomerecebe = nome;

        final String idpacienterecebe = idpaciente;


        textViewNomePaciente.setText("Paciente: " + nome);
        textViewDataReceita.setText("Data escolhida: " + data);


        imageButtonCafe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tipo = "café da manha";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", datarecebe);
                bundle.putString("crn", crnrecebe);
                bundle.putString("tipo", tipo);
                bundle.putString("nome", nomerecebe);
                bundle.putString("idpaciente", idpacienterecebe);


                mainFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });

        imageButtonAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipo = "almoço";
                PesquisaAlimentoReceitaPacienteFragment mainFragmentalmoço = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", datarecebe);
                bundle.putString("crn", crnrecebe);
                bundle.putString("tipo", tipo);
                bundle.putString("nome", nomerecebe);
                bundle.putString("idpaciente", idpacienterecebe);


                mainFragmentalmoço.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragmentalmoço, "layout_frag2")
                        .addToBackStack("layout_frag2").commit();

            }
        });

        imageButtonJantar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String tipo = "jantar";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", datarecebe);
                bundle.putString("crn", crnrecebe);
                bundle.putString("tipo", tipo);
                bundle.putString("nome", nomerecebe);
                bundle.putString("idpaciente", idpacienterecebe);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });

        imageButtonLanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String tipo = "lanche";
                PesquisaAlimentoReceitaPacienteFragment mainFragment = new PesquisaAlimentoReceitaPacienteFragment();
                Bundle bundle = new Bundle();
                bundle.putString("link", datarecebe);
                bundle.putString("crn", crnrecebe);
                bundle.putString("tipo", tipo);
                bundle.putString("nome", nomerecebe);
                bundle.putString("idpaciente", idpacienterecebe);

                mainFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_direito_nutricionista, mainFragment, "layout_frag4")
                        .addToBackStack("layout_frag4").commit();


            }
        });
        return (view);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle bundlerecebe = getArguments();
        final String crnpaciente = bundlerecebe.getString("crn");
        final String data = bundlerecebe.getString("link");
        final String nome = bundlerecebe.getString("nome");
        final String idpaciente = bundlerecebe.getString("idpaciente");
      //  String idpaciente2 = bundlerecebe.getString("idpaciente");


        outState.putSerializable("crn", crnpaciente);
        outState.putSerializable("link", data);
        outState.putSerializable("nome", nome);
        outState.putSerializable("idpaciente", idpaciente);
    }

}



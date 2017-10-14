package br.com.josecarlosestevao.draweractivity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumido;
import br.com.josecarlosestevao.draweractivity.model.AlimentoConsumidoDAO;
import br.com.josecarlosestevao.draweractivity.R;

/**
 * Created by Dell on 25/12/2016.
 */
public class Fragment2 extends Fragment {

    private EditText alimento, quantidadePessoas, orcamento;
    private Button gravarbanco;
    private AlimentoConsumido alimentoConsumido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_frag2, null);

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText("Fragment 2");

        alimento = (EditText) view. findViewById(R.id.alimento);
        gravarbanco = (Button) view.findViewById(R.id.btnGravar);



        return(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




// prepara acesso ao banco de dados
        //helper = new DatabaseHelper(this);
        alimentoConsumido = new AlimentoConsumido();

        gravarbanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alimentoConsumido.setAlimento(alimento.getText().toString());

                AlimentoConsumidoDAO dao = new AlimentoConsumidoDAO(getContext());
                dao.adiciona(alimentoConsumido);
                Toast.makeText(getContext(), getString(R.string.registro_salvo),
                        Toast.LENGTH_SHORT).show();


            }
        });
    }
}


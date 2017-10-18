package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.Login.LoginActivity;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastroNutricionistraActivity extends AppCompatActivity {

    EditText crnEditText;
    Button criarContaBtn;
    UsuarioDAO loginAdapter;
    Bundle bundle = new Bundle();
    TextView cadastro_data_nasc;
    SessionManager session;
    private Nutricionista userU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nutricionistra);

        crnEditText = (EditText) findViewById(R.id.crnEditText);
        criarContaBtn = (Button) findViewById(R.id.criarContaBtn);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        if (userU == null) {
            userU = new Nutricionista();
        }
        userU.setNome(bundle.getString("nome"));
        userU.setSenha(bundle.getString("senha"));
        userU.setEmail(bundle.getString("email"));



        criarContaBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 NutricionistaDao dao = new NutricionistaDao(getApplicationContext());
                                                 dao.adicionarNutricionista(userU);
                                                 Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();

                                                 Intent voltar = new Intent(CadastroNutricionistraActivity.this, LoginActivity.class);
                                                 startActivity(voltar);

                                             }
                                         }

        );

    }
}

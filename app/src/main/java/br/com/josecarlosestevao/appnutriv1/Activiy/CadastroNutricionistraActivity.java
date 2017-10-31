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
import br.com.josecarlosestevao.appnutriv1.Nutricionista.NutricionistaDao;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.UsuarioDAO;

public class CadastroNutricionistraActivity extends AppCompatActivity {

    EditText crnEditText;
    Button criarContaBtn;
    UsuarioDAO loginAdapter;
    Bundle bundle = new Bundle();
    TextView cadastro_data_nasc;
    SessionManager session;
    private Nutricionista usercad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nutricionistra);

        crnEditText = (EditText) findViewById(R.id.crnEditText);
        criarContaBtn = (Button) findViewById(R.id.criarContaBtn);


        criarContaBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 if (usercad == null) {
                                                     usercad = new Nutricionista();
                                                 }

                                                 Intent intent = getIntent();

                                                 Bundle bundle = intent.getExtras();
                                                 byte[] x = bundle.getByteArray("foto");
                                                 String crn = crnEditText.getText().toString();
                                                 usercad.setCrn(crn);
                                                 usercad.setFoto(x);
                                                 usercad.setNome(bundle.getString("nome"));
                                                 usercad.setSenha(bundle.getString("senha"));
                                                 usercad.setEmail(bundle.getString("email"));
                                                 NutricionistaDao dao = new NutricionistaDao(getApplicationContext());
                                                 dao.adicionarNutricionista(usercad);
                                                 Toast.makeText(getApplicationContext(), "Conta criada com sucesso", Toast.LENGTH_LONG).show();

                                                 Intent voltar = new Intent(CadastroNutricionistraActivity.this, LoginActivity.class);
                                                 startActivity(voltar);

                                             }
                                         }

        );

    }
}

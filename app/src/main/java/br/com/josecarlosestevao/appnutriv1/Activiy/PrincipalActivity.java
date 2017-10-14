package br.com.josecarlosestevao.appnutriv1.Activiy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;

public class PrincipalActivity extends AppCompatActivity {


    SessionManager session;
    TextView nomePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        nomePerfil = (TextView) findViewById(R.id.textview);
        nomePerfil.setText(name);

    }
}

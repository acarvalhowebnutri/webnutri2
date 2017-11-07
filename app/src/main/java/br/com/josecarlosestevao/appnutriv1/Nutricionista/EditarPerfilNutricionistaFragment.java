package br.com.josecarlosestevao.appnutriv1.Nutricionista;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import br.com.josecarlosestevao.appnutriv1.Constantes.ConversorImagem;
import br.com.josecarlosestevao.appnutriv1.Constantes.SelectDateFragment;
import br.com.josecarlosestevao.appnutriv1.ControleSessao.SessionManager;
import br.com.josecarlosestevao.appnutriv1.R;
import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;

/**
 * Created by Dell on 06/01/2017.
 */
public class EditarPerfilNutricionistaFragment extends Fragment {

    private static final int ID_RETORNO_TIRA_FOTO_OBJETO = 5678;
    private static final int RESULT_GALERIA = 1234;
    private static final int MENU_FOTO = Menu.FIRST;
    private static final int MENU_GALERIA = 2;
    NutricionistaDao loginAdapter;
    EditText tvnome, tvcrn, tvemail, senhaEditText, confirmarsenhaEditText;
    Button salvar, deletar, alterarNutri;
    SessionManager session;
    Nutricionista nutricionista;
    private ImageView campoFotoNutricionista;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_editar_frag_perfil_nutricionista, null);


        loginAdapter = new NutricionistaDao(getContext());
        //    loginAdapter = loginAdapter.open();


        final Intent intent = new Intent();
        Bundle params = intent.getExtras();

        tvnome = (EditText) view.findViewById(R.id.editTextExibeNOME);
        tvcrn = (EditText) view.findViewById(R.id.editTextExibeCRN);
        tvemail = (EditText) view.findViewById(R.id.editTextExibeEMAIL);
        senhaEditText = (EditText) view.findViewById(R.id.senhaEditText);
        confirmarsenhaEditText = (EditText) view.findViewById(R.id.confirmarsenhaEditText);


        alterarNutri = (Button) view.findViewById(R.id.buttonAlterar);
        campoFotoNutricionista = (ImageView) view.findViewById(R.id.imageViewFotoNutricionista);


        campoFotoNutricionista.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, MENU_FOTO, 0, "TIRAR FOTO");
                menu.add(1, MENU_GALERIA, 1, "GALERIA");


            }
        });


        carregarDadosNutricionistas();


        alterarNutri.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                nutricionista.setCrn(tvcrn.getText().toString());

                if (!senhaEditText.equals(confirmarsenhaEditText)) {
                    Toast.makeText(getContext(), "Senhas diferentes", Toast.LENGTH_LONG).show();
                } else {
                    nutricionista.setSenha(senhaEditText.getText().toString());
                }
                nutricionista.setEmail(tvemail.getText().toString());


                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");


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

    private void carregarfoto(byte[] x) {

        // nomeEditText.setText(usuario.getNome());


        campoFotoNutricionista.setImageBitmap(ConversorImagem
                .converteByteArrayPraBitmap(x));
        nutricionista.setFoto(x);
        //validaCampos();

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent;
        if (item.getItemId() == MENU_FOTO) {

            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent, ID_RETORNO_TIRA_FOTO_OBJETO);


            return true;
        } else if (item.getItemId() == MENU_GALERIA) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, RESULT_GALERIA);


        }
        return super.onContextItemSelected(item);
    }

    /**
     * Trata o resultado vindo de uma Action chamada através do método
     * startActivityForResult().
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID_RETORNO_TIRA_FOTO_OBJETO && resultCode == -1) {
            if (data != null) {
                // Recupera o objeto Bundle recebido do aplicativo de fotos.
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    /*
                     * Recupera o objeto Bitmap com a foto capturada, recebido
					 * através do objeto Bundle.
					 */
                    Bitmap foto = (Bitmap) bundle.get("data");
                    /*
                     * Define a imagem no componente ImageView da tela, para ser
					 * apresentada ao usuário.
					 */
                    campoFotoNutricionista.setImageBitmap(foto);
                    /*
                     * Converte o objeto Bitmap com a foto em um array de bytes
					 * (byte[]) e o injeta no objeto ObjetoEmprestado para ser
					 * persistido no banco de dados.
					 */
                    byte[] x = ConversorImagem.converteBitmapPraByteArray(foto, 70);
                    //  usuario.setFoto(ConversorImagem.converteBitmapPraByteArray(foto, 70));
                    carregarfoto(x);

                }
            }

        } else if (requestCode == RESULT_GALERIA && resultCode == -1) {
            Uri selectedImage = data.getData();
            String[] filePath = {
                    MediaStore.Images.Media.DATA
            };
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex
                    = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            campoFotoNutricionista.setImageBitmap(thumbnail);
            byte[] x = ConversorImagem.converteBitmapPraByteArray(thumbnail, 70);
            carregarfoto(x);
            // bundle.putByteArray("foto", ConversorImagem.converteBitmapPraByteArray(thumbnail, 70));
            //  user.setFoto(x);
          /*Uri imageUri = data.getData();
                      Bitmap thumbnail = (BitmapFactory.decod     eFile(picturePath));
                      campoFotoObjeto.setImageBitmap(thumbnail); String[] colunaArquivo = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imageUri, colunaArquivo, null, null, null);

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(colunaArquivo[0]);

            String picturePath = cursor.getString(columnIndex);


            //Bitmap foto = BitmapFactory.decodeFile(picturePath.toString());
            Bitmap imageBitmap = BitmapFactory.decodeFile(picturePath.toString());

            if (foto != null) {

                campoFotoObjeto.setImageBitmap(foto);

            }else
                campoFotoObjeto.setImageBitmap(BitmapFactory.decodeFile(pictu}ePath.toString()));
*/
        }
    }

}



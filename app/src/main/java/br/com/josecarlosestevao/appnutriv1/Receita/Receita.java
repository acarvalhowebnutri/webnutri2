package br.com.josecarlosestevao.appnutriv1.Receita;

import com.google.firebase.database.IgnoreExtraProperties;

import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 30/10/2017.
 */
@IgnoreExtraProperties
public class Receita {

    public Long id;
    public String alimento;
    public Usuario usuario = new Usuario();
    public Nutricionista nutricionista = new Nutricionista();
    private String data;
    private String idFb;


    public Receita() {

    }

    public Receita(Long id, String alimento) {
        this.alimento = alimento;
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdFb() {
        return idFb;
    }

    public void setIdFb(String idFb) {
        this.idFb = idFb;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

/*
    public Receita(String id, String alimento, String data, Nutricionista nutricionista, Usuario usuario) {
        // ...
        this.id = id;
        this.alimento = alimento;
        this.data = data;
        this.nutricionista = nutricionista;
        this.usuario = usuario;
    }
*/
/*
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("alimento", alimento);
        result.put("data", data);
        result.put("nutricionista", nutricionista);
        result.put("usuario", usuario);

        return result;
    }
*/

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



  /*  public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }




*/
/*
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    */
}

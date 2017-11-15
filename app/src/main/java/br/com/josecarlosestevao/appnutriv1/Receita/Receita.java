package br.com.josecarlosestevao.appnutriv1.Receita;

import br.com.josecarlosestevao.appnutriv1.Usuario.Nutricionista;
import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 30/10/2017.
 */

public class Receita {

    private String id;
    private String alimento;
    private String data;
    //private String usuario;
    private Usuario usuario = new Usuario();
    private Nutricionista nutricionista = new Nutricionista();


    public Receita(String id, String alimento, String data, Nutricionista nutricionista, Usuario usuario) {
        // ...
        this.id = id;
        this.alimento = alimento;
        this.data = data;
        this.nutricionista = nutricionista;
        this.usuario = usuario;
    }

    public Receita() {

    }


    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    /*

        public String getNutricionista() {
            return nutricionista;
        }

        public void setNutricionista(String nutricionista) {
            this.nutricionista = nutricionista;
        }

        private String nutricionista;
    */
    public String getId() {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


/*
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    */
}

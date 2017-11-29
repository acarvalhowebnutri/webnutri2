package br.com.josecarlosestevao.appnutriv1.Metas;

import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 28/11/2017.
 */

public class Metas {

    private String vitamin;
    private String vitamax;
    private String idPacienteFB;
    private Usuario usuario;
    private long id;

    public Metas() {

    }

    public Metas(String vitamin, String vitamax, String idPacienteFB, Long id, Usuario usuario) {
        this.vitamin = vitamin;
        this.vitamax = vitamax;
        this.idPacienteFB = idPacienteFB;
        this.id = id;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }

    public String getVitamax() {
        return vitamax;
    }

    public void setVitamax(String vitamax) {
        this.vitamax = vitamax;
    }

    public String getIdPacienteFB() {
        return idPacienteFB;
    }

    public void setIdPacienteFB(String idPacienteFB) {
        this.idPacienteFB = idPacienteFB;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

package br.com.josecarlosestevao.appnutriv1.Consumo;

import java.io.Serializable;

import br.com.josecarlosestevao.appnutriv1.Usuario.Usuario;

/**
 * Created by Dell on 09/12/2016.
 */



public class Consumo implements Serializable {
    public static final long serialVersionUID = 1l;

    private Long id;
    private String alimento;
    private String carboidrato;
    private String proteina;
    private String data;
    private String datareceita;
    private Usuario usuario = new Usuario();
    private String tipo;

    public Consumo() {
    }

    public Consumo(long id, String alimento, String carboidrato, String proteina, String data, String datareceita, String tipo) {
        this.id = id;
        this.alimento = alimento;
        this.carboidrato = carboidrato;
        this.proteina = proteina;
        this.data = data;
        this.datareceita = datareceita;
        this.tipo = tipo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDatareceita() {
        return datareceita;
    }

    public void setDatareceita(String datareceita) {
        this.datareceita = datareceita;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData(String data) {

        return this.data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(String carboidrato) {
        this.carboidrato = carboidrato;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }


}

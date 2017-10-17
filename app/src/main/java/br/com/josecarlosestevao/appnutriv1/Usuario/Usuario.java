package br.com.josecarlosestevao.appnutriv1.Usuario;

import java.io.Serializable;

/**
 * Created by Dell on 10/02/2017.
 */
public class Usuario implements Serializable {
    public static final long serialVersionUID = 1l;

    private Long id;
    private String nome;
    private String senha;
    private Integer alimentoId;
    private String dataNasc;
    private String peso;
    private String sexo;
    private String altura;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getDataNasc() {

        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    private byte[] foto;
    private Double valor;

    public Usuario(Long id, String nome, String senha, Double valor) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getAlimentoId() {
        return alimentoId;
    }

    public void setAlimentoId(Integer alimentoId) {
        this.alimentoId = alimentoId;
    }

    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }


    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, Integer alimentoId) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.alimentoId = alimentoId;
    }
}

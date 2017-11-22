package br.com.josecarlosestevao.appnutriv1.Usuario;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by Dell on 16/10/2017.
 */

public class Nutricionista implements Serializable {
    public static final long serialVersionUID = 1l;

    FirebaseDatabase database;

    private DatabaseReference mDatabase;

    private String id;
    private String nome;
    private String senha;
    private String email;
    private String crn;
    private byte[] foto;

    public Nutricionista(String id, String nome, String senha, String crn, String email) {
        this.id = id;
        this.nome = nome;
        this.email = senha;
        this.email = crn;
        this.email = email;
    }

    public Nutricionista() {
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }


}

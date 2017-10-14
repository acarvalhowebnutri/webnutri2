package br.com.josecarlosestevao.draweractivity.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dell on 09/12/2016.
 */
public class AlimentoConsumido implements Serializable {
    public static final long serialVersionUID = 1l;

    private Long id;
    private String alimento;
    private Date data;
    private Calendar dt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AlimentoConsumido() {

    }

    public AlimentoConsumido(long id, String alimento, Date data ){
        this.id =id;
        this.alimento=alimento;
        this.data=data;

    }


    public Date getData() {
        return data;
    }

    public void setData(String data) {
        this.data = new Date(data);
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }


}

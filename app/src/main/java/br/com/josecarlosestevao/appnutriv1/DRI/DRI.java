package br.com.josecarlosestevao.appnutriv1.DRI;

import java.io.Serializable;

/**
 * Created by Dell on 09/11/2017.
 */

public class DRI implements Serializable

{
    public static final long serialVersionUID = 1l;

    private String vitaminaAmin;
    private String vitaminaAmax;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVitaminaAmax() {
        return vitaminaAmax;
    }

    public void setVitaminaAmax(String vitaminaAmax) {
        this.vitaminaAmax = vitaminaAmax;
    }

    public String getVitaminaAmin() {
        return vitaminaAmin;

    }

    public void setVitaminaAmin(String vitaminaAmin) {
        this.vitaminaAmin = vitaminaAmin;
    }
}

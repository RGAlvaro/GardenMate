package com.example.gardenmate;

import java.util.ArrayList;

public class Terminal {
    private String codigo_id;
    private String usuario;
    private ArrayList<Medicion> mediciones;

    public Terminal() {
    }

    public Terminal(String codigo_id, String usuario, ArrayList<Medicion> mediciones) {
        this.codigo_id = codigo_id;
        this.usuario = usuario;
        this.mediciones = mediciones;
    }

    public String getCodigo_id() {
        return codigo_id;
    }

    public void setCodigo_id(String codigo_id) {
        this.codigo_id = codigo_id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(ArrayList<Medicion> mediciones) {
        this.mediciones = mediciones;
    }
}

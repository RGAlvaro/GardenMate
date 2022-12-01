package com.example.gardenmate;

import java.util.ArrayList;

public class Terminal {
    private String codigo_id;
    private String usuario;
    private String maceta;
    private String imagen;
    private ArrayList<Medicion> mediciones;

    public Terminal() {
    }

    public Terminal(String codigo_id, String usuario, ArrayList<Medicion> mediciones, String maceta, String imagen) {
        this.codigo_id = codigo_id;
        this.usuario = usuario;
        this.mediciones = mediciones;
        this.maceta = maceta;
        this.imagen = imagen;
    }

    public String getMaceta() {return maceta;}

    public void setMaceta(String maceta) {this.maceta = maceta;}

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen; }

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

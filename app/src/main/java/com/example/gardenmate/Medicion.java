package com.example.gardenmate;


import java.util.Date;

public class Medicion {
    private double humedad,temperatura,tierra;
    private Date fecha;

    public Medicion(double humedad, double temperatura, double tierra, Date fecha) {
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.tierra = tierra;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Medicion{" +
                "humedad=" + humedad +
                ", temperatura=" + temperatura +
                ", tierra=" + tierra +
                ", fecha=" + fecha +
                '}';
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public double getTierra() {
        return tierra;
    }

    public void setTierra(int tierra) {
        this.tierra = tierra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

package com.example.rampasimage.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by CHENAO on 6/08/2017.
 */

public class Usuario implements Serializable {

    String Id_reg;
    String rampa;
    String caja;
    String destino;
    String accion;
    String tipo;
    String salida;

    String imagen;


    public Usuario(String Id_reg, String rampa, String caja, String destino, String accion, String tipo, String salida, String imagen) {
        this.Id_reg = Id_reg;
        this.rampa = rampa;
        this.caja = caja;
        this.destino = destino;
        this.accion = accion;
        this.tipo = tipo;
        this.salida = salida;
        this.imagen = imagen;

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId_reg() {
        return Id_reg;
    }

    public void setId_reg(String id_reg) {
        Id_reg = id_reg;
    }

    public String getRampa() {
        return rampa;
    }

    public void setRampa(String rampa) {
        this.rampa = rampa;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }
}

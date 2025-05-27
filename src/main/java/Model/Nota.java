
package Model;

import java.util.Date;


public class Nota {
    private int idNota;
    private Date fechaNota;
    private double califNota;
    private int codigoStu;
    private int codigoDcte;

    // Getters y Setters

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public Date getFechaNota() {
        return fechaNota;
    }

    public void setFechaNota(Date fechaNota) {
        this.fechaNota = fechaNota;
    }

    public double getCalifNota() {
        return califNota;
    }

    public void setCalifNota(double califNota) {
        this.califNota = califNota;
    }

    public int getCodigoStu() {
        return codigoStu;
    }

    public void setCodigoStu(int codigoStu) {
        this.codigoStu = codigoStu;
    }

    public int getCodigoDcte() {
        return codigoDcte;
    }

    public void setCodigoDcte(int codigoDcte) {
        this.codigoDcte = codigoDcte;
    }
    
}


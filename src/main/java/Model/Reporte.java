
package Model;

import java.util.Date;


public class Reporte {
    private int idReporte;
    private double promGralReporte;
    private Date fechaGenReporte;
    private String estReporte;
    private int idCurso;
    private int idUsu;

    // Getters y Setters

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public double getPromGralReporte() {
        return promGralReporte;
    }

    public void setPromGralReporte(double promGralReporte) {
        this.promGralReporte = promGralReporte;
    }

    public Date getFechaGenReporte() {
        return fechaGenReporte;
    }

    public void setFechaGenReporte(Date fechaGenReporte) {
        this.fechaGenReporte = fechaGenReporte;
    }

    public String getEstReporte() {
        return estReporte;
    }

    public void setEstReporte(String estReporte) {
        this.estReporte = estReporte;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }
    
}

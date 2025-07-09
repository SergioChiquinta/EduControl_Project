
package Model;

public class EvaluacionNota {

    private String nombreEvaluacion;
    private double peso;
    private double nota;
    private String estado;
    private String estadoColor;

    // Getters y setters
    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoColor() {
        return estadoColor;
    }

    public void setEstadoColor(String estadoColor) {
        this.estadoColor = estadoColor;
    }

}

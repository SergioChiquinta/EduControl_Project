
package Model;

import java.util.List;

public class AsignaturaNota {
    
    private int id;
    private String nombreMateria;
    private double promedioMateria;
    private List<EvaluacionNota> evaluaciones;
    private String estadoGeneral;
    
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public double getPromedioMateria() {
        return promedioMateria;
    }

    public void setPromedioMateria(double promedioMateria) {
        this.promedioMateria = promedioMateria;
    }

    public List<EvaluacionNota> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<EvaluacionNota> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
    
    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }
    
}

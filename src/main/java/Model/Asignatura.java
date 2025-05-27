
package Model;


public class Asignatura {
    private int idAsig;
    private String nomAsig;
    private int idCurso;
    private int idAdmin;

    // Getters y Setters

    public int getIdAsig() {
        return idAsig;
    }

    public void setIdAsig(int idAsig) {
        this.idAsig = idAsig;
    }

    public String getNomAsig() {
        return nomAsig;
    }

    public void setNomAsig(String nomAsig) {
        this.nomAsig = nomAsig;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
    
}


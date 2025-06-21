
package Model;


public class Curso {
    private int id;
    private int materiaId;
    private int salonId;
    private int periodoId;
    private String nombreMateria; 
    private String nombreSalon;   
    private String nombrePeriodo; 
    private int estudiantesInscritos; 

    public Curso() {
    }

    public Curso(int id, int materiaId, int salonId, int periodoId, String nombreMateria, String nombreSalon, String nombrePeriodo, int estudiantesInscritos) {
        this.id = id;
        this.materiaId = materiaId;
        this.salonId = salonId;
        this.periodoId = periodoId;
        this.nombreMateria = nombreMateria;
        this.nombreSalon = nombreSalon;
        this.nombrePeriodo = nombrePeriodo;
        this.estudiantesInscritos = estudiantesInscritos;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getNombreSalon() {
        return nombreSalon;
    }

    public void setNombreSalon(String nombreSalon) {
        this.nombreSalon = nombreSalon;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public int getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public void setEstudiantesInscritos(int estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos;
    }
}
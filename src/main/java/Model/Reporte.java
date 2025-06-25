
package Model;

public class Reporte {
    private int id;
    private int estudianteId;
    private int salonId;
    private int periodoId;

    private String nombreEstudiante; // para mostrar
    private String nombreSalon;      // opcional para vista
    private String nombrePeriodo;    // opcional para vista

    private double promedioGeneral;
    private String estadoAcademico;
    private byte[] pdf;
    private String fechaGeneracion;

    // Constructor

    public Reporte(int id, int estudianteId, int salonId, int periodoId, String nombreEstudiante, String nombreSalon, String nombrePeriodo, double promedioGeneral, String estadoAcademico, byte[] pdf, String fechaGeneracion) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.salonId = salonId;
        this.periodoId = periodoId;
        this.nombreEstudiante = nombreEstudiante;
        this.nombreSalon = nombreSalon;
        this.nombrePeriodo = nombrePeriodo;
        this.promedioGeneral = promedioGeneral;
        this.estadoAcademico = estadoAcademico;
        this.pdf = pdf;
        this.fechaGeneracion = fechaGeneracion;
    }

    // Constructor vacío necesario para poder usar new Reporte()
    public Reporte() {
        
    }
    
    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
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

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
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

    public double getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setPromedioGeneral(double promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

    public String getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(String estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
    
}

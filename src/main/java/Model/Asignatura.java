
package Model;


public class Asignatura {
    private int id;
    private String nombre;
    private int docenteId; //no se muestra en la vista pero por siacaso lo pongo

    public Asignatura() {
    }

    public Asignatura(int id, String nombre, int docenteId) {
        this.id = id;
        this.nombre = nombre;
        this.docenteId = docenteId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }
}


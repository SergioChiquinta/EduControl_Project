
package Model;

public class Usuario {

    private int id;
    private String username;
    private String correo;
    private String rol;

    public Usuario(int id, String username, String correo, String rol) {
        this.id = id;
        this.username = username;
        this.correo = correo;
        this.rol = rol;
    }

    public Usuario() {
        // Constructor vacío implementado
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

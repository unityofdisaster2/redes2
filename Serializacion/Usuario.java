import java.io.Serializable;

public class Usuario implements Serializable {
    String nombre;
    String paterno;
    String materno;
    transient String pwd;
    int edad;

    public Usuario(String nombre, String paterno, String materno, String pwd, int edad) {
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.pwd = pwd;
        this.edad = edad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return this.paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return this.materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setPWD(String pwd) {
        this.pwd = pwd;
    }

    public String getPWD() {
        return this.pwd;
    }

}
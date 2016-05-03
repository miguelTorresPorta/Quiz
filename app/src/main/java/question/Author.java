package question;

/**
 * Created by migueltorresporta on 29/4/16.
 */
public class Author {

    private String nombre;
    private String descripcion;

    public Author (String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre (){
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }


}

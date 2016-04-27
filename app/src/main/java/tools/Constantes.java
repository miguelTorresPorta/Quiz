package tools;

/**
 * Clase que contiene los códigos usados en "I Wish" para
 * mantener la integridad en las interacciones entre actividades
 * y fragmentos
 */
public class Constantes {
    /**
     * Transición Home -> Detalle
     */
    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = "";
    /**
     * Dirección IP de para la AVD
     */
    private static final String IP = "10.0.2.2";
    /**
     * Direccion IP para movil, consultar con ifconfig en el terminal
     */
    //private static final String IP = "192.168.43.153";
    /**
     * URLs del Web Service
     */

    public static final String GET_CHECK = "http://" + IP + PUERTO_HOST + "/Quiz/user/check_user.php";

    public static final String INSERT_USER = "http://" + IP + PUERTO_HOST + "/Quiz/user/insertar_usuario.php";

    public static final String GET_AUTHORS = "http://" + IP + PUERTO_HOST + "/Quiz/Test/get_authors.php";

    //public static final String GET = "http://" + IP + PUERTO_HOST + "/Wish/obtener_metas.php";
    //public static final String GET_BY_ID = "http://" + IP + PUERTO_HOST + "/Wish/obtener_meta_por_id.php";
    //public static final String UPDATE = "http://" + IP + PUERTO_HOST + "/Wish/actualizar_meta.php";
    //public static final String DELETE = "http://" + IP + PUERTO_HOST + "/Wish/borrar_meta.php";
    //public static final String INSERT = "http://" + IP + PUERTO_HOST + "/Wish/insertar_meta.php";

    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    //public static final String EXTRA_ID = "IDEXTRA";

}

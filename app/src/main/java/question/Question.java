package question;

import android.app.Activity;

/**
 * Created by migueltorresporta8.
 */

public class Question{

    private String numPregunta;
    private String tituloPregunta;
    private String tipo;

    public Question (String tituloPregunta, String tipo){
        this.numPregunta = numPregunta;
        this.tituloPregunta = tituloPregunta;
        this.tipo = tipo;
    }

    public String getTituloPregunta() {
        return tituloPregunta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



}


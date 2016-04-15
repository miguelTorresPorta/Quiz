package question;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import miguel.quiz.R;

/**
 * Created by migueltorresporta.
 */
public class TrueFalse extends Question{

    private boolean correcto;
    private String respuesta;

    public TrueFalse(String tituloPregunta, String tipo, boolean correcto, String respuesta) {
        super(tituloPregunta, tipo);

        this.correcto = correcto;
        this.respuesta = respuesta;

    }

    public boolean isCorrecto(){
        return correcto;
    }

    public String getRespuesta(){
        return respuesta;
    }



}


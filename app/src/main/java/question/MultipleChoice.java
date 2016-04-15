package question;

/**
 * Created by migueltorresporta.
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import miguel.quiz.R;
import miguel.quiz.ResourceInterfaz;

public class MultipleChoice extends Question {
    ArrayList<Boolean> respuestas;
    ArrayList<String> feedback;

    public MultipleChoice(String tituloPregunta, String tipo, ArrayList<Boolean> respuestas, ArrayList<String> feedback) {
        super(tituloPregunta, tipo);
        this.respuestas = new ArrayList<Boolean>();
        this.feedback = new ArrayList<String>();
        this.respuestas.addAll(respuestas);
        this.feedback.addAll(feedback);
    }

    // Constructor por defecto
    public MultipleChoice(){
        super(null, null);
    }


    public ArrayList<Boolean> getRespuestas(){
        return respuestas;
    }

    public ArrayList<String> getFeedback(){
        return feedback;
    }

    public String getFeebackByPos(int i){
        return this.feedback.get(i);
    }

}
package question;

/**
 * Created by migueltorresporta.
 */

import java.util.ArrayList;

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


    public ArrayList<Boolean> getRespuestas(){
        return respuestas;
    }

    public String getFeebackByPos(int i){
        return this.feedback.get(i);
    }

}
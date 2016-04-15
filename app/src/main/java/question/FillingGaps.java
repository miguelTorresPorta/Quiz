package question;

/**
 * Created by migueltorresporta.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import miguel.quiz.R;

public class FillingGaps extends Question{
    //ArrayList<String> respuestas;
    private ArrayList<String> respuestas;
    private ArrayList<String> tit;
    private Integer numRespuestas;


    public FillingGaps(String tituloPregunta, String tipo, String respuestas) {
        super(tituloPregunta, tipo);
        this.respuestas = new ArrayList<String>();
        this.tit = new ArrayList<String>();
        this.numRespuestas = 0;

        respuestas = respuestas.trim();
        //respuestas.indexOf(":");
        respuestas = respuestas.substring(respuestas.indexOf(":") + 1, respuestas.length());
        String[] listaRespuestas = respuestas.split("/");
        int lenghtListaRespuetas = listaRespuestas.length;
        for (int i = 0; i < lenghtListaRespuetas; i++){
            this.respuestas.add(listaRespuestas[i].trim());
            this.numRespuestas++;
        }
        String[] listTitulo = tituloPregunta.split("_");

        int lenghtListaTitulo = listTitulo.length;
        for (int i = 0; i < lenghtListaTitulo; i++){
            if (!listTitulo[i].isEmpty()){
                this.tit.add(listTitulo[i].trim());
            }
        }

    }

    public ArrayList<String> getRespuestas(){
        return this.respuestas;
    }

    public ArrayList<String> getTit(){
        return this.tit;
    }




}

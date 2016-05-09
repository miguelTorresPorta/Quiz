package question;

/**
 * Created by migueltorresporta.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import miguel.quiz.R;

public class FillingGaps extends Question{

    private ArrayList<String> respuestasCorrectas;
    private ArrayList<String> respuestasAleatorio;
    private ArrayList<String> tit;
    private ArrayList<String> list;

    public FillingGaps(String tituloPregunta, String tipo, String respuestas) {
        super(tituloPregunta, tipo);
        this.respuestasCorrectas = new ArrayList<String>();
        this.tit = new ArrayList<String>();
        this.respuestasAleatorio = new ArrayList<>();

        respuestas = respuestas.trim();
        respuestas = respuestas.substring(respuestas.indexOf(":") + 1, respuestas.length());

        // Separamos las respuestas
        String[] listaRespuestas = respuestas.split("/");
        int lenghtListaRespuetas = listaRespuestas.length;

        // Introducimos cada respuesta en el arraylist
        for (int i = 0; i < lenghtListaRespuetas; i++){
            this.respuestasCorrectas.add(listaRespuestas[i].trim());
        }

        String[] listTitulo = tituloPregunta.split("_");

        int lenghtListaTitulo = listTitulo.length;
        for (int i = 0; i < lenghtListaTitulo; i++){
            if (!listTitulo[i].isEmpty()){
                this.tit.add(listTitulo[i].trim());
            }
        }

        // TODO Depurar Array ¿?
        ArrayList<String> ll = new ArrayList<>();
        for (int i = 0; i < this.tit.size() || i < this.respuestasCorrectas.size(); i++){
            if (i < this.tit.size())
                ll.add(this.tit.get(i));
            if (i < this.respuestasCorrectas.size()){
                String s = "";
                for (int j = 0; j < this.respuestasCorrectas.get(i).length(); j++)
                    s = s + "_";
                ll.add(s);
            }
        }
        this.list = ll;

        this.respuestasAleatorio.addAll(respuestasCorrectas);

        Collections.shuffle(this.respuestasAleatorio);

        int a = 0;

    }

    public ArrayList<String> getRespuestasCorrectas(){
        return this.respuestasCorrectas;
    }

    public ArrayList<String> getRespuestasAleatorio(){
        return this.respuestasAleatorio;
    }

    public ArrayList<String> getList() {
        return this.list;
    }

    public void setList(int posSelec, int which) {
        this.list.set(posSelec, this.respuestasAleatorio.get(which));
    }
}

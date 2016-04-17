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
    private ArrayList<String> list;


    public FillingGaps(String tituloPregunta, String tipo, String respuestas) {
        super(tituloPregunta, tipo);
        this.respuestas = new ArrayList<String>();
        this.tit = new ArrayList<String>();

        respuestas = respuestas.trim();
        //respuestas.indexOf(":");
        respuestas = respuestas.substring(respuestas.indexOf(":") + 1, respuestas.length());
        String[] listaRespuestas = respuestas.split("/");
        int lenghtListaRespuetas = listaRespuestas.length;
        for (int i = 0; i < lenghtListaRespuetas; i++){
            this.respuestas.add(listaRespuestas[i].trim());
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
        for (int i = 0; i < this.tit.size() || i < this.respuestas.size(); i++){
            if (i < this.tit.size())
                ll.add(this.tit.get(i));
            if (i < this.respuestas.size()){
                String s = "";
                for (int j = 0; j < this.respuestas.get(i).length(); j++)
                    s = s + "_";
                ll.add(s);
            }

                //ll.add(this.tit.get(i));
        }
        this.list = ll;
        }


    public ArrayList<String> getRespuestas(){
        return this.respuestas;
    }

    public ArrayList<String> getTit(){
        return this.tit;
    }


    public ArrayList<String> getList() {
        return this.list;
    }

    public void setList(int posSelec, int which) {
        this.list.set(posSelec, this.respuestas.get(which));
    }
}

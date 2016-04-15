package question;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by migueltorresporta on 29/3/16.
 */
public class Test {

    private String name;
    private String description;
    //private int numQuestions;
    private ArrayList <Question> questionsList;
    //private ArrayList <FillingGaps> fillingGaps;
    //private ArrayList <TrueFalse> trueFalse;
    //private ArrayList <MultipleChoice> multipleChoice;


    public Test (String name, String description, ArrayList<FillingGaps> fillingGaps, ArrayList<TrueFalse> trueFalse, ArrayList<MultipleChoice> multipleChoice){
        this.name = name;
        this.description = description;

        this.questionsList = new ArrayList<>();

        this.questionsList.addAll(trueFalse);
        this.questionsList.addAll(fillingGaps);
        this.questionsList.addAll(multipleChoice);

        //this.fillingGaps = new ArrayList<FillingGaps>();
        //this.trueFalse = new ArrayList<TrueFalse>();
        //this.multipleChoice = new ArrayList<MultipleChoice>();

        //this.fillingGaps.addAll(fillingGaps);
        //this.trueFalse.addAll(trueFalse);
        //this.multipleChoice.addAll(multipleChoice);
        //this.numQuestions = fillingGaps.size() + trueFalse.size() + multipleChoice.size();
    }


    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getNumQuestions() {
        return this.questionsList.size();
    }

    public Question getQuestionByPosition(int position){
        return questionsList.get(position);
    }

     /* public ArrayList<TrueFalse> getQuestionTrueFalse(){

        return this.trueFalse;
    }*/

   /* public ArrayList<MultipleChoice> getQuestionMultipleChoice() {
        return this.multipleChoice;
    }*/

  /*  public ArrayList<FillingGaps> getFillingGaps() {
        return this.fillingGaps;
    }*/
}

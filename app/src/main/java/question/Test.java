package question;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by migueltorresporta on 29/3/16.
 */
public class Test {

    private String name;
    private String description;
    private ArrayList <Question> questionsList;


    public Test (String name, String description, ArrayList<FillingGaps> fillingGaps, ArrayList<TrueFalse> trueFalse, ArrayList<MultipleChoice> multipleChoice){
        this.name = name;
        this.description = description;

        this.questionsList = new ArrayList<>();

        this.questionsList.addAll(trueFalse);
        this.questionsList.addAll(fillingGaps);
        this.questionsList.addAll(multipleChoice);

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

 }

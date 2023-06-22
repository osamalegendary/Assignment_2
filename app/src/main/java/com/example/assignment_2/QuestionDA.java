package com.example.assignment_2;

import java.util.ArrayList;
import java.util.List;

public class QuestionDA    {

    private ArrayList<Question> quest = new ArrayList<>();
    public QuestionDA(){

        quest.add(new Question("20 + 20 = ?","30","40","50","35","40"));
        quest.add(new Question("90 / 30 = ?","30","60","3","25","3"));
        quest.add(new Question("8 * 6 = ?","48","59","70","22","48"));
        quest.add(new Question("100 - 33,5 = ?","60","66","65","66,5","66,5"));


    }


    public List<Question> getQuestionList() {
        return quest;
    }
    public List<Question> getAnswer(String correct){

        ArrayList<Question> answers = new ArrayList<>();

        for(Question q : quest)

            if(q.getCorrect().equals(correct))
            {
                answers.add(q);

            }


        return answers;
    }
}


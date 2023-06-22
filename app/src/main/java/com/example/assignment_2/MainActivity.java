package com.example.assignment_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;

    private TextView questionTextView;
    private RadioGroup answerRadioGroup;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.textItem);
        answerRadioGroup = findViewById(R.id.answerGroup);
        nextButton = findViewById(R.id.nextButton);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        // Create and populate the question list
        QuestionDA questionDA = new QuestionDA();
        questionList = questionDA.getQuestionList();

        // Display the first question
        displayQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the selected answer
                int selectedRadioButtonId = answerRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedAnswer = selectedRadioButton.getText().toString();
                saveAnswer(selectedAnswer);

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion();
                } else {
                    // All questions have been answered
                    // You can perform any necessary actions here, such as showing a result or score
                    showResult();
                }
            }
        });
    }

    private void displayQuestion() {
        Question question = questionList.get(currentQuestionIndex);

        // Set the question text
        questionTextView.setText(question.getQuestion());

        // Set the answer options
        RadioButton answer1RadioButton = findViewById(R.id.answer1);
        RadioButton answer2RadioButton = findViewById(R.id.answer2);
        RadioButton answer3RadioButton = findViewById(R.id.answer3);
        RadioButton answer4RadioButton = findViewById(R.id.answer4);

        answer1RadioButton.setText(question.getAnswer1());
        answer2RadioButton.setText(question.getAnswer2());
        answer3RadioButton.setText(question.getAnswer3());
        answer4RadioButton.setText(question.getAnswer4());

        // Clear the selection
        answerRadioGroup.clearCheck();
    }

    private void saveAnswer(String answer) {
        String currentQuestionKey = "question_" + currentQuestionIndex;
        editor.putString(currentQuestionKey, answer);
        editor.apply();
    }

    private void showResult() {
        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < questionList.size(); i++) {
            String question = questionList.get(i).getQuestion();
            String userAnswerKey = "question_" + i;
            String userAnswer = sharedPreferences.getString(userAnswerKey, "");
            String correctAnswer = questionList.get(i).getCorrect();
            resultBuilder.append("Question: ").append(question).append("\n");
            resultBuilder.append("Your Answer: ").append(userAnswer).append("\n");
            resultBuilder.append("Correct Answer: ").append(correctAnswer).append("\n\n");
        }
        String result = resultBuilder.toString();

        // Display the result (you can customize how you want to show it, e.g., in a TextView)
        // For example, if you have a TextView with the ID "resultTextView":
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(result);
    }
}

package codepath.flashcard;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import codepath.flashcard.classes.Question;

public class MainActivity extends AppCompatActivity {

    private int questionIndex = 1;
    private ArrayList<Question> questions = Question.questions;
    private Question currentQuestion =  questions.get(questionIndex);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // load questions
        Question.loadQuestionData(getApplicationContext());
        for (Question question : Question.questions) {
            Log.e("Question: ", String.valueOf(question));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setQuestion();
    }


    private void setQuestion() {

        // main question
        final TextView question = (TextView)findViewById(R.id.question);
        question.setText(currentQuestion.getQuestion());

        // set options
        setQuestionOptions();


    }

    private void setQuestionOptions() {

        // question options
        ArrayList<Button> options = new ArrayList<Button>();
        options.add((Button)findViewById(R.id.option1));
        options.add((Button)findViewById(R.id.option2));
        options.add((Button)findViewById(R.id.option3));

        int optionIndex = 0;
        for (String option : currentQuestion.getOptions()) {
            final Button optionBtn = options.get(optionIndex);
            optionBtn.setText(option);
            optionIndex++;

            // add events to each option
            optionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionBtn);
                }
            });
        }
    }

    private void selectOption(Button selectedOption) {

        // check if selected option is correct
        if (getResources().getResourceEntryName(selectedOption.getId()).equals(currentQuestion.getCorrect())) {
            Log.e("CORRECT", "YES");
        }

        // handle error
        else {

        }
    }
}

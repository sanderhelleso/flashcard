package codepath.flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    private int questionIndex = 0;
    private ArrayList<Question> questions = Question.questions;
    private Question currentQuestion;
    private int totalPoints = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            boolean result = data.getExtras().getBoolean("result");
            if (result) {
                displaySnackBar();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        // load questions
        if (!Question.questionsLoaded) {
            Question.loadQuestionData(getApplicationContext());
            for (Question question : Question.questions) {
                Log.e("Question: ", String.valueOf(question));
            }

            Question.questionsLoaded = true;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set and display current question
        setQuestion();

        // initiate fab event to change activity to add question
        addQuestionIntent();

    }

    private void addQuestionIntent() {
        // fab
        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabAddQuestion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    private void togglePrevNext() {

        // prev / next
        final Button prev = (Button)findViewById(R.id.prev);
        final Button next = (Button)findViewById(R.id.next);

        // enable buttons
        prev.setEnabled(true);
        next.setEnabled(true);

        // set base colors
        prev.getBackground().setAlpha(255);
        next.getBackground().setAlpha(255);

        // disable prev if at first question
        if (questionIndex == 0) {
            prev.setEnabled(false);
            prev.getBackground().setAlpha(35);
        }

        // disable next if at last question
        if (questionIndex == questions.size() - 1) {
            next.setEnabled(false);
            next.getBackground().setAlpha(35);
        }

        // go to previous question
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex--;
                setQuestion();
            }
        });

        // go to next question
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex++;
                setQuestion();
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void setQuestion() {

        // set the current question
        currentQuestion = questions.get(questionIndex);

        // add events to toggle next / prev questions
        togglePrevNext();

        // display question
        final TextView question = (TextView)findViewById(R.id.question);
        question.setText(currentQuestion.getQuestion());

        // display question id
        final TextView questionID = (TextView)findViewById(R.id.questionID);
        questionID.setText("Question " + currentQuestion.getNr() + " / " + questions.size());

        // display how many points the question is worth
        final TextView questionPoints = (TextView)findViewById(R.id.questionPoints);
        questionPoints.setText(currentQuestion.getPoints() + " Points");

        // display total points
        final TextView totalPointsView = (TextView)findViewById(R.id.totalPoints);
        totalPointsView.setText("My Total Points: " + totalPoints);

        // set options
        setQuestionOptions();

    }

    private void setQuestionOptions() {

        // question options
        final ArrayList<Button> options = new ArrayList<Button>();
        options.add((Button)findViewById(R.id.option1));
        options.add((Button)findViewById(R.id.option2));
        options.add((Button)findViewById(R.id.option3));

        int optionIndex = 0;
        for (String option : currentQuestion.getOptions()) {
            final Button optionBtn = options.get(optionIndex);

            // reset color
            optionBtn.setBackgroundColor(rgb(124, 115, 230));

            // enable buttons
            optionBtn.setEnabled(true);

            // set text
            optionBtn.setText(option);
            optionIndex++;

            // add events to each option
            optionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionBtn, options);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectOption(Button selectedOption, ArrayList<Button> options) {

        for (Button option : options) {

            // set wrong option color
            option.setBackgroundColor(rgb(232, 74, 95));

            // disable until next question
            option.setEnabled(false);

            // display correct option
            // check if selected option is correct
            if (getResources().getResourceEntryName(option.getId()).equals(currentQuestion.getCorrect())) {

                // set correct option color
                option.setBackgroundColor(rgb(69, 235, 165));
            }
        }

        // check if selected option is correct
        if (getResources().getResourceEntryName(selectedOption.getId()).equals(currentQuestion.getCorrect())) {
            totalPoints += currentQuestion.getPoints();
        }

        else {
            // lose 100 points for wrong answer
            totalPoints -= 100;
            if (totalPoints < 0) {
                totalPoints = 0;
            }
        }

        // update score
        final TextView totalPointsView = (TextView)findViewById(R.id.totalPoints);
        totalPointsView.setText("My Total Points: " + totalPoints);
    }

    private void displaySnackBar() {
        Snackbar.make(findViewById(android.R.id.content), "Successfully created question!", Snackbar.LENGTH_SHORT).show();
    }
}

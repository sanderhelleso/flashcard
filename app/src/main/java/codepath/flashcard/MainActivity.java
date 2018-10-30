package codepath.flashcard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import codepath.flashcard.classes.Announcement;
import codepath.flashcard.classes.Question;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    // initial values of deck
    private int questionIndex = 0;
    private int totalPoints = 0;
    private ArrayList<Question> questions = Question.questions;
    private Question currentQuestion;
    private Announcement announcement = new Announcement();

    // map to store ID and answer of card
    @SuppressLint("UseSparseArrays")
    HashMap<Question, String> answers = new HashMap<>();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult
            boolean result = data.getExtras().getBoolean("result");
            if (result) {
                announcement.displaySnackBarSuccess();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        // load questions
        if (!Question.questionsLoaded) {
           loadQuestions();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set and display current question
        setQuestion();

        // initiate fab event to change activity to add question
        addQuestionIntent();

    }

    private void loadQuestions() {
        Question.loadQuestionData(getApplicationContext());
        Question.questionsLoaded = true;
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

    @SuppressLint("SetTextI18n")
    private void setQuestion() {

        // set the current question
        currentQuestion = questions.get(questionIndex);

        // display question
        final TextView question = (TextView)findViewById(R.id.question);
        question.setText(currentQuestion.getQuestion());

        // display question id
        final TextView questionID = (TextView)findViewById(R.id.questionID);
        questionID.setText("Question " +  currentQuestion.getNr());

        // display how many points the question is worth
        final TextView questionPoints = (TextView)findViewById(R.id.questionPoints);
        questionPoints.setText(currentQuestion.getPoints() + " Points");

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
            optionBtn.getBackground().clearColorFilter();

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

            // disable until next question
            option.setEnabled(false);

            // set wrong option color
            option.getBackground().setColorFilter(rgb(232, 74, 95), PorterDuff.Mode.SRC);

            if (getResources().getResourceEntryName(option.getId()).equals(currentQuestion.getCorrect())) {
                option.getBackground().setColorFilter(rgb(69, 235, 165), PorterDuff.Mode.SRC);
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

        // add answer to map
        answers.put(currentQuestion, getResources().getResourceEntryName(selectedOption.getId()));

        // update score
        final TextView totalPointsView = (TextView)findViewById(R.id.totalPoints);
        totalPointsView.setText("Total Points " + totalPoints);

        // remove question from available questions
        questions.remove(questionIndex);

        // check if out of questions
        outOfQuestions();
    }

    private void nextQuestion() {
        announcement.displaySnackBarNextQuestion(questions);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        setQuestion();
                    }
                },
                2500);
    }

    private void outOfQuestions() {
        if (questions.isEmpty()) {
            announcement.displaySnackBarRedirect();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, ShowResultActivity.class);
                            intent.putExtra("answers", answers);
                            MainActivity.this.startActivityForResult(intent, 200);
                        }
                    },
                    2000);
        }

        else {
            nextQuestion();
        }
    }
}

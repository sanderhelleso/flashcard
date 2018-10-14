package codepath.flashcard.classes;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Question {

    // all questions are stores in this list
    public static ArrayList<Question> questions = new ArrayList<Question>();

    // FIELD VARIABLES
    private int nr;
    private int points;
    private int time;
    private String question;
    private ArrayList<String> options = new ArrayList<String>();
    private String correct;

    // CONSTRUCTOR
    public Question(int nr, int points, int time, String question, ArrayList<String> options, String correct) {
        this.nr = nr;
        this.points = points;
        this.time = time;
        this.question = question;
        this.options = options;
        this.correct = correct;

        // add question to questions
        questions.add(this);
    }

    // load data containing questions
    public static String loadQuestionData(Context context) {
        String questionData = null;

        // attempt to read JSON data from file
        try {
            InputStream is = context.getAssets().open("questions.json");

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            questionData = new String(buffer, "UTF-8");
            createQuestion(questionData);

            // catch error and display
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        // return data
        return questionData;
    }

    private static void createQuestion(String data) {
        Log.e("Data: ", data);
    }


    // GETTERS
    public int getNr() {
        return nr;
    }

    public int getPoints() {
        return points;
    }

    public int getTime() {
        return time;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrect() {
        return correct;
    }
}

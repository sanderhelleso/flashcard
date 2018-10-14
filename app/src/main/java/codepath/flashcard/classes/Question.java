package codepath.flashcard.classes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
    public static void loadQuestionData(Context context) {
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
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void createQuestion(String data) throws JSONException {
        Log.e("Data: ", data);

        // store data in a JSON array
        JSONArray dataObj = new JSONArray(data);

        // for each JSON object in array, create a question
        // and store individual question in static list
        for (int i = 0; i < dataObj.length(); i++) {

            // get current JSON object
            JSONObject row = dataObj.getJSONObject(i);

            // get individual JSON properties and values
            int nr = row.getInt("questionNr");
            int points = row.getInt("points");
            int time = row.getInt("time");
            String questionQuestion = row.getString("question");

            // question options
            JSONObject options = new JSONObject(row.getString("options"));
            ArrayList<String> questionOptions = new ArrayList<String >();

            // get the three options for question and add to list
            questionOptions.add(options.getString("option1"));
            questionOptions.add(options.getString("option2"));
            questionOptions.add(options.getString("option3"));

            String correctOption = row.getString("correctOption");

            // create new question with retrieved values
            Question question = new Question(nr, points, time, questionQuestion, questionOptions, correctOption);
        }

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

    @Override
    public String toString() {
        return "Question{" +
                "nr=" + nr +
                ", points=" + points +
                ", time=" + time +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", correct='" + correct + '\'' +
                '}';
    }
}

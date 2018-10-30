package codepath.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import codepath.flashcard.classes.Question;

public class ShowResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        // fetch data from main activity and set to private map
        displayResults((HashMap<Question, String>)getIntent().getSerializableExtra("answers"));
    }

    private void displayResults(HashMap<Question, String> answers) {
        for (Map.Entry<Question, String> entry : answers.entrySet()) {
            Question key = entry.getKey();
            String value = entry.getValue();
            Log.e("ANSWERS", key + ": " + value);
        }
    }
}

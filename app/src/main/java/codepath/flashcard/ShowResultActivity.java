package codepath.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ShowResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        // fetch data from main activity and set to private map
        displayResults((HashMap<Integer, String>)getIntent().getSerializableExtra("answers"));
    }

    private void displayResults(HashMap<Integer, String> answers) {
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();
            Log.e("ANSWERS", key + ": " + value);
        }
    }
}

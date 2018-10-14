package codepath.flashcard;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import codepath.flashcard.classes.Question;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String QUESTION_DATA = Question.loadQuestionData(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package codepath.flashcard;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(intent);
    }

    int id = 0;
    private void displayResults(HashMap<Question, String> answers) {
        for (Map.Entry<Question, String> entry : answers.entrySet()) {
            Question key = entry.getKey();
            String value = entry.getValue();
            Log.e("ANSWERS", key + ": " + value);

            ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.mainConstraint);
            ConstraintSet set = new ConstraintSet();

            TextView view = new TextView(this);
            view.setText(key.getQuestion());
            view.setId(id);
            layout.addView(view,0);
            set.clone(layout);
            set.connect(view.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 120 * id);
            set.applyTo(layout);
            id++;
        }
    }
}

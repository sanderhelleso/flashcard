package codepath.flashcard;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // initate back to main acitivty fab
        backToMainActivity();

        // initiate save question fab
        saveQuestion();
    }

    private void saveQuestion() {
        final FloatingActionButton saveQuestionFab = (FloatingActionButton)findViewById(R.id.fabSaveQuestion);
        saveQuestionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.fabCancelAddQuestion).callOnClick();
            }
        });
    }

    private void backToMainActivity() {

        // back to main fab
        final FloatingActionButton backToMainFab = (FloatingActionButton)findViewById(R.id.fabCancelAddQuestion);
        backToMainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddQuestionActivity.this, MainActivity.class));
            }
        });
    }
}

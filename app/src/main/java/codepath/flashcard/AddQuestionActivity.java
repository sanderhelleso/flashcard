package codepath.flashcard;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        // initate back to main acitivty fab
        backToMainActivity();

        // initiate save question fab
        saveQuestion();
    }

    // save question
    private void saveQuestion() {
        final FloatingActionButton saveQuestionFab = (FloatingActionButton)findViewById(R.id.fabSaveQuestion);
        saveQuestionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validate, add, and dismiss
                if (validateQuestion()) {
                    Intent data = new Intent();
                    data.putExtra("result", true); // put true value that card was successfully created
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }

                // display toast error if missing fields
                else {
                    displayError();
                }
            }
        });
    }

    // dismiss activity and return to main
    private void backToMainActivity() {

        // back to main fab
        final FloatingActionButton backToMainFab = (FloatingActionButton)findViewById(R.id.fabCancelAddQuestion);
        backToMainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("result", false); // put true value that card was successfully created
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes this activity and pass data to the original activity that launched this activity
            }
        });
    }

    // validate question, check value of fields
    private boolean validateQuestion() {
        int[] ids = new int[]{R.id.questionOption0, R.id.questionOption1, R.id.questionOption2, R.id.questionOption3};
        for (int id : ids) {
            EditText field = (EditText) findViewById(id);
            if (field.getText().toString().equals("")) {
                return false;
            }
        }

        return true;
    }

    // display toast error
    private void displayError() {
        Toast.makeText(getApplicationContext(), "Please fill out every option", Toast.LENGTH_SHORT).show();
    }
}

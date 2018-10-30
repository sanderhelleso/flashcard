package codepath.flashcard.classes;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Announcement extends AppCompatActivity {

    public void displaySnackBarSuccess() {
        Snackbar.make(findViewById(android.R.id.content), "Successfully created question!", Snackbar.LENGTH_LONG).show();
    }

    public void displaySnackBarRedirect() {
        Snackbar.make(findViewById(android.R.id.content), "Awesome! Lets look at the result!", Snackbar.LENGTH_SHORT).show();
    }

    public void displaySnackBarNextQuestion(ArrayList<Question> questions) {
        Snackbar.make(findViewById(android.R.id.content), (questions.size()) + " questions to go!", Snackbar.LENGTH_SHORT).show();
    }
}

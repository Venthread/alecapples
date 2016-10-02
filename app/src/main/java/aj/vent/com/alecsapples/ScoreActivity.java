package aj.vent.com.alecsapples;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by AJ on 10/2/2016.
 */

public class ScoreActivity extends AppCompatActivity {

    TextView score;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_list);
        score = (TextView) findViewById(R.id.pointText);

        retrieveScore();


    }

    private void retrieveScore() {

        SharedPreferences prefs = getSharedPreferences(SelectionActivity.MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);

        if (prefs.getInt("score", 0) != 0) {
            int scoreRestored = prefs.getInt("score", 0); //0 is the default value.
            score.setText(Integer.toString(scoreRestored));
        }
        else{
            score.setText("No score.");
        }

    }
}

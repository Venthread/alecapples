package aj.vent.com.alecsapples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class SelectionActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_menu);
        makeStartButton();
        makeScoreButton();
    }

    private void makeScoreButton() {
        Button startButton = (Button) findViewById(R.id.button2);
        startButton.setText("High Score");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScoreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeStartButton() {
        Button startButton = (Button) findViewById(R.id.button1);
        startButton.setText("Start Game!");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

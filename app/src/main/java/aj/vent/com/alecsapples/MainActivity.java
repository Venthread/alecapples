package aj.vent.com.alecsapples;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView alec;
    AnimatorSet animSetXY;
    View tree1;
    View tree2;
    View tree3;
    TextView appleCountBox;
    TextView pointCountBox;
    TextView timerBox;
    View botBox;
    Button button;
    int apples = 0;
    int point;
    int percentageX;
    int percentageY;
    ObjectAnimator animX;
    ObjectAnimator animY;
    private AnimatorSet animSetXY2;
    private ObjectAnimator animY2;
    private ObjectAnimator animX2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alec = (ImageView) findViewById(R.id.alecView);
        tree1 = findViewById(R.id.tree1);
        tree2 = findViewById(R.id.tree2);
        tree3 = findViewById(R.id.tree3);
        botBox = findViewById(R.id.botBox);
        appleCountBox = (TextView) findViewById(R.id.appleCountBox);
        appleCountBox.setText("Apples: 0");
        pointCountBox = (TextView) findViewById(R.id.pointCountBox);
        timerBox = (TextView) findViewById(R.id.timer);
        button = (Button) findViewById(R.id.button);

        button.setText("Start");
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setUpTimer();

                apples = 0;
                point =0;
                String pointsString = Integer.toString(point);
                pointCountBox.setText("Points: "+ pointsString);
                appleCountBox.setText("Apples: 0");
                button.setVisibility(View.INVISIBLE);
                tree1.setVisibility(View.VISIBLE);
                tree2.setVisibility(View.VISIBLE);
                tree3.setVisibility(View.VISIBLE);
                alec.setImageResource(R.drawable.pixel_dude);


            }
        });

    }

    private void setUpTimer() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerBox.setText("Timer: " + millisUntilFinished / 1000);}
            public void onFinish() {



                saveScore();

                Intent intent = new Intent(getBaseContext(), SelectionActivity.class);
                startActivity(intent);
                }
        }.start();
    }

    private void saveScore() {

        // MY_PREFS_NAME - a static String variable like:
        SharedPreferences.Editor editor = getSharedPreferences(SelectionActivity.MY_PREFS_NAME, MODE_PRIVATE).edit();
        //editor.putString("name", "Elena");
        editor.putInt("score", point);
        editor.commit();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx;
        float ty;

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;
                tx = event.getRawX() - (alec.getWidth() / 2);
                ty = event.getRawY() - (alec.getHeight() / 2);
                percentageX = (int) ((tx * 100) / width);
                percentageY = (int) ((ty * 100) / height);
                int[] alecPos = new int[2];
                alec.getLocationOnScreen(alecPos);
                Log.d("  START ", "Alect =   " + tx + "    alect  =   " + ty);
                Log.d("  START ",  "END %x =   " + percentageX + "    END %y  =   " + percentageY);
                animX = ObjectAnimator.ofFloat(alec, "x", tx - 45);
                animY = ObjectAnimator.ofFloat(alec, "y", ty - 134);
                animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.setInterpolator(new LinearInterpolator());
                animSetXY.setDuration(1000);
                animSetXY.start();

                break;

            case MotionEvent.ACTION_UP:

                animSetXY.cancel();
                int[] alecPos2 = new int[2];
                alec.getLocationOnScreen(alecPos2);
                DisplayMetrics displaymetrics2 = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics2);
                int height2 = displaymetrics2.heightPixels;
                int width2 = displaymetrics2.widthPixels;
                percentageX = ((alecPos2[0] * 100) / width2);
                percentageY = ((alecPos2[1] * 100) / height2);



                if (percentageY < 30) {
                    if (apples < 9){
                        apples++;
                        if (apples >= 3) tree1.setVisibility(View.INVISIBLE);
                        if (apples>=6 ) tree2.setVisibility(View.INVISIBLE);
                        if (apples >= 9) tree3.setVisibility(View.INVISIBLE);}
                    else {
                        alec.setBackgroundResource(R.drawable.alec_full);
                        alec.setImageResource(R.drawable.alec_full);
                    }

                    appleCountBox.setText("Apples: " + String.valueOf(apples));
                    animateShakeTrees();
                }

                if (percentageY > 70 && (percentageX < 75 && percentageX > 10)){
                    depositApples();
                }
                Log.d("  END ", "Alecx =   " + alecPos2[0] + "    alecY  =   " + alecPos2[1]);
                Log.d("  END ", "END %x =   " + percentageX + "    END %y  =   " + percentageY);
            default:
        }
        return true;
    }

    private void depositApples() {
        point = point + apples;
        String pointsString = Integer.toString(point);
        pointCountBox.setText("Points: "+ pointsString);
        apples = 0;
        appleCountBox.setText("Apples: 0");
        ObjectAnimator boxBounce = ObjectAnimator.ofFloat(findViewById(R.id.botBox), "translationY", 0,-80,0, -50,0);
        boxBounce.setDuration(300);
        boxBounce.setInterpolator(new AccelerateInterpolator());
        boxBounce.start();
        alec.setBackgroundResource(R.drawable.pixel_dude);
        alec.setImageResource(R.drawable.pixel_dude);
        tree1.setVisibility(View.VISIBLE);
        tree2.setVisibility(View.VISIBLE);
        tree3.setVisibility(View.VISIBLE);
    }

    private void animateShakeTrees() {
        ObjectAnimator shake = ObjectAnimator.ofFloat(findViewById(R.id.tree1), "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0);
        shake.setDuration(50);
        shake.setInterpolator(new DecelerateInterpolator());
        shake.start();
        ObjectAnimator shake2 = ObjectAnimator.ofFloat(findViewById(R.id.tree2), "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0);
        shake.setDuration(200);
        shake2.setInterpolator(new BounceInterpolator());
        shake2.start();
        ObjectAnimator shake3 = ObjectAnimator.ofFloat(findViewById(R.id.tree3), "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0);
        shake.setDuration(100);
        shake3.setInterpolator(new BounceInterpolator());
        shake3.start();
    }


}

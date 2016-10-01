package aj.vent.com.alecsapples;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
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
    View botBox;
    int apples = 0;
    int point;
    int percentageX;
    int percentageY;

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
        appleCountBox.setText("0");
        pointCountBox = (TextView) findViewById(R.id.pointCountBox);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveAlec(event);
        return true;
    }

    public  void shakeTree(MotionEvent event){
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
                Log.d("  Log ", "x =   " + percentageX + "    y  =   " + percentageY);
                Log.d("  Log ", "tx =   " + tx + "    alec Bottom  =   " + alec.getBottom());
                Log.d("  Apples ", " Apples  =   " + apples + "    ty  =   " + ty);
                ObjectAnimator animX = ObjectAnimator.ofFloat(alec, "x", tx - 45);
                ObjectAnimator animY = ObjectAnimator.ofFloat(alec, "y", ty - 134);
                animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.setDuration(1000);
                animSetXY.start();
                break;

            case MotionEvent.ACTION_UP:
                animSetXY.cancel();
                if (percentageY <= 30 && alec.getBottom() > 200) {
                    apples++;
                    appleCountBox.setText(String.valueOf(apples));
                }
            default:
        }

    }

    public void moveAlec(MotionEvent event) {
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

                //Log.d("  Apples ", " Apples  =   " + apples + "    ty  =   " + ty);
                ObjectAnimator animX = ObjectAnimator.ofFloat(alec, "x", tx - 45);
                ObjectAnimator animY = ObjectAnimator.ofFloat(alec, "y", ty - 134);
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
                percentageX = (int) ((alecPos2[0] * 100) / width2);
                percentageY = (int) ((alecPos2[1] * 100) / height2);

                if (percentageY < 30) {

                    apples++;
                    appleCountBox.setText(String.valueOf(apples));


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

                if (percentageY > 70 && (percentageX < 75 && percentageX > 10)){
                    appleCountBox.setText("0");
                    point = point + apples;
                    String pointsString = Integer.toString(point);
                    pointCountBox.setText(pointsString);
                    apples = 0;

                    ObjectAnimator boxBounce = ObjectAnimator.ofFloat(findViewById(R.id.botBox), "translationY", 0,-120,0, -70,0);
                    boxBounce.setDuration(300);
                    boxBounce.setInterpolator(new AccelerateInterpolator());
                    boxBounce.start();

                }



                Log.d("  END ", "Alecx =   " + alecPos2[0] + "    alecY  =   " + alecPos2[1]);
                Log.d("  END ", "END %x =   " + percentageX + "    END %y  =   " + percentageY);
            default:
        }
    }
}

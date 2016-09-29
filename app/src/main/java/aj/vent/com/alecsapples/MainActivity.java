package aj.vent.com.alecsapples;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView alec;
    AnimatorSet animSetXY;
    View topBox;
    TextView appleCountBox;
    View botBox;
    int apples = 0;
    int percentageX;
    int percentageY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alec = (ImageView) findViewById(R.id.alecView);
        topBox = findViewById(R.id.botBox);
        botBox = findViewById(R.id.topBox);
        appleCountBox = (TextView)findViewById(R.id.appleCountBox);

    }

    private void getScreenSize(){
        View PareentView = findViewById(R.id.alecView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx;
        float ty;
        PointF DownPT = new PointF(); // Record Mouse Position When Pressed Down
        PointF StartPT = new PointF(); // Record Start Position of 'img'


        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:

                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int height = displaymetrics.heightPixels;
                int width = displaymetrics.widthPixels;

                tx = event.getRawX() - (alec.getWidth() / 2);
                ty = event.getRawY() - (alec.getHeight() / 2);

                percentageX= (int)((tx*100)/width);
                percentageY= (int)((ty*100)/height);

                Log.d("  Log ","x =   " + percentageX + "    y  =   " + percentageY);
                Log.d("  Log ","tx =   " + tx + "    alec Bot yo  =   " + alec.getBottom());
                Log.d("  Apples "," Apples yo =   " + apples + "    ty  =   " + ty);

                ObjectAnimator animX = ObjectAnimator.ofFloat(alec, "x", tx-45);
                ObjectAnimator animY = ObjectAnimator.ofFloat(alec, "y", ty-134);
                animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.setDuration(1000);
                animSetXY.start();
                break;

            case MotionEvent.ACTION_UP:
                animSetXY.cancel();

                if (percentageY <= 30 && alec.getBottom()> 200){
                    apples++;
                    appleCountBox.setText(String.valueOf(apples));
                }


            default:
        }
        return true;
    }
}

package actio.ashcompany.com.travelagentv11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;


public class MainActivity extends Activity implements Runnable{

    ProgressBar loading;

    private static int PROGRESS_INITIAL = 0;
    private static int PROGRESS_MAX = 100;
    private static int PROGRESS_STEP = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = (ProgressBar)findViewById(R.id.progressBar);
        loading.setVisibility(ProgressBar.VISIBLE);
        loading.setProgress(PROGRESS_INITIAL);
        loading.setMax(PROGRESS_MAX);
        new Thread(this).start();
    }

    @Override
    public void run() {

        int currentPosition= 0;
        while (currentPosition<PROGRESS_MAX)
        {

            try {
                Thread.sleep(1000);
                currentPosition += PROGRESS_STEP; }
            catch (InterruptedException e) {
                return;}
            catch (Exception e) {
                return; }
            loading.setProgress(currentPosition);
        }

        Intent from = new Intent(MainActivity.this,Login.class);
        startActivity(from);
    }
}

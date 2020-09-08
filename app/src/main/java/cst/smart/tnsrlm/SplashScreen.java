package cst.smart.tnsrlm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_survey);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }
            }
        };
        logoTimer.start();

    }

}

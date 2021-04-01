package com.example.emsapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private GifImageView splashView;
    public static final String TAG = "splash";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        ///for .glf splash screen///
        splashView = findViewById(R.id.splashView);
        runSplash();

        //Splash Screen duration
       /* int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (currentUser == null){
                    Intent intent = new Intent(SplashScreenActivity.this, UserSignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else if (currentUser.getEmail().equals("admin@gmail.com")){
                    Intent intent = new Intent(SplashScreenActivity.this, AdminControllerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, UserHomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        }, secondsDelayed * 3500);*/
    }

    private void runSplash() {
        Thread timer  = new Thread(){
            public void run(){
                try {
                    sleep(3500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally {

                    if (currentUser == null) {
                        Intent intent = new Intent(SplashScreenActivity.this, UserSignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else if (currentUser.getEmail().equals("admin@gmail.com")) {
                        Intent intent = new Intent(SplashScreenActivity.this, AdminControllerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreenActivity.this, UserHomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                   /* SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                    String userName  = preferences.getString("loginState",null);
                    Log.d(TAG, "run: "+ userName);
                    if(userName == null){
                        Intent intent = new Intent(SplashScreenActivity.this, UserSignInActivity.class);
                        startActivity(intent);
                    }else if(userName!= null && userName.equals("Admin") ) {
                        Intent intent = new Intent(SplashScreenActivity.this, AdminControllerActivity.class);
                        intent.putExtra("pgId", userName);
                        startActivity(intent);
                    }else if(userName!= null) {
                        Intent intent = new Intent(SplashScreenActivity.this, UserHomePageActivity.class);
                        intent.putExtra("pgId", userName);
                        startActivity(intent);
                    }*/
                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
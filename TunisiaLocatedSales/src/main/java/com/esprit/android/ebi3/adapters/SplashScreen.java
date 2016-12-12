package com.esprit.android.ebi3.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.Welcome;
import com.esprit.android.ebi3.font.KenBurnsView;

public class SplashScreen extends Activity {

    public static final String SPLASH_SCREEN_OPTION = "com.csform.android.uiapptemplate.SplashScreensActivity";
    public static final String SPLASH_SCREEN_OPTION_1 = "Option 1";
    public static final String SPLASH_SCREEN_OPTION_2 = "Option 2";
    public static final String SPLASH_SCREEN_OPTION_3 = "Option 3";

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
        setContentView(R.layout.activity_splash_screen);



        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = (ImageView) findViewById(R.id.enactuslogo);
        welcomeText = (TextView) findViewById(R.id.welcome_text);

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()){
        mKenBurns.setImageResource(R.drawable.loginback);

        String category = SPLASH_SCREEN_OPTION_1;
        Bundle extras = getIntent().getExtras();

            category = SPLASH_SCREEN_OPTION_3;

        setAnimation(category);}
        else{

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreen.this);

            // Setting Dialog Title
            alertDialog.setTitle("Pas de connexion!");

            // Setting Dialog Message
            alertDialog.setMessage("Veuillez verifier votre connexion internet");

            // On pressing Settings button
            alertDialog.setPositiveButton("Réesayer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(
                            SplashScreen.this,
                            SplashScreen.class);
                    startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
    }

    /** Animation depends on category.
     * */
    private void setAnimation(String category) {
        if (category.equals(SPLASH_SCREEN_OPTION_1)) {
            animation1();
        } else if (category.equals(SPLASH_SCREEN_OPTION_2)) {
            animation2();
        } else if (category.equals(SPLASH_SCREEN_OPTION_3)) {
            animation2();
            animation3();
        }
    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
    }

    private void animation2() {
        mLogo.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    private void animation3() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Intent intent = new Intent(SplashScreen.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        }, 8000);


    }
}


package com.esprit.android.ebi3;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.esprit.android.ebi3.Utils.GPSTracker;
import com.esprit.android.ebi3.Utils.PrefUtil;
import com.esprit.android.ebi3.Utils.Utils;


import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.esprit.android.ebi3.Utils.IntentUtil;
import com.esprit.android.ebi3.ui.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class Welcome extends BaseActivity {

    public static String PREFERENCE_FILENAME = "reporting_app";
    Button bbt,signin;
    String profileImgUrl;
    int id;
    public Button InscriptionBtn;
    private CallbackManager callbackManager;
    private PrefUtil prefUtil;
    private IntentUtil intentUtil;
    public ProgressDialog loading;
    Button visit;
    String Name;
    String photoURL;
    LoginButton imgf;
    @Bind(R.id.image)
    ImageView mImageView;
    String EmailEnter, Password;
    Context ctx=this;
    public Spinner sp;
    public static ProgressDialog loadings;
    GPSTracker gps;
    String NAME=null, PRENOM=null, EMAIL=null,ADRESSE=null,TEL=null,Description=null,ImagePath=null;
    private EditText inputName,inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_welcome);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");


        ButterKnife.bind(this);
        mBackground = mImageView;
        moveBackground();
        visit= (Button) findViewById(R.id.VisitBtn);

     //   bbt=(Button)findViewById(R.id.buttoninscri);
        signin=(Button)findViewById(R.id.SignBtn);
      //  InscriptionBtn = (Button) findViewById(R.id.buttoninscri);
        prefUtil = new PrefUtil(this);
        intentUtil = new IntentUtil(this);


        inputName = (EditText) findViewById(R.id.input_email);
        inputPassword =(EditText) findViewById(R.id.input_password);
        sp=(Spinner) findViewById(R.id.spinnerunite);
        Toast.makeText(Welcome.this, "Pour accéeder au service de notification , Activez votre GPS", Toast.LENGTH_LONG).show();





        visit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Save new user data into Parse.com Data Storage

                //   Intent intent = new Intent(Welcome.this, Inscription.class);

                //  startActivity(intent);
                Intent i = new Intent(
                        Welcome.this,
                        InscriptionActivity.class);
                startActivity(i);
                finish();
                return false;
            }
        });

        signin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EmailEnter = inputName.getText().toString();
                Password = inputPassword.getText().toString();
                if(EmailEnter.trim().length()==0)
                {Toast.makeText(Welcome.this, "Tapez votre username !", Toast.LENGTH_LONG).show();}
                else if(Password.trim().length()==0)
                {Toast.makeText(Welcome.this, "Tapez votre mot de passe !", Toast.LENGTH_LONG).show();}
else
                // Save new user data into Parse.ge.com Data Storage
                {    loading = ProgressDialog.show(Welcome.this,"Chargement...","Un moment svp...",false,false);
                BackGround b = new BackGround();
                b.execute(EmailEnter, Password);
                loading.dismiss();}



                return false;

                }});









    }
    private String message(Profile profile) {
        StringBuilder stringBuffer = new StringBuilder();
        if (profile != null) {
            stringBuffer.append("Welcome ").append(profile.getFirstName());
            System.out.println(profile.getName());
        }
        return stringBuffer.toString();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {


        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, responseCode, intent);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // finish();
        finishAffinity();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Utils.hasHoneycomb()) {
            View demoContainerView = findViewById(R.id.image);
            demoContainerView.setAlpha(0);
            ViewPropertyAnimator animator = demoContainerView.animate();
            animator.alpha(1);
            if (Utils.hasICS()) {
                animator.setStartDelay(250);
            }
            animator.setDuration(1000);
        }
    }



  class BackGround extends AsyncTask<String, String, String> {

      @Override
      protected String doInBackground(String... params) {
          String username = params[0];
          String password = params[1];
          String data="";
          // ProgressDialog loading = ProgressDialog.show(Welcome.this,"Chargement...","Un moment svp...",false,false);
          int tmp;

          try {
              URL url = new URL("http://www.e-bi3.com/server/login.php");
              String urlParams = "username="+username+"&password="+password;

              HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
              httpURLConnection.setDoOutput(true);
              OutputStream os = httpURLConnection.getOutputStream();
              os.write(urlParams.getBytes());
              os.flush();
              os.close();

              InputStream is = httpURLConnection.getInputStream();
              while((tmp=is.read())!=-1){
                  data+= (char)tmp;
              }

              is.close();
              httpURLConnection.disconnect();
              loading.dismiss();
              return data;
          } catch (MalformedURLException e) {
              e.printStackTrace();
              return "Exception: "+e.getMessage();
          } catch (IOException e) {
              e.printStackTrace();
              return "Exception: "+e.getMessage();
          }
      }

      @Override
      protected void onPostExecute(String s) {
          System.out.println("this is onpost");
          loading.dismiss();
          try {
              JSONObject root = new JSONObject(s);
              JSONObject user_data = root.getJSONObject("user");


              if(user_data!=null)
              {
                  NAME = user_data.getString("nom");
                  PRENOM = user_data.getString("prenom");
                 id=user_data.getInt("id");
                  // PASSWORD = user_data.getString("password");
                  EMAIL = user_data.getString("email");
                  ADRESSE = user_data.getString("adresse");
                  TEL = user_data.getString("tel");
                  Description = user_data.getString("Description");
                  ImagePath = user_data.getString("ImagePath");

                  Intent i = new Intent(
                          Welcome.this,
                          PagerWelcom.class);


                  SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                  SharedPreferences.Editor prefEditor = reportingPref.edit();
                  prefEditor.clear();
                  prefEditor.putString("Nom", NAME);
                  prefEditor.putString("Prenom", PRENOM);
                  prefEditor.putString("Email", EMAIL);
                  prefEditor.putString("Adresse", ADRESSE);
                  prefEditor.putString("Telephone", TEL);
                  prefEditor.putString("Description", Description);
                  prefEditor.putString("ImagePath", ImagePath);
                  prefEditor.putString("Interested", sp.getSelectedItem().toString());

                  prefEditor.putInt("Id", id);

                  prefEditor.commit();
                  gps = new GPSTracker(Welcome.this);

                  if(gps.canGetLocation()){

                      double mylatitude = gps.getLatitude();
                      double mylongitude = gps.getLongitude();
                      prefEditor.putFloat("long", (float) mylongitude);
                      prefEditor.putFloat("lat", (float) mylatitude);
                      prefEditor.commit();
                      // \n is for new line
                      Toast.makeText(Welcome.this, "Your Location is - \nLat: " + mylatitude + "\nLong: " + mylongitude, Toast.LENGTH_LONG).show();
                      startActivity(i);
                     finish();
                  }

                  else{
                      startActivity(i);
                      finish();
                  }


              }
              else    Toast.makeText(Welcome.this, "noooon", Toast.LENGTH_LONG).show();
          }
          catch (JSONException e)
          {
              e.printStackTrace();
              System.out.println("Exception: "+e.getMessage());
              if(e!=null){ Toast.makeText(Welcome.this, "Verifiez vos paramétres", Toast.LENGTH_LONG).show();
              loading.dismiss();}
          }




      }
  }

}











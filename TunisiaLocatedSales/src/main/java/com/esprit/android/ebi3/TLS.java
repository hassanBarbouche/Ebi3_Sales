package com.esprit.android.ebi3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.GPSTracker;
import com.esprit.android.ebi3.models.Comment;
import com.esprit.android.ebi3.models.User;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hassan on 23/02/2014.
 */
public class TLS extends MultiDexApplication {

    private static Context mContext;
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    public static String PREFERENCE_FILENAME = "reporting_app";
    Location myLocation;
    float distance;
    public Location locationA;
    RequestQueue requestQueue1;
    public static boolean GetoutActivity;
    String showUrl = "http://www.e-bi3.com/server/getproductbyuserCategorieOnly.php";
    GPSTracker gps;
    public static Intent mServiceIntent ;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = getApplicationContext();
        NotificationFragment.comlist= new ArrayList<Comment>();

        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
        GetoutActivity=false;



      //  Intent mServiceIntent = new Intent(this, NotifService.class);
       // startService(mServiceIntent);
        mServiceIntent = new Intent(this, NotifService.class);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
     /*   ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                BackGround bk = new BackGround();
                bk.execute();

            }
        }, 3, 4, TimeUnit.MINUTES);*/

      //  Parse.initialize(this, "vmjv3rzq0dljmz2DwtJ0hthF9Ryt4YwT3jGq93Ws", "4OkPaB77s6lzAaaBE5IWtKuaa9P9bjwIC5ONQwbW");
     //   ParseUser.enableRevocableSessionInBackground();
        // Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));

      //  ParseUser.enableAutomaticUser();
   //     ParseACL defaultACL = new ParseACL();
//
        // If you would like all objects to be private by default, remove this
        // line.

    //    defaultACL.setPublicWriteAccess(true);
      //  defaultACL.setPublicReadAccess(true);



  /*      gps = new GPSTracker(ebi3.this);

        if(gps.canGetLocation()){

            double mylatitude = gps.getLatitude();
            double mylongitude = gps.getLongitude();
            prefEditor.putFloat("long", (float) mylongitude);
            prefEditor.putFloat("lat", (float) mylatitude);
            prefEditor.commit();
            // \n is for new line


        }
        else{

           // gps.showSettingsAlertCont(ebi3.class);
        }*/
    /*    ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {

                System.out.println(" ASYYYNC");
                System.out.println(" ASYYYNC2");
                System.out.println(" ASYYYNC3");
                System.out.println(" ASYYYNC4");
                final   SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                final SharedPreferences.Editor prefEditor = reportingPref.edit();


                Map<String, String> params = new HashMap<String, String>();
                params = new HashMap<String, String>();
                params.put("category", reportingPref.getString("Interested", ""));

                requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                //System.out.println("ww");
                //   final ProgressDialog loading = ProgressDialog.show(ebi3.this, "Chargement...", "Un petit moment svp...", false, false);
                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try {
                            final AtomicBoolean done = new AtomicBoolean(false);

                            JSONArray listproduit = response.getJSONArray("product");
                            for (int i = 0; i < listproduit.length(); i++) {
                                JSONObject nourriture = listproduit.getJSONObject(i);


                                double longitude1 = 0, longitude2 = 0, latitude1 = 0, latitude2 = 0;

                                System.out.println(nourriture.getString("name"));
                                Geocoder coder = new Geocoder(ebi3.this);
                                try {
                                    ArrayList<Address> a = (ArrayList<Address>) coder.getFromLocationName("Tunisie, Kef", 1);
                                    for (Address add : a) {

                                        longitude1 = add.getLongitude();
                                        latitude1 = add.getLatitude();
                                        locationA = new Location("point A");
                                        locationA.setLatitude(longitude1);
                                        locationA.setLongitude(latitude1);
                                        System.out.println(longitude1 + " hay ilongitude");
                                        System.out.println(latitude1 + " hay ilatitude");


                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                double f1 = (double) reportingPref.getFloat("long", 0);
                                double f2 = (double) reportingPref.getFloat("lat", 0);

                                Location myloc;
                                myloc = new Location("my location");

                                myloc.setLatitude(f1);
                                myloc.setLongitude(f2);
                                System.out.println(myloc.getLatitude() + " ma latitude" + myloc.getLongitude() + " ma longitutde");

                                System.out.println(f1 + " ffff " + f2);


                                //    float distance2 = myloc.distanceTo(locationA);
                                Location userloc = new Location("user location");
                                try {
                                    System.out.println("Try aaaaa :");
                                    ArrayList<Address> a = (ArrayList<Address>) coder.getFromLocationName(nourriture.getString("adresse") + ", " + nourriture.getString("place") + ", Tunisie", 1);
                                    for (Address add : a) {

                                        longitude1 = add.getLongitude();
                                        latitude1 = add.getLatitude();
                                        userloc = new Location("user location");
                                        userloc.setLongitude(latitude1);
                                        userloc.setLatitude(longitude1);
                                        System.out.println(longitude1 + " hay userloc1");
                                        System.out.println(latitude1 + " hay userloc2");


                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                // System.out.println(dist + " laaa distance");
                                System.out.println(nourriture.getString("adresse") + ", " + nourriture.getString("place") + ", Tunisie");
                                System.out.println(userloc.getLatitude() + " user lat");
                                System.out.println(userloc.getLongitude() + " user long");
                                // System.out.println(distance2 + " this is distance");
                                System.out.println((myloc.distanceTo(userloc)) + " " + nourriture.getString("name"));
                                if (myloc.distanceTo(userloc) < 6000)

                                {
                                    float distfrom = (myloc.distanceTo(userloc))/1000;
                                    String zz = (new DecimalFormat("##.##").format(distfrom));

                                    mNotificationManager = (NotificationManager)
                                            ebi3.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    final PendingIntent contentIntent = PendingIntent.getActivity(ebi3.this, 0,
                                            new Intent(ebi3.this, ebi3.class), 0);

                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(ebi3.this)
                                                    .setSmallIcon(R.drawable.tlslogo)
                                                    .setContentTitle(nourriture.getString("name"))
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                                    + " à " + zz + "km" + " de chez vous"))
                                                    .setContentText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                            + " à " + zz+"km"+" de chez vous");

                                    mBuilder.setContentIntent(contentIntent);
                                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                                    User u = new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                        nourriture.getString("Description"),
                                        "Jobs",
                                        "Tunisie",
                                        nourriture.getString("adresse"),
                                        nourriture.getString("tel"),
                                        nourriture.getString("email"),
                                        nourriture.getString("ImagePath"),
                                        nourriture.getInt("rate"),
                                        nourriture.getInt("Ratedby"));

                                    Comment C1 = new Comment(1,nourriture.getString("nom") + nourriture.getString("prenom")+" Vend "+nourriture.getString("name")+" à "+zz+"Km de chez vous",u);
                                    NotificationFragment.comlist= new ArrayList<Comment>();
                                    NotificationFragment.comlist.add(C1);

                                }
                           /*     if (Objects.equals(nourriture.getString("place"), "Kef")){
                                    mNotificationManager = (NotificationManager)
                                            ebi3.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    final PendingIntent contentIntent = PendingIntent.getActivity(   ebi3.this, 0,
                                            new Intent(   ebi3.this, ebi3.class), 0);

                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(   ebi3.this)
                                                    .setSmallIcon(R.drawable.ic_launcher)
                                                    .setContentTitle( nourriture.getString("name"))
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
                                                                    +" prés de chez vous"))
                                                    .setContentText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
                                                            +" prés de chez vous");

                                    mBuilder.setContentIntent(contentIntent);
                                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

                                }


                            }


                            // loading.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue1.add(jsObjRequest);
            }
        }, 1, 4, TimeUnit.MINUTES);*/
     //   ParseACL.setDefaultACL(defaultACL, true);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    @Override
    public void onTerminate() {
        Intent mServiceIntent = new Intent(this, NotifService.class);
        startService(mServiceIntent);
        super.onTerminate();

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onTrimMemory(final int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN && GetoutActivity==false) {
            // We're in the Background
            System.out.println("We are on the backkk maaan");
          //  Intent mServiceIntent = new Intent(this, NotifService.class);
            NotifService.passenow=true;
            startService(mServiceIntent);
        }

        else {
           // Intent mServiceIntent = new Intent(this, NotifService.class);
            NotifService.passenow=false;
            stopService(mServiceIntent);
        }
        // you might as well implement some memory cleanup here and be a nice Android dev.
    }



    private static final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Log.e("", "onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            Log.e("","onActivityDestroyed:" + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            Log.e("","onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            Log.e("","onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity,
                                                Bundle outState) {
            Log.e("","onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            Log.e("","onActivityStarted:" + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            Log.e("","onActivityStopped:" + activity.getLocalClassName());
        }
    }





    public static Context getAppContext() {
        return mContext;
    }

    class BackGround extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {



                    final   SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                    final SharedPreferences.Editor prefEditor = reportingPref.edit();


                    Map<String, String> paramss = new HashMap<String, String>();
                    paramss = new HashMap<String, String>();
                    paramss.put("category", reportingPref.getString("Interested", ""));

                    requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    //System.out.println("ww");
                    //   final ProgressDialog loading = ProgressDialog.show(ebi3.this, "Chargement...", "Un petit moment svp...", false, false);
                    CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, paramss, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            try {
                                final AtomicBoolean done = new AtomicBoolean(false);

                                JSONArray listproduit = response.getJSONArray("product");
                                for (int i = 0; i < listproduit.length(); i++) {
                                    JSONObject nourriture = listproduit.getJSONObject(i);


                                    double longitude1 = 0, longitude2 = 0, latitude1 = 0, latitude2 = 0;

                                    System.out.println(nourriture.getString("name"));
                                    Geocoder coder = new Geocoder(TLS.this);
                                    try {
                                        ArrayList<Address> a = (ArrayList<Address>) coder.getFromLocationName("Tunisie, Kef", 1);
                                        for (Address add : a) {

                                            longitude1 = add.getLongitude();
                                            latitude1 = add.getLatitude();
                                            locationA = new Location("point A");
                                            locationA.setLatitude(longitude1);
                                            locationA.setLongitude(latitude1);
                                            System.out.println(longitude1 + " hay ilongitude");
                                            System.out.println(latitude1 + " hay ilatitude");


                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    double f1 = (double) reportingPref.getFloat("long", 0);
                                    double f2 = (double) reportingPref.getFloat("lat", 0);

                                    Location myloc;
                                    myloc = new Location("my location");

                                    myloc.setLatitude(f1);
                                    myloc.setLongitude(f2);
                                    System.out.println(myloc.getLatitude() + " ma latitude" + myloc.getLongitude() + " ma longitutde");

                                    System.out.println(f1 + " ffff " + f2);


                                    //    float distance2 = myloc.distanceTo(locationA);
                                    Location userloc = new Location("user location");
                                    try {
                                        System.out.println("Try aaaaa :");
                                        ArrayList<Address> a = (ArrayList<Address>) coder.getFromLocationName(nourriture.getString("adresse") + ", " + nourriture.getString("place") + ", Tunisie", 1);
                                        for (Address add : a) {

                                            longitude1 = add.getLongitude();
                                            latitude1 = add.getLatitude();
                                            userloc = new Location("user location");
                                            userloc.setLongitude(latitude1);
                                            userloc.setLatitude(longitude1);
                                            System.out.println(longitude1 + " hay userloc1");
                                            System.out.println(latitude1 + " hay userloc2");


                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    // System.out.println(dist + " laaa distance");
                                    System.out.println(nourriture.getString("adresse") + ", " + nourriture.getString("place") + ", Tunisie");
                                    System.out.println(userloc.getLatitude() + " user lat");
                                    System.out.println(userloc.getLongitude() + " user long");
                                    // System.out.println(distance2 + " this is distance");
                                    System.out.println((myloc.distanceTo(userloc)) + " " + nourriture.getString("name"));
                                    if (myloc.distanceTo(userloc) < 6000)

                                    {
                                        float distfrom = (myloc.distanceTo(userloc))/1000;
                                        String zz = (new DecimalFormat("##.##").format(distfrom));

                                        mNotificationManager = (NotificationManager)
                                                TLS.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                        final PendingIntent contentIntent = PendingIntent.getActivity(TLS.this, 0,
                                                new Intent(TLS.this, NourritureListActivity.class), 0);


                                        NotificationCompat.Builder mBuilder =
                                                new NotificationCompat.Builder(TLS.this)
                                                        .setSmallIcon(R.drawable.tlslogo)
                                                        .setContentTitle(nourriture.getString("name"))
                                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                                .bigText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                                        + " à " + zz + "km" + " de chez vous"))
                                                        .setContentText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                                + " à " + zz + "km" + " de chez vous");

                                        mBuilder.setContentIntent(contentIntent);
                                        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


                                        User u = new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
                                                nourriture.getString("Description"),
                                                "Jobs",
                                                "Tunisie",
                                                nourriture.getString("adresse"),
                                                nourriture.getString("tel"),
                                                nourriture.getString("email"),
                                                nourriture.getString("ImagePath"),
                                                nourriture.getInt("rate"),
                                                nourriture.getInt("Ratedby"));

                                        Comment C1 = new Comment(1,nourriture.getString("nom") + nourriture.getString("prenom")+" Vend "+nourriture.getString("name")+" à "+zz+"Km de chez vous",u);
                                        NotificationFragment.comlist= new ArrayList<Comment>();
                                        NotificationFragment.comlist.add(C1);

                                    }
                           /*     if (Objects.equals(nourriture.getString("place"), "Kef")){
                                    mNotificationManager = (NotificationManager)
                                            ebi3.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    final PendingIntent contentIntent = PendingIntent.getActivity(   ebi3.this, 0,
                                            new Intent(   ebi3.this, ebi3.class), 0);

                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(   ebi3.this)
                                                    .setSmallIcon(R.drawable.ic_launcher)
                                                    .setContentTitle( nourriture.getString("name"))
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
                                                                    +" prés de chez vous"))
                                                    .setContentText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
                                                            +" prés de chez vous");

                                    mBuilder.setContentIntent(contentIntent);
                                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

                                }*/


                                }


                                // loading.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.append(error.getMessage());

                        }
                    });
                    requestQueue1.add(jsObjRequest);

            String st="ok";
            return st;

        }

        @Override
        protected void onPostExecute(String s) {





        }
    }
}

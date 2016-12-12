package com.esprit.android.ebi3;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.GPSTracker;
import com.esprit.android.ebi3.adapters.SwipeImageAdapter;
import com.esprit.android.ebi3.models.Comment;
import com.esprit.android.ebi3.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Hassan on 25/04/16.
 */
public  class NotifService extends IntentService {

    private static Context mContext;
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    public static String PREFERENCE_FILENAME = "reporting_app";
    Location myLocation;
    float distance;
    public Location locationA;
    public static boolean passenow;
    RequestQueue requestQueue1;
    String showUrl = "http://www.e-bi3.com/server/getproductbyuserCategorieOnly.php";
    GPSTracker gps;

    public NotifService() {
        super("NotifService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context mContext = getApplicationContext();
        NotificationFragment.comlist= new ArrayList<Comment>();
        gps = new GPSTracker(NotifService.this);



        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        Timer timer = new Timer();



// This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {

            public void run() {

                System.out.println(passenow + "this is nooow");
                    BackGround bk = new BackGround();
                    bk.execute();

            }
        }, 0, 1, TimeUnit.MINUTES);

    }



    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

//            Looper.prepare();
            if (passenow == true && gps.canGetLocation()) {

                final SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                final SharedPreferences.Editor prefEditor = reportingPref.edit();


                Map<String, String> paramss = new HashMap<String, String>();
                paramss = new HashMap<String, String>();
                paramss.put("category", reportingPref.getString("Interested", ""));

                requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                //System.out.println("ww");
                //  Welcome.loadings = ProgressDialog.show(getApplicationContext(), "Chargement...", "Un petit moment svp...", false, false);
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
                                Geocoder coder = new Geocoder(NotifService.this);
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
                                    float distfrom = (myloc.distanceTo(userloc)) / 1000;
                                    String zz = (new DecimalFormat("##.##").format(distfrom));

                                    mNotificationManager = (NotificationManager)
                                            NotifService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                    Intent notifIntent = new Intent(NotifService.this, SingleProductActivity.class);
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

                                    SwipeImageAdapter.prodimg= nourriture.getString("ProductImage1");
                                    SwipeImageAdapter.prodimg2= nourriture.getString("ProductImage2");
                                    SwipeImageAdapter.prodimg3= nourriture.getString("ProductImage3");
                                    SwipeImageAdapter.title1= nourriture.getString("ProductImage1Title");
                                    SwipeImageAdapter.title2= nourriture.getString("ProductImage2Title");
                                    SwipeImageAdapter.title3= nourriture.getString("ProductImage3Title");
                                    SwipeImageAdapter.desc1= nourriture.getString("ProductImage1Desc");
                                    SwipeImageAdapter.desc2= nourriture.getString("ProductImage2Desc");
                                    SwipeImageAdapter.desc3= nourriture.getString("ProductImage3Desc");

                                   // Intent i = new Intent(NourritureListActivity.this, SingleProductActivity.class);
                                    notifIntent.putExtra("name",nourriture.getString("name") + "");
                                    notifIntent.putExtra("description",nourriture.getString("description"));
                                    notifIntent.putExtra("quantite",nourriture.getString("quantity"));
                                    notifIntent.putExtra("lieu",nourriture.getString("place"));
                                    notifIntent.putExtra("date",nourriture.getString("date"));
                                    notifIntent.putExtra("prix",(float) nourriture.getInt("price")+"");
                                    notifIntent.putExtra("user",  u);



                                    notifIntent.putExtra("produitimage1", nourriture.getString("ProductImage1"));

                                    final PendingIntent contentIntent = PendingIntent.getActivity(NotifService.this, 0,
                                            notifIntent, 0);


                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(NotifService.this)
                                                    .setSmallIcon(R.drawable.tlslogo)
                                                    .setContentTitle(nourriture.getString("name"))
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                                    + " à " + zz + "km" + " de chez vous"))
                                                    .setContentText(nourriture.getString("nom") + nourriture.getString("prenom") + " vend " + nourriture.getString("name")
                                                            + " à " + zz + "km" + " de chez vous");

                                    mBuilder.setContentIntent(contentIntent);
                                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


                                    Comment C1 = new Comment(1, nourriture.getString("nom") + nourriture.getString("prenom") + " Vend " + nourriture.getString("name") + " à " + zz + "Km de chez vous", u);
                                    NotificationFragment.comlist = new ArrayList<Comment>();
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

                String st = "ok";
                return st;

            }
            String st = "ok";
            return st;
        }

        @Override
        protected void onPostExecute(String s) {





        }
    }
}

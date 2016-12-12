package com.esprit.android.ebi3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hassan on 20/02/16.
 */
public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText UsernameEnter,NomEnter,PrenomEnter,EmailEnter,AdresseEnter,ConfirmEmailEnter,TelephoneEnter,PasswordEnter,ConfirmPasswordEnter,DescriptionEnter;
    private Button Inscription;
    RequestQueue requestQueue;
    String insertUrl = "http://www.e-bi3.com/server/insertUser2.php";
    private Bitmap bitmap;
    private ImageView UserImg;
    private Button PhotoChoose;
    ProgressDialog loading;

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inscrption);
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");



        NomEnter = (EditText) findViewById(R.id.Nom);
        UsernameEnter = (EditText) findViewById(R.id.UserName);
        PrenomEnter = (EditText) findViewById(R.id.Prenom);
        EmailEnter = (EditText) findViewById(R.id.Email);
        ConfirmEmailEnter = (EditText) findViewById(R.id.ConfirmEmail);
        AdresseEnter = (EditText) findViewById(R.id.Adresse);
        TelephoneEnter = (EditText) findViewById(R.id.telephone);
        PasswordEnter = (EditText) findViewById(R.id.input_password);
        ConfirmPasswordEnter = (EditText) findViewById(R.id.input_password2);
        DescriptionEnter = (EditText) findViewById(R.id.Description);
        Inscription = (Button) findViewById(R.id.BtnInscription);
        PhotoChoose = (Button) findViewById(R.id.ChoosephotoBtn);
        UserImg = (ImageView) findViewById(R.id.UserImg);
        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.userimagedefault);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        PhotoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showFileChooser();


            }});

        Inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(UsernameEnter.getText().toString().trim().length()==0)
                {Toast.makeText(InscriptionActivity.this, "Veuillez choisir un username", Toast.LENGTH_LONG).show();}

             else   if(ConfirmPasswordEnter.getText().toString().trim().length()==0)
                {Toast.makeText(InscriptionActivity.this, "Veuillez choisir un mot de passe", Toast.LENGTH_LONG).show();}
                else   if(PasswordEnter.getText().toString().trim().length()==0)
                {Toast.makeText(InscriptionActivity.this, "Veuillez choisir un mot de passe", Toast.LENGTH_LONG).show();}

          else     if((!EmailEnter.getText().toString().equals(ConfirmEmailEnter.getText().toString())))
               {
                   EmailEnter.setHintTextColor(Color.parseColor("#e01124"));
                   EmailEnter.setTextColor(Color.parseColor("#e01124"));
                   ConfirmEmailEnter.setHintTextColor(Color.parseColor("#e01124"));
                   ConfirmEmailEnter.setTextColor(Color.parseColor("#e01124"));
                   Toast.makeText(InscriptionActivity.this, "Verifier votre Email", Toast.LENGTH_LONG).show();
               }



            else    if((!PasswordEnter.getText().toString().equals(ConfirmPasswordEnter.getText().toString())))
                {
                    PasswordEnter.setHintTextColor(Color.parseColor("#e01124"));
                    PasswordEnter.setTextColor(Color.parseColor("#e01124"));
                    ConfirmPasswordEnter.setHintTextColor(Color.parseColor("#e01124"));
                    ConfirmPasswordEnter.setTextColor(Color.parseColor("#e01124"));
                    Toast.makeText(InscriptionActivity.this, "Verifier votre mot de passe", Toast.LENGTH_LONG).show();
                }
                else

                {
                    String imageProfil = getStringImage(bitmap);
                    loading = ProgressDialog.show(InscriptionActivity.this,"Chargement...","Un moment svp...",false,false);
                    BackGroundInscription bkinsc = new BackGroundInscription();
                    bkinsc.execute(UsernameEnter.getText().toString(),NomEnter.getText().toString(),PrenomEnter.getText().toString(),
                            TelephoneEnter.getText().toString(),EmailEnter.getText().toString(),PasswordEnter.getText().toString(),
                            AdresseEnter.getText().toString(),DescriptionEnter.getText().toString(),imageProfil);
            /*        final ProgressDialog loading = ProgressDialog.show(InscriptionActivity.this,"Uploading...","Please wait...",false,false);

                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());

                        loading.dismiss();
                        if((response.toString()=="username ou email existe deja"))
                        {Toast.makeText(InscriptionActivity.this, response.toString(), Toast.LENGTH_LONG).show();}
                        //Toast.makeText(InscriptionActivity.this, response , Toast.LENGTH_LONG).show();
                        else{
                        startActivity(new Intent(InscriptionActivity.this, Welcome.class));
                        Toast.makeText(InscriptionActivity.this, "Enregistré avec succés! Connectez Vous", Toast.LENGTH_LONG).show();
                        finish();}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();

                        startActivity(new Intent(InscriptionActivity.this, Welcome.class));
                        Toast.makeText(InscriptionActivity.this, "Enregistré avec succés! Connectez Vous", Toast.LENGTH_LONG).show();
                        finish();
                        System.out.println(error.toString()+" eroor");
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();

                        String image = getStringImage(bitmap);
                        parameters.put("username",UsernameEnter.getText().toString());
                        parameters.put("nom",NomEnter.getText().toString());
                        parameters.put("prenom",PrenomEnter.getText().toString());
                        parameters.put("tel",TelephoneEnter.getText().toString());
                       // parameters.put("username",NomEnter.getText().toString());
                        parameters.put("email",EmailEnter.getText().toString());
                        parameters.put("password",PasswordEnter.getText().toString());
                        parameters.put("adresse",AdresseEnter.getText().toString());
                        parameters.put("Description",DescriptionEnter.getText().toString());
                        parameters.put("path",image);
                        return parameters;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        // Removed this line if you dont need it or Use application/json
                       // params.put("Content-Type", "application/json; charset=utf-8");
                        return params;
                    }
                };


                  RequestQueue requestQueue = Volley.newRequestQueue(InscriptionActivity.this);

                  /*  Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
                    Network network = new BasicNetwork(new HurlStack());
                    requestQueue = new RequestQueue(cache, network);


                requestQueue.add(request);
                 //   requestQueue.start();
                 */

            }}

        });



    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP | Base64.URL_SAFE);
       // String base64EncodedCredentials =Base64.encodeToString(data, Base64.NO_WRAP|Base64.URL_SAFE);
        return encodedImage;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NotifService.passenow=false;
        TLS.GetoutActivity=false;

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                UserImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showFileChooser() {
        NotifService.passenow=false;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        TLS.GetoutActivity=true;
        startActivityForResult(Intent.createChooser(intent, "Choisir votre photo"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InscriptionActivity.this, Welcome.class));
    }

    class BackGroundInscription extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String username = params[0];
            String nom = params[1];
            String prenom = params[2];
            String tel = params[3];
            String email = params[4];
            String password = params[5];
            String adresse = params[6];
            String Description = params[7];
            String imageProfil = params[8];
           // System.out.println("ths is image prof"+imageProfil);

            String data="";

            int tmp;

            try {
               // String image = getStringImage(bitmap);
                URL url = new URL("http://www.e-bi3.com/server/insertUser2-2.php");
                String urlParams = "username="+username+"&nom="+nom+"&prenom="+prenom+"&tel="+tel+"&email="+email+"&password="+password+"&adresse="+adresse+"&Description="+Description+"&path="+imageProfil;

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
               // loading.dismiss();
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
            if(s.toString().equals("Successfully Uploaded"))
            {
                System.out.println(s + " this is my resppp");
                startActivity(new Intent(InscriptionActivity.this, Welcome.class));
                Toast.makeText(InscriptionActivity.this, "Enregistré avec succés! Connectez Vous", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (s.toString().equals("username ou email existe deja")){
                System.out.println(s.toString() + " this is my resppp error");
                Toast.makeText(InscriptionActivity.this, "Username ou email existe déja!", Toast.LENGTH_LONG).show();

            }

            else{Toast.makeText(InscriptionActivity.this, "Une erreur s'est produite veuillez reesayer!", Toast.LENGTH_LONG).show();}




        }
    }
}

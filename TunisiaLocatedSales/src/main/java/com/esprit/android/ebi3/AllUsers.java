package com.esprit.android.ebi3;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.models.User;
import com.esprit.android.ebi3.ui.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 22/02/16.
 */
public class AllUsers extends BaseActivity {




    RequestQueue requestQueue1;
    String showUrl = "http://197.28.181.203:8888/pim1/GetAllUsers.php";
    public List<User> ListUsers;
    public Context context;

    public AllUsers(Context context) {
        this.context = context;
    }

    public List<User> getAllUsers (){


/*  ListUsers= new ArrayList<User>();
     requestQueue1 = Volley.newRequestQueue(context);
     System.out.println("ww");
     JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
             showUrl, new Response.Listener<JSONObject>() {

         @Override
         public void onResponse(JSONObject response) {
             System.out.println(response.toString());
             try {
                 JSONArray students = response.getJSONArray("user");
                 for (int i = 0; i < students.length(); i++) {
                     JSONObject users = students.getJSONObject(i);

                     ListUsers.add(new User(
                             users.getInt("id"),
                             users.getString("nom")+" " + users.getString("prenom"),
                             users.getString("Description"),
                             "Null",
                             "Tunisie",
                             users.getString("adresse"),
                             users.getString("tel"),
                             users.getString("email"),
                             users.getString("ImagePath")

                     ));
                 }


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
     requestQueue1.add(jsonObjectRequest);


     return ListUsers;*/
        return null;
 }

    public User FindUserById(int id){

         ListUsers= new ArrayList<User>();
        requestQueue1 = Volley.newRequestQueue(context);
        System.out.println("ww");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray students = response.getJSONArray("user");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject users = students.getJSONObject(i);

                        ListUsers.add(new User(
                                users.getInt("id"),
                                users.getString("nom")+" " + users.getString("prenom"),
                                users.getString("Description"),
                                "Null",
                                "Tunisie",
                                users.getString("adresse"),
                                users.getString("tel"),
                                users.getString("email"),
                                users.getString("ImagePath")

                        ));
                    }


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
        requestQueue1.add(jsonObjectRequest);

       // ArrayList<User> aruser = (ArrayList<User>) getAllUsers();
        for (int i = 0; i < ListUsers.size(); i++)
        {User u = ListUsers.get(i);
            System.out.println(u.getId()+" rr " + u.getUserName());
         if( u.getId()==id)
             return u;


        }

      return null;
    }
}

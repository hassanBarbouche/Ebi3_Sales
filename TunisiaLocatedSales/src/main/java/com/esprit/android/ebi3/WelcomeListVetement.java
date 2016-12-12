package com.esprit.android.ebi3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Hassan on 06/02/16.
 */
public class WelcomeListVetement extends Fragment {
    public  static ListView maListViewPerso;
    public static ImageView backopacity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_welcome, container, false);
         maListViewPerso = (ListView) view.findViewById(R.id.list_view);

        RelativeLayout Back = (RelativeLayout) view.findViewById(R.id.PagerBack);
        ImageView Logo = (ImageView) view.findViewById(R.id.HeaderImg);
        TextView Titre = (TextView) view.findViewById(R.id.HeaderText);


         backopacity = (ImageView) view.findViewById(R.id.mainBack);
        Back.setBackgroundResource(R.drawable.vetementback);
        Logo.setBackgroundResource(R.drawable.vetementcouleur);
        TextView headerdesc = (TextView) view.findViewById(R.id.HeaderDesc);
        headerdesc.setText("'La frippe en ligne' Trouvez tout ce que vous voulez comme vetements : Robes, Pull, Chemise, Pantalon, Costume, Chaussure, Casequette, Bonnet, Bijoux, " +
                "Accesoires...");
        Titre.setText("Vétements");


        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                    //   ArtisanatListActivity.From = "Broderie";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Pull et Tshirt");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 1) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Pantalons et Jupes");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 2) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Chaussures");

                    startActivity(i);
                    getActivity().finish();

                }


                if (position == 3) {

                    // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Robes");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 4) {

                    //   NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Bonnets et Casquettes");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 5) {

                    // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), VetementListActivity.class);
                    i.putExtra("From", "Bijoux");

                    startActivity(i);
                    getActivity().finish();

                }


            }
        });

        return  view;
    }

}

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
public class WelcomeListArtisanat extends Fragment {
    public static ListView maListViewPerso;
    public static ImageView backopacity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_welcome, container, false);
         maListViewPerso = (ListView) view.findViewById(R.id.list_view);
        RelativeLayout Back = (RelativeLayout) view.findViewById(R.id.PagerBack);
        ImageView Logo = (ImageView) view.findViewById(R.id.HeaderImg);
         backopacity = (ImageView) view.findViewById(R.id.mainBack);
        TextView Titre = (TextView) view.findViewById(R.id.HeaderText);



        Back.setBackgroundResource(R.drawable.artisanatback);
        Logo.setBackgroundResource(R.drawable.artisanatcouleur);
        TextView headerdesc = (TextView) view.findViewById(R.id.HeaderDesc);
        headerdesc.setText("Tout l'art traditionel tunisien entre vos mains ... Du margoum au robes traditionelles en passant par les décorations intérieurs et les tableaux de" +
                "peinture... Il y en a pour tous les gouts: Broderie, Tappiserie, Poterie, Peinture, Instruments et Décoration...");
        Titre.setText("Artisanat");

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                 //   ArtisanatListActivity.From = "Broderie";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Broderie");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 1) {

                  //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Tappiserie");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 2) {

                  //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Poterie");

                    startActivity(i);
                    getActivity().finish();

                }


                if (position == 3) {

                   // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Peinture");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 4) {

                 //   NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "A manger");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 5) {

                   // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Bois");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 6) {

                   // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From", "Instruments de musique");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 7) {

                 //   NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ArtisanatListActivity.class);
                    i.putExtra("From", "Maison");

                    startActivity(i);
                    getActivity().finish();

                }




            }
        });


        return  view;
    }



}

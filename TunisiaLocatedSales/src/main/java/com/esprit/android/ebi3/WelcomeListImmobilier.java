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
public class WelcomeListImmobilier extends Fragment {
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
        Back.setBackgroundResource(R.drawable.immobilierback2);
        Logo.setBackgroundResource(R.drawable.immobiliercouleur);
        TextView headerdesc = (TextView) view.findViewById(R.id.HeaderDesc);
        headerdesc.setText("Vous déménagez ? Vous cherchez ou habitter? Vous voulez investir dans un terrain? vous voulez vendre ou louer votre maison..." +
                "c'est ici .");
        Titre.setText("Immobilier");

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                    //   ArtisanatListActivity.From = "Broderie";


                    Intent i = new Intent(getActivity(), ImmobilierListActivity.class);
                    i.putExtra("From", "Maison");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 1) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ImmobilierListActivity.class);
                    i.putExtra("From", "Appartement");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 2) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ImmobilierListActivity.class);
                    i.putExtra("From", "Entrepot");

                    startActivity(i);
                    getActivity().finish();

                }


                if (position == 3) {

                    // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), ImmobilierListActivity.class);
                    i.putExtra("From", "Terrain");

                    startActivity(i);
                    getActivity().finish();

                }




            }
        });




        return  view;
    }

}

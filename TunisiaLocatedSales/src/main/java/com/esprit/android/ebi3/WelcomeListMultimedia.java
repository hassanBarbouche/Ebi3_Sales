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
public class WelcomeListMultimedia extends Fragment {
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



        Back.setBackgroundResource(R.drawable.multimediaback2);
        Logo.setBackgroundResource(R.drawable.multimediacouleur);
        TextView headerdesc = (TextView) view.findViewById(R.id.HeaderDesc);
        headerdesc.setText("Toute les bonne occasion du multimedia sont ici ... Vous n'avez plus envie de votre téléphone ou votre téléviseur de salon, vendez le ici ..." +
                "ou bien venez en acheter un autre: Ordinateur, Téléphone, TV, Son, Musique et films ...");
        Titre.setText("Multimedia");

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                    //   ArtisanatListActivity.From = "Broderie";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "Ordinateur");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 1) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "Telephone");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 2) {

                    //  NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "TV");

                    startActivity(i);
                    getActivity().finish();

                }


                if (position == 3) {

                    // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "Son");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 4) {

                    //   NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "Photographie");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 5) {

                    // NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), MultimediaListActivity.class);
                    i.putExtra("From", "Film et Musique");

                    startActivity(i);
                    getActivity().finish();

                }




            }
        });


        return  view;
    }



}

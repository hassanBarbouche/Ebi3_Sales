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
import android.widget.TextView;

import com.esprit.android.ebi3.adapters.ArticleCustomAdapter;
import com.esprit.android.ebi3.models.Article;
import com.esprit.android.ebi3.models.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 06/02/16.
 */
public class WelcomeListNourriture extends Fragment {
    public  static ListView maListViewPerso;
    public static ImageView backopacity;
    private List<Article> imgList2;

    private  List<Produit> listprod;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_welcome, container, false);
        imgList2 = new ArrayList<Article>();
        Article A10 = new Article(R.drawable.veget, "Légumes", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
        Article A11 = new Article(R.drawable.ble, "Céréales", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
        Article A13 = new Article(R.drawable.eggs, "Oeufs", "Différentes variétés d'oeufs...");
        Article A14 = new Article(R.drawable.fruit, "Fruit", "Oranges, Pommes, Poire, Fraise, Cerise, Peches, Abricot, Pastéque...");
        Article A15 = new Article(R.drawable.pain, "A manger", "Pain, Mlawi, Sandwich, Plat, Jus...");
        Article A16 = new Article(R.drawable.honey, "Miel", "Différentes variétés de miel...");
        Article A17 = new Article(R.drawable.fish, "Poisson", "Poisson Rouge, Poisson Bleu, crustacés...");
        Article A18 = new Article(R.drawable.epice, "Epices", "Poivre, Sel, Safran, Cumin...");
        Article A19 = new Article(R.drawable.nourritureblanc, "Tout", " ");


        imgList2.add(A10);
        imgList2.add(A11);
        imgList2.add(A13);
        imgList2.add(A14);
        imgList2.add(A15);
        imgList2.add(A16);
        imgList2.add(A17);
        imgList2.add(A18);
        imgList2.add(A19);
         maListViewPerso = (ListView) view.findViewById(R.id.list_view);
         backopacity = (ImageView) view.findViewById(R.id.mainBack);
        ImageView Logo = (ImageView) view.findViewById(R.id.HeaderImg);
        TextView headerdesc = (TextView) view.findViewById(R.id.HeaderDesc);
        TextView headertxt = (TextView) view.findViewById(R.id.HeaderText);
        Logo.setBackgroundResource(R.drawable.nourriturescouleur);
        headertxt.setText("Nourriture");
        headerdesc.setText("Toute la nourriture que vous cherchez. Que ce soit des produits agricoles ou bien du pret a manger , on éspére que vous trouverez tout ce que" +
                "vous cherchez : Fruits, Légumes, Céréales, Pates, Miel, a Manger, Poissons, Epices... ");
       maListViewPerso.setAdapter(new ArticleCustomAdapter(getActivity(), R.layout.one_article, imgList2));

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Legumes");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 1) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Cereales");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 2) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Oeufs");

                    startActivity(i);
                    getActivity().finish();

                }


                if (position == 3) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Fruits");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 4) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","A manger");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 5) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Miel");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 6) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Poisson");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 7) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);
                    i.putExtra("From","Epices");

                    startActivity(i);
                    getActivity().finish();

                }

                if (position == 8) {

                    NourritureListActivity.From = "Legumes";


                    Intent i = new Intent(getActivity(), NourritureListActivity.class);


                    startActivity(i);
                    getActivity().finish();

                }


            }
        });

        return  view;
    }

}

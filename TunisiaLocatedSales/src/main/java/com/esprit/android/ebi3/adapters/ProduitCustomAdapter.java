package com.esprit.android.ebi3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.models.Produit;

import java.util.List;

public class ProduitCustomAdapter extends ArrayAdapter<Produit> {

	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  public Context mContext;

	  public ProduitCustomAdapter(Context context, int resourceId, List<Produit> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    this.mContext = context;
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


	  }
	  
	  //ViewHolder Design Pattern
	  public static class ViewHolder {
		    public TextView textTitle, LieuTxt,QuantiteTxt,PrixTxt,DateTxt,UserName;
		    public ImageView Productimage,UserImage;
		  }
	  
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  View rowView = convertView;
		  //parent est le layout , convertview est notre vue avec scroll = rowView, resourceid bagla leha = 0

		  ViewHolder holder = new ViewHolder();
		  //Réutiliser les Views
		  if (rowView == null) {
			rowView = inflater.inflate(resourceId, parent, false);
			  //Configuration du ViewHolder
			  holder.Productimage = (ImageView) rowView.findViewById(R.id.imgArticle);
			  holder.UserImage = (ImageView) rowView.findViewById(R.id.ProductUserImg);
			  holder.textTitle = (TextView) rowView.findViewById(R.id.titreArticle);
			  holder.LieuTxt = (TextView) rowView.findViewById(R.id.ProductLieu);
			  holder.QuantiteTxt = (TextView) rowView.findViewById(R.id.ProductQuantite);
			  holder.PrixTxt = (TextView) rowView.findViewById(R.id.ProductPrix);
			  holder.DateTxt = (TextView) rowView.findViewById(R.id.ProductDate);
			  holder.UserName = (TextView) rowView.findViewById(R.id.ProductUserName);

		  rowView.setTag(holder);//insérer le holder dans notre layout
		  }else {
			  //Affecter les données aux Views
			  holder = (ViewHolder) rowView.getTag(); // Si on recycle la vue, on récupère son holder en tag
		  }
		  Produit prod = getItem(position); // Dans tous les cas, on récupère l'article concerné

		  // On place dans le holder les informations sur l'article
		  holder.textTitle.setText(prod.getTitre());
		  holder.LieuTxt.setText(prod.getLieu());
		  holder.QuantiteTxt.setText(prod.getQuantite());
		  holder.PrixTxt.setText( prod.getPrix()+" dt");
		  holder.DateTxt.setText(prod.getDate());
		  holder.UserName.setText(prod.getUser().getUserName());
		//  holder.Productimage.setImageResource(prod.getProductImage());
		  //holder.UserImage.setImageResource(prod.getUser().getUserImg());
		  ImageUtil.displayImage(holder.Productimage, prod.getProductImage(), null);
		  ImageUtil.displayRoundImage(holder.UserImage, prod.getUser().getImagePath(), null);
		 // Picasso.with(mContext).load(article.getImage()).into(holder.image);

		  return rowView;
	  }

	}
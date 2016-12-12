package com.esprit.android.ebi3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.models.Article;

import java.util.List;

public class ArticleCustomAdapter extends ArrayAdapter<Article> {
	
	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  public Context mContext;

	  public ArticleCustomAdapter(Context context, int resourceId, List<Article> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    this.mContext = context;
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


	  }
	  
	  //ViewHolder Design Pattern
	  public static class ViewHolder {
		    public TextView textTitle, DescText;
		    public ImageView image;
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
			  holder.image = (ImageView) rowView.findViewById(R.id.imgArticle);
			  holder.textTitle = (TextView) rowView.findViewById(R.id.titreArticle);

			  holder.DescText = (TextView) rowView.findViewById(R.id.descArticle);
		  rowView.setTag(holder);//insérer le holder dans notre layout
		  }else {
			  //Affecter les données aux Views
			  holder = (ViewHolder) rowView.getTag(); // Si on recycle la vue, on récupère son holder en tag
		  }
		  Article article = getItem(position); // Dans tous les cas, on récupère l'article concerné

		  // On place dans le holder les informations sur l'article
		  holder.textTitle.setText(article.getTitre());
		  holder.DescText.setText(article.getDesc());
		  holder.image.setImageResource(article.getImage());
		 // Picasso.with(mContext).load(article.getImage()).into(holder.image);

		  return rowView;
	  }

	}
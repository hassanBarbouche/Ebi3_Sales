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
import com.esprit.android.ebi3.models.Comment;

import java.util.List;

public class CommentaireCustomAdapter extends ArrayAdapter<Comment> {

	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  public Context mContext;

	  public CommentaireCustomAdapter(Context context, int resourceId, List<Comment> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    this.mContext = context;
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


	  }
	  
	  //ViewHolder Design Pattern
	  public static class ViewHolder {
		    public TextView Commentor, CommentTxt;
		    public ImageView ComentorImg;
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
			  holder.ComentorImg = (ImageView) rowView.findViewById(R.id.ComUserImg);
			  holder.Commentor = (TextView) rowView.findViewById(R.id.Commentor);
			  holder.CommentTxt = (TextView) rowView.findViewById(R.id.Comment);


		  rowView.setTag(holder);//insérer le holder dans notre layout
		  }else {
			  //Affecter les données aux Views
			  holder = (ViewHolder) rowView.getTag(); // Si on recycle la vue, on récupère son holder en tag
		  }
		  Comment commentaire = getItem(position); // Dans tous les cas, on récupère l'article concerné

		  // On place dans le holder les informations sur l'article
		  holder.Commentor.setText(commentaire.getUser().getUserName());
		  holder.CommentTxt.setText(commentaire.getCorps());
		 // holder.ComentorImg.setImageResource(commentaire.getCommentorImg());
		  ImageUtil.displayRoundImage(holder.ComentorImg,commentaire.getUser().getImagePath(), null);
		 // Picasso.with(mContext).load(article.getImage()).into(holder.image);

		  return rowView;
	  }

	}
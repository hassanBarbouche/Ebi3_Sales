package com.esprit.android.ebi3.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.PromoteActivity;
import com.esprit.android.ebi3.R;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.models.Produit;
import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;
import com.samsung.android.sdk.iap.lib.listener.OnPaymentListener;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;
import com.samsung.android.sdk.iap.lib.vo.PurchaseVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduitProfileCustomAdapter extends ArrayAdapter<Produit> {

	  private int resourceId = 0;
	  private LayoutInflater inflater;
	  public Context mContext;
	private static final int IAP_MODE = SamsungIapHelper.IAP_MODE_TEST_SUCCESS;

	// Item ID for test button of purchase one item
	// ========================================================================
	private static final String ITEM_ID          = "PromoteEbi3";
	// ========================================================================

	// Item ID for test button of cached items inbox list
	// ========================================================================
	private static final String ITEM_IDS         = "Nuclear, Claymore, " +
			"Blockbuster";

	ViewHolder holder ;
	String showUrl = "http://www.e-bi3.com/server/update_item.php";
	String showUrl2 = "http://www.e-bi3.com/server/delete_item.php";
	RequestQueue requestQueue1;

	  public ProduitProfileCustomAdapter(Context context, int resourceId, List<Produit> mediaItems) {
	    super(context, 0, mediaItems);
	    this.resourceId = resourceId;
	    this.mContext = context;
	    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


	  }
	  
	  //ViewHolder Design Pattern
	  public static class ViewHolder {
		    public EditText textTitle, LieuTxt,QuantiteTxt,PrixTxt,DateTxt;
		    public ImageView Productimage,Productimage2;
		  public Button Upadte,Delete,Promote;
		  }
	  
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  View rowView = convertView;
		  //parent est le layout , convertview est notre vue avec scroll = rowView, resourceid bagla leha = 0

		  holder = new ViewHolder();
		  //Réutiliser les Views
		  if (rowView == null) {
			rowView = inflater.inflate(resourceId, parent, false);
			  //Configuration du ViewHolder
			  holder.Productimage = (ImageView) rowView.findViewById(R.id.imgArticle);
			  holder.Productimage2 = (ImageView) rowView.findViewById(R.id.imgArticle2);

			  holder.textTitle = (EditText) rowView.findViewById(R.id.titreArticleP);
			  holder.LieuTxt = (EditText) rowView.findViewById(R.id.ProductLieuP);
			  holder.QuantiteTxt = (EditText) rowView.findViewById(R.id.ProductQuantiteP);
			  holder.PrixTxt = (EditText) rowView.findViewById(R.id.ProductPrixP);
			  holder.DateTxt = (EditText) rowView.findViewById(R.id.ProductDateP);
			  holder.Upadte = (Button) rowView.findViewById(R.id.UpdateBtn);
			  holder.Delete = (Button) rowView.findViewById(R.id.DeleteBtn);
			  holder.Promote = (Button) rowView.findViewById(R.id.inappbtn);


		  rowView.setTag(holder);//insérer le holder dans notre layout
		  }else {
			  //Affecter les données aux Views
			  holder = (ViewHolder) rowView.getTag(); // Si on recycle la vue, on récupère son holder en tag

		  }
		 final  Produit prod = getItem(position); // Dans tous les cas, on récupère l'article concerné

		  // On place dans le holder les informations sur l'article
		  holder.textTitle.setText(prod.getTitre());
		  holder.LieuTxt.setText(prod.getLieu());
		  holder.QuantiteTxt.setText(prod.getQuantite());
		  holder.PrixTxt.setText( prod.getPrix()+"");
		  holder.DateTxt.setText(prod.getDate());

		//  holder.Productimage.setImageResource(prod.getProductImage());
		  //holder.UserImage.setImageResource(prod.getUser().getUserImg());
		  ImageUtil.displayImage(holder.Productimage, prod.getProductImage(), null);
		  ImageUtil.displayImage(holder.Productimage2, prod.getProductImage2(), null);


		  holder.Upadte.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View view) {

//				  final ProgressDialog loading = ProgressDialog.show(mContext, "Mise a jour...", "Un petit moment svp...", false, false);
				  System.out.println(holder.textTitle.getText().toString() + " this is texttts");
				  StringRequest request = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>() {
					  @Override
					  public void onResponse(String response) {

						  System.out.println(response.toString());
						  //	  loading.dismiss();
						  Toast.makeText(mContext, "Mise a jour effectué", Toast.LENGTH_LONG).show();
						  System.out.println(response + "this is resp");
					  }
				  }, new Response.ErrorListener() {
					  @Override
					  public void onErrorResponse(VolleyError error) {
						  // loading.dismiss();

						  //Showing toast
						  Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_LONG).show();
						  System.out.println(error.getMessage().toString() + "this is resp");
					  }
				  }) {

					  @Override
					  protected Map<String, String> getParams() throws AuthFailureError {
						  Map<String, String> params = new HashMap<String, String>();
						  params.put("id", prod.getId() + "");
						  params.put("name", holder.textTitle.getText().toString());
						  params.put("quantity", holder.QuantiteTxt.getText().toString());
						  params.put("price", holder.PrixTxt.getText().toString());
						  return params;
					  }
				  };

				  RequestQueue requestQueue = Volley.newRequestQueue(mContext);


				  requestQueue.add(request);

				  Toast.makeText(mContext, "Produit mis a jour !", Toast.LENGTH_LONG).show();


			  }
		  });

		  holder.Delete.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View view) {

		//		  final ProgressDialog loading = ProgressDialog.show(mContext, "Mise a jour...", "Un petit moment svp...", false, false);
				  System.out.println(holder.textTitle.getText().toString() + " this is texttts");
				  StringRequest request = new StringRequest(Request.Method.POST, showUrl2, new Response.Listener<String>() {
					  @Override
					  public void onResponse(String response) {

						  System.out.println(response.toString());
						  //	  loading.dismiss();
						  Toast.makeText(mContext, "Produit Supprimé", Toast.LENGTH_LONG).show();
						  System.out.println(response + "this is resp");
					  }
				  }, new Response.ErrorListener() {
					  @Override
					  public void onErrorResponse(VolleyError error) {
						  // loading.dismiss();

						  //Showing toast
						  Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_LONG).show();
						  System.out.println(error.getMessage().toString() + "this is resp");
					  }
				  }) {

					  @Override
					  protected Map<String, String> getParams() throws AuthFailureError {
						  Map<String, String> params = new HashMap<String, String>();
						  params.put("id", prod.getId() + "");

						  return params;
					  }
				  };

				  RequestQueue requestQueue = Volley.newRequestQueue(mContext);


				  requestQueue.add(request);

				  Toast.makeText(mContext, "Produit supprimé !", Toast.LENGTH_LONG).show();



			  }
		  });

		  holder.Promote.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View view) {



				  Intent i = new Intent(
						  mContext,
						  PromoteActivity.class);
				  i.putExtra("name",prod.getTitre());
				  i.putExtra("id_prod", prod.getId()+"");
				  i.putExtra("price_promo",prod.getPrix()+"");
				  i.putExtra("place_promo",prod.getLieu()+"");
				  i.putExtra("user",prod.getUser()+"");

				  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				  mContext.startActivity(i);






			  }
		  });



		  // Picasso.with(mContext).load(article.getImage()).into(holder.image);

		  return rowView;
	  }

	OnPaymentListener mOnPaymentListener = new OnPaymentListener()
	{
		@Override
		public void onPayment( ErrorVo _errorVo, PurchaseVo _purchaseVo )
		{
			if( _errorVo != null &&
					_errorVo.getErrorCode() == SamsungIapHelper.IAP_ERROR_NONE )
			{
				// TODO When purchase has been finished successfully,

				//      processes here.


			}

			// Test code : result log
			// ================================================================
			String paymentResult = "";

			if( _errorVo != null )
			{
				paymentResult = _errorVo.dump() + "\n\n";
			}

			if( _purchaseVo != null )
			{
				paymentResult += _purchaseVo.dump();
			}

			Log.e("Purchase", paymentResult);
			// ================================================================
		}
	};

	}
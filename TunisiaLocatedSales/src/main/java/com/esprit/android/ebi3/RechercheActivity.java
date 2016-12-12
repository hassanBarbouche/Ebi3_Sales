package com.esprit.android.ebi3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.ArticleCustomAdapter;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.models.Article;
import com.esprit.android.ebi3.models.Produit;
import com.esprit.android.ebi3.models.User;
import com.esprit.android.ebi3.provider.FragmentTags;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class RechercheActivity extends ActionBarActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

	private FragmentManager fragmentManager;

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	public ImageView backImg,TunisiaMap;
	public RelativeLayout maincont;
	RequestQueue requestQueue1;
	String showUrl = "http://www.e-bi3.com/server/recherche.php";
    private GridView mygrid;
	private CharSequence mDrawerTitle;
	EditText recherchekey,prixmintxt,prixmaxtxt;
	private CharSequence mTitle;
	Button Gosearch;
	public List<Produit> listprod;
	public ViewPager viewpager;
	private List<Article> imgList1;
	private ContextMenuDialogFragment mMenuDialogFragment;
	public String ville,categorie,recherchetxt,prixmin,prixmax;
	Fragment fragment;
	Button Ariana,Beja,Bizerte,Gabes,Gafsa,Gasserine,Gbeli,Jendouba,Karwen,Kef,Mahdia,Manouba,Mednine,Mestir,Nabeul,Sfax,Sidibouzid,Siliana,Sousse,Tatawin,Tozeur,Tunis,Zaghwen,AllTunisia;
	public static String PREFERENCE_FILENAME = "reporting_app";
	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;
	protected ImageLoader imageLoader;

	@Override
	protected void onResume() {
		super.onResume();
		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));

		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
		super.onCreate(savedInstanceState);
		listprod = new ArrayList<Produit>();
		setContentView(R.layout.recherche_layout);
		Gosearch = (Button) findViewById(R.id.GoBtn);
		recherchekey = (EditText) findViewById(R.id.RechercheKey);
		prixmaxtxt = (EditText) findViewById(R.id.PrixMax);
		prixmintxt = (EditText) findViewById(R.id.PrixMin);
		//backImg = (ImageView) findViewById(R.id.mainBack);
		//maincont = (RelativeLayout) findViewById(R.id.MainContainer);
		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();
		//addFragment(new MainFragment(), true, R.id.container);
		SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

		String Nom = reportingPref.getString("Nom", "");
		String Prenom = reportingPref.getString("Prenom", "");
		String Email = reportingPref.getString("Email", "");
		String Description = reportingPref.getString("Description", "");
		String Adresse = reportingPref.getString("Adresse", "");
		String Telephone = reportingPref.getString("Telephone", "");
		String ImagePath = reportingPref.getString("ImagePath", "");

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		FragmentManager fragmentManager = getSupportFragmentManager();

		mygrid = (GridView) findViewById(R.id.CategorieGrid);


		Ariana = (Button) findViewById(R.id.Ariana);
		Beja = (Button) findViewById(R.id.Beja);
		Bizerte = (Button) findViewById(R.id.Bizerte);
		Gabes = (Button) findViewById(R.id.Gabes);
		Gafsa = (Button) findViewById(R.id.Gafsa);
		Gasserine = (Button) findViewById(R.id.Gasserine);
		Jendouba = (Button) findViewById(R.id.Jendouba);

		Gbeli = (Button) findViewById(R.id.Gbeli);
		Karwen = (Button) findViewById(R.id.kairouan);
		Kef = (Button) findViewById(R.id.Kef);
		Mahdia = (Button) findViewById(R.id.Mahdia);
		Manouba = (Button) findViewById(R.id.Manouba);
		Mednine = (Button) findViewById(R.id.Mednine);
		Mestir = (Button) findViewById(R.id.Monastir);

		Nabeul = (Button) findViewById(R.id.Nabeul);
		Sfax = (Button) findViewById(R.id.Sfax);
		Sidibouzid = (Button) findViewById(R.id.Sidibouzid);
		Siliana = (Button) findViewById(R.id.Siliana);
		Sousse = (Button) findViewById(R.id.Sousse);
		Tatawin = (Button) findViewById(R.id.Tatawin);
		Tozeur = (Button) findViewById(R.id.Tozeur);

		Tunis = (Button) findViewById(R.id.Tunis);
		Zaghwen = (Button) findViewById(R.id.Zaghwen);
		AllTunisia = (Button) findViewById(R.id.AllTunisia);

		TunisiaMap = (ImageView) findViewById(R.id.TunisiaMap);

		imgList1 = new ArrayList<Article>();
		Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
		Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
		Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
		Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
		Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
		Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


		imgList1.add(A1);
		imgList1.add(A2);
		imgList1.add(A3);
		imgList1.add(A4);
		imgList1.add(A5);
		imgList1.add(A6);

		mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList1));


		Gosearch.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				prixmax = prixmaxtxt.getText().toString();
				prixmin = prixmintxt.getText().toString();
				recherchetxt = recherchekey.getText().toString();

				if(ville==""|| ville==null)
				{
					Toast.makeText(RechercheActivity.this, "Veuillez choisir une ville !" , Toast.LENGTH_LONG).show();


				}

				else if(categorie==""|| categorie==null)
				{
					Toast.makeText(RechercheActivity.this, "Veuillez choisir une catégorie !" , Toast.LENGTH_LONG).show();


				}

				else if(recherchetxt==""|| recherchetxt==null|| recherchetxt==" ")
				{
					Toast.makeText(RechercheActivity.this, "Veuillez tapper le nom d'un produit !" , Toast.LENGTH_LONG).show();


				}

				else{
					System.out.println(recherchetxt+ " thid is rechercetxt");

				Map<String, String> params = new HashMap<String, String>();
				params.put("name", recherchetxt);
				params.put("minprice", prixmin);
				params.put("maxprice", prixmax);
				params.put("category", categorie);
				params.put("place", ville);


				/////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

				requestQueue1 = Volley.newRequestQueue(getApplicationContext());
				//System.out.println("ww");
				CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println(response.toString());
						try {
							final AtomicBoolean done = new AtomicBoolean(false);

							JSONArray listproduit = response.getJSONArray("recherche");
							if (listproduit==null){
								System.out.println("list nulll");
							}
							for (int i = 0; i < listproduit.length(); i++) {
								JSONObject prod = listproduit.getJSONObject(i);





								ResultListActivity.ListNourriture.add(new Produit(
										prod.getString("ProductImage1"),
										prod.getString("ProductImage2"),
										prod.getString("ProductImage3"),
										prod.getString("ProductImage1Title"),
										prod.getString("ProductImage2Title"),
										prod.getString("ProductImage3Title"),
										prod.getString("ProductImage1Desc"),
										prod.getString("ProductImage2Desc"),
										prod.getString("ProductImage3Desc"),
										prod.getString("name"),
										prod.getString("description"),
										prod.getString("place"),
										prod.getString("quantity"),
										prod.getString("date"),
										(float) prod.getInt("price"),

										new User(prod.getInt("Id"), prod.getString("nom") + prod.getString("prenom"),
												prod.getString("Description"),
												"Jobs",
												"Tunisie",
												prod.getString("adresse"),
												prod.getString("tel"),
												prod.getString("email"),
												prod.getString("ImagePath"))
								));
								System.out.println(listprod.size() + " my size");

								if(listprod.size()==0)
								{
									ResultListActivity.vide=true;
								}
								else
								{
									ResultListActivity.vide=false;
								}
								System.out.println(prod.getInt("Id")+" zzzzz");


							}


							System.out.println(listprod.size()+" this is size");


                        	Intent i = new Intent(RechercheActivity.this, ResultListActivity.class);
							startActivity(i);
							finish();

						} catch (JSONException e) {
							e.printStackTrace();
							System.out.println(e.getMessage().toString() + " aa aaa aa");
							ResultListActivity.vide=true;
							Intent i = new Intent(RechercheActivity.this, ResultListActivity.class);
							startActivity(i);
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						System.out.append(error.getMessage());
						ResultListActivity.vide=true;
						Intent i = new Intent(RechercheActivity.this, ResultListActivity.class);
						startActivity(i);

					}
				});
				requestQueue1.add(jsObjRequest);}
				return false;
			}
		});

		mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {


				if (position == 0) {
					List<Article> imgList2 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourriturescouleur, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList2.add(A1);
					imgList2.add(A2);
					imgList2.add(A3);
					imgList2.add(A4);
					imgList2.add(A5);
					imgList2.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList2));
					categorie = "Nourriture";


				}

				if (position == 1) {
					List<Article> imgList3 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatcouleur, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList3.add(A1);
					imgList3.add(A2);
					imgList3.add(A3);
					imgList3.add(A4);
					imgList3.add(A5);
					imgList3.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList3));
					categorie = "Artisanat";


				}

				if (position == 2) {
					List<Article> imgList4 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.servicecouleur, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList4.add(A1);
					imgList4.add(A2);
					imgList4.add(A3);
					imgList4.add(A4);
					imgList4.add(A5);
					imgList4.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList4));
					categorie = "Services";


				}

				if (position == 3) {
					List<Article> imgList5 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediacouleur, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList5.add(A1);
					imgList5.add(A2);
					imgList5.add(A3);
					imgList5.add(A4);
					imgList5.add(A5);
					imgList5.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList5));
					categorie = "Multimedia";


				}

				if (position == 4) {
					List<Article> imgList6 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobiliercouleur, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementblanc, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList6.add(A1);
					imgList6.add(A2);
					imgList6.add(A3);
					imgList6.add(A4);
					imgList6.add(A5);
					imgList6.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList6));
					categorie = "Immobilier";


				}

				if (position == 5) {
					List<Article> imgList7 = new ArrayList<Article>();
					Article A1 = new Article(R.drawable.nourritureblanc, "Nourriture", "Tomates, Pommes de terre, Ongions, Laitues, Carottes, Radis, Choux , Betraves, Poivrons , Mais, Artichaux ");
					Article A2 = new Article(R.drawable.artisanatblanc, "Artisanat", "Blé, Orges, Farine, Patte, Couscous, Mhamsa, Nwasser, Chorba, Frik ");
					Article A3 = new Article(R.drawable.serviceblanc, "Service", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A4 = new Article(R.drawable.multimediablanc, "Multimedia", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A5 = new Article(R.drawable.immobilierblanc, "Immobilier", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");
					Article A6 = new Article(R.drawable.vetementcouleur, "Vétements", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed ");


					imgList7.add(A1);
					imgList7.add(A2);
					imgList7.add(A3);
					imgList7.add(A4);
					imgList7.add(A5);
					imgList7.add(A6);

					mygrid.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.grid_categorie, imgList7));

					categorie = "Vetements";

				}


			}
		});


		Ariana.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.ariana);
				Ariana.setTextColor(Color.parseColor("#ffcb31"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Ariana";


				return false;
			}
		});

		Beja.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.beja);
				Beja.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));

				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Beja";
				return false;
			}
		});

		Bizerte.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.bizerte);
				Bizerte.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));

				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Bizerte";
				return false;
			}
		});

		Gabes.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.gabes);
				Gabes.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));

				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Gabes";
				return false;
			}
		});

		Gafsa.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.gafsa);
				Gafsa.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));

				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));
				ville = "Gafsa";

				return false;
			}
		});

		Gasserine.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.gasserine);
				Gasserine.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));

				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Gasserine";
				return false;
			}
		});

		Jendouba.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.jendouba);
				Jendouba.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));

				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Jendouba";
				return false;
			}
		});

		Gbeli.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.gbeli);
				Gbeli.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));

				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Gbeli";
				return false;
			}
		});

		Karwen.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.karwen);
				Karwen.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));

				Kef.setTextColor(Color.parseColor("#ffffff"));
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Karwen";
				return false;
			}
		});

		Kef.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.kef);
				Kef.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));

				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Kef";
				return false;
			}
		});

		Mahdia.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.mahdia);
				Mahdia.setTextColor(Color.parseColor("#ffcb31"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Mahdia";
				return false;
			}
		});

		Manouba.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.manouba);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffcb31"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Manouba";
				return false;
			}
		});

		Mednine.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.mednine);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffcb31"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Mednine";
				return false;
			}
		});


		Mestir.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.mestir);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffcb31"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Mestir";
				return false;
			}
		});


		Nabeul.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.nabeul);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffcb31"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Nabeul";
				return false;
			}
		});


		Sfax.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.sfax);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffcb31"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Sfax";
				return false;
			}
		});


		Sidibouzid.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.sidibouzid);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffcb31"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Sidibouzid";
				return false;
			}
		});


		Siliana.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.siliana);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffcb31"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Silana";
				return false;
			}
		});


		Sousse.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.sousse);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffcb31"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Sousse";
				return false;
			}
		});


		Tatawin.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.tatawin);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffcb31"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Tatawin";
				return false;
			}
		});


		Tozeur.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.tozeur);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffcb31"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Tozeur";
				return false;
			}
		});


		Tunis.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.tunis);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffcb31"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Tunis";
				return false;
			}
		});



		Zaghwen.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.zaghwen);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffcb31"));
				AllTunisia.setTextColor(Color.parseColor("#ffffff"));

				ville = "Zaghwen";
				return false;
			}
		});


		AllTunisia.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				TunisiaMap.setImageResource(R.drawable.alltunisia);
				Mahdia.setTextColor(Color.parseColor("#ffffff"));
				Ariana.setTextColor(Color.parseColor("#ffffff"));
				Beja.setTextColor(Color.parseColor("#ffffff"));
				Bizerte.setTextColor(Color.parseColor("#ffffff"));
				Gabes.setTextColor(Color.parseColor("#ffffff"));
				Gafsa.setTextColor(Color.parseColor("#ffffff"));
				Gasserine.setTextColor(Color.parseColor("#ffffff"));
				Jendouba.setTextColor(Color.parseColor("#ffffff"));
				Gbeli.setTextColor(Color.parseColor("#ffffff"));
				Karwen.setTextColor(Color.parseColor("#ffffff"));
				Kef.setTextColor(Color.parseColor("#ffffff"));

				Manouba.setTextColor(Color.parseColor("#ffffff"));
				Mednine.setTextColor(Color.parseColor("#ffffff"));
				Mestir.setTextColor(Color.parseColor("#ffffff"));
				Nabeul.setTextColor(Color.parseColor("#ffffff"));
				Sfax.setTextColor(Color.parseColor("#ffffff"));
				Sidibouzid.setTextColor(Color.parseColor("#ffffff"));
				Siliana.setTextColor(Color.parseColor("#ffffff"));
				Sousse.setTextColor(Color.parseColor("#ffffff"));
				Tatawin.setTextColor(Color.parseColor("#ffffff"));
				Tozeur.setTextColor(Color.parseColor("#ffffff"));
				Tunis.setTextColor(Color.parseColor("#ffffff"));
				Zaghwen.setTextColor(Color.parseColor("#ffffff"));
				AllTunisia.setTextColor(Color.parseColor("#ffcb31"));

				ville = "AllTunisia";
				return false;
			}
		});












		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		View headerView = getLayoutInflater().inflate(
				R.layout.header_navigation_drawer_social, mDrawerList, false);
		myimage = (ImageView) headerView.findViewById(R.id.header_navigation_drawer_social_image);
		UserMail = (TextView) headerView.findViewById(R.id.UserMail);
		UserName= (TextView) headerView.findViewById(R.id.UserName);
		UserDesc= (TextView) headerView.findViewById(R.id.UserDesc);

		UserName.setText(Nom +" "+ Prenom);
		UserMail.setText(Email);
		UserDesc.setText(Description);
		ImageUtil.displayImage(myimage, ImagePath, null);
		//ImageView iv = (ImageView) headerView.findViewById(R.id.imageoo);
		//ImageUtil.displayRoundImage(iv,
		//	"http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

		mDrawerList.addHeaderView(headerView);// Add header before adapter (for
		// pre-KitKat)
		mDrawerList.setAdapter(new DrawerSocialAdapter(this));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		int color = getResources().getColor(R.color.material_grey_100);
		color = Color.argb(0xCD, Color.red(color), Color.green(color),
				Color.blue(color));
		mDrawerList.setBackgroundColor(color);
		mDrawerList.getLayoutParams().width = (int) getResources()
				.getDimension(R.dimen.drawer_width_social);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		/*if (savedInstanceState == null) {
			mDrawerLayout.openDrawer(mDrawerList);
		}*/
	}


	private void initMenuFragment() {
		MenuParams menuParams = new MenuParams();
		menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
		menuParams.setMenuObjects(getMenuObjects());
		menuParams.setClosableOutside(false);

		mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
		mMenuDialogFragment.setItemClickListener(this);
		mMenuDialogFragment.setItemLongClickListener(this);
	}


	private List<MenuObject> getMenuObjects() {
		// You can use any [resource, bitmap, drawable, color] as image:
		// item.setResource(...)
		// item.setBitmap(...)
		// item.setDrawable(...)
		// item.setColor(...)
		// You can set image ScaleType:
		// item.setScaleType(ScaleType.FIT_XY)
		// You can use any [resource, drawable, color] as background:
		// item.setBgResource(...)
		// item.setBgDrawable(...)
		// item.setBgColor(...)
		// You can use any [color] as text color:
		// item.setTextColor(...)
		// You can set any [color] as divider color:
		// item.setDividerColor(...)

		List<MenuObject> menuObjects = new ArrayList<>();

		MenuObject close = new MenuObject();
		close.setResource(R.drawable.icn_close);
		close.setBgColor(R.color.black);
		close.setDividerColor(R.color.black);

		MenuObject send = new MenuObject("Ajouter Nourriture");
		send.setResource(R.drawable.icn_nouriture);
		send.setBgColor(R.color.black);
		send.setDividerColor(R.color.black);

		MenuObject like = new MenuObject("Ajouter Artisanat");
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_artisanat);
		like.setBitmap(b);
		like.setBgColor(R.color.black);
		like.setDividerColor(R.color.black);

		MenuObject addFr = new MenuObject("Ajouter Service");
		BitmapDrawable bd = new BitmapDrawable(getResources(),
				BitmapFactory.decodeResource(getResources(), R.drawable.icn_service));
		addFr.setDrawable(bd);
		addFr.setBgColor(R.color.black);
		addFr.setDividerColor(R.color.black);

		MenuObject addFav = new MenuObject("Ajouter Multimedia");
		addFav.setResource(R.drawable.icn_multimedia);
		addFav.setBgColor(R.color.black);
		addFav.setDividerColor(R.color.black);

		MenuObject block = new MenuObject("Ajouter Vétement");
		block.setResource(R.drawable.icn_vetement);
		block.setBgColor(R.color.black);
		block.setDividerColor(R.color.black);

		MenuObject block2 = new MenuObject("Ajouter Véhicule");
		block2.setResource(R.drawable.icn_vehicule);
		block2.setBgColor(R.color.black);
		block2.setDividerColor(R.color.black);

		menuObjects.add(close);
		menuObjects.add(send);
		menuObjects.add(like);
		menuObjects.add(addFr);
		menuObjects.add(addFav);
		menuObjects.add(block);
		menuObjects.add(block2);
		return menuObjects;
	}


	private void initToolbar() {
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
		TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		//mToolbar.setNavigationIcon(R.drawable.btn_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mToolBarTextView.setText("Tunisa Located Sales");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
			case R.id.context_menu:
				if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
					mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
				}
				break;

			case R.id.about:
			{
				Intent intent = new Intent(RechercheActivity.this, AboutActivity.class);
				startActivity(intent);
				finish();
			}
			break;

			case R.id.HomeBtn:
			{Intent i = new Intent(RechercheActivity.this, Main.class);
				startActivity(i);
				finish();}
			break;
			case R.id.NotificationBtn: {





	/*	if ( WelcomeListArtisanat.backopacity.getAlpha()==1 || WelcomeListNourriture.backopacity.getAlpha()==1 ||WelcomeListService.backopacity.getAlpha()==1)
		{  WelcomeListArtisanat.backopacity.setAlpha(0);
			WelcomeListNourriture.backopacity.setAlpha(0);
			WelcomeListService.backopacity.setAlpha(0);
		}*/
			//	else {

				if(NotificationFragment.isin==true)
				{WelcomeListArtisanat.backopacity.setImageResource(R.drawable.trans);
			WelcomeListNourriture.backopacity.setImageResource(R.drawable.trans);
			WelcomeListService.backopacity.setImageResource(R.drawable.trans);}
				manageFragment(NotificationFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
	//	}
				//backImg.setImageResource(R.drawable.opacityrect3);
			}
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment currentIntanceFragment = findFragmentByTag(tag);
		if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
			if (currentIntanceFragment != null) {
				ft.show(currentIntanceFragment);
			} else {
				currentIntanceFragment = newInstanceFragment;
				ft.add(R.id.MainContainer, currentIntanceFragment, tag.toString());
				if (addToBackStack) {
					ft.addToBackStack(null);
				}
			}
		} else {
			ft.hide(currentIntanceFragment);
			fm.popBackStack();
		}
		ft.commit();
	}

	private Fragment findFragmentByTag(FragmentTags tag) {
		return getSupportFragmentManager().findFragmentByTag(tag.toString());
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			if(position==1)
			{Intent intent = new Intent(RechercheActivity.this, NourritureListActivity.class);
				startActivity(intent);
				finish();}

			if(position==2)
			{Intent intent = new Intent(RechercheActivity.this, ArtisanatListActivity.class);
				startActivity(intent);
				finish();}

			if(position==3)
			{Intent intent = new Intent(RechercheActivity.this, ServiceListActivity.class);
				startActivity(intent);
				finish();}

			if(position==4)
			{Intent intent = new Intent(RechercheActivity.this, MultimediaListActivity.class);
				startActivity(intent);
				finish();}



			if(position==5)
			{Intent intent = new Intent(RechercheActivity.this, ImmobilierListActivity.class);
				startActivity(intent);
				finish();}

			if(position==6)
			{Intent intent = new Intent(RechercheActivity.this, VetementListActivity.class);
				startActivity(intent);
				finish();}

			if(position==7)
			{Intent intent = new Intent(RechercheActivity.this, RechercheActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==8)
			{Intent intent = new Intent(RechercheActivity.this, ProfileActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==9)
			{Intent intent = new Intent(RechercheActivity.this, Welcome.class);
				startActivity(intent);
				finish();
			}

		}
	}

	/*protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
		invalidateOptionsMenu();
		String backStackName = fragment.getClass().getName();
		boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
		if (!fragmentPopped) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(containerId, fragment, backStackName)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			if (addToBackStack)
				transaction.addToBackStack(backStackName);
			transaction.commit();
		}
	}*/

	@Override
	public void onMenuItemClick(View clickedView, int position) {
		if(position==1)
		{   AddProductActivity.categorie="Nourriture";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==2)
		{   AddProductActivity.categorie="Artisanat";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}

		if(position==3)
		{   AddProductActivity.categorie="Services";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==4)
		{   AddProductActivity.categorie="Multimedia";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==5)
		{   AddProductActivity.categorie="Immobilier";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==6)
		{   AddProductActivity.categorie="Vetements";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(RechercheActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onMenuItemLongClick(View clickedView, int position) {
		Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setTitle(int titleId) {
		setTitle(getString(titleId));
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {

	}
}
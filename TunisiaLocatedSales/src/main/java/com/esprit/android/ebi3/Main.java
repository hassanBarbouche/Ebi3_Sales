package com.esprit.android.ebi3;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.esprit.android.ebi3.Utils.GPSTracker;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.ArticleCustomAdapter;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.models.Article;
import com.esprit.android.ebi3.models.Produit;
import com.esprit.android.ebi3.provider.FragmentTags;
import com.esprit.android.ebi3.ui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.viewpagerindicator.CirclePageIndicator;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

	private FragmentManager fragmentManager;
	public static String PREFERENCE_FILENAME = "reporting_app";
	String showUrl = "http://www.e-bi3.com/server/getproductbyuserCategorieOnly.php";
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	GPSTracker gps;
	public ImageView backImg;
	public RelativeLayout maincont;
	RequestQueue requestQueue1;
	Location locationA,locationB,myLocation;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	float distance;
	public double longi,lat;
	private List<Produit> ListNourriture;
	public ViewPager viewpager;
	private List<Article> imgList1, imgList2, imgList3,imgList4,imgList5,imgList6;
	private ContextMenuDialogFragment mMenuDialogFragment;
	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	Fragment fragment;
	Button notifbtn;
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
		imgList1 = new ArrayList<Article>();
		ListNourriture = new ArrayList<Produit>();
		Article A1 = new Article(R.drawable.cleaning, "Ménage", "Femmes de ménage, Equipe de nettoyage, Produit de nettoyage...  ");
		Article A2 = new Article(R.drawable.repare, "Réparation", "Plomberie, Réparation véhicules, Réparation parabole...");
		Article A3 = new Article(R.drawable.babysit, "Babysitting", "Babysitting, surveillance, garderie, jardin d'enfants...");
		Article A4 = new Article(R.drawable.book, "Education", "Cours privés, Fasicules, Documents, Révision...");
		Article A5 = new Article(R.drawable.cooking, "Cuisine", "Gastronomie, Trateurs, Pattiserie...");
		Article A6 = new Article(R.drawable.securite, "Sécurité", "Gardien, Sécurité, Surveillance...");


		imgList1.add(A1);
		imgList1.add(A2);
		imgList1.add(A3);
		imgList1.add(A4);
		imgList1.add(A5);
		imgList1.add(A6);


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


		imgList3 = new ArrayList<Article>();
		Article A20 = new Article(R.drawable.bbroderie, "Broderie", "Koftan, Robe de mariée, Habille traditionel...");
		Article A21 = new Article(R.drawable.ttapis, "Tappiserie", "Tapis, Margoum, Klim");
		Article A22 = new Article(R.drawable.ppoterie, "Potterie", "Différentes variétés de poterie...");
		Article A23 = new Article(R.drawable.ppainting, "Peinture", "Tableaux, Peinture Artisanale...");
		Article A24 = new Article(R.drawable.bbois, "Bois", "Bois, Menuiserie, Artisanat de bois");
		Article A25 = new Article(R.drawable.gguitar, "Intruments Traditionels", "Guitare, Luthe, Flute, Bendir, Darbouka...");
		Article A26 = new Article(R.drawable.mmaison, "Maison", "Decoration et meuble de maison...");


		imgList3.add(A20);
		imgList3.add(A21);
		imgList3.add(A22);
		imgList3.add(A23);
		imgList3.add(A24);
		imgList3.add(A25);
		imgList3.add(A26);


		imgList4 = new ArrayList<Article>();
		Article A30 = new Article(R.drawable.multimediablanc, "Ordinateurs", "Pc de bureau, Pc Portable, Mac, Station de travail...");
		Article A31 = new Article(R.drawable.smartphone, "Téléphones", "Smartphone Apple,Samsung,Huawei,HTC...");
		Article A32 = new Article(R.drawable.tv, "TV", "Télévision LED, Télévision TCD, Télévision Curved, Accesoires...");
		Article A33 = new Article(R.drawable.son, "Son", "Haut-parleurs, Baffles, Micro, Subwoofer, HomeCinema...");
		Article A34 = new Article(R.drawable.apphoto, "Photographie", "Appareil Photo Argentique, Appareil Photo Reflexe numérique, Caméscope...");
		Article A35 = new Article(R.drawable.cd, "Films et Musique", "Films, Albums, Vinyles...");


		imgList4.add(A30);
		imgList4.add(A31);
		imgList4.add(A32);
		imgList4.add(A33);
		imgList4.add(A34);
		imgList4.add(A35);


		imgList5 = new ArrayList<Article>();
		Article A36 = new Article(R.drawable.immobilierblanc, "Maison", "Maison à louer, Maison à vendre... ");
		Article A37 = new Article(R.drawable.appart, "Appartement", "Appartement à louer, Appartements à vendre");
		Article A38 = new Article(R.drawable.entrepot, "Entrepôt", "Entrepot a louer, Entrepot a vendre...");
		Article A39 = new Article(R.drawable.terrain, "Terrain", "Terrain à louer, Terrain a vendre");


		imgList5.add(A36);
		imgList5.add(A37);
		imgList5.add(A38);
		imgList5.add(A39);

		imgList6 = new ArrayList<Article>();
		Article A40 = new Article(R.drawable.tshirt, "Pulls et T-Shirt", "Pulls, T-shirt, Chemise, Veste, Gilet... ");
		Article A41 = new Article(R.drawable.pantalon, "Pantalons et jupes", "Pantalon, Jupes...");
		Article A42 = new Article(R.drawable.chaussure, "Chaussures", "Chaussures, Sandales, Talons...");
		Article A43 = new Article(R.drawable.vetementblanc, "Robes", "Différentes variétés de Robes");
		Article A44 = new Article(R.drawable.bonnet, "Bonnets et casquettes", "Casquettes, Bonnets, Casques, Chapeaux");
		Article A45 = new Article(R.drawable.bijoux, "Bijoux", "Différentes variété de bijoux");


		imgList6.add(A40);
		imgList6.add(A41);
		imgList6.add(A42);
		imgList6.add(A43);
		imgList6.add(A44);
		imgList6.add(A45);

		SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

		String Nom = reportingPref.getString("Nom", "");
		String Prenom = reportingPref.getString("Prenom", "");
		String Email = reportingPref.getString("Email", "");
		String Description = reportingPref.getString("Description", "");
		String Adresse = reportingPref.getString("Adresse", "");
		String Telephone = reportingPref.getString("Telephone", "");
		String ImagePath = reportingPref.getString("ImagePath", "");
		System.out.println(ImagePath+"  aaaaaaaaa");
		 longi = (double )reportingPref.getFloat("long",0.0f);
		 lat = (double) reportingPref.getFloat("lat",0.0f);
		System.out.println(lat+"  " + longi + " mes coordoné");
		myLocation = new Location("my location");
		myLocation.setLatitude(longi);
		myLocation.setLongitude(lat);


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
		backImg = (ImageView) findViewById(R.id.mainBack);
		maincont = (RelativeLayout) findViewById(R.id.MainContainer);
		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();
		//addFragment(new MainFragment(), true, R.id.container);


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
		//getSupportActionBar().setTitle("ebi3");
		//getSupportActionBar().setIcon(R.drawable.tlslogo);
		//getSupportActionBar().setCustomView(R.layout.menu_tab);
		ViewGroup cont = null;



/*		mNotificationManager = (NotificationManager)
				Main.this.getSystemService(Context.NOTIFICATION_SERVICE);

		final PendingIntent contentIntent = PendingIntent.getActivity(   Main.this, 0,
				new Intent(   Main.this, Main.class), 0);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(   Main.this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle("Ebi3 Notification")
						.setStyle(new NotificationCompat.BigTextStyle()
								.bigText("this"))
						.setContentText("this");

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

		Map<String, String> params = new HashMap<String, String>();
		params = new HashMap<String, String>();
		params.put("category", "Services");

		requestQueue1 = Volley.newRequestQueue(getApplicationContext());
		//System.out.println("ww");
		final ProgressDialog loading = ProgressDialog.show(Main.this, "Chargement...", "Un petit moment svp...", false, false);
		CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				System.out.println(response.toString());
				try {
					final AtomicBoolean done = new AtomicBoolean(false);

					JSONArray listproduit = response.getJSONArray("product");
					for (int i = 0; i < listproduit.length(); i++) {
						JSONObject nourriture = listproduit.getJSONObject(i);


						double longitude1=0,longitude2=0,latitude1=0,latitude2=0;

						System.out.println(nourriture.getString("name"));
						Geocoder coder = new Geocoder(Main.this);
						try {
							ArrayList<Address> a = (ArrayList<Address>) coder.getFromLocationName("Tunisie, Kef", 1);
							for(Address add : a){

									 longitude1 = add.getLongitude();
									 latitude1 = add.getLatitude();
								 locationA = new Location("point A");
								locationA.setLatitude(longitude1);
								locationA.setLongitude(latitude1);
								System.out.println(longitude1 + " hay ilongitude");
								System.out.println(latitude1 + " hay ilatitude");


						} }catch (IOException e) {
							e.printStackTrace();
						}


						try {
							ArrayList<Address> b = (ArrayList<Address>) coder.getFromLocationName("Tunisie Kef", 1);
							for(Address add : b){

								 longitude2 = add.getLongitude();
								 latitude2 = add.getLatitude();

								final float[] results= new float[3];
								Location.distanceBetween(lat,longi,latitude2,longitude2,results);
								System.out.println(results[0] + " the 0 result");
								System.out.println(results[1] + " the 1 result");
								System.out.println(results[2] + " the 2 result");
							} }catch (IOException e) {
							e.printStackTrace();
						}
						locationB = new Location("point B");
						locationB.setLatitude(36.800320);
						locationB.setLongitude(10.187253);

						Location locationC = new Location("point B");
						locationC.setLatitude(36.657996);
						locationC.setLongitude( 9.579736);
						distance = myLocation.distanceTo(locationA);
						double dist = distance(36.800320,10.187253,36.767271, 10.271513);


						BackGround2 bik = new BackGround2();
						bik.execute();

						System.out.println(dist + " laaa distance");

						System.out.println(distance + " this is distance");
						if (Objects.equals(nourriture.getString("place"), "Kef")){



							NotificationCompat.Builder mBuilder =
									new NotificationCompat.Builder(   Main.this)
											.setSmallIcon(R.drawable.ic_launcher)
											.setContentTitle( nourriture.getString("name"))
											.setStyle(new NotificationCompat.BigTextStyle()
													.bigText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
															+" prés de chez vous"))
											.setContentText(nourriture.getString("nom") + nourriture.getString("prenom")+" vend "+ nourriture.getString("name")
													+" prés de chez vous");

							mBuilder.setContentIntent(contentIntent);
							mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

						}





					}








					System.out.println(ListNourriture.size() + " this is size");

					loading.dismiss();

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.append(error.getMessage());

			}
		});
		requestQueue1.add(jsObjRequest);
		//	else nourlist.setAdapter(new ProduitCustomAdapter(getBaseContext(), R.layout.one_article_main, imgList1));
*/
		viewpager = (ViewPager) findViewById(R.id.MainViewPager);
		notifbtn = (Button) findViewById(R.id.NotificationBtn);
		FragmentManager fragmentManager = getSupportFragmentManager();

		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
		if(extras.getString("from") != null && (extras.getString("from").equals("one")))
		{

			manageFragment(TutoFragment.newInstance(), FragmentTags.CUSTOMIZE, true);

		}
		}
		viewpager.setAdapter(new MainVadapter(fragmentManager));
		//	WelcomeListNourriture.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList2));
		viewpager.setOffscreenPageLimit(6);


		viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			int positionCurrent;
			int thispos;
			boolean dontLoadList;

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				thispos = position;


			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == 0) { // the viewpager is idle as swipping ended
					new Handler().postDelayed(new Runnable() {
						public void run() {
							if (!dontLoadList) {
								if (thispos == 0) {
									WelcomeListNourriture.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList2));
								}

								if (thispos == 1) {
									WelcomeListArtisanat.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList3));

								}

								if (thispos == 2) {
									WelcomeListService.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList1));

								}

								if (thispos == 3) {
									WelcomeListMultimedia.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList4));

								}

								if (thispos == 4) {
									WelcomeListImmobilier.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList5));

								}

								if (thispos == 5) {
									WelcomeListVetement.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList6));

								}
								//async thread code to execute loading the list...
							}
						}
					}, 200);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				positionCurrent = position;
				if (positionOffset == 0 && positionOffsetPixels == 0) // the offset is zero when the swiping ends
				{
					dontLoadList = false;
				} else
					dontLoadList = true; // To avoid loading content for list after swiping the pager.
			}
		});


		CirclePageIndicator titleIndicator = (CirclePageIndicator) findViewById(R.id.titles);
		titleIndicator.setViewPager(viewpager);


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
	//	Glide.with(Main.this)
	//			.load("http://www.online-image-editor.com//styles/2014/images/example_image.png")
	//			.into(myimage);
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

		MenuObject block2 = new MenuObject("Ajouter Immobilier");
		block2.setResource(R.drawable.icn_immobilier);
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
					Intent intent = new Intent(Main.this, AboutActivity.class);
					 startActivity(intent);
					 finish();
				}
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
			{Intent intent = new Intent(Main.this, NourritureListActivity.class);
			startActivity(intent);
			finish();}

			if(position==2)
			{Intent intent = new Intent(Main.this, ArtisanatListActivity.class);
				startActivity(intent);
				finish();}

			if(position==3)
			{Intent intent = new Intent(Main.this, ServiceListActivity.class);
				startActivity(intent);
				finish();}

			if(position==4)
			{Intent intent = new Intent(Main.this, MultimediaListActivity.class);
				startActivity(intent);
				finish();}



			if(position==5)
			{Intent intent = new Intent(Main.this, ImmobilierListActivity.class);
				startActivity(intent);
				finish();}

			if(position==6)
			{Intent intent = new Intent(Main.this, VetementListActivity.class);
				startActivity(intent);
				finish();}

			if(position==7)
			{Intent intent = new Intent(Main.this, RechercheActivity.class);
				startActivity(intent);
			finish();
			}
			if(position==8)
			{Intent intent = new Intent(Main.this, ProfileActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==9)
			{Intent intent = new Intent(Main.this, Welcome.class);
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
		//Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
		if(position==1)
		{   AddProductActivity.categorie="Nourriture";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==2)
		{   AddProductActivity.categorie="Artisanat";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}

		if(position==3)
		{   AddProductActivity.categorie="Services";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==4)
		{   AddProductActivity.categorie="Multimedia";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==5)
		{
			AddProductActivity.categorie="Vetements";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==6)
		{
			AddProductActivity.categorie="Immobilier";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(Main.this, AddProductActivity.class);
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

	private double getDistanceInfo(double lat1, double lng1, String destinationAddress) {
		StringBuilder stringBuilder = new StringBuilder();
		Double dist = 0.0;
		try {

			destinationAddress = destinationAddress.replaceAll(" ","%20");
			String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lng1 + "&destination=" + destinationAddress + "&mode=driving&sensor=false";

			HttpPost httppost = new HttpPost(url);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();


			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject = new JSONObject(stringBuilder.toString());

			JSONArray array = jsonObject.getJSONArray("routes");

			JSONObject routes = array.getJSONObject(0);

			JSONArray legs = routes.getJSONArray("legs");

			JSONObject steps = legs.getJSONObject(0);

			JSONObject distance = steps.getJSONObject("distance");

			Log.i("Distance", distance.toString());
			dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dist;
	}



	private double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		return (dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	  class BackGround2 extends AsyncTask<Void, Void, Void> {


		@Override
		protected Void doInBackground(Void... voids) {
			double dd = getDistanceInfo(lat,longi,"Menzah 6 Ariana Tunisie");
			System.out.println(dd + "distance from async");


			return null;
		}
	}

	class MainVadapter extends FragmentStatePagerAdapter {


		public MainVadapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			 fragment = null;
			if (position == 0) {
				fragment = new WelcomeListNourriture();


			}
			if (position == 1) {
				fragment = new WelcomeListArtisanat();


			}

			if (position == 2) {
				fragment = new WelcomeListService();


			}

			if (position == 3) {
				fragment = new WelcomeListMultimedia();


			}

			if (position == 4) {
				fragment = new WelcomeListImmobilier();


			}

			if (position == 5) {
				fragment = new WelcomeListVetement();


			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 6;
		}
	}
}
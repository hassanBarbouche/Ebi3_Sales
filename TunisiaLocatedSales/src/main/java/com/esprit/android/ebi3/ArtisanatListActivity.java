package com.esprit.android.ebi3;

import android.app.ProgressDialog;
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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.adapters.ProduitCustomAdapter;
import com.esprit.android.ebi3.adapters.SwipeCategorieArtisanatAdapter;
import com.esprit.android.ebi3.adapters.SwipeImageAdapter;
import com.esprit.android.ebi3.models.Produit;
import com.esprit.android.ebi3.models.User;
import com.esprit.android.ebi3.provider.FragmentTags;
import com.esprit.android.ebi3.ui.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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


public class ArtisanatListActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
     private Button firsticone;
	RequestQueue requestQueue1;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	public ViewPager viewpager;
	private List<Produit> imgList1,imgList2,imgList3,ListNourriture;
	public  static ListView nourlist;

	//public static int prodimg;
	String showUrl = "http://www.e-bi3.com/server/getproductbyuserCategorie.php";
	String showUrl2 = "http://www.e-bi3.com/server/getproductbyuserCategorieOnly.php";
	public static String PREFERENCE_FILENAME = "reporting_app";
	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;
	protected ImageLoader imageLoader;

	private ContextMenuDialogFragment mMenuDialogFragment;
	Fragment fragment;
	Button notifbtn;
	private FragmentManager fragmentManager;

	@Override
	protected void onResume() {
		super.onResume();
		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 imgList1 = new ArrayList<Produit>();

		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));


		ListNourriture = new ArrayList<Produit>();





		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_nourriture);
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

		String Nom = reportingPref.getString("Nom", "");
		String Prenom = reportingPref.getString("Prenom", "");
		String Email = reportingPref.getString("Email", "");
		String Description = reportingPref.getString("Description", "");
		String Adresse = reportingPref.getString("Adresse", "");
		String Telephone = reportingPref.getString("Telephone", "");
		String ImagePath = reportingPref.getString("ImagePath", "");


		notifbtn = (Button) findViewById(R.id.NotificationBtn);
		FragmentManager fragmentManager = getSupportFragmentManager();
		nourlist = (ListView) findViewById(R.id.nourritureList);

		nourlist.setBackgroundResource(R.drawable.artisanatback);

		JSONObject paramss = new JSONObject();
		try {
			paramss.put("category", "Fruits");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Map<String, String> params = new HashMap<String, String>();

		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			if( extras.getString("From")!=null)
			{
				if(extras.getString("From").equals("Broderie"))
				{


					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Broderie");

				}
				if(extras.getString("From").equals("Tappiserie"))
				{



					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Tappiserie");
					//WelcomeListNourriture.From=null;

				}

				if(extras.getString("From").equals("Poterie"))
				{


					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Poterie");

				}

				if(extras.getString("From").equals("Peinture"))
				{


					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Peinture");

				}

				if(extras.getString("From").equals("Bois"))
				{



					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Bois");

				}

				if(extras.getString("From").equals("Instruments de musique"))
				{


					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Instruments de musique");

				}

				if(extras.getString("From").equals("Maison"))
				{


					params = new HashMap<String, String>();
					params.put("category", "Artisanat");
					params.put("SubCategory", "Maison");

				}






			}}

		else{

			showUrl=showUrl2;
			params = new HashMap<String, String>();
			params.put("category", "Artisanat");
		}






		/////////////////////////////////////////////////////**********************//////////////////////////////////////////////////////////////////////////////////////////////

		requestQueue1 = Volley.newRequestQueue(getApplicationContext());
		//System.out.println("ww");
		final ProgressDialog loading = ProgressDialog.show(ArtisanatListActivity.this,"Chargement...","Un petit moment svp...",false,false);
		CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, showUrl, params, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				System.out.println(response.toString());
				try {
					final AtomicBoolean done = new AtomicBoolean(false);

					JSONArray listproduit = response.getJSONArray("product");
					for (int i = listproduit.length()-1; i > 0; i--) {
						JSONObject nourriture = listproduit.getJSONObject(i);





						ListNourriture.add(new Produit(
								nourriture.getString("ProductImage1"),
								nourriture.getString("ProductImage2"),
								nourriture.getString("ProductImage3"),
								nourriture.getString("ProductImage1Title"),
								nourriture.getString("ProductImage2Title"),
								nourriture.getString("ProductImage3Title"),
								nourriture.getString("ProductImage1Desc"),
								nourriture.getString("ProductImage2Desc"),
								nourriture.getString("ProductImage3Desc"),
								nourriture.getString("name"),
								nourriture.getString("description"),
								nourriture.getString("place"),
								nourriture.getString("quantity"),
								nourriture.getString("date"),
								(float) nourriture.getInt("price"),

								new User(nourriture.getInt("Id"), nourriture.getString("nom") + nourriture.getString("prenom"),
										nourriture.getString("Description"),
										"Jobs",
										"Tunisie",
										nourriture.getString("adresse"),
										nourriture.getString("tel"),
										nourriture.getString("email"),
										nourriture.getString("ImagePath"),
										nourriture.getInt("rate"),
										nourriture.getInt("Ratedby"))
						));
						System.out.println(ListNourriture.size() + " my size");
						System.out.println(nourriture.getInt("Id")+" zzzzz");





					}








					System.out.println(ListNourriture.size()+" this is size");
					nourlist.setAdapter(new ProduitCustomAdapter(getBaseContext(), R.layout.one_article_main, ListNourriture));
					loading.dismiss();

				} catch (JSONException e) {
					e.printStackTrace();
					loading.dismiss();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.append(error.getMessage());
				loading.dismiss();

			}
		});
		requestQueue1.add(jsObjRequest);


/*		if (SwipeCategorieAdapter.touched==true)
		{
			nourlist.setAdapter(new ProduitCustomAdapter(getBaseContext(), R.layout.one_article_main, imgList2));
		}*/

		nourlist = (ListView) findViewById(R.id.nourritureList);
		nourlist.setAdapter(new ProduitCustomAdapter(getBaseContext(), R.layout.one_article_main, imgList1));
        viewpager=(ViewPager)findViewById(R.id.categorieViewPager);
		viewpager.setAdapter(new SwipeCategorieArtisanatAdapter(this));


		nourlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {


				Produit prod = (Produit) parent.getItemAtPosition(position);

				SwipeImageAdapter.prodimg= prod.getProductImage();
				SwipeImageAdapter.prodimg2= prod.getProductImage2();
				SwipeImageAdapter.prodimg3= prod.getProductImage3();
				SwipeImageAdapter.title1= prod.getProductImageTitle();
				SwipeImageAdapter.title2= prod.getProductImage2Title();
				SwipeImageAdapter.title3= prod.getProductImageT3itle();
				SwipeImageAdapter.desc1= prod.getProductImageDescription();
				SwipeImageAdapter.desc2= prod.getProductImage2Description();
				SwipeImageAdapter.desc3= prod.getProductImage3Description();
				//prodimg = prod.getProductImage();
				Intent i = new Intent(ArtisanatListActivity.this, SingleProductActivity.class);
				i.putExtra("name", prod.Titre + "");
				i.putExtra("description", prod.Desc + "");
				i.putExtra("quantite", prod.Quantite + "");
				i.putExtra("lieu", prod.Lieu + "");
				i.putExtra("date", prod.Date + "");
				i.putExtra("prix", prod.Prix + "");
				i.putExtra("user", prod.user);
				i.putExtra("from","artisanat");


				i.putExtra("produitimage1", prod.ProductImage);

				startActivity(i);
				finish();


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

			case R.id.HomeBtn:
			{Intent i = new Intent(ArtisanatListActivity.this, Main.class);
				startActivity(i);
				finish();}
			break;
			case R.id.about:
			{
				Intent intent = new Intent(ArtisanatListActivity.this, AboutActivity.class);
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
			{Intent intent = new Intent(ArtisanatListActivity.this, NourritureListActivity.class);
				startActivity(intent);
				finish();}

			if(position==2)
			{Intent intent = new Intent(ArtisanatListActivity.this, ArtisanatListActivity.class);
				startActivity(intent);
				finish();}

			if(position==3)
			{Intent intent = new Intent(ArtisanatListActivity.this, ServiceListActivity.class);
				startActivity(intent);
				finish();}

			if(position==4)
			{Intent intent = new Intent(ArtisanatListActivity.this, MultimediaListActivity.class);
				startActivity(intent);
				finish();}



			if(position==5)
			{Intent intent = new Intent(ArtisanatListActivity.this, ImmobilierListActivity.class);
				startActivity(intent);
				finish();}

			if(position==6)
			{Intent intent = new Intent(ArtisanatListActivity.this, VetementListActivity.class);
				startActivity(intent);
				finish();}

			if(position==7)
			{Intent intent = new Intent(ArtisanatListActivity.this, RechercheActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==8)
			{Intent intent = new Intent(ArtisanatListActivity.this, ProfileActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==9)
			{Intent intent = new Intent(ArtisanatListActivity.this, Welcome.class);
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void onMenuItemClick(View clickedView, int position) {
		if(position==1)
		{   AddProductActivity.categorie="Nourriture";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==2)
		{   AddProductActivity.categorie="Artisanat";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}

		if(position==3)
		{   AddProductActivity.categorie="Services";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==4)
		{   AddProductActivity.categorie="Multimedia";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==5)
		{   AddProductActivity.categorie="Immobilier";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==6)
		{   AddProductActivity.categorie="Vetements";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(ArtisanatListActivity.this, AddProductActivity.class);
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
		Intent i = new Intent(ArtisanatListActivity.this, Main.class);
		startActivity(i);
		finish();
	}
}


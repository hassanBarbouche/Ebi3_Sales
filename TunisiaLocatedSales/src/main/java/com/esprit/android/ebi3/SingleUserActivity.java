package com.esprit.android.ebi3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.CommentaireCustomAdapter;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.models.Comment;
import com.esprit.android.ebi3.models.Commentaire;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SingleUserActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
     private Button firsticone;
	String insertUrl = "http://www.e-bi3.com/server/InsertCom.php";
	String showUrl = "http://www.e-bi3.com/server/update_rate.php";
	int id;
	int nbruser;
	public static String PREFERENCE_FILENAME = "reporting_app";
	private List<Comment>  listcomment;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	public ViewPager viewpager;
	public EditText comenter;

	public Button comOK;
	public int putid;
	Button star1,star2,star3,star4,star5,rateit;
	public GridView myGrid;
	public List<Commentaire> comList;
    private TextView UserName,UserDesc,UserVille,UserTravail,UserAdresse,UserTel,UserMail,nbrRate;
	private ImageView UserImg;
	private ContextMenuDialogFragment mMenuDialogFragment;
	Fragment fragment;
	private TextView userdesc;
	private ImageView userimg;
	Button notifbtn;
	private ImageView myimage;
	private TextView UserMail2,UserDesc2,UserName2;
	private FragmentManager fragmentManager;
	int RATE=0;
	protected ImageLoader imageLoader;

	@Override
	protected void onResume() {
		super.onResume();
		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		comList = new ArrayList<Commentaire>();
		listcomment = new ArrayList<Comment>();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));

		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");





		final SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

		id = reportingPref.getInt("Id", 0);
		String Nom = reportingPref.getString("Nom", "");
		String Prenom = reportingPref.getString("Prenom", "");
		String Email = reportingPref.getString("Email", "");
		String Description = reportingPref.getString("Description", "");
		String Adresse = reportingPref.getString("Adresse", "");
		String Telephone = reportingPref.getString("Telephone", "");
		String ImagePath = reportingPref.getString("ImagePath", "");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_page);

		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);



		star1 = (Button) findViewById(R.id.star1);
		star2 = (Button) findViewById(R.id.star2);
		star3 = (Button) findViewById(R.id.star3);
		star4 = (Button) findViewById(R.id.star4);
		star5 = (Button) findViewById(R.id.star5);
		rateit = (Button) findViewById(R.id.rateit);

        comenter = (EditText) findViewById(R.id.comenter);
		comOK = (Button) findViewById(R.id.ButtonOK);
		nbrRate = (TextView) findViewById(R.id.numerofraiting);
		UserName = (TextView) findViewById(R.id.UserName);
		UserDesc = (TextView) findViewById(R.id.Descri);
		UserVille = (TextView) findViewById(R.id.UserVille);
		UserTravail = (TextView) findViewById(R.id.UserMetier);
		UserAdresse = (TextView) findViewById(R.id.UserAdresse);
		UserTel = (TextView) findViewById(R.id.UserTel);
		UserMail = (TextView) findViewById(R.id.UserMail);
		UserImg = (ImageView) findViewById(R.id.UserImg);
		myGrid = (GridView) findViewById(R.id.CommentaireList);
		userdesc = (TextView) findViewById(R.id.Descri);
		userimg = (ImageView) findViewById(R.id.UserImg);
		//Intent i = getIntent();
		//myGrid.setAdapter(new CommentaireCustomAdapter(getBaseContext(), R.layout.commentaire_layout, comList));
		Bundle extras = getIntent().getExtras();
		final User user = (User) extras.getSerializable("user");

		System.out.println(user.getId()+" finalid");
		System.out.println(user.getRatedby()+" banana yejbed feha");
		int Vectoriasekret = user.getRatedby();
		putid= user.getId();
		UserName.setText(user.UserName);
		UserDesc.setText(user.UserDescription);
		UserVille.setText(user.UserVille);
		UserTravail.setText(user.UserMetier);
		UserAdresse.setText(user.UserAdresse);
		UserTel.setText(user.UserTelephone);
		UserMail.setText(user.UserMail);
		userdesc.setText(user.UserDescription);
		UserImg.setImageResource(user.UserImg);
		ImageUtil.displayRoundImage(userimg, user.ImagePath, null);
		nbrRate.setText(Vectoriasekret+"");



		BackGround b = new BackGround();
		b.execute(putid + "");

		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		notifbtn = (Button) findViewById(R.id.NotificationBtn);
		FragmentManager fragmentManager = getSupportFragmentManager();



		if(user.getRatedby()==0)
		{nbruser=1;}
		else{nbruser=user.getRatedby()+1;}

		if((user.getRate()/nbruser)==0)
		{
			star1.setBackgroundResource(R.drawable.onestarvide);
			star2.setBackgroundResource(R.drawable.onestarvide);
			star3.setBackgroundResource(R.drawable.onestarvide);
			star4.setBackgroundResource(R.drawable.onestarvide);
			star5.setBackgroundResource(R.drawable.onestarvide);

		}

		if((user.getRate()/nbruser)==1)
		{
			star1.setBackgroundResource(R.drawable.onestarfull);
			star2.setBackgroundResource(R.drawable.onestarvide);
			star3.setBackgroundResource(R.drawable.onestarvide);
			star4.setBackgroundResource(R.drawable.onestarvide);
			star5.setBackgroundResource(R.drawable.onestarvide);

		}

		if((user.getRate()/nbruser)==2)
		{
			star1.setBackgroundResource(R.drawable.onestarfull);
			star2.setBackgroundResource(R.drawable.onestarfull);
			star3.setBackgroundResource(R.drawable.onestarvide);
			star4.setBackgroundResource(R.drawable.onestarvide);
			star5.setBackgroundResource(R.drawable.onestarvide);

		}

		if((user.getRate()/nbruser)==3)
		{
			star1.setBackgroundResource(R.drawable.onestarfull);
			star2.setBackgroundResource(R.drawable.onestarfull);
			star3.setBackgroundResource(R.drawable.onestarfull);
			star4.setBackgroundResource(R.drawable.onestarvide);
			star5.setBackgroundResource(R.drawable.onestarvide);

		}

		if((user.getRate()/nbruser)==4)
		{
			star1.setBackgroundResource(R.drawable.onestarfull);
			star2.setBackgroundResource(R.drawable.onestarfull);
			star3.setBackgroundResource(R.drawable.onestarfull);
			star4.setBackgroundResource(R.drawable.onestarfull);
			star5.setBackgroundResource(R.drawable.onestarvide);

		}
		if((user.getRate()/nbruser)==5)
		{
			star1.setBackgroundResource(R.drawable.onestarfull);
			star2.setBackgroundResource(R.drawable.onestarfull);
			star3.setBackgroundResource(R.drawable.onestarfull);
			star4.setBackgroundResource(R.drawable.onestarfull);
			star5.setBackgroundResource(R.drawable.onestarfull);

		}


		star1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

                star1.setBackgroundResource(R.drawable.onestarfull);
	            star2.setBackgroundResource(R.drawable.onestarvide);
				star3.setBackgroundResource(R.drawable.onestarvide);
				star4.setBackgroundResource(R.drawable.onestarvide);
				star5.setBackgroundResource(R.drawable.onestarvide);
				RATE = 1;


			}});

		star2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				star1.setBackgroundResource(R.drawable.onestarfull);
				star2.setBackgroundResource(R.drawable.onestarfull);
				star3.setBackgroundResource(R.drawable.onestarvide);
				star4.setBackgroundResource(R.drawable.onestarvide);
				star5.setBackgroundResource(R.drawable.onestarvide);
				RATE = 2;

			}});

		star3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				star1.setBackgroundResource(R.drawable.onestarfull);
				star2.setBackgroundResource(R.drawable.onestarfull);
				star3.setBackgroundResource(R.drawable.onestarfull);
				star4.setBackgroundResource(R.drawable.onestarvide);
				star5.setBackgroundResource(R.drawable.onestarvide);

				RATE = 3;
			}});

		star4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				star1.setBackgroundResource(R.drawable.onestarfull);
				star2.setBackgroundResource(R.drawable.onestarfull);
				star3.setBackgroundResource(R.drawable.onestarfull);
				star4.setBackgroundResource(R.drawable.onestarfull);
				star5.setBackgroundResource(R.drawable.onestarvide);

				RATE = 4;
			}});

		star5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				star1.setBackgroundResource(R.drawable.onestarfull);
				star2.setBackgroundResource(R.drawable.onestarfull);
				star3.setBackgroundResource(R.drawable.onestarfull);
				star4.setBackgroundResource(R.drawable.onestarfull);
				star5.setBackgroundResource(R.drawable.onestarfull);

				RATE = 5;
			}});

		rateit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				star1.setClickable(false);
				star2.setClickable(false);
				star3.setClickable(false);
				star4.setClickable(false);
				star5.setClickable(false);
				rateit.setClickable(false);

				final int finalerate =( user.getRate() + RATE ) ;
				//nbrrate.setText(nbrrate.getText());
				System.out.println(finalerate);
				nbrRate.setText((Integer.parseInt(nbrRate.getText().toString()) + 1) + "");

				//				  final ProgressDialog loading = ProgressDialog.show(mContext, "Mise a jour...", "Un petit moment svp...", false, false);
				//System.out.println(holder.textTitle.getText().toString() + " this is texttts");
				StringRequest request = new StringRequest(Request.Method.POST, showUrl, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.println(response.toString());
						//	  loading.dismiss();
						Toast.makeText(SingleUserActivity.this, "Rating effectué !", Toast.LENGTH_LONG).show();
						System.out.println(response + "this is resp");
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// loading.dismiss();

						//Showing toast
						Toast.makeText(SingleUserActivity.this, error.getMessage()+"", Toast.LENGTH_LONG).show();
						System.out.println(error.getMessage() + "this is resp");
					}
				}) {

					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> params = new HashMap<String, String>();
						params.put("id", user.getId()+"");
						params.put("rate", finalerate+"");
						params.put("Ratedby",(user.getRatedby()+1)+"");

						return params;
					}
				};

				RequestQueue requestQueue = Volley.newRequestQueue(SingleUserActivity.this);


				requestQueue.add(request);



			}});

		comOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				listcomment.add(new Comment(
						putid,
						comenter.getText().toString(),
						new User( reportingPref.getString("Nom", "") + reportingPref.getString("Prenom", ""),
								reportingPref.getString("Description", ""),
								"Jobs",
								"Tunisie",
								reportingPref.getString("Adresse", ""),
								reportingPref.getString("Telephone", ""),
								reportingPref.getString("Email", ""),
								reportingPref.getString("ImagePath", ""))
				));
				myGrid.setAdapter(new CommentaireCustomAdapter(getBaseContext(), R.layout.commentaire_layout, listcomment));


				final ProgressDialog loading = ProgressDialog.show(SingleUserActivity.this,"Uploading...","Please wait...",false,false);

				StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						System.out.println(response.toString());
						loading.dismiss();
						Toast.makeText(SingleUserActivity.this, response , Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loading.dismiss();

						//Showing toast
						Toast.makeText(SingleUserActivity.this, error.getMessage()+"", Toast.LENGTH_LONG).show();

					}
				}) {

					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String,String> parameters  = new HashMap<String, String>();


						parameters.put("corps",comenter.getText().toString());
						parameters.put("owner", id + "");
						parameters.put("commented",putid+"");

						return parameters;
					}
				};

				RequestQueue requestQueue = Volley.newRequestQueue(SingleUserActivity.this);



				requestQueue.add(request);


			}});



		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.list_view);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		View headerView = getLayoutInflater().inflate(
				R.layout.header_navigation_drawer_social, mDrawerList, false);

		myimage = (ImageView) headerView.findViewById(R.id.header_navigation_drawer_social_image);
		UserMail2 = (TextView) headerView.findViewById(R.id.UserMail);
		UserName2= (TextView) headerView.findViewById(R.id.UserName);
		UserDesc2= (TextView) headerView.findViewById(R.id.UserDesc);

		UserName2.setText(Nom +" "+ Prenom);
		UserMail2.setText(Email);
		UserDesc2.setText(Description);
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
			{Intent i = new Intent(SingleUserActivity.this, Main.class);
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
				{
					//WelcomeListArtisanat.backopacity.setImageResource(R.drawable.trans);
					//WelcomeListNourriture.backopacity.setImageResource(R.drawable.trans);
					//WelcomeListService.backopacity.setImageResource(R.drawable.trans);
				}
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
			{Intent intent = new Intent(SingleUserActivity.this, NourritureListActivity.class);
				startActivity(intent);
				finish();}

			if(position==2)
			{Intent intent = new Intent(SingleUserActivity.this, ArtisanatListActivity.class);
				startActivity(intent);
				finish();}

			if(position==3)
			{Intent intent = new Intent(SingleUserActivity.this, ServiceListActivity.class);
				startActivity(intent);
				finish();}

			if(position==4)
			{Intent intent = new Intent(SingleUserActivity.this, MultimediaListActivity.class);
				startActivity(intent);
				finish();}



			if(position==5)
			{Intent intent = new Intent(SingleUserActivity.this, ImmobilierListActivity.class);
				startActivity(intent);
				finish();}

			if(position==6)
			{Intent intent = new Intent(SingleUserActivity.this, VetementListActivity.class);
				startActivity(intent);
				finish();}

			if(position==7)
			{Intent intent = new Intent(SingleUserActivity.this, RechercheActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==8)
			{Intent intent = new Intent(SingleUserActivity.this, ProfileActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==9)
			{Intent intent = new Intent(SingleUserActivity.this, Welcome.class);
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
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==2)
		{   AddProductActivity.categorie="Artisanat";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}

		if(position==3)
		{   AddProductActivity.categorie="Services";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==4)
		{   AddProductActivity.categorie="Multimedia";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==5)
		{   AddProductActivity.categorie="Immobilier";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==6)
		{   AddProductActivity.categorie="Vetements";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(SingleUserActivity.this, AddProductActivity.class);
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

	class BackGround extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String id = params[0];

			String data="";
			int tmp;

			try {
				URL url = new URL("http://www.e-bi3.com/server/getCommentByUser.php");
				String urlParams = "commented="+id;
				System.out.println("this iddddd "+ id);

				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setDoOutput(true);
				OutputStream os = httpURLConnection.getOutputStream();
				os.write(urlParams.getBytes());
				os.flush();
				os.close();

				InputStream is = httpURLConnection.getInputStream();
				while((tmp=is.read())!=-1){
					data+= (char)tmp;
				}

				is.close();
				httpURLConnection.disconnect();
				System.out.println("this is " + data);
				return data;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return "Exception: "+e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "Exception: "+e.getMessage();
			}
		}

		@Override
		protected void onPostExecute(String s) {

			System.out.println("this is on post");



			try {
				JSONObject root = new JSONObject(s);
				JSONArray user_data = root.getJSONArray("commentaire");

				for (int i = 0; i < user_data.length(); i++) {
					JSONObject Comments = user_data.getJSONObject(i);





					listcomment.add(new Comment(
							Integer.parseInt(Comments.getString("id")),
							Comments.getString("corps"),
							new User( Comments.getString("nom") + Comments.getString("prenom"),
									Comments.getString("Description"),
									"Jobs",
									"Tunisie",
									Comments.getString("adresse"),
									Comments.getString("tel"),
									Comments.getString("email"),
									Comments.getString("ImagePath"))
					));






				}
				myGrid.setAdapter(new CommentaireCustomAdapter(getBaseContext(), R.layout.commentaire_layout, listcomment));

			} catch (JSONException e)
			{
				e.printStackTrace();
				System.out.println("Exception: "+e.getMessage());
				if(e!=null){ Toast.makeText(SingleUserActivity.this, "User not found", Toast.LENGTH_LONG).show();}
			}




		}
	}
}


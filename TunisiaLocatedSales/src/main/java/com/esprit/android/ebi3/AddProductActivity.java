package com.esprit.android.ebi3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.provider.FragmentTags;
import com.esprit.android.ebi3.ui.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddProductActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private int position ;
	public static String PREFERENCE_FILENAME = "reporting_app";
	private int PICK_IMAGE_REQUEST = 1;
	public static String categorie;
	private TextView headertxt;
	public static RelativeLayout mainlay;
	private CharSequence mDrawerTitle;
	String insertUrl = "http://www.e-bi3.com/server/addproduct.php";
	private CharSequence mTitle;
	private ImageView headerimg;
	String image1,image2,image3;
	public ViewPager viewpager;
	public  Bitmap bitmap1,bitmap2,bitmap3;
	private ImageView photo1,photo2,photo3;
	private EditText titre,desc,quantite,prix,photo1titre,photo2titre,photo3titre,photo1desc,photo2desc,photo3desc;

	private ContextMenuDialogFragment mMenuDialogFragment;
	Fragment fragment;
	Button notifbtn;
	int id;
	private FragmentManager fragmentManager;
    Button addphoto1,addphoto2,addphoto3,envoyer;
	protected ImageLoader imageLoader;
	private Spinner sp,spville ;

	private ImageView myimage;
	private TextView UserMail,UserDesc,UserName;



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));


		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
        position=0;


		SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
		String Nom = reportingPref.getString("Nom", "");
		String Prenom = reportingPref.getString("Prenom", "");
		String Email = reportingPref.getString("Email", "");
		String Description = reportingPref.getString("Description", "");
		String Adresse = reportingPref.getString("Adresse", "");
		String Telephone = reportingPref.getString("Telephone", "");
		String ImagePath = reportingPref.getString("ImagePath", "");
		 id = reportingPref.getInt("Id", 0);


		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);
		sp = (Spinner) findViewById(R.id.spinnecategorie);
		spville = (Spinner) findViewById(R.id.spinneville);
        headerimg = (ImageView) findViewById(R.id.imageView9);
		headertxt = (TextView) findViewById(R.id.textView17);
		titre=(EditText) findViewById(R.id.input_titreprod);
		desc= (EditText) findViewById(R.id.input_descprod);
		mainlay = (RelativeLayout) findViewById(R.id.MainContainer);
		quantite = (EditText) findViewById(R.id.input_quantiteprod);
		prix = (EditText) findViewById(R.id.input_prixeprod);
		photo1titre = (EditText) findViewById(R.id.input_Photo1titre);
		photo2titre = (EditText) findViewById(R.id.input_Photo2titre);
		photo3titre = (EditText) findViewById(R.id.input_Photo3titre);

		photo1desc = (EditText) findViewById(R.id.input_Photo1Desc);
		photo2desc = (EditText) findViewById(R.id.input_Photo2Desc);
		photo3desc = (EditText) findViewById(R.id.input_Photo3Desc);

        addphoto1 = (Button) findViewById(R.id.addphoto1);
		addphoto2 = (Button) findViewById(R.id.addphoto2);
		addphoto3 = (Button) findViewById(R.id.addphoto3);
		envoyer = (Button) findViewById(R.id.envoyerbtn);
        photo1 = (ImageView) findViewById(R.id.photo1);
		photo2 = (ImageView) findViewById(R.id.photo2);
		photo3 = (ImageView) findViewById(R.id.photo3);



if(categorie=="Artisanat")
{
	mainlay.setBackgroundResource(R.drawable.artisanatback);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.categorie_artisanat, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
	sp.setAdapter(adapter);
	headerimg.setImageResource(R.drawable.artisanatcouleur);
	headertxt.setText("Artisanat");
}

		else
if(categorie=="Services")
{
	mainlay.setBackgroundResource(R.drawable.menageback);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.categorie_services, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
	sp.setAdapter(adapter);
	headerimg.setImageResource(R.drawable.servicecouleur);
	headertxt.setText("Services");
}

else
if(categorie=="Multimedia")
{
	mainlay.setBackgroundResource(R.drawable.multimediaback2);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.categorie_multimedia, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
	sp.setAdapter(adapter);
	headerimg.setImageResource(R.drawable.multimediacouleur);
	headertxt.setText("Multimedia");
}

else
if(categorie=="Immobilier")
{
	mainlay.setBackgroundResource(R.drawable.immobilierback2);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.categorie_immobilier, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
	sp.setAdapter(adapter);
	headerimg.setImageResource(R.drawable.immobiliercouleur);
	headertxt.setText("Immobilier");
}

else
if(categorie=="Vetements")
{
	mainlay.setBackgroundResource(R.drawable.vetementback);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			R.array.categorie_vetements, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
	sp.setAdapter(adapter);
	headerimg.setImageResource(R.drawable.vetementcouleur);
	headertxt.setText("Vetements");
}

		fragmentManager = getSupportFragmentManager();
		initToolbar();
		initMenuFragment();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		notifbtn = (Button) findViewById(R.id.NotificationBtn);


		FragmentManager fragmentManager = getSupportFragmentManager();

		addphoto1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				showFileChooser();


			}
		});

		addphoto2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				showFileChooser();


			}
		});

		addphoto3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				showFileChooser();


			}
		});



		envoyer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				final ProgressDialog loading = ProgressDialog.show(AddProductActivity.this,"Uploading...","Please wait...",false,false);

				StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						System.out.println(response.toString());
						loading.dismiss();
						Toast.makeText(AddProductActivity.this, "Produit Ajouté" , Toast.LENGTH_LONG).show();
						System.out.println(response + "this is resp");
						Intent i = new Intent(
								AddProductActivity.this,
								Main.class);
						startActivity(i);
						finish();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loading.dismiss();

						//Showing toast
						Toast.makeText(AddProductActivity.this, "Produit Ajouté !!", Toast.LENGTH_LONG).show();
						System.out.println( error.getMessage()+"" + "this is resp");
						String json = null;
						Intent i = new Intent(
								AddProductActivity.this,
								Main.class);
						startActivity(i);
						finish();

						NetworkResponse response = error.networkResponse;
						if(response != null && response.data != null){
							switch(response.statusCode){
								case 400:
									json = new String(response.data);
									json = trimMessage(json, "message");
									if(json != null) displayMessage(json);
									break;
							}
							//Additional cases
						}
					}
				}) {

					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String,String> parameters  = new HashMap<String, String>();

						if(bitmap1!=null)
						{   image1 = getStringImage(bitmap1);}
						else{
							image1="";
						}

						if(bitmap2!=null)
						{   image2 = getStringImage(bitmap2);}
						else{
							image2="";
						}

						if(bitmap3!=null)
						{   image3 = getStringImage(bitmap3);}
						else{
							image3="";
						}
						Calendar c = Calendar.getInstance();
						int day = c.get(Calendar.DAY_OF_MONTH);
						int month = c.get(Calendar.MONTH);
						int year = c.get(Calendar.YEAR);

						String datte= day+"/"+month+"/"+year;
						System.out.println(datte + " this is daaate");


						parameters.put("name",titre.getText().toString());
						parameters.put("quantity",quantite.getText().toString());
						parameters.put("price",prix.getText().toString());
						parameters.put("category",categorie);
						parameters.put("date",datte);
						parameters.put("ProductImage1",image1);
						parameters.put("ProductImage1Title",photo1titre.getText().toString());
						parameters.put("ProductImage1Desc",photo1desc.getText().toString());
						parameters.put("ProductImage2",image2);
						parameters.put("ProductImage2Title",photo2desc.getText().toString());
						parameters.put("ProductImage2Desc",photo2desc.getText().toString());
						parameters.put("ProductImage3",image3);
						parameters.put("ProductImage3Title",photo3desc.getText().toString());
						parameters.put("ProductImage3Desc",photo3desc.getText().toString());
						parameters.put("owner",id+"");
						parameters.put("description",desc.getText().toString());
						parameters.put("SubCategory",sp.getSelectedItem().toString());
						parameters.put("place",spville.getSelectedItem().toString());
						return parameters;
					}

				}
						;


				RequestQueue requestQueue = Volley.newRequestQueue(AddProductActivity.this);



				requestQueue.add(request);

				//Toast.makeText(AddProductActivity.this, "Produit ajouté !", Toast.LENGTH_LONG).show();



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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		TLS.GetoutActivity=false;

		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			Uri filePath = data.getData();



			if(position==0 )
			{
				try {

					//Getting the Bitmap from Gallery
					ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
					//Getting the Bitmap from Gallery

					bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

					System.out.println(filePath+ " this is filepath");



					//Setting the Bitmap to ImageView
					ImageUtil.displayImage(photo1, filePath.toString(), null);
					//  image1.setImageBitmap(bitmap1);
					position=1;
				} catch (IOException e) {
					e.printStackTrace();
				}}

			else if(position==1)
			{
				try {
					//Getting the Bitmap from Gallery

					ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
					//Getting the Bitmap from Gallery

					bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
					//Setting the Bitmap to ImageView




					//    image2.setImageBitmap(bitmap2);
					ImageUtil.displayImage(photo2, filePath.toString(), null);
					position=2;
				} catch (IOException e) {
					e.printStackTrace();
				}}

			else if(position==2 )
			{
				try {
					ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
					//Getting the Bitmap from Gallery


					bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
					//Setting the Bitmap to ImageView




					ImageUtil.displayImage(photo3, filePath.toString(), null);
					//    image3.setImageBitmap(bitmap3);
					position=0;
				} catch (IOException e) {
					e.printStackTrace();
				}}
		}
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
	private void showFileChooser() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		TLS.GetoutActivity=true;
		startActivityForResult(Intent.createChooser(intent, "Choisir votre photo"), PICK_IMAGE_REQUEST);
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
	protected void onResume() {
		super.onResume();
		NotifService.passenow=false;
		System.out.println(NotifService.passenow + " this is pasinng");
	}
	public String getStringImage(Bitmap bmp){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 15, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
		return encodedImage;
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
			{Intent i = new Intent(AddProductActivity.this, Main.class);
				startActivity(i);
				finish();}
			break;

			case R.id.about:
			{
				Intent intent = new Intent(AddProductActivity.this, AboutActivity.class);
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
			{Intent intent = new Intent(AddProductActivity.this, NourritureListActivity.class);
				startActivity(intent);
				finish();}

			if(position==2)
			{Intent intent = new Intent(AddProductActivity.this, ArtisanatListActivity.class);
				startActivity(intent);
				finish();}

			if(position==3)
			{Intent intent = new Intent(AddProductActivity.this, ServiceListActivity.class);
				startActivity(intent);
				finish();}

			if(position==4)
			{Intent intent = new Intent(AddProductActivity.this, MultimediaListActivity.class);
				startActivity(intent);
				finish();}



			if(position==5)
			{Intent intent = new Intent(AddProductActivity.this, ImmobilierListActivity.class);
				startActivity(intent);
				finish();}

			if(position==6)
			{Intent intent = new Intent(AddProductActivity.this, VetementListActivity.class);
				startActivity(intent);
				finish();}

			if(position==7)
			{Intent intent = new Intent(AddProductActivity.this, RechercheActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==8)
			{Intent intent = new Intent(AddProductActivity.this, ProfileActivity.class);
				startActivity(intent);
				finish();
			}
			if(position==9)
			{Intent intent = new Intent(AddProductActivity.this, Welcome.class);
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
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==2)
		{   AddProductActivity.categorie="Artisanat";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}

		if(position==3)
		{   AddProductActivity.categorie="Services";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==4)
		{   AddProductActivity.categorie="Multimedia";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==5)
		{   AddProductActivity.categorie="Immobilier";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
			startActivity(intent);
			finish();
		}
		if(position==6)
		{   AddProductActivity.categorie="Vetements";
			//AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
			Intent intent = new Intent(AddProductActivity.this, AddProductActivity.class);
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
	public String trimMessage(String json, String key){
		String trimmedString = null;

		try{
			JSONObject obj = new JSONObject(json);
			trimmedString = obj.getString(key);
		} catch(JSONException e){
			e.printStackTrace();
			return null;
		}

		return trimmedString;
	}

	//Somewhere that has access to a context
	public void displayMessage(String toastString){
		Toast.makeText(AddProductActivity.this, toastString, Toast.LENGTH_LONG).show();
	}
}


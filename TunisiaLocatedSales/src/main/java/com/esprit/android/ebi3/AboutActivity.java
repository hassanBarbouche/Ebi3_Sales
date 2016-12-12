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
import android.support.v4.app.FragmentStatePagerAdapter;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.android.ebi3.Utils.ImageUtil;
import com.esprit.android.ebi3.adapters.DrawerSocialAdapter;
import com.esprit.android.ebi3.models.Article;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 13/02/16.
 */
public class AboutActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {


    private FragmentManager fragmentManager;

    Fragment fragment;
    public static String PREFERENCE_FILENAME = "reporting_app";
    Button notifbtn;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ImageView backImg;
    public RelativeLayout maincont;
    private ImageView myimage;
    private TextView UserMail,UserDesc,UserName;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public ViewPager viewpager;
    private List<Article> imgList1, imgList2, imgList3,imgList4,imgList5,imgList6;
    protected ImageLoader imageLoader;
    private ContextMenuDialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
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
        SharedPreferences reportingPref = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);

        String Nom = reportingPref.getString("Nom", "");
        String Prenom = reportingPref.getString("Prenom", "");
        String Email = reportingPref.getString("Email", "");
        String Description = reportingPref.getString("Description", "");
        String Adresse = reportingPref.getString("Adresse", "");
        String Telephone = reportingPref.getString("Telephone", "");
        String ImagePath = reportingPref.getString("ImagePath", "");


        viewpager = (ViewPager) findViewById(R.id.MainViewPager);
        notifbtn = (Button) findViewById(R.id.NotificationBtn);
        FragmentManager fragmentManager = getSupportFragmentManager();

        viewpager.setAdapter(new MainVadapter(fragmentManager));
        //	WelcomeListNourriture.maListViewPerso.setAdapter(new ArticleCustomAdapter(getBaseContext(), R.layout.one_article, imgList2));
        viewpager.setOffscreenPageLimit(3);
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
            {Intent i = new Intent(AboutActivity.this, Main.class);
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
    @Override
    protected void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            if(position==1)
            {Intent intent = new Intent(AboutActivity.this, NourritureListActivity.class);
                startActivity(intent);
                finish();}

            if(position==2)
            {Intent intent = new Intent(AboutActivity.this, ArtisanatListActivity.class);
                startActivity(intent);
                finish();}

            if(position==3)
            {Intent intent = new Intent(AboutActivity.this, ServiceListActivity.class);
                startActivity(intent);
                finish();}

            if(position==4)
            {Intent intent = new Intent(AboutActivity.this, MultimediaListActivity.class);
                startActivity(intent);
                finish();}



            if(position==5)
            {Intent intent = new Intent(AboutActivity.this, ImmobilierListActivity.class);
                startActivity(intent);
                finish();}

            if(position==6)
            {Intent intent = new Intent(AboutActivity.this, VetementListActivity.class);
                startActivity(intent);
                finish();}

            if(position==7)
            {Intent intent = new Intent(AboutActivity.this, RechercheActivity.class);
                startActivity(intent);
                finish();
            }
            if(position==8)
            {Intent intent = new Intent(AboutActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
            if(position==9)
            {Intent intent = new Intent(AboutActivity.this, Welcome.class);
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
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }
        if(position==2)
        {   AddProductActivity.categorie="Artisanat";
            //AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }

        if(position==3)
        {   AddProductActivity.categorie="Services";
            //AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }
        if(position==4)
        {   AddProductActivity.categorie="Multimedia";
            //AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }
        if(position==5)
        {   AddProductActivity.categorie="Immobilier";
            //AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
            startActivity(intent);
            finish();
        }
        if(position==6)
        {   AddProductActivity.categorie="Vetements";
            //AddProductActivity.mainlay.setBackgroundResource(R.drawable.artisanatback);
            Intent intent = new Intent(AboutActivity.this, AddProductActivity.class);
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

    class MainVadapter extends FragmentStatePagerAdapter {


        public MainVadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = null;
            if (position == 0) {
                fragment = new About1Fragment();


            }
            if (position == 1) {
                fragment = new About2Fragment();


            }

            if (position == 2) {
                fragment = new About3Fragment();


            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }




}

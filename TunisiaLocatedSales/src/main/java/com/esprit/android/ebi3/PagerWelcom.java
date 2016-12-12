package com.esprit.android.ebi3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Hassan on 02/02/16.
 */
public class PagerWelcom extends FragmentActivity {
public ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_welcome);
        viewpager = (ViewPager) findViewById(R.id.PagerW);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewpager.setAdapter(new myadapter(fragmentManager));
        NotifService.passenow=false;
        stopService(TLS.mServiceIntent);



    }

    @Override
    protected void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }
}

class myadapter extends FragmentPagerAdapter
{


    public myadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position==0)
        { fragment = new Welcome1() ;


        }
        if(position==1) {
            fragment = new Welcome2();

        }
        if(position==2) {
            fragment = new Welcome3();
            return fragment;
        }
        if(position==3) {
            fragment = new Welcome4();
            return fragment;
        }

        if(position==4) {
            fragment = new Welcome5();
            return fragment;
        }

        if(position==5) {
            fragment = new Welcome6();
            return fragment;
        }

        if(position==6) {
            fragment = new Welcome7();
            return fragment;
        }
        if(position==7) {
            fragment = new Welcome8();
            return fragment;
        }

        if(position==8) {
            fragment = new Welcome9();
            return fragment;
        }

        if(position==9) {
            fragment = new Welcome10();
            return fragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}

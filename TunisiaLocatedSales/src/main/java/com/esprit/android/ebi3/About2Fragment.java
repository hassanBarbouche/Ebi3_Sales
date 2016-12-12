package com.esprit.android.ebi3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hassan on 06/02/16.
 */
public class About2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about2_layout, container, false);


        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");



        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }



}

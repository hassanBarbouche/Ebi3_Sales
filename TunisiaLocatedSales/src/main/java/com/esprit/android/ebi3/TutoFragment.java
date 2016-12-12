package com.esprit.android.ebi3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hassan on 11/02/16.
 */
public class TutoFragment extends Fragment {
    private static final String TAG = TutoFragment.class.getSimpleName();


    @Bind(R.id.exitframe)
    Button exitbtn;



    public static Fragment newInstance() {
        return new TutoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tuto1, container, false);
        ButterKnife.bind(this, rootView);

        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");

        exitbtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Save new user data into Parse.com Data Storage


                getActivity().getSupportFragmentManager().popBackStack();

                return false;
            }
        });


        return rootView;



    }


















    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}


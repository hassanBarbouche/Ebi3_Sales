package com.esprit.android.ebi3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.esprit.android.ebi3.adapters.CommentaireCustomAdapter;
import com.esprit.android.ebi3.models.Comment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hassan on 11/02/16.
 */
public class NotificationFragment extends Fragment {
    private static final String TAG = NotificationFragment.class.getSimpleName();
    @Bind(R.id.NotificationList)
    ListView notificationlist;

    @Override
    public void onResume() {
        super.onResume();
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
    }


  public static List<Comment> comlist;
  public static boolean isin;
    public static Fragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_layout, container, false);
        ButterKnife.bind(this, rootView);
        NotifService.passenow=false;
        System.out.println(NotifService.passenow + " this is pasinng");
      /*  comlist=new ArrayList<Comment>();

        User u = new User(1,"Mazouni Anas","azertygrfd","Jobs",
                "Tunisie","6 rue hah","99362843","azefdrfd","http://10.0.3.2:8888/pim1/uploads/61.png");

        Comment C1 = new Comment(1,"azeertufjfdnf",u);


        comlist.add(C1);*/


       notificationlist.setAdapter(new CommentaireCustomAdapter(getActivity(), R.layout.notif_layout, comlist));


        isin=true;



        return rootView;



    }


















    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}


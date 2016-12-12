package com.esprit.android.ebi3.Utils;

import android.app.Activity;
import android.content.Intent;

import com.esprit.android.ebi3.AccessTokenActivity;

/**
 * Created by kirkita on 06.10.15.
 */
public class IntentUtil {

    private Activity activity;

    // constructor
    public IntentUtil(Activity activity) {
        this.activity = activity;
    }

    public void showAccessToken() {
        Intent i = new Intent(activity, AccessTokenActivity.class);
        activity.startActivity(i);
    }
}

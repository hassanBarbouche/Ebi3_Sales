/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.esprit.android.ebi3.provider;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    //ParseAnalytics.trackAppOpenedInBackground(getIntent());
    //.trackAppOpenedInBackground(Intent.getIntent());


    // Add your initialization code here
    Parse.initialize(this, "vmjv3rzq0dljmz2DwtJ0hthF9Ryt4YwT3jGq93Ws", "4OkPaB77s6lzAaaBE5IWtKuaa9P9bjwIC5ONQwbW");
   // Parse.initialize(this,getString(R.string.parse_app_id),getString(R.string.parse_client_key));

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();

    // If you would like all objects to be private by default, remove this
    // line.
    defaultACL.setPublicReadAccess(true);

    ParseACL.setDefaultACL(defaultACL, true);





  }
}

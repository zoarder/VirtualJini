package com.technologies.virtualjini.volley;

import com.technologies.virtualjini.R;
import com.technologies.virtualjini.utils.AppController;
import com.technologies.virtualjini.utils.BuildConfig;

/**
 * Created by Droid on 21-Apr-16.
 */
public class CheckBaseURL {


    public String URL() {
        String URL = "";
        if (BuildConfig.KEY_IS_DEVELOPMENT_BUILD) {
            URL = AppController.getContext().getString(R.string.development_address);
        } else {
            URL = AppController.getContext().getString(R.string.production_address);
        }
        return URL;
    }
}

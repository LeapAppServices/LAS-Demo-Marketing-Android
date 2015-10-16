package com.maxleap.demo.marketing;

import android.app.Application;
import android.os.Bundle;

import com.maxleap.MaxLeap;
import com.maxleap.utils.ManifestInfo;


public class App extends Application {

    public static final String APP_ID = "Replace this with your App Id";
    public static final String API_KEY = "Replace this with your Rest Key";

    @Override
    public void onCreate() {
        super.onCreate();

        if (APP_ID.startsWith("Replace") || API_KEY.startsWith("Replace")) {
            throw new IllegalArgumentException("Please replace with your app id and api key first before" +
                    "using MaxLeap SDK.");
        }

        Bundle bundle = ManifestInfo.getApplicationMetaData(this);
        String senderId = bundle.getString("com.maxleap.push.gcm_sender_id");
        if (senderId == null || senderId.contains("yourSenderId")) {
            throw new IllegalArgumentException("Please replace with your sender id first before using Push.");
        }

		/*
         * Fill in this section with your MaxLeap credentials
		 */
        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_VERBOSE);
        MaxLeap.Options options = new MaxLeap.Options();
        options.appId = APP_ID;
        options.clientKey = API_KEY;
        options.marketingEnable = true;
        MaxLeap.initialize(this, options);
    }

}

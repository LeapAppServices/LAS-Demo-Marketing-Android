package as.leap.demo.marketing;

import android.app.Application;
import android.os.Bundle;

import as.leap.LASConfig;
import as.leap.utils.ManifestInfo;

public class App extends Application {

    public static final String APP_ID = "Replace this with your App Id";
    public static final String API_KEY = "Replace this with your Rest Key";

    @Override
    public void onCreate() {
        super.onCreate();

        if (APP_ID.startsWith("Replace") || API_KEY.startsWith("Replace")) {
            throw new IllegalArgumentException("Please replace with your app id and api key first before" +
                    "using LAS SDK.");
        }

        Bundle bundle = ManifestInfo.getApplicationMetaData(this);
        String senderId = bundle.getString("as.leap.push.gcm_sender_id");
        if (senderId == null || senderId.contains("yourSenderId")) {
            throw new IllegalArgumentException("Please replace with your sender id first before using Push.");
        }

		/*
         * Fill in this section with your LAS credentials
		 */
        LASConfig.setLogLevel(LASConfig.LOG_LEVEL_VERBOSE);
        LASConfig.setMarketingEnabled(true);
        LASConfig.initialize(this, APP_ID, API_KEY);
    }

}

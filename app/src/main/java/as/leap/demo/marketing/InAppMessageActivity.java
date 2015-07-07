package as.leap.demo.marketing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import as.leap.LASAnalytics;
import as.leap.LASLog;
import as.leap.LASMarketing;

public class InAppMessageActivity extends AppCompatActivity {

    public static final String TAG = InAppMessageActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_message);

        final EditText eventEditText = (EditText) findViewById(R.id.event_edit_text);
        findViewById(R.id.trigger_event_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = eventEditText.getText().toString();
                if (TextUtils.isEmpty(event)) {
                    LASLog.t("event cannot be empty");
                    return;
                }
                LASAnalytics.logEvent(event);
                LASLog.t("fire event " + event);
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                LASLog.i(TAG, key + " = " + intent.getStringExtra(key));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_test_mode:
                LASMarketing.openTestMode(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LASMarketing.setInAppMessageDisplayActivity(this);

        LASAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LASMarketing.dismissCurrentInAppMessage();

        LASMarketing.clearInAppMessageDisplayActivity();

        LASAnalytics.onPause(this);
    }


}

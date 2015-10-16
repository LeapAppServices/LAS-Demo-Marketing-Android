package com.maxleap.demo.marketing;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.maxleap.MLPushBroadcastReceiver;

public class CustomPushReceiver extends MLPushBroadcastReceiver {

    @Override
    protected Class<? extends Activity> getActivity(Intent intent) {
        return InAppMessageActivity.class;
    }

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        return super.getNotification(context, intent);
    }

    @Override
    protected void startIntent(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.startIntent(context, intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    protected Uri getUri(Intent intent) {
        return super.getUri(intent);
    }

    @Override
    protected Bitmap getLargeIcon(Context context) {
        return super.getLargeIcon(context);
    }

    @Override
    protected int getSmallIconId(Context context) {
        return super.getSmallIconId(context);
    }
}

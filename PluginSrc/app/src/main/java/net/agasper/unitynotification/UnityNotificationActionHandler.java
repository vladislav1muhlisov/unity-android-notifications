package net.agasper.unitynotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by gileadis on 11/14/17.
 */

public class UnityNotificationActionHandler extends BroadcastReceiver {

    private final String ACTION_NOTIFICATION_MESSAGE = "ACTION_NOTIFICATION_MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        String gameObject = intent.getStringExtra("gameObject");
        String handlerMethod = intent.getStringExtra("handlerMethod");
        String actionId = intent.getStringExtra("actionId");
        boolean foreground = intent.getBooleanExtra("foreground", true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);

        if (foreground) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.geargames.aow");
            launchIntent.setAction(ACTION_NOTIFICATION_MESSAGE);
            launchIntent.putExtra("id", id);
            launchIntent.putExtra("gameObject", gameObject);
            launchIntent.putExtra("handlerMethod", handlerMethod);
            launchIntent.putExtra("actionId", actionId);
            context.startActivity(launchIntent);
        }

        UnityPlayer.UnitySendMessage(gameObject, handlerMethod, actionId);
    }
}

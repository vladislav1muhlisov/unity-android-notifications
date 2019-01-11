package net.agasper.unitynotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;

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
        String json = intent.getStringExtra("json");
        boolean foreground = intent.getBooleanExtra("foreground", true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);

        String result = buildJsonResult(actionId, json);
        if (foreground) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.geargames.aow");
            launchIntent.setAction(ACTION_NOTIFICATION_MESSAGE);
            launchIntent.putExtra("id", id);
            launchIntent.putExtra("gameObject", gameObject);
            launchIntent.putExtra("handlerMethod", handlerMethod);
            launchIntent.putExtra("actionId", actionId);
            launchIntent.putExtra("result", result);
            context.startActivity(launchIntent);
        }
        else {
            UnityPlayer.UnitySendMessage(gameObject, handlerMethod, result);
        }
    }

    private String buildJsonResult(String identifier, String json) {
        String result =
                "{\n" +
                "\"identifier\" : \"" + identifier + "\",\n" +
                "\"data\" : " + json +
                "}\n";
        return result;
    }
}
package br.com.badrequest.insporte.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import br.com.badrequest.insporte.R;
import br.com.badrequest.insporte.activity.Menu;
import br.com.badrequest.insporte.bean.User;

public class Util {

//    private static User getUser(Context context) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        User user = new User();
//        user.setId(preferences.getString(Constants.PROP_USER_ID, null));
//        user.setPassword(preferences.getString(Constants.PROP_USER_PASS, null));
//        return user.getPassword() != null ? user : null;
//    }
//
//    private static void saveUser(Context context, User user) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(Constants.PROP_USER_ID, user.getId()).commit();
//        editor.putString(Constants.PROP_USER_PASS, user.getPassword()).commit();
//    }

    public static boolean haveNetworkConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return (ni != null);
    }

//    public static void sendNotification(Context context, String msg) {
//        NotificationManager notificationManager = (NotificationManager)
//                context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, Menu.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_stat_incidente)
//                        .setContentTitle("Aviso inSPorte")
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(msg))
//                        .setContentText(msg);
//
//        mBuilder.setContentIntent(contentIntent);
//        notificationManager.notify(1, mBuilder.build());
//
//    }
}

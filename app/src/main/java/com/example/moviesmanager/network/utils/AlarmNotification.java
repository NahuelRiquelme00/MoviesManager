package com.example.moviesmanager.network.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.moviesmanager.R;
import com.example.moviesmanager.views.ui.MainActivity;
import com.example.moviesmanager.views.ui.VerMasTardeFragment;


public final class AlarmNotification extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 1;

    public void onReceive(Context context,Intent p1) {
        createSimpleNotification(context);
    }

    private void createSimpleNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("VerMasTarde", "VerMasTarde");

        int flag = VERSION.SDK_INT >=  Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flag);

        Notification notification = new Builder(context, MainActivity.MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_aplicacion__movie_24)
                .setContentTitle("Mirate algunas de estas pelis")
                //.setContentText("Entra a la app para ver algunas de tus pelis favoritas y las que guardaste para ver mas tarde")
                .setStyle(
                        new NotificationCompat.BigTextStyle().bigText(
                                "Entra a la app para ver que peliculas guardaste para ver mas tarde o tus favoritas (Si queres volver a verlas)"
                        )
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }



}

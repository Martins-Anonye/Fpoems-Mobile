package com.example.martins.fpoems;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.martins.fpoems.DbLinkMaker.copydbLinkToDestination;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {


            String link = remoteMessage.getData().get("LINK");

            String databasename = remoteMessage.getData().get("DATABASENAME");
            String tablename = remoteMessage.getData().get("TABLENAME");

            new copydbLinkToDestination(getApplicationContext()).updateContactForFPOEMSTABLE(1, link, databasename, tablename);


            String linkguid = remoteMessage.getData().get("LINKGUID");
            String guidname = remoteMessage.getData().get("LINKGUIDNAME");
            new copydbLinkToDestination(getApplicationContext()).updateContactForCoverPageAndExamGuide(1, linkguid, guidname);

        }

        if (remoteMessage.getNotification() != null) {

            Intent intent = new Intent(this, MainActivityEMS.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setSmallIcon(R.drawable.oko_logo);
            notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
            notificationBuilder.setContentText(remoteMessage.getNotification().getBody()); //ditto

            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build()); /* ID of notification */

        }


        //


    }


}




package com.pavisalavisa.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE="message";
    public static final int NOTIFICATION_ID=5453;

    public DelayedMessageService(){
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        //runs in a background thread
        synchronized(this){
            try{
                wait(10000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        String text=intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }

    private void showText(final String text){
        Intent intent =new Intent(this, MainActivity.class);//create an intent for MainActivity
        TaskStackBuilder stackBuilder= TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);//add the intent to backstack
        stackBuilder.addNextIntent(intent);
        //whenb the user clicks on the notification the notification uses this pending intent
        //to start the main activity
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification=new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}

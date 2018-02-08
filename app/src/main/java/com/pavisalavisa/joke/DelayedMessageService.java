package com.pavisalavisa.joke;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE="message";
    private Handler handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //runs on the main thread
        //called every time the intent service is started
        handler=new Handler();
        return super.onStartCommand(intent,flags,startId);
    }
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
        handler.post(new Runnable(){
            //post the toast code to the main thread using handler
           @Override
            public void run(){
               Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
               //getApplicationContext context of whatever is in foreground
           }
        });
    }
}

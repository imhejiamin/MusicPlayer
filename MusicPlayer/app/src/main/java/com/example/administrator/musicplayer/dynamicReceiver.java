package com.example.administrator.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

/**
 * 一个简单的动态广播接收器
 */

public class dynamicReceiver extends BroadcastReceiver {

    private  static  final String DYNAMICATION = "com.example.administrator.musicPlayer.dynamicReceiver";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onReceive(Context context , Intent intent) {

        if(intent.getAction().equals(DYNAMICATION)){

            Bundle bundle = intent.getExtras();

            String song = bundle.getString("song");
            String singer = bundle.getString("singer");
            int cover = bundle.getInt("cover");
            String lyric = bundle.getString("lyric");
            int source = bundle.getInt("source");


            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), bundle.getInt("cover"));
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);

            builder.setContentTitle("《" +song +"》" + "正在播放")
                    .setContentText("查看歌曲背景")
                    .setTicker("MusicPlayer正在播放")
                    .setLargeIcon(bm)
                    .setSmallIcon(cover)
                    .setAutoCancel(true);


            Intent mIntent = new Intent(context, StoryActivity.class);

            mIntent.putExtra("song", song);
            mIntent.putExtra("singer",singer);
            mIntent.putExtra("lyric", lyric);
            mIntent.putExtra("source",source);//int 类型
            mIntent.putExtra("cover", cover); //int 类型


            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notify = builder.build();
            notificationManager.notify(0, notify);




        }

    }
}
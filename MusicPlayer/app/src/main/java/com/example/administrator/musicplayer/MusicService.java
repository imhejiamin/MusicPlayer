package com.example.administrator.musicplayer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.util.Log;

public class MusicService extends Service{
    static MediaPlayer mediaPlayer = new MediaPlayer();
    //public MediaPlayer mediaPlayer = null;
    public final Binder binder = new MyBinder();
    public class MyBinder extends Binder{
        MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public Binder onBind(Intent intent){

        return binder;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){ //如果还有service任务，就停止并释放
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    public void preMusic(Context context,int source) {
        try{

            mediaPlayer = MediaPlayer.create(context, source);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("tag","error");
        }
    }


    public void play_button_control() {       // 控制播放/暂停，由playactivity设置监听器控制触发
        if (mediaPlayer != null) {            //如果mediaplayer已经加载到了资源
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();          //如果正在播的话就暂停
            } else {
                mediaPlayer.start();          //否则就从当前位置开始播放
            }
        }
    }


    public void stop(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}


package com.example.administrator.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.wcy.lrcview.LrcView;

import static com.example.administrator.musicplayer.R.id.singer;

/**
 * Created by Administrator on 2017/12/31.
 */


@RequiresApi(api = Build.VERSION_CODES.N)
public class PlayActivity extends AppCompatActivity {

    private ImageViewPlus cover_image; //封面
    private SeekBar seekBar; //进度条

    private List<song> mDatas;

    public static final int ORDER_PLAY = 1;
    public static final int SINGLE_PLAY = 2;
    private int play_mode = ORDER_PLAY;

    private ImageView pre;
    private ImageView play;
    private ImageView next;
    private ImageView back;
    private ImageView look_background;
    private ImageView commentIcon;


    private TextView song_textview;
    private TextView singer_textview;


    private TextView musictime;
    private TextView totaltime;
    private SimpleDateFormat time = new SimpleDateFormat("m" + "m:ss");

    private MusicService musicService;//实例music service
    private float degree = 0; //封面旋转度数

    private LrcView lrcView;

    private int[] pic={
            R.drawable.cover_pic,
            R.drawable.taylor,
            R.drawable.linjj,
            R.drawable.adele,
            R.drawable.yoga
    };
    private int[] songs={
            R.raw.gaobaiqiqiu,
            R.raw.lovestory,
            R.raw.tashuo,
            R.raw.hello,
            R.raw.shuohuang
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_page);

        //initial过程,绑定service
        final Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        bindService(intent,sc, Context.BIND_AUTO_CREATE); //将service绑定到play activity

        cover_image = (ImageViewPlus ) findViewById(R.id.cover);
        seekBar = (SeekBar)findViewById(R.id.seekBar);


        pre = (ImageView) findViewById(R.id.pre);
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        commentIcon = (ImageView) findViewById(R.id.commentIcon);

        song_textview = (TextView)findViewById(R.id.song);
        singer_textview = (TextView)findViewById(singer);

        musictime = (TextView) findViewById(R.id.musicTime);
        totaltime = (TextView) findViewById(R.id.totalTime);

        lrcView = (LrcView) findViewById(R.id.lrc_view);



        Intent intent1 = getIntent();
        final Bundle extras = intent1.getExtras();

        String song = null ;
        String singer = null;
        String lyric = null ;
        int source ;
        int cover;

        song =  extras.getString("song");
        singer = extras.getString("singer");
        lyric = extras.getString("lyric");
        source = extras.getInt("source");
        cover = extras.getInt("cover");

        lrcView.loadLrc(getLrcText(lyric));

        song_textview.setText(song.toString());
        singer_textview.setText(singer.toString());
        cover_image.setImageResource(cover);

        mDatas = new ArrayList<>();
        mDatas.add(new song("告白气球","周杰伦","gaobaiqiqiu.lrc",songs[0],pic[0]));
        mDatas.add(new song("Love Story","Taylor Swift","lovestory.lrc",songs[1],pic[1]));
        mDatas.add(new song("她说","林俊杰","tashuo.lrc",songs[2],pic[2]));
        mDatas.add(new song("Hello","Adele","hello.lrc",songs[3],pic[3]));
        mDatas.add(new song("说谎","林宥嘉","shuohuang.lrc",songs[4],pic[4]));

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //上一首

                String song_name = extras.getString("song");
                int temp = -1;
                for(int i = 0;i< mDatas.size();i++){
                    if(song_name.equals(mDatas.get(i).getSong())){
                        temp = i;
                    }
                }
                if(temp != -1){
                    if(temp == 0){ //第一首
                        Toast.makeText(PlayActivity.this, "已经是第一首啦", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        musicService.stop();
                        PlayActivity.this.finish();
                        Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
                        intent.putExtra("song", mDatas.get(temp-1).getSong());
                        intent.putExtra("singer",mDatas.get(temp-1).getSinger());
                        intent.putExtra("lyric", mDatas.get(temp-1).getLyric());
                        intent.putExtra("source", mDatas.get(temp-1).getSource());//int 类型
                        intent.putExtra("cover",mDatas.get(temp-1).getCover()); //int 类型
                        startActivity(intent);
                    }
                }


            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicService.mediaPlayer.isPlaying()){
                    play.setBackgroundResource(R.drawable.play);
                }
                else {
                    play.setBackgroundResource(R.drawable.pause);
                }
                musicService.play_button_control(); //通过service控制播放器Media player
                handler.post(UpdateThread);   //使用handler更新图片和seekbar

                //广播
                String DYNAMICATION = "com.example.administrator.musicPlayer.dynamicReceiver";

                if(musicService.mediaPlayer.isPlaying()){
                    String song =  extras.getString("song");
                    String singer = extras.getString("singer");
                    String lyric = extras.getString("lyric");
                    int source = extras.getInt("source");
                    int cover = extras.getInt("cover");

                    Bundle bundle = new Bundle();
                    bundle.putString("song",song);
                    bundle.putString("singer",singer);
                    bundle.putString("lyric", lyric);
                    bundle.putInt("source", source);//int 类型
                    bundle.putInt("cover",cover); //int 类型

                    // 注册动态广播
                    IntentFilter dynamic_filter = new IntentFilter();
                    dynamicReceiver d_receiver = new dynamicReceiver();
                    dynamic_filter.addAction(DYNAMICATION);
                    registerReceiver(d_receiver , dynamic_filter);

                    Intent intentBroadcast = new Intent(DYNAMICATION);
                    intentBroadcast.putExtras(bundle);
                    //发送动态广播
                    sendBroadcast(intentBroadcast);

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //下一首

                String song_name = extras.getString("song");
                int temp = -1;
                for(int i = 0;i< mDatas.size();i++){
                    if(song_name.equals(mDatas.get(i).getSong())){
                        temp = i;
                    }
                }

                if(temp != -1){
                    if(temp == mDatas.size()-1){   //最后一首
                        Toast.makeText(PlayActivity.this, "已经是最后一首啦", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        musicService.stop();

                        PlayActivity.this.finish();
                        Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
                        intent.putExtra("song", mDatas.get(temp+1).getSong());
                        intent.putExtra("singer",mDatas.get(temp+1).getSinger());
                        intent.putExtra("lyric", mDatas.get(temp+1).getLyric());
                        intent.putExtra("source", mDatas.get(temp+1).getSource());//int 类型
                        intent.putExtra("cover",mDatas.get(temp+1).getCover()); //int 类型
                        startActivity(intent);
                    }
                }
            }
        });

        ImageView share = (ImageView)findViewById(R.id.shareIcon);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayActivity.this,"此功能暂未开放，敬请期待~",Toast.LENGTH_SHORT).show();
            }
        });

        commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String song =  extras.getString("song");
                String singer = extras.getString("singer");

                Intent intentComment = new Intent(PlayActivity.this, SelfComment.class);
                intentComment.putExtra("song", song);

                startActivity(intentComment);
            }
        });

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener); //对seekbar设置seekbar监听器


        back.setOnClickListener(new View.OnClickListener() { //返回键
            @Override
            public void onClick(View v) {
                PlayActivity.this.finish();
                musicService.stop();
            }
        });


        look_background = (ImageView)findViewById(R.id.look_background);  //查看歌曲背景故事
        look_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String song =  extras.getString("song");
                String singer = extras.getString("singer");
                String lyric = extras.getString("lyric");
                int source = extras.getInt("source");
                int cover = extras.getInt("cover");

                Intent intent1 = new Intent(PlayActivity.this, StoryActivity.class);
                intent1.putExtra("song", song);
                intent1.putExtra("singer",singer);
                intent1.putExtra("lyric", lyric);
                intent1.putExtra("source", source);//int 类型
                intent1.putExtra("cover",cover); //int 类型

                startActivity(intent1);
            }
        });


    } //onCreate 完成



    //读取歌词文件
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    };


    //创建并实例化seekbar监听器，管理人为拉动进度条seekbar的更新
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar s, int progress, boolean fromUser) {  }
        @Override
        public void onStartTrackingTouch(SeekBar s) { } //开始拖动进度条时调用
        @Override
        public void onStopTrackingTouch(SeekBar s) {  //停止拖动进度条时调用
            //停止拖动进度条，将当前进度返回给 media player，如果暂停就直接从当前进度播放
            musicService.mediaPlayer.seekTo(s.getProgress());
        }

    };
    // sc ,bindService()的参数 ----用于绑定service
    private ServiceConnection sc = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name , IBinder iBinder){
            Intent intent1 = getIntent();
            Bundle extras = intent1.getExtras();
            int source = extras.getInt("source");

            musicService = ((MusicService.MyBinder)(iBinder)).getService();
            musicService.preMusic(PlayActivity.this,source);
            totaltime.setText(time.format(MusicService.mediaPlayer.getDuration()));
            seekBar.setMax(musicService.mediaPlayer.getDuration()); //设置获取当前歌曲长度并设置成seekbar的最大值

            //lrcView.updateTime(MusicService.mediaPlayer.getCurrentPosition());
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {    //解绑
             sc = null;
             musicService = null;
        }
    };


    @Override
    public boolean onKeyDown(int keyCode , KeyEvent keyEvent){  //监听手机屏幕按键
        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){     //用户点击了返回键KEYCODE_BACK
            moveTaskToBack(true);                               //将任务放到后台运行
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    // Handler与UI是同一线程，可以通过Handler更新UI上的组件状态：需要更新的是seekbar的进度移动和封面旋转
    private Handler handler = new Handler();    //创建一个handler
    Thread UpdateThread = new Thread() {   //创建一个Runnable接口实现的更新线程
        @Override
        public void run() {
            if(musicService.mediaPlayer.isPlaying()) {

                musictime.setText(time.format(MusicService.mediaPlayer.getCurrentPosition()));
                //totaltime.setText(time.format(MusicService.mediaPlayer.getDuration()));
                seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());  // 获取音乐当前进度，更新到seekbar
                cover_image.setRotation(degree += 0.2);
                //封面图片每次更新旋转0.15度
                handler.postDelayed(UpdateThread, 10);
                //设置线程的更新频率：10ms，利用handler更新到UI
                lrcView.updateTime(MusicService.mediaPlayer.getCurrentPosition());
            }
        }
    };
}

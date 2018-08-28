package com.example.administrator.musicplayer;

/**
 * Created by Administrator on 2017/12/31.
 */

public class song {
    public String song;  //歌名
    public String singer; //歌手名字
    public String lyric;  //歌词
    public int source;  //歌曲来源
    public int cover;  //歌曲的封面

    public song (String a,String b,String c,int d,int e){
        song = a;
        singer = b;
        lyric = c;
        source = d;
        cover = e;
    }

    public String getSong(){
        return song ;
    }
    public String getSinger(){
        return singer ;
    }
    public String getLyric(){
        return lyric ;
    }
    public int getSource(){
        return source ;
    }
    public int getCover(){
        return cover;
    }

}

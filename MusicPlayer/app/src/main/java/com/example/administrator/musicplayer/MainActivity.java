package com.example.administrator.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView songListView ;
    private main_page_adapter adapter;
    private List<song> mDatas;
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
        setContentView(R.layout.activity_main);

        mDatas = new ArrayList<>();
        mDatas.add(new song("告白气球","周杰伦","gaobaiqiqiu.lrc",songs[0],pic[0]));
        mDatas.add(new song("Love Story","Taylor Swift","lovestory.lrc",songs[1],pic[1]));
        mDatas.add(new song("她说","林俊杰","tashuo.lrc",songs[2],pic[2]));
        mDatas.add(new song("Hello","Adele","hello.lrc",songs[3],pic[3]));
        mDatas.add(new song("说谎","林宥嘉","shuohuang.lrc",songs[4],pic[4]));

        songListView = (ListView)findViewById(R.id.listView);
        songListView.setAdapter( adapter = new main_page_adapter(MainActivity.this, mDatas) );

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("song", mDatas.get(position).getSong());
                intent.putExtra("singer",mDatas.get(position).getSinger());
                intent.putExtra("lyric", mDatas.get(position).getLyric());
                intent.putExtra("source", mDatas.get(position).getSource());//int 类型
                intent.putExtra("cover",mDatas.get(position).getCover()); //int 类型

                startActivity(intent);

            }
        });



    }
}

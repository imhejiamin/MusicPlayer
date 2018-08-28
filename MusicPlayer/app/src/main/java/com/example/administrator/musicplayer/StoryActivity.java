package com.example.administrator.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 歌曲背景故事
 */

public class StoryActivity extends AppCompatActivity {


    private ImageView back;
    private TextView backgroud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_background);

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() { //返回键
            @Override
            public void onClick(View v) {
                StoryActivity.this.finish();
            }
        });

        backgroud = (TextView)findViewById(R.id.background);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String song = bundle.getString("song");
        if(song.equals("告白气球")){
            backgroud.setText("《告白气球》是由方文山作词，周杰伦作曲并演唱的歌曲，收录于周杰伦2016年6月24日发行的专辑《周杰伦的床边故事》中。词曲创作者方文山为周杰伦创作了《印地安老斑鸠》之后，在《周杰伦的睡前故事》这张专辑里为周杰伦量身打造了一首甜美浪漫曲风的歌曲——《告白气球》。该歌曲灵感来源于法国的美景，觉得应该来一首“简单爱”类似的歌曲创作，回顾一下以前那种对于初恋、小清新的感觉。在创作过程中周杰伦一直保持着童心未泯的心态与方文山一起合作，自己坦言这首歌是专辑里面，写得最简单的，最好写的。");
        }
        if(song.equals("Love Story")){
            backgroud.setText("《Love Story》是由美国乡村流行乐女歌手泰勒·斯威夫特演唱的一首乡村歌曲，词曲由泰勒·斯威夫特创作，泰勒·斯威夫特和内森·查普曼共同制作，收录在她2008年11月发行的第二张录音室专辑《Fearless》。在创作这首歌时，泰勒的父母强烈反对她和一个男生交往，泰勒得到了来自莎士比亚的经典悲剧《罗密欧与朱丽叶》的灵感，联系自己的生活实际：一对恋人因为家族矛盾和父母反对无法走到一起，但却不屈于命运安排，最后用真挚的爱情感化了父母，过上幸福生活的故事。她回到房间、关上门，坐在卧室的地板上花了20分钟写下这首歌。泰勒没有借用莎士比亚的悲剧结尾，而是自己书写了一个全新、美好的结局，使整个故事更加感人，歌曲曲风也不显乏味。");
        }
        if(song.equals("她说")){
            backgroud.setText("《她说》是由孙燕姿填词，林俊杰谱曲并演唱的歌曲，收录于林俊杰2010年12月8日发行的专辑《她说》中。林俊杰想创作一首“很有故事”的歌曲，因此他想到了孙燕姿。他认为孙燕姿外表坚强，但内心一定有一块很脆弱的地方，所以他打电话邀请孙燕姿作词，二人在电话中聊了歌曲的概念，林俊杰告诉孙燕姿希望歌曲名称定为《她说》。几天后，孙燕姿和林俊杰在中国内地某个商演的后台见面，孙燕姿初次听到了该曲的DEMO。之后不到两个星期，孙燕姿将歌词发给了林俊杰，林俊杰亲自担任该曲制作人。");
        }
        if(song.equals("Hello")){
            backgroud.setText("《Hello》是英国女歌手阿黛尔·阿德金斯演唱的一首流行歌曲，歌词、曲谱由阿黛尔、格莱格汀合作编写，格莱格汀负责音乐制作、出版。在之前创作歌曲的过程中，阿黛尔都喜欢在家里写歌。然而阿黛尔在这一次写歌的时候，却更换了创作地点，并将工作放在伦敦奇斯威克进行，《Hello》的创作工作历时六个月完成。阿黛尔创作这首歌曲，是因为她觉得自己与所有认识之人的生活都在不断往前发展。她还说，这首歌曲并不是描写过去的感情关系，而是叙述她与自己所爱的每一个人之间的关系。但是，这并不表示阿黛尔与这些人的关系已经破裂了，而是阿黛尔认为自己需要写一首能够告诉这些人是她与他们失去联系的歌曲。");
        }
        if(song.equals("说谎")){
            backgroud.setText("《说谎》是李双飞谱曲，施人诚填词，由林宥嘉演唱的一首情歌，同时也是电视剧《杜拉拉升职记》插曲和电影《针尖上的天使》的主题曲。该歌收录在林宥嘉2009年10月30日发行的专辑《感官/世界》中《说谎》这首歌其实是林宥嘉在专辑中最后配唱完成的歌曲，因为编曲的一再求好而不断重编，也因为他一直坚持希望可以找到一个对的方式来诠释这个虚构的故事中的真实心情，而一再地配唱。整首歌曲启用了类似小说的结构，几乎都用对白式的句子来交代故事与主角的心情，歌中反复唱着“我没有说谎”，但几乎句句都是谎话，可说是布局结构相当大胆的一首作品。");
        }
        else{

        }








    }

}

package com.example.administrator.musicplayer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class main_page_adapter extends BaseAdapter {

    private Context context;  //当前上下文
    private List<song> mDatas;

    public class ViewHolder {
        TextView song;
        TextView singer;
        ImageView cover;

    }
    //构造函数
    public main_page_adapter(Context context, List<song> mDatas){
        this.context = context;
        this.mDatas = mDatas;
    }


    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        if(mDatas == null){
            return 0;
        }
        else
            return mDatas.size();
    }
    @Override
    public Object getItem(int i){
        if(mDatas == null){
            return 0;
        }
        else
            return mDatas.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        //新声明一个View变量和ViewHolder变量
        View convertView;
        ViewHolder viewHolder;

        //当view为空时才加载布局，并且创建一个ViewHolder，获得布局中的3个控件。
        if(view == null){

            //通过inflate方法加载布局，context这个参数需要使用adapter的Activity传入
            convertView = LayoutInflater.from(context).inflate(R.layout.song_info , null);
            viewHolder = new ViewHolder();
            viewHolder.song = (TextView)convertView.findViewById(R.id.song);
            viewHolder.singer = (TextView)convertView.findViewById(R.id.singer);
            viewHolder.cover = (ImageView)convertView.findViewById(R.id.cover);

            //用setTag方法将处理好的viewHolder放入view中
            convertView.setTag(viewHolder);
        }
        else{
            //否则，让convertView等于view，然后从中取出ViewHolder即可。
            convertView = view;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //从ViewHolder中取出对应的对象，然后赋值
        viewHolder.song.setText(mDatas.get(i).getSong());
        viewHolder.singer.setText(mDatas.get(i).getSinger());
        viewHolder.cover.setImageResource(mDatas.get(i).getCover());

        return convertView;
    }


}


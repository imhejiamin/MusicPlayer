package com.example.administrator.musicplayer;

import android.content.ContentResolver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hxr on 2018/1/7.
 */

public class Comment {
    private String date;
    private String commentDetail;

    public Comment(String date, String commentDetail){
        this.date = date;
        this.commentDetail = commentDetail;
    }

    public String getDate(){return date;}
    public String getCommentDetail(){return commentDetail;}



}

package com.example.administrator.musicplayer;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.database.SQLException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.administrator.musicplayer.R.id.contentPanel;
import static com.example.administrator.musicplayer.R.id.listView;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SelfComment extends AppCompatActivity {
    private ListView commentListView;
    private EditText commentEditText;
    private ImageView sendImageView;
    private ImageView backInSelfComment;

    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, String>> commentsForSong = new ArrayList<>();

    private static String TABLE_NAME = "GAOBAIQIQIU";

    //get from bundle
    private String songName;

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm yyyy/MM/DD");

    private void findView(){
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        commentListView = (ListView) findViewById(R.id.commentListView);
        sendImageView = (ImageView) findViewById(R.id.sendImageView);
        backInSelfComment = (ImageView) findViewById(R.id.backInSelfComment);
    }

    private void handleListView(){
        try{
            //load the comments before
            commentsForSong.clear();
            myDB db = new myDB(getBaseContext());
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            String selectString = "select * from " + TABLE_NAME;
            Cursor cursor = sqLiteDatabase.rawQuery(selectString, null);
            if(cursor != null){
                while(cursor.moveToNext()){
                    String comment = cursor.getString(0),
                            date = cursor.getString(1);
                    HashMap<String, String> tmp = new HashMap<>();
                    tmp.put("comment", comment);
                    tmp.put("date", date);
                    commentsForSong.add(tmp);
                }
                simpleAdapter = new SimpleAdapter(SelfComment.this, commentsForSong, R.layout.comment_item,
                        new String[]{"comment", "date"},
                        new int[]{R.id.item_content, R.id.item_date});
                commentListView.setAdapter(simpleAdapter);
            }

        }catch (SQLException e){ }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_comment);

        Intent intent1 = getIntent();
        final Bundle extras = intent1.getExtras();
        songName = extras.getString("song");

        if(songName.equals("告白气球"))
            TABLE_NAME = "GAOBAIQIQIU";
        else if(songName.equals("Love Story"))
            TABLE_NAME = "LOVESTORY";
        else if(songName.equals("她说"))
            TABLE_NAME = "TASHUO";
        else if(songName.equals("Hello"))
            TABLE_NAME = "HELLO";
        else if(songName.equals("说谎"))
            TABLE_NAME = "SHUOHUANG";

        findView();

        handleListView();

        backInSelfComment.setOnClickListener(new View.OnClickListener() { //返回键
            @Override
            public void onClick(View v) {
                SelfComment.this.finish();
            }
        });

        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int pos, long id) {
                //delete
                AlertDialog.Builder dialog = new AlertDialog.Builder(SelfComment.this);
                dialog.setMessage("真的要删掉吗QAQ");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDB db = new myDB(getBaseContext());
                        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                        String deleteString = "delete from "
                                + TABLE_NAME
                                + " where comment = ?";
                        sqLiteDatabase.execSQL(deleteString, new String[]{commentsForSong.get(pos).get("comment")});
                        commentsForSong.remove(pos);
                        simpleAdapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                return false;
            }
        });

        sendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detail = commentEditText.getText().toString();

                if(detail.equals("")){
                    Toast.makeText(SelfComment.this, "内容为空",Toast.LENGTH_SHORT ).show();
                }
                else{
                    //add
                    Date curDate = new Date(System.currentTimeMillis());
                    String dateStr = format.format(curDate);

                    myDB db = new myDB(getBaseContext());
                    SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("comment", detail);
                    values.put("date", dateStr);
                    sqLiteDatabase.insert(TABLE_NAME, null, values);
                    sqLiteDatabase.close();
                    handleListView();
                    commentEditText.setText("");
                }

            }
        });

    }
}

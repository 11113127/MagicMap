package com.example.kimp.magicmaprebulid2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by user2 on 2015/9/29.
 */
public class Select extends Activity {
    static String TAG = "SelectTAG";
    Button btn_friendlist, btn_activitylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        findV();
        setClickListener();

    }

    public void findV(){
        btn_activitylist = (Button) findViewById(R.id.btn_activitylist);
        btn_friendlist = (Button) findViewById(R.id.btn_friendlist);
    }

    public void setClickListener(){
        btn_friendlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Go to Friend List");
                Intent intent = new Intent(Select.this, FriendList.class);
                startActivity(intent);
            }
        });

        btn_activitylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Go to Friend Activity List");
                Intent intent = new Intent(Select.this, ActivityList.class);
                startActivity(intent);
            }
        });
    }
}

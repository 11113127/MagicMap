package com.example.kimp.magicmaprebulid2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by user2 on 2015/10/14.
 */
public class Refresh extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh);
        Intent intent = new Intent(Refresh.this, ActivityList.class);
        startActivity(intent);
        finish();
    }
}

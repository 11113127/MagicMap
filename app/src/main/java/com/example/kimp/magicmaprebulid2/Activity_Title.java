package com.example.kimp.magicmaprebulid2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user2 on 2015/10/6.
 */
public class Activity_Title extends Activity {

    EditText edt_activityName;
    Button btn_nextStep;
    ListView listView;
    String getName[], getSIMID[];
    String lat="", lng="";
    String list = "";
    ArrayList<Integer> checkedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        getAddLatLng();
        findV();
        getUserName();
        clickListener();
    }

    public void findV(){
        edt_activityName = (EditText) findViewById(R.id.edt_ActivityName);
        btn_nextStep = (Button) findViewById(R.id.btn_nextStep);
        listView = (ListView) findViewById(R.id.list_friend);
        checkedList = new ArrayList<Integer>();
    }

    public void getAddLatLng(){
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            lat = bundle.getString("lat");
            lng = bundle.getString("lng");
        }

    }

    public void clickListener(){
        btn_nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_activityName.getText().toString().equals("")){
                    Toast.makeText(Activity_Title.this, R.string.activity_title_text_wrong, Toast.LENGTH_SHORT).show();
                }else {
                    String inputName = edt_activityName.getText().toString();
                    addActivity(inputName, checkedList);
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.getCheckedItemPositions().get(position)) {
                    checkedList.add(position);
                } else {
                    checkedList.remove(new Integer(position));
                }
            }
        });
    }

    public void addActivity(String name, ArrayList<Integer> friends){
        for (int i = 0; i < friends.size(); i++) {
            if(friends.size()-1 == i)
            {
                list += getSIMID[friends.get(i)];
            }else
            {
                list += getSIMID[friends.get(i)] + ",";
            }
        }
        Model model = new Model();
        model.addNewActivity(name, lat, lng, list, addActivityHandler);
    }

    public void getUserName(){
        Model model = new Model();
        model.userListModel(getUserHandler);
    }

    private Handler getUserHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            Log.e("LoginDebug", "Login Result:" + result);
            getJSONsetList(result);
        }
    };

    private void getJSONsetList(String result){
            try {
                JSONArray ja = new JSONArray(result);
                getName = new String[ja.length()];
                getSIMID = new String[ja.length()];

            /*剖析JSON, 放進String 陣列*/
                for (int i=0; i<ja.length(); i++){
                    JSONObject jo = new JSONObject(ja.get(i).toString());
                    getName[i] = jo.getString("name");
                    getSIMID[i] = jo.getString("simid");
                }

            /*設定ListView Adapter*/
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //設定ListView為複選模式
                listView.setAdapter(new ArrayAdapter<String>(
                        Activity_Title.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        getName));

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    private Handler addActivityHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("result");
            Log.e("LoginDebug", "Login Result:" + result);
            GotoMap(result);
        }
    };

    public void GotoMap(String result){
        String getAid = "";
        try {
            JSONObject jo = new JSONObject(result);
            getAid = jo.getString("aid");
            Log.e("aid", "Login Result:" + getAid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Activity_Title.this, Map.class);
        Bundle bundle = new Bundle();
        bundle.putString("aid", getAid);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}

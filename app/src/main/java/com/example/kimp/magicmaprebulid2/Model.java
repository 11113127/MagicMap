package com.example.kimp.magicmaprebulid2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;

/**
 * Created by user2 on 2015/9/29.
 */
public class Model {
    static String TAG = "Model";

    private void connectService(String serviceName, Handler handler) {
        connectService(serviceName, new HashMap(), handler);
    }

    private void connectService(String serviceName, HashMap parameter, Handler handler) {
        Thread runService = new Thread(new RunService(serviceName, parameter, handler));
        runService.start();
    }

    protected void loginModel(String name, Handler handler) {
        HashMap hMap = new HashMap();
        hMap.put("simid", value.simid);
        hMap.put("name", name);
        connectService("login", hMap, handler);
    }

    protected void getActivityModel(Handler handler) {
        HashMap hMap = new HashMap();
        hMap.put("simid", value.simid);
        connectService("myActivities", hMap, handler);
        Log.e(TAG + "myActivityies", value.simid);
    }

    protected void userListModel(Handler handler) {
        connectService("user", handler);
    }

    protected void setLocation(double lat, double lng, Handler handler) {
        HashMap hMap = new HashMap();
        hMap.put("simid", value.simid);
        hMap.put("lat", lat);
        hMap.put("lng", lng);
        Log.e(TAG+"updateMyLocation", value.simid + lat+", " + lng);
        connectService("updateMylocation", hMap, handler);
    }

    protected void addNewActivity(String name, String lat, String lng, String friends, Handler handler) {
        HashMap hMap = new HashMap();
        hMap.put("title", name);
        hMap.put("lat", lat);
        hMap.put("lng", lng);
        hMap.put("friend", friends);
        connectService("addActivities", hMap, handler);
        Log.e(TAG + "addActivities", value.simid + "LatLng:" + lat + ", " + lng);
    }

    protected void deleteActivity(String aid,  Handler handler){
        HashMap hMap = new HashMap();
        hMap.put("aid", aid);
        hMap.put("simid", value.simid);
        connectService("deleteActivities", hMap, handler);
        Log.e(TAG + "deleteActivities", "Delete");
    }

    protected void setMapMarker(String aid, Handler handler) {
        HashMap hMap = new HashMap();
        hMap.put("aid", aid);
        connectService("aboutActivity", hMap, handler);
    }

}

class RunService implements Runnable {
    String serviceName;
    HashMap parameter;
    Handler handler;

    public RunService(String serviceName, HashMap parameter, Handler handler) {
        this.serviceName = serviceName;
        this.parameter = parameter;
        this.handler = handler;
    }

    @Override
    public void run() {
        //Please open network permission
        String paramContent = "";
        int runNum = 0;

        for (Object param : parameter.keySet()) {
            paramContent += (param + "=" + parameter.get(param));
            if (runNum != parameter.size() - 1) {
                paramContent += "&";
            }
            runNum++;
        }


        try {
            HttpClient client = new DefaultHttpClient();
            String url = value.service + serviceName + ".php?" + paramContent;
            Log.d("LoginDebug", url);
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            //執行 url 設定的 Login Service
            HttpEntity resEntity = response.getEntity();
            String result = EntityUtils.toString(resEntity);
            //回傳執行的結果並存放在 result 當中
            Bundle countBundle = new Bundle();
            countBundle.putString("result", result);
            //打包活動清單相關資料，並命名為 list
            Message msg = new Message();
            msg.setData(countBundle);
            handler.sendMessage(msg);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

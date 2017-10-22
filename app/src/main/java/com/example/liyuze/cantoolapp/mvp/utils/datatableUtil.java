package com.example.liyuze.cantoolapp.mvp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.signal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by magic on 17/10/15.
 */

public class datatableUtil {

    public static final String TAG = "datatableUtil";

    public static void read(Context context,String[] files){
        HashMap<String,List<signal>> result;
        for(String file:files){
            result = read(context,file);
            Constants.DATATABLE.putAll(result);
        }
    }

    private static HashMap<String,List<signal>> read(Context context,String filename){

        AssetManager am = context.getAssets();
        InputStream is;
        Map<String,List<signal>> DATATABLE = new HashMap<>();
        try {
            is = am.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while( (line = br.readLine()) != null){
                if(line.length() != 0) {
                    String[] temp = line.split(" ");
                    if(DATATABLE.containsKey(temp[1])){
                        DATATABLE.get(temp[1]).add(new signal(
                                temp[3],
                                Integer.parseInt(temp[4]),
                                Integer.parseInt(temp[5]),
                                Integer.parseInt(temp[6]),
                                Double.parseDouble(temp[7]),
                                Double.parseDouble(temp[8]),
                                Double.parseDouble(temp[9]),
                                Double.parseDouble(temp[10]),
                                temp[11]));
                    }else{
                        List<signal> list = new ArrayList<>();
                        if(temp.length == 12) {
                            list.add(new signal(
                                    temp[3],
                                    Integer.parseInt(temp[4]),
                                    Integer.parseInt(temp[5]),
                                    Integer.parseInt(temp[6]),
                                    Double.parseDouble(temp[7]),
                                    Double.parseDouble(temp[8]),
                                    Double.parseDouble(temp[9]),
                                    Double.parseDouble(temp[10]),
                                    temp[11]));
                        }
                        DATATABLE.put(temp[1],list);
                        Constants.MESSAGETABLE.put(temp[1],
                                new canmessage(temp[0].substring(0,temp[0].length()-1),
                                        temp[1],
                                        Integer.parseInt(temp[2])));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"信号描述数据库加载错误");
        }

        return (HashMap<String, List<signal>>) DATATABLE;

    }
}

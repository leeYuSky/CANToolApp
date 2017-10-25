package com.example.liyuze.cantoolapp.mvp.utils;

import android.util.Log;

import com.example.liyuze.cantoolapp.mvp.model.realSignal;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liyuze on 17/10/21.
 */

public class DBUtil {

    public static final String TAG = DBUtil.class.toString();

    public static void insertSignal(String msg,String messageUUID){
        Map<String,Double> result = CANMessageUtil.MessageParse(msg);
        int messageId = CANMessageUtil.getId(msg);
//        String messageUUID = UUID.randomUUID().toString();
        Date date = new Date();
        for(Map.Entry<String,Double> entry : result.entrySet()){
            new realSignal(messageUUID,messageId,entry.getKey(),entry.getValue(),date).save();
        }

        List<realSignal> realSignals = DataSupport.findAll(realSignal.class);

        for(realSignal signal : realSignals){
            Log.e(TAG,"{");
            Log.e(TAG,"   "+ signal.getMessageUUID());
            Log.e(TAG,"   " + signal.getMessageId());
            Log.e(TAG,"   " + signal.getSignalName());
            Log.e(TAG,"   " + signal.getRealValue());
            Log.e(TAG,"   " + signal.getDate());
            Log.e(TAG,"}");
        }

    }

    public static List<realSignal> getRealSignal(int count,String signalName){
        List<realSignal> realSignals = DataSupport
                .where("signalName = ?",signalName)
                .order("date desc")
                .limit(count)
                .find(realSignal.class);
        return realSignals;
    }

    public static List<realSignal> getRealSignalByUUID(String uuid){
        List<realSignal> realSignals = DataSupport
                .where("messageUUID = ?",uuid)
                .find(realSignal.class);
        return realSignals;
    }
}

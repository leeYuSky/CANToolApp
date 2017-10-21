package com.example.liyuze.cantoolapp.mvp.model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by liyuze on 17/10/21.
 */

public class realSignal extends DataSupport {

    private String messageUUID;
    private Integer messageId;
    private String signalName;
    private Double realValue;
    private Date date;

    public realSignal(){

    }

    public realSignal(String messageUUID, int messageId, String signalName, Double realValue, Date date) {
        this.messageUUID = messageUUID;
        this.messageId = messageId;
        this.signalName = signalName;
        this.realValue = realValue;
        this.date = date;
    }

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(String messageUUID) {
        this.messageUUID = messageUUID;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }

    public Double getRealValue() {
        return realValue;
    }

    public void setRealValue(Double realValue) {
        this.realValue = realValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

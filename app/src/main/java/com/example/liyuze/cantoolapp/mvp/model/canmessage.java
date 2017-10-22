package com.example.liyuze.cantoolapp.mvp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by liyuze on 17/10/22.
 */

public class canmessage {

    private String messageName;
    private String messageId;
    private int byteCount;

    public canmessage() {
    }

    public canmessage(String messageName, String messageId, int byteCount) {
        this.messageName = messageName;
        this.messageId = messageId;
        this.byteCount = byteCount;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getByteCount() {
        return byteCount;
    }

    public void setByteCount(int byteCount) {
        this.byteCount = byteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        canmessage that = (canmessage) o;

        return new EqualsBuilder()
                .append(byteCount, that.byteCount)
                .append(messageName, that.messageName)
                .append(messageId, that.messageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(messageName)
                .append(messageId)
                .append(byteCount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "canmessage{" +
                "messageName='" + messageName + '\'' +
                ", messageId='" + messageId + '\'' +
                ", byteCount=" + byteCount +
                '}';
    }
}

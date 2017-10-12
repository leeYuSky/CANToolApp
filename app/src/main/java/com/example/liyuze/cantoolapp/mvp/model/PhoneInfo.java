package com.example.liyuze.cantoolapp.mvp.model;

/**
 * Created by liyuze on 17/10/12.
 */

public class PhoneInfo {

    private String name;
    private String MAC;

    public PhoneInfo(String name, String MAC) {
        this.name = name;
        this.MAC = MAC;
    }

    public String getName() {
        return name;
    }

    public String getMAC() {
        return MAC;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}

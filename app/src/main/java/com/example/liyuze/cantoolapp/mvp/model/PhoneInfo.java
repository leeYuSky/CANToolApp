package com.example.liyuze.cantoolapp.mvp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PhoneInfo phoneInfo = (PhoneInfo) o;

        return new EqualsBuilder()
                .append(name, phoneInfo.name)
                .append(MAC, phoneInfo.MAC)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(MAC)
                .toHashCode();
    }
}

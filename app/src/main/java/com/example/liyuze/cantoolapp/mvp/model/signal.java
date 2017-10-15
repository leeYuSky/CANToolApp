package com.example.liyuze.cantoolapp.mvp.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by liyuze on 17/10/15.
 */

public class signal {

    private String name;
    private int start;
    private int length;
    private int type;
    private double a;
    private double offset;
    private double min;
    private double max;
    private String unit;

    public signal(String name, int start, int length, int type, double a, double offset, double min, double max, String unit) {
        this.name = name;
        this.start = start;
        this.length = length;
        this.type = type;
        this.a = a;
        this.offset = offset;
        this.min = min;
        this.max = max;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "signal{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", length=" + length +
                ", type=" + type +
                ", a=" + a +
                ", offset=" + offset +
                ", min=" + min +
                ", max=" + max +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        signal signal = (signal) o;

        return new EqualsBuilder()
                .append(start, signal.start)
                .append(length, signal.length)
                .append(type, signal.type)
                .append(a, signal.a)
                .append(offset, signal.offset)
                .append(min, signal.min)
                .append(max, signal.max)
                .append(name, signal.name)
                .append(unit, signal.unit)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(start)
                .append(length)
                .append(type)
                .append(a)
                .append(offset)
                .append(min)
                .append(max)
                .append(unit)
                .toHashCode();
    }
}

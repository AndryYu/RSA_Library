package com.zyu.wsecx.outter.util;

public interface Selector extends Cloneable {
    boolean match(Object obj);

    Object clone();
}

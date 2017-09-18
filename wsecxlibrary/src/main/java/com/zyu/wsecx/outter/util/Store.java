package com.zyu.wsecx.outter.util;

import java.util.Collection;

public interface Store {
    Collection getMatches(Selector selector)
            throws StoreException;
}

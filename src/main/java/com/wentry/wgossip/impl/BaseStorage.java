package com.wentry.wgossip.impl;

import com.wentry.wgossip.IStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseStorage implements IStorage {

    Map<String, String> data = new ConcurrentHashMap<>();

    @Override
    public String put(String key, String val) {
        return data.put(key, val);
    }

    @Override
    public void del(String del) {
        data.remove(del);
    }

    @Override
    public String get(String key) {
        return data.get(key);
    }
}

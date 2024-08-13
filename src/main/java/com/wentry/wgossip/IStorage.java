package com.wentry.wgossip;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IStorage {

    String put(String key, String val);

    void del(String del);

    String get(String key);

}

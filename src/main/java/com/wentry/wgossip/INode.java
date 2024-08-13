package com.wentry.wgossip;

/**
 * @Description:
 * @Author: tangwc
 */
public interface INode {


    void put(String key, String val);

    void del(String key);

    String get(String key);

}

package com.wentry.wgossip.impl;

import com.wentry.wgossip.INode;
import com.wentry.wgossip.IStorage;
import com.wentry.wgossip.ILogAppender;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseNode implements INode {

    IStorage storage = new BaseStorage();
    ILogAppender logAppender = new BaseLogAppender(storage);

    @Override
    public void put(String key, String val) {
        logAppender.append(new Log().setCmd(Log.PUT).setKey(key).setVal(val));
    }

    @Override
    public void del(String key) {
        logAppender.append(new Log().setCmd(Log.DEL).setKey(key));
    }

    @Override
    public String get(String key) {
        return storage.get(key);
    }

}

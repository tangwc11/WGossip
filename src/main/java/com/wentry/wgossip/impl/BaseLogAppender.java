package com.wentry.wgossip.impl;

import com.wentry.wgossip.ILogAppender;
import com.wentry.wgossip.IStorage;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseLogAppender implements ILogAppender {

    private IStorage storage;

    private BaseLogAppender() {
    }

    public BaseLogAppender(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void append(Log log) {
        switch (log.getCmd()) {
            case Log.PUT:
                storage.put(log.getKey(), log.getVal());
                break;
            case Log.DEL:
                storage.del(log.getKey());
                break;
            default:
        }
    }

}

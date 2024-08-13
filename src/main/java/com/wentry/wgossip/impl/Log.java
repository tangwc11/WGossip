package com.wentry.wgossip.impl;

import java.util.Objects;

/**
 * @Description:
 * @Author: tangwc
 */
public class Log {

    /**
     * 雪花算法生成的递增的id最合适，这里简单用nanoTime
     */
    private String id = "id-" + System.nanoTime();

    static final int PUT = 1;
    static final int DEL = 2;
    private int cmd;
    private String key;
    private String val;

    public String getId() {
        return id;
    }

    public Log setId(String id) {
        this.id = id;
        return this;
    }

    public int getCmd() {
        return cmd;
    }

    public Log setCmd(int cmd) {
        this.cmd = cmd;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Log setKey(String key) {
        this.key = key;
        return this;
    }

    public String getVal() {
        return val;
    }

    public Log setVal(String val) {
        this.val = val;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Log log = (Log) o;
        return id.equals(log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

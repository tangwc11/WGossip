package com.wentry.wgossip;

import java.util.List;

/**
 * @Description:
 * @Author: tangwc
 */
public interface ICluster {

    /**
     * 获取集群所有的节点
     */
    List<INode> getNodes();

    /**
     * 集群状态是否已经完全同步
     */
    boolean syncOver();

    /**
     * 阻塞等待直到同步
     */
    void waitSync();

}

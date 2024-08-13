package com.wentry.wgossip.impl;

import com.wentry.utils.RandomSelector;
import com.wentry.wgossip.ICluster;
import com.wentry.wgossip.INode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: tangwc
 */
public class GossipCluster implements INode, ICluster {

    /**
     * 集群节点个数
     */
    int clusterSize;
    /**
     * 通知个数
     */
    int fanout;
    List<INode> nodes;
    Deque<Log> logDeque = new ArrayDeque<>();
    Log currSyncLog = null;
    List<Thread> blocked = new ArrayList<>();

    private GossipCluster() {
    }

    /**
     * clusterSize必传
     * clusterSize和fanout决定了达到一致状态的平均同步轮数：n = log-fanout-clusterSize
     */
    public GossipCluster(int clusterSize, int fanout) {
        this.clusterSize = clusterSize;
        this.fanout = Math.min(clusterSize, fanout);
        nodes = new ArrayList<>();
        for (int i = 0; i < this.clusterSize; i++) {
            nodes.add(new BaseNode());
        }
        //单开一个线程进行同步
        initSchedule();
    }

    private void initSchedule() {
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(
                        this::doSync,
                        0,
                        100,
                        TimeUnit.MILLISECONDS
                );
    }

    Map<Log, Set<INode>> syncedCount = new HashMap<>();

    private void doSync() {
        Log log = fetchLog();
        if (log == null) {
            return;
        }
        List<INode> nodes = randomGetNodes();
        if (nodes == null) {
            return;
        }
        for (INode node : nodes) {
//            System.out.println("node:" + node + ", sync:" + log);
            switch (log.getCmd()) {
                case Log.PUT:
                    node.put(log.getKey(), log.getVal());
                    break;
                case Log.DEL:
                    node.del(log.getKey());
                    break;
                default:
            }
            syncedCount.get(log).add(node);
        }

        //最后判断一下是否已经完全同步，如果完全同步了，这里放行被阻塞的线程
        if (syncOver()) {
            for (Thread thread : blocked) {
                LockSupport.unpark(thread);
            }
        }
    }

    private List<INode> randomGetNodes() {
        return RandomSelector.selectRandomElements(nodes, fanout);
    }

    private Log fetchLog() {
        if (currSyncLog == null) {
            if (logDeque.isEmpty()) {
                return null;
            }
            currSyncLog = logDeque.pollFirst();
            syncedCount.put(currSyncLog, new HashSet<>());
            return currSyncLog;
        }
        //当前log已经完全同步了，寻找下一个log进行同步
        if (syncedCount.get(currSyncLog).size() == nodes.size()) {
            if (logDeque.isEmpty()) {
                //不存在下一个log，这里直接返回null，currLog不用变
                return null;
            }
            currSyncLog = logDeque.pollFirst();
            syncedCount.put(currSyncLog, new HashSet<>());
        }
        return currSyncLog;
    }

    @Override
    public void put(String key, String val) {
        logDeque.addLast(new Log().setCmd(Log.PUT).setKey(key).setVal(val));
    }

    @Override
    public void del(String key) {
        logDeque.addLast(new Log().setCmd(Log.DEL).setKey(key));
    }

    @Override
    public String get(String key) {
        //随机取就行，毕竟是最终一致，不一致也没关系
        return nodes.get(new Random().nextInt(nodes.size())).get(key);
    }


    @Override
    public List<INode> getNodes() {
        return nodes;
    }

    @Override
    public boolean syncOver() {
        return (currSyncLog == null && logDeque.isEmpty())
                ||
                (currSyncLog != null
                        && syncedCount.get(currSyncLog).size() == nodes.size()
                        && logDeque.isEmpty()
                );
    }

    @Override
    public void waitSync() {
        if (!syncOver()) {
            //阻塞住当前线程
            blocked.add(Thread.currentThread());
            LockSupport.park();
        }
    }
}

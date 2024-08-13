package com.wentry.wgossip.example;

import com.wentry.utils.LogUtils;
import com.wentry.wgossip.INode;
import com.wentry.wgossip.impl.GossipCluster;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: tangwc
 */
public class Test {
    public static void main(String[] args) {
        //20个节点，每次同步5个
        GossipCluster cluster = new GossipCluster(20, 5);


        cluster.put("key", "1");
        cluster.put("key", "2");
        cluster.put("key", "3");
        cluster.put("key", "4");
        cluster.put("key", "5");
        cluster.put("key", "6");

        System.out.println(LogUtils.wrapTimeLog("赋值结束，数据同步中..."));

        //启动一个线程在同步完成之前读取，这里的key肯定是不一致的，
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(LogUtils.wrapTimeLog("当前集群对外提供的值是：" + cluster.get("key")));
            }
        }, 1, 1, TimeUnit.SECONDS);

        cluster.waitSync();

        List<INode> nodes = cluster.getNodes();

        System.out.println(LogUtils.wrapTimeLog("同步完毕，开始校验各个节点获取的值是否一致..."));
        for (INode node : nodes) {
            System.out.println(LogUtils.wrapTimeLog("node:" + node + ", key:" + node.get("key")));
        }
        System.out.println(LogUtils.wrapTimeLog("检验完毕"));

    }
}

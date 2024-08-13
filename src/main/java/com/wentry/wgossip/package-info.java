/**
 * @Description: gossip协议的简单实现
 * @Author: tangwc
 *
 * 具备以下几个特性：
 * 1。 每个节点具有全量的数据
 * 2。 数据以日志的方式被记录
 * 3。 日志具有递增的id，以用于区分先后顺序
 * 4。 数据存储在本地，只是用于简单demo
 * 5。 此实现没有去中心化，通过一个cluster作为门面
 *
 * gossip的动画演示可以参照：https://flopezluis.github.io/gossip-simulator/
 * 本质就是收敛的随机同步算法
 */
package com.wentry.wgossip;
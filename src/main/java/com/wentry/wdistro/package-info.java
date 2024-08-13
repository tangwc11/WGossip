/**
 * @Description:
 * @Author: tangwc
 *
 * distro也是一种最终一致的算法，只不过节点不保存全量的数据，按照分片进行处理
 * 节点之间，进行分片元数据的同步，以保证集群数据的一致性以及命令的转发目的地的一致性
 */
package com.wentry.wdistro;
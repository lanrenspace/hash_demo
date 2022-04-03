package com.lanrenspace;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author lanrenspace@163.com
 * @Description: 一致性ip_hash 哈希算法,不含虚拟节点
 **/
public class ConsistentHashNoVirtual {


    public static void main(String[] args) {
        // 吧服务器节点ip的哈希值对应到哈希环上（哈希环是2的32次方大小）
        String[] tomcatServers = new String[]{"192.168.3.110", "192.168.3.111", "192.168.3.112", "192.168.3.113"};
        SortedMap<Integer, String> hashServerMap = new TreeMap<>();
        for (String tomcatServer : tomcatServers) {
            // 算出每一个ip的hash值，对应到hash环上，存储hash值与ip的对应关系
            int serverHash = Math.abs(tomcatServer.hashCode());
            // 存储hash值与ip的对应关系
            hashServerMap.put(serverHash, tomcatServer);
        }
        // 针对客户端ip算出hash值
        String[] clientIps = new String[]{"192.168.23.1", "192.168.25.23", "192.168.45.67", "192.168.46.79", "192.168.3.110"};
        for (String client : clientIps) {
            int clientHash = Math.abs(client.hashCode());
            // 从哈希环上找到顺时针离当前客户端hash最近的节点
            // tailMap 可以根据key返回比当前key大的所有节点map
            SortedMap<Integer, String> tailHashServerMap = hashServerMap.tailMap(clientHash);
            // 因为hash环是一个闭环，如果没有找到比当前客户端节点hash大的节点，那就是起始节点
            String tomcatServerIp = hashServerMap.get(hashServerMap.firstKey());
            if (!tailHashServerMap.isEmpty()) {
                tomcatServerIp = tailHashServerMap.get(tailHashServerMap.firstKey());
            }
            System.out.println("客户端：" + client + " 被路由到的服务器ip为：" + tomcatServerIp);
        }
    }
}

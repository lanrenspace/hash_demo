package com.lanrenspace;

/**
 * @Author lanrenspace@163.com
 * @Description: 模拟普通的ip_hash实现
 **/
public class GeneralHash {

    public static void main(String[] args) {
        // 客户端请求IP
        String[] clientIps = new String[]{"192.168.3.110", "192.168.3.170", "192.168.3.120"};
        // 服务器数量
        int serverCount = 7;

        // hash(ip)%node_counts=index
        // 根据index锁定应该将客户端路由到的tomcat服务器
        for (String clientIp : clientIps) {
            int hashCode = Math.abs(clientIp.hashCode());
            int index = hashCode % serverCount;
            System.out.println("客户端：" + clientIp + " 被路由到的服务器编号为：" + index);
        }
    }
}

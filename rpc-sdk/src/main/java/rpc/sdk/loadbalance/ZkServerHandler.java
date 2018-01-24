package rpc.sdk.loadbalance;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rpc.sdk.constants.GlobalConstant;
import rpc.sdk.util.Logger;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * <br/>==========================
 * 负载均衡处理器，由zookeeper来实现
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
@Component
public class ZkServerHandler implements Watcher{

    /**
     * 活动结点
     */
    private static final String GROUP_NAME = "/nodes";

    private ZooKeeper zooKeeper;

    @Value("${zk.server.url}")
    private String zkServerUrl;
    @Value("${zk.server.timeout}")
    private String zkServerTimeout;

    @PostConstruct
    public void initZooKeeper() {
        try {
            zooKeeper = new ZooKeeper(zkServerUrl, Integer.parseInt(zkServerTimeout), this);
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        //建立连接
        if (event.getState() == KeeperState.SyncConnected) {
            createGroup();
        }
    }

    /**
     * 创建zookeeper节点
     */
    private void createGroup() {
        try {
            Stat stat = zooKeeper.exists(GROUP_NAME, false);
            if (stat == null) {
                zooKeeper.create(GROUP_NAME, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException |InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    /**
     * 可用的服务器列表
     * @return
     */
    public List<String> availableNodes() {
        List<String> nodeList = new ArrayList<>();
        try {
            createGroup();
            List<String> children = zooKeeper.getChildren(GROUP_NAME, true, new Stat());
            children.forEach(child -> {
                try {
                    nodeList.add(new String(zooKeeper.getData(GROUP_NAME + "/" + child, true, new Stat()), GlobalConstant.DEFAULT_ENCODING));
                } catch (KeeperException |InterruptedException|UnsupportedEncodingException e) {
                    Logger.error(e.getMessage(), e);
                }
            });
        } catch (KeeperException|InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }
        return nodeList;
    }

    /**
     * 增加结点
     * @param node 结点名称
     * @param data 结点数据
     */
    public void addNode(String node, String data) {
        try {
            //删除原有的结点
            Stat stat = zooKeeper.exists(GROUP_NAME + "/" + node, false);
            if (stat != null) {
                zooKeeper.delete(GROUP_NAME + "/" + node, Version.REVISION);
            }
            zooKeeper.create(GROUP_NAME + "/" + node, data.getBytes(GlobalConstant.DEFAULT_ENCODING),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException|InterruptedException|UnsupportedEncodingException e) {
            Logger.error(e.getMessage(), e);
        }
    }

}

package com.example.mall.note.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <dl>
 * <dt><p>@description zk api <p>@date 2021/12/29 14:44</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@Component
public class ZkApi {

    private static final Logger logger = LoggerFactory.getLogger(ZkApi.class);

    @Resource
    private ZooKeeper zooKeeper;

    /**
     * 判断指定节点是否存在
     *
     * @param nodePath 节点名
     * @param needWatch 指定是否复用zookeeper中默认的Watcher
     * @return Stat exists
     */
    public Stat exists(String nodePath, boolean needWatch) {
        try {
            return zooKeeper.exists(nodePath, needWatch);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}", nodePath, e);
            return null;
        }
    }

    /**
     * 检测结点是否存在 并设置监听事件
     * 三种监听类型： 创建，删除，更新
     *
     * @param nodePath 节点名
     * @param watcher 传入指定的监听类
     * @return Stat exists
     */
    public Stat exists(String nodePath, Watcher watcher) {
        try {
            return zooKeeper.exists(nodePath, watcher);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}", nodePath, e);
            return null;
        }
    }

    /**
     * 创建持久化节点
     *
     * @param nodePath 节点名
     * @param nodeData 节点值
     * @return boolean createNode
     */
    public boolean createNode(String nodePath, String nodeData) {
        try {
            zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            logger.error("【创建持久化节点异常】{},{},{}", nodePath, nodeData, e);
            return false;
        }
    }

    /**
     * 修改持久化节点
     *
     * @param nodePath 节点名
     * @param nodeData 节点值
     */
    public boolean updateNode(String nodePath, String nodeData) {
        try {
            //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zooKeeper.setData(nodePath, nodeData.getBytes(), -1);
            return true;
        } catch (Exception e) {
            logger.error("【修改持久化节点异常】{},{},{}", nodePath, nodeData, e);
            return false;
        }
    }

    /**
     * 删除持久化节点
     *
     * @param nodePath 节点名
     */
    public boolean deleteNode(String nodePath) {
        try {
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zooKeeper.delete(nodePath, -1);
            return true;
        } catch (Exception e) {
            logger.error("【删除持久化节点异常】{},{}", nodePath, e);
            return false;
        }
    }

    /**
     * 获取当前节点的子节点(不包含孙子节点)
     *
     * @param nodePath 父节点path
     */
    public List<String> getChildren(String nodePath) throws KeeperException, InterruptedException {
        List<String> list = zooKeeper.getChildren(nodePath, false);
        return Optional.ofNullable(list).orElse(new ArrayList<>());
    }

    /**
     * 获取指定节点的值
     *
     * @param nodePath 节点名
     * @return String getData
     */
    public String getData(String nodePath, Watcher watcher) {
        try {
            Watcher orElse = Optional.ofNullable(watcher).orElse(new MyWatcherDefault());
            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData(nodePath, orElse, stat);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 测试方法  初始化
     */
    @PostConstruct
    public void init() {
        String nodePath = "/zk-watcher-test";
        logger.info("【执行初始化测试方法】 。。。。。。。。。。。。 保存 {'node': '/zk-watcher-test', 'node_val': 'test_val' }");
        boolean b = createNode(nodePath, "test_val");
        String value = getData(nodePath, event -> {
            //  三种监听类型： 创建，删除，更新
            logger.info("【Watcher】监听事件={}", event.getState());
            logger.info("【Watcher】监听路径为={}", event.getPath());
            logger.info("【Watcher】监听的类型为={}", event.getType());
        });
        logger.info("【执行初始化测试方法】 。。。。。。。。。。。。 保存 返回值 = {}", value);

        // 删除节点出发 监听事件
        deleteNode(nodePath);
    }

}

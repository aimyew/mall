package com.example.mall.note.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <dl>
 * <dt><p>@description My Watcher Children <p>@date 2021/12/30 11:16</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@Slf4j
public class MyWatcherChildren implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        String stringBuilder = "==========MyWatcherChildren start==============" +
                "\r\n" + "MyWatcherChildren state: " + event.getState().name() +
                "\r\n" + "MyWatcherChildren type: " + event.getType().name() +
                "\r\n" + "MyWatcherChildren path: " + event.getPath() +
                "\r\n" + "==========MyWatcherChildren end==============";
        log.info(stringBuilder);
    }
}
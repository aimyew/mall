package com.example.mall.note.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <dl>
 * <dt><p>@description My Watcher Data <p>@date 2021/12/30 11:19</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@Slf4j
public class MyWatcherData implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        String stringBuilder = "==========DataWatcher start==============" +
                "\r\n" + "DataWatcher state: " + event.getState().name() +
                "\r\n" + "DataWatcher type: " + event.getType().name() +
                "\r\n" + "DataWatcher path: " + event.getPath() +
                "\r\n" + "==========DataWatcher end==============";
        log.info(stringBuilder);
    }
}
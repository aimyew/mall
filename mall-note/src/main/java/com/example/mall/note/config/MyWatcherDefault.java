package com.example.mall.note.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * <dl>
 * <dt><p>@description My Watcher Default <p>@date 2021/12/30 11:15</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@Slf4j
public class MyWatcherDefault implements Watcher {

    @Override
    public void process(WatchedEvent event) {
        String stringBuilder = "==========MyWatcherDefault start==============" +
                "\r\n" + "MyWatcherDefault state: " + event.getState().name() +
                "\r\n" + "MyWatcherDefault type: " + event.getType().name() +
                "\r\n" + "MyWatcherDefault path: " + event.getPath() +
                "\r\n" + "==========MyWatcherDefault end==============";
        log.info(stringBuilder);
    }
}
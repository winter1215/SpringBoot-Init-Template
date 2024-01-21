package com.tower.common.rmq.handler;

import com.tower.common.annotation.RmqHandler;
import com.tower.common.rmq.EventHandler;
import com.tower.common.rmq.EventModel;
import com.tower.common.rmq.EventType;

import java.util.List;

/**
 * 点赞的事件处理器
 * @author winter
 * @create 2023-01-15 上午12:31
 */

@RmqHandler(topic = {EventType.VOTE, EventType.COMMENT})
public class VoteHandler implements EventHandler {
    @Override
    public void doHandler(EventModel eventModel) {
        System.out.println("vote handler: " + eventModel);
    }
}

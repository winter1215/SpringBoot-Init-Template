package com.tower.common.rmq.handler;

import com.tower.common.annotation.RmqHandler;
import com.tower.common.rmq.EventHandler;
import com.tower.common.rmq.EventModel;
import com.tower.common.rmq.EventType;


/**
 * @author winter
 * @create 2023-01-16 下午7:58
 */
@RmqHandler(topic = {EventType.VOTE})
public class CommentHandler implements EventHandler {
    @Override
    public void doHandler(EventModel eventModel) {
        System.out.println("comment handler: " + eventModel);
    }
}

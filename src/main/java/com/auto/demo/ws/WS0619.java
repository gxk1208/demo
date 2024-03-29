package com.auto.demo.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/19 10:11
 */

@ServerEndpoint(value="/websocketTest/{userId}")
public class WS0619 {
    private Logger logger = LoggerFactory.getLogger(WS0619.class);

    private static String userId;

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException {
        WS0619.userId = userId;
        logger.debug("新连接：{}",userId);
    }

    //关闭时执行
    @OnClose
    public void onClose(){
        logger.debug("连接：{} 关闭", WS0619.userId);
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.debug("收到用户{}的消息{}", WS0619.userId,message);
        session.getBasicRemote().sendText("收到 "+ WS0619.userId+" 的消息 "); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        logger.debug("用户id为：{}的连接发送错误", WS0619.userId);
        error.printStackTrace();
    }

}

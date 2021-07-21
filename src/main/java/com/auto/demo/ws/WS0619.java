package com.auto.demo.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/19 10:11
 */

@Slf4j
@Component
@ServerEndpoint(value="/websocketTest/{userId}")
public class WS0619 {

    private static String userId;



    @Autowired
    private HttpServletRequest request1;

    private static HttpServletRequest request;

    @PostConstruct
    public void init(){
        request = this.request1;
    }

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) throws IOException {
        log.info("{}",request);
        WS0619.userId = userId;
        log.info("新连接：{}",userId);
    }

    //关闭时执行
    @OnClose
    public void onClose(){
        log.info("连接：{} 关闭", WS0619.userId);
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到用户{}的消息{}", WS0619.userId,message);
        session.getBasicRemote().sendText("收到 "+ WS0619.userId+" 的消息 "); //回复用户
        for (int i = 0; i < 5; i++) {
            session.getBasicRemote().sendText(String.valueOf(i));
        }
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        log.info("用户id为：{}的连接发送错误", WS0619.userId);
        error.printStackTrace();
    }

}

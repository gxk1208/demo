package com.auto.demo.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/4/20 10:19
 */
@Slf4j
@Component
@ServerEndpoint("/test/{id}")
public class WebSocketServer {
}

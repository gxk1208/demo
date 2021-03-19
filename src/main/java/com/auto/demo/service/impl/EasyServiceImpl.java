package com.auto.demo.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.service.EasyService;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/17 15:55
 */
@Service
public class EasyServiceImpl implements EasyService {

    @Override
    public String test() {
        return "1";
    }

    @Override
    public String imgCode(HttpServerRequest request, HttpServerResponse response) {

        return null;
    }
}

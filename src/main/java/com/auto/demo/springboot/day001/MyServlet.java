package com.auto.demo.springboot.day001;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Enumeration;
/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/16 15:41
 */
@WebServlet(
        // servlet名称
        name = "myServlet",
        // 哪些请求路径会被servlet处理
        value = "/urlPatters",

        loadOnStartup = 1,

        initParams = {
                @WebInitParam(name = "param1",value = "param1"),
                @WebInitParam(name = "param2",value = "param2")
        },
        // 是否支持异步
        asyncSupported = false
)
public class MyServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration initParameterNames = this.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String initParamName = (String) initParameterNames.nextElement();
            String initParamValue = this.getInitParameter(initParamName);
            try {
                resp.getWriter().append(String.format("%s:%s", initParamName, initParamValue));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

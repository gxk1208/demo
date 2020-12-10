package com.auto.demo.utils;


import javax.sql.DataSource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import java.lang.reflect.Proxy;
/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:59
 */
public class JdbcPool implements DataSource {
    /**
     *  * @Field: listConnections 使用LinkedList集合来存放数据库链接，
     * 由于要频繁读写List集合，所以这里使用LinkedList存储数据库连接比较合适  
     */
    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
    private static Connection conn;
    // 在静态代码块中加载src/jdbc.properties数据库配置文件
    static {
        try {
            String driver = "com.mysql.jdbc.cj.Driver";
            String url = "jdbc:mysql://localhost:3306/autotable?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
            String username = "root";
            String password = "root";
            // 数据库连接池的初始化连接数大小
            int jdbcPoolInitSize = 10;
            // 加载数据库驱动
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                conn = DriverManager.getConnection(url, username, password);
            // 将获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池
                listConnections.add(conn);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        if (listConnections.size() > 0) {
            // 从listConnections集合中获取一个数据库连接
            //返回Connection对象的代理对象
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (!method.getName().equals("close")) {
                                return method.invoke(conn, args);
                            } else {
                                // 如果调用的是Connection对象的close方法，就把conn还给数据库连接池
                                listConnections.add(conn);
                                return null;
                            }
                        }
                    });
        } else {
            throw new RuntimeException("对不起，数据库忙");
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

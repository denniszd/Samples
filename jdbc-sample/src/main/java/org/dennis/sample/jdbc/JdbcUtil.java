package org.dennis.sample.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-12 10:45
 */
public class JdbcUtil {
    /**
     * 获取数据库连接
     *
     * @return Connection实例
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "dennisadmin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param connection Connection实例
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

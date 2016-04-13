package org.dennis.sample.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 用户DAO
 *
 * @author deng.zhang
 * @since 1.0.0 2016-04-12 10:52
 */
public class UserDao {

    /**
     * 保存用户
     *
     * @param user User实例
     * @return 用户ID
     */
    public int save(User user) {
        if (user == null) {
            System.out.println("保存失败，参数为null");
            return -1;
        }
        //数据库连接
        Connection connection = JdbcUtil.getConnection();
        if (connection == null) {
            System.out.println("保存失败，数据库连接为null");
            return -1;
        }
        int id = 0;
        String sql = "INSERT INTO t_user(username,gender,birthday,password,email) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getGender());
            if (user.getBirthday() != null) {
                preparedStatement.setDate(3, new Date(user.getBirthday().getTime()));
            } else {
                preparedStatement.setDate(3, null);
            }
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());

            preparedStatement.executeUpdate();
            //检索执行语句时所自动生成的所有键
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeConnection(connection);
        }
        return id;
    }

    /**
     * 根据ID删除用户
     *
     * @param id 用户ID
     * @return 删除的行数
     */
    public int delete(int id) {
        return 0;
    }

    /**
     * 修改用户
     *
     * @param user User实例
     * @return 修改的行数
     */
    public int update(User user) {
        return 0;
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 若指定ID的用户存在，则返回User实例，否则返回null
     */
    public User query(int id) {
        return null;
    }

    /**
     * 验证用户
     *
     * @param userName 用户名
     * @param password 密码
     * @return 若存在指定用户名和密码的用户，则返回true，否则返回false
     */
    public boolean verify(String userName, String password) {
        //数据库连接
        Connection connection = JdbcUtil.getConnection();
        if (connection == null) {
            System.out.println("查询失败，数据库连接为null");
            return false;
        }
        int count = 0;
        String sql = "SELECT COUNT(id) FROM t_user WHERE username = ? AND password = ?";
        try {
//            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, userName);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) FROM t_user WHERE username='" + userName + "' AND password='" + password + "'");

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeConnection(connection);
        }

        return count > 0;
    }
}
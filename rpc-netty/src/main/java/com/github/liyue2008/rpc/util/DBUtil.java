package com.github.liyue2008.rpc.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhangxuelei1
 * @date: 2020/5/8 11:07
 */
public class DBUtil {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改数据
     */
    public static boolean update(String sql, Object... param) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 1; i <= param.length; i++) {
                preparedStatement.setObject(i, param[i - 1]);
            }
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询数据
     */
    public static List<URI> select(String sql) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql)) {
            List<URI> uriList = new ArrayList<>();
            while (rs.next()) {
                String host = rs.getString(1);
                Integer port = rs.getInt(2);
                URI uri = URI.create("rpc://" + host + ":" + port);
                uriList.add(uri);
            }
            return uriList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.tang.util;

import com.tang.Constants;
import com.tang.exception.DBException;

import java.sql.*;

public final class DBUtil {

    static {
        try {
            Class.forName(PropertyUtil.getProperties(Constants.DRIVER));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    PropertyUtil.getProperties(Constants.URL),
                    PropertyUtil.getProperties(Constants.ROOT),
                    PropertyUtil.getProperties(Constants.ROOT_PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void setAutoCommit (Connection con, boolean isAutoCommit) {

        try {
            con.setAutoCommit(isAutoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }

    public static void rollback (Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }

    public static void commit (Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }
    }

    public static void close(Connection con, ResultSet rs,
            Statement pstm) {
        try {
            if (con != null) {
                con.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}

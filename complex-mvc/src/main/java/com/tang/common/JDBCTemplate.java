package com.tang.common;

import com.tang.exception.DBException;
import com.tang.model.QuestionOption;
import com.tang.util.DBUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class JDBCTemplate<T> {

    public static int numberCount = 0;

    public List<T> query(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            jdbcCallback.setParams(pstm);
            rs = pstm.executeQuery();
            while (rs.next()) {
                T object = jdbcCallback.rsToObject(rs);
                list.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return list;
    }

    public T queryOne(String sql, JDBCCallback<T> jdbcCallback) {
        List<T> list = this.query(sql, jdbcCallback);
        if (!list.isEmpty() && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public int count(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int totalCount = -1;

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            jdbcCallback.setParams(pstm);
            rs = pstm.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return totalCount;
    }

    public int insert(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int id = -1;

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            jdbcCallback.setParams(pstm);
            pstm.executeUpdate();
            //Get the id of record
            rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return id;
    }

    public int[] insert(String sql, JDBCCallback<T> jdbcCallback, QuestionOption[] questionOptions) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int ids[] = new int[questionOptions.length];

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < questionOptions.length; i++) {
                numberCount = i;
                jdbcCallback.setParams(pstm);
                pstm.executeUpdate();
                //Get the id of record
                rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    ids[i] = rs.getInt(1);
                }
            }
            numberCount = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return ids;
    }

    public int update(String sql, JDBCCallback<T> jdbcCallback, QuestionOption[] questionOptions) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            for (int i = 0; i < questionOptions.length; i++) {
                numberCount = i;
                jdbcCallback.setParams(pstm);
                pstm.executeUpdate();
                count++;
            }
            numberCount = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return count;
    }

    public int update(String sql, JDBCCallback<T> jdbcCallback) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = -1;

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            jdbcCallback.setParams(pstm);
            count = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return count;
    }

}

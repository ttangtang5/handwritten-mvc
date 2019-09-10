package com.tang.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCAbstractTemplate<T> implements JDBCCallback<T> {

    @Override
    public T rsToObject(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public void setParams(PreparedStatement pstm) throws SQLException {
    }

}

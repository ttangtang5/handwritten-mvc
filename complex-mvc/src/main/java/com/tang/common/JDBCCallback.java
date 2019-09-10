package com.tang.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JDBCCallback<T> {

    T rsToObject(ResultSet rs) throws SQLException;

    void setParams(PreparedStatement pstm) throws SQLException;

}

package com.tang.dao.impl;

import com.tang.common.JDBCAbstractTemplate;
import com.tang.common.JDBCTemplate;
import com.tang.dao.UserDao;
import com.tang.model.User;
import com.tang.util.StringUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {


    @Override
    public User getUserByUserName(final String userName) {
        if (StringUtil.checkString(userName)) {
            return null;
        }

        String sql = "SELECT * FROM user WHERE user_name = ?";

        JDBCTemplate<User> jdbcTemplate = new JDBCTemplate<User>();
        return jdbcTemplate.queryOne(sql, new JDBCAbstractTemplate<User>() {
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, userName);
            }
            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setDisplayName(rs.getString("display_name"));
                user.setGender(rs.getString("gender"));
                user.setTelephoneNumber(rs.getString("telephone_number"));
                user.setEmail(rs.getString("email"));
                user.setPhoto(rs.getString("photo"));
                user.setCreateTime(rs.getDate("create_time"));
                user.setUpdateTime(rs.getDate("update_time"));
                user.setCreateBy(rs.getInt("create_by"));
                user.setUpdateBy(rs.getInt("update_by"));
                user.setFlag(rs.getString("flag"));
                return user;
            }
        });
    }
}

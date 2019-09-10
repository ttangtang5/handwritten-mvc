package com.tang.dao.impl;

import com.tang.common.AppContext;
import com.tang.common.JDBCAbstractTemplate;
import com.tang.common.JDBCCallback;
import com.tang.common.JDBCTemplate;
import com.tang.dao.QuestionDao;
import com.tang.exception.DBException;
import com.tang.model.Pagination;
import com.tang.model.Question;
import com.tang.model.QuestionOption;
import com.tang.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDaoImpl implements QuestionDao {

    @Override
    public int createQuestion(final Question question) {

        String sql = "INSERT INTO question("
                + "question_description, question_score, "
                + "create_by,create_time, "
                + "display_id) "
                + "VALUES(?, ?, ?, NOW(), ?)";

        JDBCTemplate<Integer> jdbcTemplate = new JDBCTemplate<Integer>();
        return jdbcTemplate.insert(sql, new JDBCAbstractTemplate<Integer>() {
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, question.getQuestionDescription());
                pstm.setDouble(2, question.getQuestionScore());
                pstm.setInt(3, question.getCreateBy());
                pstm.setString(4, question.getDisplayId());
            }
        });
    }

    public int getQuestionCount(final String condition) {
        String sql = "SELECT COUNT(*) FROM question WHERE flag = ?";
        if (condition != null) {
            sql += "AND question_description LIKE ?";
        }
        JDBCTemplate<Integer> jdbcTemplate = new JDBCTemplate<Integer>();
        return jdbcTemplate.count(sql, new JDBCAbstractTemplate<Integer>() {
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, "T");
                if (condition != null) {
                    pstm.setString(2, "%"+condition+"%");
                }
            }
        });
    }

    @Override
    public List<Question> findQuestionList(final Pagination pagination, boolean isASC) {

        List<Question> questionList = null;
        JDBCTemplate<Question> jDBCTemplate = new JDBCTemplate<Question>();

        String type = isASC ? "ASC" : "DESC";
        String sql = "SELECT * FROM question WHERE flag = ? ORDER BY id " + type + " LIMIT ?,?";

        int totalCount = this.getQuestionCount(null);
        pagination.setTotalCount(totalCount);

        //Deal with the currentPage parameter in here. The
        //If the currentPage > pageCount ==> currentPage = pageCount
        if (pagination.getPageCount() < pagination.getCurrentPage()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }
        //If the currentPage <= 0 ==> currentPage = 1
        if (pagination.getCurrentPage() <= 0) {
            pagination.setCurrentPage(1);
        }

        questionList = jDBCTemplate.query(sql, new JDBCCallback<Question>() {
            @Override
            public Question rsToObject(ResultSet rs) throws SQLException {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestionDescription(rs.getString("question_description"));
                question.setQuestionScore(rs.getDouble("question_score"));
                question.setCreateBy(rs.getInt("create_by"));
                question.setUpdateBy(rs.getInt("update_by"));
                question.setCreateTime(rs.getDate("create_time"));
                question.setUpdateTime(rs.getDate("update_time"));
                question.setFlag(rs.getString("flag"));
                question.setDisplayId(rs.getString("display_id"));
                return question;
            }

            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, "T");
                pstm.setInt(2, pagination.getOffset());
                pstm.setInt(3, pagination.getPageSize());
            }
        });

        return questionList;
    }

    @Override
    public boolean deleteQuestionByIds(int[] ids) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        String sql = "UPDATE question SET flag = ? WHERE id in (";
        for (int i = 0;i < ids.length;i++) {
            sql += "?,";
        }
        sql = sql.substring(0, sql.lastIndexOf(",")) + ")";

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            pstm.setString(1, "F");
            for (int i = 0;i < ids.length;i++) {
                pstm.setInt(i+2, ids[i]);
            }
            if (pstm.executeUpdate()>=1) {
                return true;
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
        return false;
    }

    @Override
    public List<Question> findQuestionListByDescription(Pagination pagination, String condition, boolean isASC) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Question question = null;
        List<Question> questionList = new ArrayList<Question>();

        String type = isASC ? "ASC" : "DESC";


        String sql = "SELECT * FROM question WHERE flag = 'T' "
                   + "AND question_description LIKE ? ORDER BY id " + type + " LIMIT ?,?";

        int totalCount = this.getQuestionCount(condition);
        pagination.setTotalCount(totalCount);

        //Deal with the currentPage parameter in here.
        //If the currentPage > pageCount ==> currentPage = pageCount
        if (pagination.getPageCount() < pagination.getCurrentPage()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }
        //If the currentPage <= 0 ==> currentPage = 1
        if (pagination.getCurrentPage() <= 0) {
            pagination.setCurrentPage(1);
        }

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            pstm.setString(1, "%" + condition + "%");
            pstm.setInt(2, pagination.getOffset());
            pstm.setInt(3, pagination.getPageSize());
            rs = pstm.executeQuery();
            while (rs.next()) {
                question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestionDescription(rs.getString("question_description"));
                question.setQuestionScore(rs.getDouble("question_score"));
                question.setCreateBy(rs.getInt("create_by"));
                question.setUpdateBy(rs.getInt("update_by"));
                question.setCreateTime(rs.getDate("create_time"));
                question.setUpdateTime(rs.getDate("update_time"));
                question.setFlag(rs.getString("flag"));
                question.setDisplayId(rs.getString("display_id"));
                questionList.add(question);
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
        return questionList;
    }

    @Override
    public int updateQuestion(final Question question) {

        String sql = "UPDATE question SET update_by =? , "
                   + "question_description = ? , update_time = now(),"
                   + "display_id = ? WHERE id = ?;";

        JDBCTemplate<Integer> jdbcTemplate = new JDBCTemplate<Integer>();
        return jdbcTemplate.update(sql, new JDBCAbstractTemplate<Integer>() {
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setInt(1, question.getUpdateBy());
                pstm.setString(2, question.getQuestionDescription());
                pstm.setString(3, question.getDisplayId());
                pstm.setInt(4, question.getId());
            }
        });
    }


    @Override
    public int updateQuestionOption(final QuestionOption[] questionOptions) {
        String sql = "UPDATE question_option SET question_option = ? "
                   + ",answer = ? WHERE id = ?";

        JDBCTemplate<Integer> jdbcTemplate = new JDBCTemplate<Integer>();
        return jdbcTemplate.update(sql, new JDBCAbstractTemplate<Integer>(){
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, questionOptions[JDBCTemplate.numberCount].getQuestionOption());
                pstm.setString(2, questionOptions[JDBCTemplate.numberCount].getAnswer());
                pstm.setInt(3, questionOptions[JDBCTemplate.numberCount].getQuestionId());
            }
        }, questionOptions);
    }

    @Override
    public int[] createQuestionOption(final QuestionOption[] questionOptions) {
        String sql = "INSERT INTO question_option("
                + "question_option, question_index, answer, question_id)"
                + "VALUES(?, ?, ?, ?)";

        JDBCTemplate<Integer[]> jdbcTemplate = new JDBCTemplate<Integer[]>();
        return jdbcTemplate.insert(sql, new JDBCAbstractTemplate<Integer[]>(){
            @Override
            public void setParams(PreparedStatement pstm) throws SQLException {
                pstm.setString(1, questionOptions[JDBCTemplate.numberCount].getQuestionOption());
                pstm.setInt(2, questionOptions[JDBCTemplate.numberCount].getQuestionIndex());
                pstm.setString(3, questionOptions[JDBCTemplate.numberCount].getAnswer());
                pstm.setInt(4, questionOptions[JDBCTemplate.numberCount].getQuestionId());
            }
        }, questionOptions);
    }

    @Override
    public Map<Object,Object> findQuestionOptionById(int questionId) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        QuestionOption questionOption = null;
        Map<Object,Object> questionOptionMap = new HashMap<Object,Object>();
        List<QuestionOption> questionOptionList = new ArrayList<QuestionOption>();

        String sql = "SELECT * FROM question_option qo , question q "
                + "WHERE q.id = qo.question_id AND qo.question_id = "+questionId;

        boolean isNeedClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                isNeedClose = true;
            }
            pstm = (PreparedStatement) con.prepareStatement(sql);
            rs = pstm.executeQuery();
            boolean judge = true;
            while (rs.next()) {
                questionOption = new QuestionOption();
                questionOption.setId(rs.getInt("id"));
                questionOption.setQuestionOption(rs.getString("question_option"));
                questionOption.setQuestionId(rs.getInt("question_id"));
                questionOption.setQuestionIndex(rs.getInt("question_index"));
                questionOption.setAnswer(rs.getString("answer"));
                questionOptionList.add(questionOption);
                questionOptionMap.put("questionDescription", rs.getString("question_description"));
                if (judge) {
                    questionOptionMap.put("questionDisplayId", rs.getString("display_id"));
                    judge = false;
                }
            }
            questionOptionMap.put("questionOptionList", questionOptionList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            DBUtil.close(null, rs, pstm);
            if (isNeedClose) {
                DBUtil.close(con, null, null);
            }
        }
        return questionOptionMap;
    }
}

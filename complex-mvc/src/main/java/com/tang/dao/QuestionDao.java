 package com.tang.dao;

 import com.tang.model.Pagination;
 import com.tang.model.Question;
 import com.tang.model.QuestionOption;
 import java.util.List;
 import java.util.Map;

 public interface QuestionDao {

     int createQuestion(Question question);

     List<Question> findQuestionList(Pagination pagination, boolean isASC);

     boolean deleteQuestionByIds(int ids[]);

     List<Question> findQuestionListByDescription(Pagination pagination, String condition, boolean isASC);

     int updateQuestion(Question question);

     int[] createQuestionOption(QuestionOption[] questionOptions);

     Map<Object,Object> findQuestionOptionById(int questionId);

     int updateQuestionOption(QuestionOption[] questionOptions);
 }

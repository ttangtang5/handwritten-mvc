package com.tang.service;

import com.tang.exception.ParameterException;
import com.tang.model.Pagination;
import com.tang.model.Question;
import com.tang.model.QuestionOption;
import java.util.List;
import java.util.Map;

public interface QuestionService {

    public int addQuestion(Question question, QuestionOption[] questionOptions) throws ParameterException;

    public List<Question> showAllQuestion(Pagination pagination, boolean isASC);

    public boolean deleteQuestionByIds(int ids[]);

    public List<Question> findQuestionListByDescription(Pagination pagination, String condition, boolean isASC);

    public Map<Object,Object> findQuestionOptionById(int questionId);

    public int updateQuestionOption(QuestionOption[] questionOptions, Question question) throws ParameterException;

}

package com.tang.service.impl;

import com.tang.dao.QuestionDao;
import com.tang.exception.ParameterException;
import com.tang.model.Pagination;
import com.tang.model.Question;
import com.tang.model.QuestionOption;
import com.tang.service.QuestionService;
import com.tang.util.StringUtil;

import java.util.List;
import java.util.Map;

public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public int addQuestion(Question question, QuestionOption[] questionOptions) throws ParameterException {
        ParameterException parameterException = new ParameterException();

        String displayId = question.getDisplayId();
        if (StringUtil.checkString(displayId)) {
            parameterException.addErrorFields("displayIdIsNull", "The displayId is null");
        }
        String description = question.getQuestionDescription();
        if (StringUtil.checkString(description)) {
            parameterException.addErrorFields("descriptionIsNull", "The description is null");
        }
        if (!parameterException.isEmpty()) {
            throw parameterException;
        }

        question.setDisplayId(StringUtil.replaceAll(StringUtil.replaceAll(displayId, "<", "&lt"), ">", "&gt"));

        question.setQuestionDescription(StringUtil.replaceAll(StringUtil.replaceAll(description, "<", "&lt"), ">", "&gt"));

        int key = questionDao.createQuestion(question);
        for (int i = 0; i < questionOptions.length; i++) {
            questionOptions[i].setQuestionIndex(key);
            questionOptions[i].setQuestionId(key);
            String questionOption = questionOptions[i].getQuestionOption();
            String answer = questionOptions[i].getAnswer();
            if (StringUtil.checkString(answer)) {
                parameterException.addErrorFields("answerIsNull", "The answer is null");
            }

            if (StringUtil.checkString(questionOption)) {
                parameterException.addErrorFields("questionOptionIsNull", "The questionOption is null");
            }
            questionOptions[i].setQuestionOption(StringUtil.replaceAll(StringUtil.replaceAll(questionOption, "<", "&lt"), ">", "&gt"));

            questionOptions[i].setAnswer(StringUtil.replaceAll(StringUtil.replaceAll(answer, "<", "&lt"), ">", "&gt"));
        }
        if (!parameterException.isEmpty()) {
            throw parameterException;
        }

        questionDao.createQuestionOption(questionOptions);
        return key;

    }

    @Override
    public List<Question> showAllQuestion(Pagination pagination, boolean isASC) {
        return questionDao.findQuestionList(pagination, isASC);
    }

    @Override
    public boolean deleteQuestionByIds(int[] ids) {
        return questionDao.deleteQuestionByIds(ids);
    }

    @Override
    public List<Question> findQuestionListByDescription(Pagination pagination,
            String condition, boolean isASC) {
        return questionDao.findQuestionListByDescription(pagination, condition, isASC);
    }

    @Override
    public int updateQuestionOption(QuestionOption[] questionOptions, Question question) throws ParameterException {
        ParameterException parameterException = new ParameterException();
        for (int i = 0; i < questionOptions.length; i++) {
            if (StringUtil.checkString(questionOptions[i].getQuestionIndex().toString())) {
                parameterException.addErrorFields("questionIndexError", "questionDBID is null");
            }

            if (StringUtil.checkString(questionOptions[i].getId().toString())) {
                parameterException.addErrorFields("optionAIDError", "optionID is null");
            }
            String questionOption = questionOptions[i].getQuestionOption();
            if (StringUtil.checkString(questionOption)) {
                parameterException.addErrorFields("questionOptionError", "questionOption is null");
            }

            String answer = questionOptions[i].getAnswer();
            if (StringUtil.checkString(answer)) {
                parameterException.addErrorFields("answerIsNull", "The answer is null");
            }

            if (!parameterException.isEmpty()) {
                throw parameterException;
            }

            questionOptions[i].setQuestionOption(StringUtil.replaceAll(StringUtil.replaceAll(questionOption, "<", "&lt"), ">", "&gt"));

            questionOptions[i].setAnswer(StringUtil.replaceAll(StringUtil.replaceAll(answer, "<", "&lt"), ">", "&gt"));
        }
        String displayId = question.getDisplayId();
        if (StringUtil.checkString(displayId)) {
            parameterException.addErrorFields("displayIdIsNull", "The displayId is null");
        }
        String description = question.getQuestionDescription();
        if (StringUtil.checkString(description)) {
            parameterException.addErrorFields("descriptionIsNull", "The description is null");
        }
        if (!parameterException.isEmpty()) {
            throw parameterException;
        }
        question.setDisplayId(StringUtil.replaceAll(StringUtil.replaceAll(displayId, "<", "&lt"), ">", "&gt"));

        question.setQuestionDescription(StringUtil.replaceAll(StringUtil.replaceAll(description, "<", "&lt"), ">", "&gt"));
        questionDao.updateQuestionOption(questionOptions);
        return questionDao.updateQuestion(question);
    }

    @Override
    public Map<Object,Object> findQuestionOptionById(int questionId) {
        return questionDao.findQuestionOptionById(questionId);
    }
}

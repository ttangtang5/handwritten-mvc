package com.tang.controller;

import com.tang.Constants;
import com.tang.common.ModelAndView;
import com.tang.exception.ParameterException;
import com.tang.model.Pagination;
import com.tang.model.Question;
import com.tang.model.QuestionOption;
import com.tang.model.User;
import com.tang.service.QuestionService;
import com.tang.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionController {

    private QuestionService questionService ;

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public ModelAndView createQuestion(Map<String, Object> sessionsMap,
                                       Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            User user = (User) sessionsMap.get(Constants.USER);
            String displayId = requestMap.get("questionId");
            modelAndView.setRequestAttribute("displayId", displayId);
            String answerA = requestMap.get("answerA");
            modelAndView.setRequestAttribute("answerA", answerA);
            String questionTitle = requestMap.get("question");
            modelAndView.setRequestAttribute("question", questionTitle);
            String answerB = requestMap.get("answerB");
            modelAndView.setRequestAttribute("answerB", answerB);
            String answerC = requestMap.get("answerC");
            modelAndView.setRequestAttribute("answerC", answerC);
            String answerD = requestMap.get("answerD");
            modelAndView.setRequestAttribute("answerD", answerD);
            String answer_radio = requestMap.get("answer_radio");
            modelAndView.setRequestAttribute("answer_radio", answer_radio);
            Question question = new Question();
            QuestionOption[] questionOptions = new QuestionOption[4];
            //TODO Could conside Transaction management
            //Add question object attribute
            question.setQuestionDescription(questionTitle);
            question.setQuestionScore(4.0);
            question.setCreateBy(user.getId());
            question.setDisplayId(displayId);
            //Add questionOption object attribute
            String answer = null;
            for (int i = 0; i < 4;i++) {
                QuestionOption questionOption = new QuestionOption();
                switch (i) {
                case 0: questionOption.setQuestionOption(answerA);
                        answer = "answerA";
                        break;
                case 1:
                        questionOption.setQuestionOption(answerB);
                        answer = "answerB";
                        break;
                case 2:
                        questionOption.setQuestionOption(answerC);
                        answer = "answerC";
                        break;
                default:
                        questionOption.setQuestionOption(answerD);
                        answer = "answerD";
                        break;
                }
                if (answer.indexOf(answer_radio) != -1) {
                    questionOption.setAnswer("T");
                } else {
                    questionOption.setAnswer("F");
                }
                questionOptions[i] = questionOption;
            }
            questionService.addQuestion(question, questionOptions);
            modelAndView.setSessionAttribute("addQuestionSuccess", "addQuestionSuccess");
            Pagination pagination = getPagination(sessionsMap, requestMap);
            boolean isASC = false;
            if ("isASC".equals(requestMap.get("isASC"))) {
                isASC = true;
            }
            List<Question> questionList = questionService.showAllQuestion(pagination, isASC);
            modelAndView.setRequestAttribute(Constants.QUESTIONLIST, questionList);
            modelAndView.setSessionAttribute("pagination", pagination);
            modelAndView.setRedirect(false);
            modelAndView.setView("success");
            return modelAndView;
        } catch (ParameterException parameterException) {
            Map<String,Object> errorFields = parameterException.getErrorFields();
            modelAndView.setSessionAttribute("createQuestionMsg", "createQuestionFailed");
            modelAndView.setRequestAttribute(Constants.ERROR_MESSAGE, errorFields);
            modelAndView.setRedirect(false);
            modelAndView.setView("error");
            return modelAndView;
        }
    }

    public ModelAndView showQuestion(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        String action = requestMap.get("action");
        ModelAndView modelAndView = new ModelAndView();
        if ("create".equals(action)) {
            modelAndView.setRedirect(false);
            modelAndView.setView("createQuestionPage");
            return modelAndView;
        }
        Pagination pagination = getPagination(sessionsMap, requestMap);
        boolean isASC = false; //Default order by DESC
        if ("ASC".equals(requestMap.get("isASC"))) {
            isASC = true;
        }
        List<Question> questionList = questionService.showAllQuestion(pagination, isASC);
        modelAndView.setRequestAttribute(Constants.QUESTIONLIST, questionList);
        modelAndView.setSessionAttribute("pagination", pagination);
        modelAndView.setRequestAttribute("isASC", isASC);
        modelAndView.setRedirect(false);
        modelAndView.setView("/WEB-INF/jsp/content_admin.jsp");
        return modelAndView;
    }

    public ModelAndView deleteQuestion(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        String id = requestMap.get("ids");
        String[] ids = id.split(",");
        int[] idArray = new int[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idArray[i] = Integer.parseInt(ids[i]);
        }
        questionService.deleteQuestionByIds(idArray);
        modelAndView.setSessionAttribute("addQuestionSuccess", "Delete Success");
        Pagination pagination = (Pagination)sessionsMap.get("pagination");
        if (pagination != null) {
            modelAndView.setRequestAttribute("pageSize", pagination.getPageSize());
        } else {
            pagination = getPagination(sessionsMap, requestMap);
        }
        boolean isASC = false;
        if ("isASC".equals(requestMap.get("isASC"))) {
            isASC = true;
        }

        List<Question> questionList = questionService.showAllQuestion(pagination, isASC);
        modelAndView.setRequestAttribute(Constants.QUESTIONLIST, questionList);
        modelAndView.setSessionAttribute("pagination", pagination);
        modelAndView.setRedirect(false);
        modelAndView.setView("success");
        return modelAndView;
    }

    public ModelAndView findQuestionListByDescription(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        Pagination pagination = getPagination(sessionsMap, requestMap);
        String condition = requestMap.get("formKeyWord");
        modelAndView.setRequestAttribute("formKeyWord", condition);
        boolean isASC = false; //Default order by DESC
        if ("ASC".equals(requestMap.get("isASC"))) {
            isASC = true;
        }
        List<Question> questionList = questionService.findQuestionListByDescription(pagination, condition, isASC);
        modelAndView.setRequestAttribute(Constants.QUESTIONLIST, questionList);
        modelAndView.setRequestAttribute("isASC", isASC);
        modelAndView.setSessionAttribute("pagination", pagination);
        modelAndView.setRedirect(false);
        modelAndView.setView("show");
        return modelAndView;
    }

    //TODO 更新问题选项详情
    public ModelAndView updateQuestionOption(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        //The question's display Id
        String display_Id = requestMap.get("questionId");
        //The question's index
        String questionIndex = requestMap.get("questionDBID");
        //The question's description
        String questionTitle = requestMap.get("question");
        //The questionOption's database ID
        String optionAID = requestMap.get("optionAID");
        String optionBID = requestMap.get("optionBID");
        String optionCID = requestMap.get("optionCID");
        String optionDID = requestMap.get("optionDID");
        //The question's option content
        String answerA = requestMap.get("answerA");
        String answerB = requestMap.get("answerB");
        String answerC = requestMap.get("answerC");
        String answerD = requestMap.get("answerD");
        //The quetsion's answer
        String answerRadio = requestMap.get("answer_radio");

        QuestionOption[] questionOptions = new QuestionOption[4];

        try {
            //The variable save the name of option.
            String answer = null;
            QuestionOption questionOption = null;
            for (int i = 0; i < 4; i++) {
                questionOption = new QuestionOption();
                switch (i) {
                case 0: questionOption.setId(Integer.parseInt(optionAID));
                        questionOption.setQuestionOption(answerA);
                        answer = "answerA";
                        break;
                case 1: questionOption.setId(Integer.parseInt(optionBID));
                        questionOption.setQuestionOption(answerB);
                        answer = "answerB";
                        break;
                case 2: questionOption.setId(Integer.parseInt(optionCID));
                        questionOption.setQuestionOption(answerC);
                        answer = "answerC";
                        break;
                default: questionOption.setId(Integer.parseInt(optionDID));
                        questionOption.setQuestionOption(answerD);
                        answer = "answerD";
                        break;
                }
                //If current answer include the answer_radio's value, this is a answer
                if (answer.indexOf(answerRadio) != -1) {
                    questionOption.setAnswer("T");
                } else {
                    questionOption.setAnswer("F");
                }
                questionOption.setQuestionId(Integer.parseInt(questionIndex));
                questionOption.setQuestionIndex(Integer.parseInt(questionIndex));
                questionOptions[i] = questionOption;
            }

            requestMap.put("questionDescription", questionTitle);
            requestMap.put("display_Id", display_Id);
            requestMap.put("questionIndex", questionIndex);
            User user = (User) sessionsMap.get(Constants.USER);
            Question question = new Question();
            question.setUpdateBy(user.getId());
            question.setDisplayId(display_Id);
            question.setQuestionDescription(questionTitle);
            question.setId(Integer.parseInt(questionIndex));
            questionService.updateQuestionOption(questionOptions, question);
            modelAndView.setView("success");
            modelAndView.setRedirect(false);
            return modelAndView;
        } catch (ParameterException parameterExcpetion) {
            modelAndView.setSessionAttribute("questionId", questionIndex);
            //modelAndView.setRedirect(true);
            modelAndView.setView("errorupdateQuestionOption");
            return modelAndView;
        }

    }

    public Pagination getPagination(Map<String, Object> sessionsMap, Map<String, String> requestMap) {
        Pagination pagination = new Pagination();
        String currentPageStr = requestMap.get("currentPage");
        String pageSizeStr = requestMap.get("pageSize");
        if (pageSizeStr == null) {
            Integer pageSize = (Integer)sessionsMap.get("pageSize");
            if (pageSize != null) {
                pageSizeStr =  String.valueOf(pageSize);
            }
        }
        //If user sets the current page, change the current page by user.
        if (!StringUtil.checkString(currentPageStr)) {
            int currentPage = Integer.parseInt(currentPageStr);
            pagination.setCurrentPage(currentPage);
        } else {//Use default.
            pagination.setCurrentPage(1);
        }
        //If user don't sets the pagesize, useing default.
        if (!StringUtil.checkString(pageSizeStr)) {
            int pageSize = Integer.parseInt(pageSizeStr);
            pagination.setPageSize(pageSize);
        }
        if (pageSizeStr != null) {
            pagination.setPageSize(Integer.parseInt(pageSizeStr));
        }
        return pagination;
    }

    public ModelAndView update(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        String id = requestMap.get("questionId");
        int questionId = -1;
        if (id == null) {
            id = (String) sessionsMap.get("questionId");
        }
        questionId = Integer.parseInt(id);

        Map<Object,Object> questionOptionMap = questionService.findQuestionOptionById(questionId);
        @SuppressWarnings("unchecked")
        ArrayList<QuestionOption> list = (ArrayList<QuestionOption>) questionOptionMap.get("questionOptionList");
        modelAndView.setRequestAttribute("questionOptionList", list);
        modelAndView.setRequestAttribute("questionOptionMap", questionOptionMap);

        //Only view the question detail
        if ("viewQuestionDetail".equals(requestMap.get("action"))) {
            modelAndView.setRedirect(false);
            modelAndView.setView("questionDetail");
            return modelAndView;
        }

        modelAndView.setRedirect(false);
        modelAndView.setView("questionOptionDetail");
        return modelAndView;
    }
}

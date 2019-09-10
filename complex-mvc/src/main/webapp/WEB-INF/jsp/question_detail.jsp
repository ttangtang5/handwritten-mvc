<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>The detail of question</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/content_admin.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/question_detail.css" type="text/css" />
</head>
<script  type="text/javascript" src="<%=request.getContextPath()%>/static/js/question_detail.js">
</script>
<body>
  <div class="boss">   
  
    <div class="contentAdmin_head">
      <div class="contentAdmin_head_company_logo">
      </div>
      
      <div class="contentAdmin_head_OnlineExamWebSystem">
        Online Exam Web System
      </div>
    
      <div class="contentAdmin_head_right">
        <div class="contentAdmin_head_right_headLogo">
          
          <div class="contentAdmin_head_right_username">
            <span id="contentAdmin_head_right_username_span">${user.userName}</span>
          </div>
          
          <div class="contentAdmin_head_right_logout">
            <a class ="contentAdmin_head_right_logout_a" href="logout.action?action=logout" style="text-decoration:none" >Logout</a>
          </div>
        </div>
      </div>
      
      <div class="contentAdmin_head_language">
        Chinese
      </div>
    </div>
    
    <!-- content body begin! -->
    <div class="contentAdmin_body">
      <div id="question" class="contentAdmin_body_questionManeger" style="background:#FFF">
        <a class="contentAdmin_body_questionManeger_a" href="question.action">Question Management</a>
      </div>
      
      <div id="exam" class="contentAdmin_body_examManeger">
        <a class="contentAdmin_body_examManeger_a" href="javascript:changeDiv('exam')">Exam Management</a>
      </div>
    </div>
  </div>
  
  <div id="questionAndExamManagement" class="contentAdmin_foot">
    <div class="navigation">
      <label >
        Exam Management
      </label>
      
      <label>
        &gt;
      </label>
      
      <label>
        Question List
      </label>
      
      <label>
        &gt;
      </label>
      
      <label>
        ${questionOptionMap.get("questionDisplayId")}
        ${questionOptionMap.get("id")}
      </label>
    </div>
    
    <div class="contentAdmin_createquestion_foot_content">
        <form id="editQuestionForm" method="post">
          <div class="question_id">
            <label>Question ID:</label>
            <input type="hidden" id="questionDBID" name="questionDBID" value="${questionOptionList[0].questionIndex}"/>
            <label>${questionOptionMap.get('questionDisplayId')}</label>
          </div>
          
          <div class="question">
            <label>Question:</label>
            <div class="description">
              <label >${questionOptionMap.get("questionDescription")}</label>
            </div>
          </div>
          
          <div class="answer">
            <label class="Answer" >Answer:</label>
            <div class="description">
	            <div>
	              <label class="option" style="${questionOptionList[0].answer=='T'?'background-color:red':''}">A ${questionOptionList[0].questionOption}</label>
	              <input type="hidden" name="optionAID" value="${questionOptionList[0].id}" />
	            </div>
	            
	            <div>
	              <label class="option" style="${questionOptionList[1].answer=='T'?'background-color:red':''}">B ${questionOptionList[1].questionOption}</label>
	              <input type="hidden" name="optionBID" value="${questionOptionList[1].id}" />
	            </div>
	            
	            <div>
	              <label class="option" style="${questionOptionList[2].answer=='T'?'background-color:red':''}">C ${questionOptionList[2].questionOption}</label>
	              <input type="hidden" name="optionCID" value="${questionOptionList[2].id}" />
	            </div>
	            
	            <div>
	              <label class="option" style="${questionOptionList[3].answer=='T'?'background-color:red':''}">D ${questionOptionList[3].questionOption}</label>
	              <input type="hidden" name="optionDID" value="${questionOptionList[3].id}" />
	            </div>
	            <input type="hidden" name="action" value="createQuestion"/>
            </div>
          </div>
        </form>
            
        <div class="manager">
          <img id="editImage" src="<%=request.getContextPath()%>/static/images/BTN_Edit_30x150.png" onclick="editQuestion()" >
          <img src="<%=request.getContextPath()%>/static/images/BTN_Cancel_30x150.png" onclick="goBack()">
        </div>
        
    </div>
    
  </div>
  
</body>
</html>
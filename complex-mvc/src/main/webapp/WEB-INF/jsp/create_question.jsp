<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create question</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/content_admin.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/create_question.css" type="text/css" />
</head>
<script  type="text/javascript" src="<%=request.getContextPath()%>/static/js/create_question.js">
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
      
       <div id="createQuestionMsg" style="display:none">
         <label>${createQuestionMsg}</label>
         <input type="hidden" value="${createQuestionMsg}" id="isCreateQuestion" name="isCreateQuestion" />
         <% session.removeAttribute("createQuestionMsg"); %>
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
        Create Question
      </label>
    </div>
    
    <div class="contentAdmin_createquestion_foot_content">
        <form id="questionForm" action="createQuestion.action" method="post">
          <div class="question_id">
            <label>Question ID:</label>
            <input id="text" value="${displayId}" type="text" name="questionId" /><span id="questionIdError"></span>
          </div>
          
          <div class="question">
            <label>Question:</label>
            <textarea name="question">${question}</textarea><span id="questionError"></span>
          </div>
          
          <div class="answer">
            <label class="Answer">Answer:</label>
            
            <div>
              <input ${answer_radio=='A'?'checked':''} value="A" id="radio" type="radio" name="answer_radio" />
              <label class="option">A</label>
              <input value="${answerA}" id="text" type="text" name="answerA"/><span id="answerAerror"></span>
            </div>
            
            <div>
              <input ${answer_radio=='B'?'checked':''} value="B" id="radio" type="radio" name="answer_radio" />
              <label class="option">B</label>
              <input value="${answerB}" id="text" type="text" name="answerB"/><span id="answerBerror"></span>
            </div>
            
            <div>
              <input ${answer_radio=='C'?'checked':''} value="C" id="radio" type="radio" name="answer_radio" />
              <label class="option">C</label>
              <input value="${answerC}" id="text" type="text" name="answerC"/><span id="answerCerror"></span>
            </div>
            
            <div>
              <input ${answer_radio=='D'?'checked':''} value="D" id="radio" type="radio" name="answer_radio" />
              <label class="option">D</label>
              <input value="${answerD}" id="text" type="text" name="answerD"/><span id="answerDerror"></span>
            </div>
            <input type="hidden" name="action" value="createQuestion"/>
            
          </div>
        </form>
        
            
        <div class="manager">
          <img src="<%=request.getContextPath()%>/static/images/BTN_Create_30x150.png" onclick="createQuestion()" >
          <img src="<%=request.getContextPath()%>/static/images/BTN_Cancel_30x150.png" onclick="goBack()">
        </div>
        
    </div>
    
  </div>
  
</body>
</html>
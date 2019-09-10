<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/content_admin.css" type="text/css" />
  <title>Content Admin Page</title>
  </head>
  <script  type="text/javascript" src="<%=request.getContextPath()%>/static/js/content_admin.js">
  </script>
<body>
  <div id="sureDelete">
    <label class="deleteLabel">Are you sure delete the question? </label>
      <img src="<%=request.getContextPath()%>/static/images/BTN_Pop_Yes_30x80.png" onclick="checkDelete('sure')">
      <img src="<%=request.getContextPath()%>/static/images/BTN_Pop_Cancel_30x80.png" onclick="checkDelete('no')">
  </div>

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
          <span id="contentAdmin_head_right_username_span">${user.displayName}</span>
        </div>
        
        <div class="contentAdmin_head_right_logout">
          <a class ="contentAdmin_head_right_logout_a" href="logout.action?action=logout" style="text-decoration:none" >Logout</a>
        </div>
      </div>
    </div>
    
    <div class="contentAdmin_head_language">
      <label>中文</label>
    </div>
  </div>
  
  <!-- content body begin! -->
  <div class="contentAdmin_body">
    <div id="question" class="contentAdmin_body_questionManeger" >
      <a class="contentAdmin_body_questionManeger_a" href="javascript:changeDiv('question')">Question Management</a>
    </div>
    
    <div id="exam" class="contentAdmin_body_examManeger">
      <a class="contentAdmin_body_examManeger_a" href="javascript:changeDiv('exam')">Exam Management</a>
    </div>
    
    <div id="addQuestionSuccess" style="display:none">
      <label>${addQuestionSuccess}</label>
      <input type="hidden" value="${addQuestionSuccess}" id="isAddSuccess" name="isAddSuccess" />
      <% session.removeAttribute("addQuestionSuccess"); %>
    </div>
    
  </div>
  <!-- content body end! -->
  
  
  <!-- Qestion and Exam DIV begin-->
  <!-- content foot begin! -->
  <div id="questionAndExamManagement" class="contentAdmin_foot">
  
  <!--==================== Question Management DIV end   ====================-->
    <div id="contentAdmin_foot_content_id" class="contentAdmin_foot_content">
     
      <div class="contentAdmin_foot_content_left">
        <div name ="questionManagement" id="questionList" class="contentAdmin_foot_content_left_QuestionList" onmouseover="changeColor('questionList')">
          <a onclick="goToQuestionList()">Qestion List</a>
        </div>
        <div name="questionManagement" id="createQuestion" class="contentAdmin_foot_content_left_CreateQuestion" onmouseover="changeColor('createQuestion')">
          <a onclick="goToCreateQuestion('createQuestion')">Create Question</a>
        </div>
      </div>
      <input type="hidden" name="currentPage" id="currentPage" value="${pagination.currentPage}"/>
       <div class="contentAdmin_foot_right_form">
          <form method="get" action="lazySearch.action" id="searchForm" >
            <input value="${formKeyWord}" class="form_keyword" type="text" name="formKeyWord" id="form_keyword" placeholder="Place input the keyword" />
            <input type="hidden" name="action" value="lazySearch" />
            <input type="hidden" name="isASC" value="${isASC ? 'ASC' : 'DESC'}"/><!-- 默认为false 所以一开始为desc -->
            <input type="hidden" name="pageSize" value="${pagination.pageSize}"/>
            <img class="form_logo" onclick = "search()"/>
          </form>
        </div>
      
      <div class="contentAdmin_foot_right">

        <div class="first_line">
            <label class="ID">
              ID
              <input type="hidden" id="isASC" value="${isASC ? 'ASC' : 'DESC'}"/><!-- 默认为false 所以一开始为desc -->
              <i class="ID_image"><img id="orderBy" onclick="orderby()"/></i>
            </label>
            
            <label class="description" >
              Description
            </label>
            
            <label class="Edit">
              Edit
            </label>

            <label class="edit_image" >
              <img name="edit_image" src="<%=request.getContextPath() %>/static/images/ICN_Unselected_15x15.png">
            </label>
        </div>
        
        <form id="deleteForm" method="post">
           <c:forEach items="${questionList}" var="question" varStatus="status">
           <!-- Display line begin -->
               <div class="content_line" name="content_line">
                   <label class="index">
                     ${status.index+1}
                   </label>
                   
                   <label class="ID">
                     ${question.displayId}
                     <i class="ID_image"></i>
                   </label>
                   
                   <label title="${question.questionDescription}" onclick="viewQuestionDetail('${question.id}')" class="description" >
                     ${question.questionDescription}
                   </label>
                   
                   <label class="Edit">
                     <img src="<%=request.getContextPath() %>/static/images/ICN_Edit_15x15.png" onclick="editQuestion('${question.id}')"/>
                   </label>
       
                   <label class="edit_image" >
                     <img name="edit_image" src="<%=request.getContextPath() %>/static/images/ICN_Unselected_15x15.png" />
                     <input type="hidden" name="questionDBId" value="${question.id}">
                   </label>
               </div>
                <!-- Display line end -->
           </c:forEach>
        </form>
        <div class="contentAdmin_foot_right_foot">
          <input type="hidden" id="lazySearchKeyWord" name="lazySearchKeyWord" value="${formKeyWord}"/>
          <label>
            <img src="<%=request.getContextPath()%>/static/images/BTN_PageLeft_20x15.png" onclick="pagination('${pagination.currentPage==1?pagination.currentPage:pagination.currentPage-1}')"/>
          </label>
          <label>
            <a href="javascript:pagination(1)">1</a>
          </label>
          <label>
            <a href="javascript:pagination(2)">2</a>
          </label>
          <label>
            <a href="javascript:pagination(3)">3</a>
          </label>
          <label id="dot">
            ...
          </label>
          <label>
            <a href="javascript:pagination(65)">65</a>
          </label>
          <label>
            <img src="<%=request.getContextPath()%>/static/images/BTN_PageRight_20x15.png" onclick="pagination('${pagination.currentPage==pagination.pageCount?pagination.pageCount:pagination.currentPage+1}')"/>
          </label>
          <select id="pageSize" onchange="pagination(1)">
              <option value="2" ${pagination.pageSize==2?"selected":null} >2</option>
              <option value="4" ${pagination.pageSize==4?"selected":null} >4</option>
              <option value="6" ${pagination.pageSize==6?"selected":null} >6</option>
              <option value="8" ${pagination.pageSize==8?"selected":null} >8</option>
              <option value="10" ${pagination.pageSize==10?"selected":null} >10</option>
          </select>
          <label id="perPage">
            Per page
            <input type="text" name="pageInput" id="pageInput"/>
          </label>
          <label>
            <img src="<%=request.getContextPath()%>/static/images/BTN_Go_20x15.png" onclick="pagination('goImage')">
          </label>
          <label class="delete">
            <img src="<%=request.getContextPath()%>/static/images/BTN_Delete_30x80.png" onclick="checkDelete()"/>
          </label>
        </div>
        
      </div>
      
    </div>
  <!--==================== Question Management DIV end   ====================-->
  
  
  
  <!--==================== Exam Management DIV begin ====================-->
  <div id="ExamManagement" class="contentAdmin_exam" style="display:none">
  
      <div class="contentAdmin_foot_content_left">
        <div name ="questionManagement" id="questionList" class="contentAdmin_foot_content_left_QuestionList" onmouseover="changeColor('questionList')">
          Exam List
        </div>
        <div name="questionManagement" id="createQuestion" class="contentAdmin_foot_content_left_CreateQuestion" onmouseover="changeColor('createQuestion')">
          Create Exam
        </div>
      </div>
      
       <div class="contentAdmin_foot_right_form">
          <form action="" method="post" >
            <input class="form_keyword" type="text" name="form_keyword_2" placeholder="Place input the keyword" />
            <i class="form_logo"></i>
          </form>
        </div>
      
      <div class="contentAdmin_foot_right">

        <div class="first_line">
            <label class="ID">
              ID
              <i class="ID_image"></i>
            </label>
            
            <label class="description">
              Description
            </label>
            
            <label class="Edit">
              Edit
            </label>
            
            <label class="edit_image" >
              <img name="edit_image_exam" src="<%=request.getContextPath() %>/static/images/ICN_Unselected_15x15.png" />
            </label>
        </div>
        
        <% int b[] = {1,1,2,2,2,3}; 
          request.setAttribute("b", b);%>
        
        <form id="deleteForm123123" method="post">
            <c:forEach items="${b}" var="b" varStatus="status">
            <!-- Display line begin -->
                <div class="content_line" name="content_line">
                    <label class="index">
                      ${status.index+1}
                    </label>
                
                    <label class="ID">
                      ID
                      <i class="ID_image"></i>
                    </label>
                    
                    <label class="description">
                      Description12312312312452314234
                    </label>
                    
                    <label class="edit_image" >
                      <img name="edit_image_exam" src="<%=request.getContextPath() %>/static/images/ICN_Unselected_15x15.png" />
                    </label>
                </div>
                 <!-- Display line end -->
            </c:forEach>
        </form>
        
        <div class="contentAdmin_foot_right_foot">
          <label>
            <img src="<%=request.getContextPath()%>/static/images/BTN_PageLeft_20x15.png" />
          </label>
          <label>
            <a href="">1</a>
          </label>
          <label>
            <a href="">2</a>
          </label>
          <label>
            <a href="">3</a>
          </label>
          <label id="dot">
            ...
          </label>
          <label>
            <a href="">65</a>
          </label>
          <label>
            <img src="<%=request.getContextPath()%>/static/images/BTN_PageRight_20x15.png" />
          </label>
          <select>
              <option value="10">10</option>
              <option value="20">20</option>
              <option value="30">30</option>
              <option value="40">40</option>
          </select>
          <label id="perPage">
            Per page
            <input type="text" name="page"/>
          </label>
          <label >
            <img src="<%=request.getContextPath()%>/static/images/BTN_Go_20x15.png">
          </label>
          <label class="delete">
            <img src="<%=request.getContextPath()%>/static/images/BTN_Delete_30x80.png" />
          </label>
        </div>
        
      </div>
      
  </div>
  <!--==================== Exam Management DIV end   ====================-->
  
  </div>
  <!-- content foot end! -->
  <!-- Qestion and Exam Management DIV  end-->
  
</div>
</body>
</html>
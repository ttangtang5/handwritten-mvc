<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/style_login.css" type="text/css" />
<title>Login Page</title>
</head>
<script  type="text/javascript" src="<%=request.getContextPath()%>/static/js/style_login.js">
</script>
<body>
  <div class="login_head">
    <div class="login_company_logo">
      <div class="login_OnlineExamWebSystem">
        Online Exam Web System
      </div>
    </div>
    
    <div class="login_welcome">
      Welcome on login!
    </div>
    
    <div class="login_form">
       <div id="login_errorMsg" class="login_errorMsg" style="visibility:visible">
          <span id="errorMsgID" class="login_errorMsg_span">${errorMsg}</span>
       </div>
    
      <form id="loginForm" action="loginPost.action" method="post">
        <div class="login_form_username">
          <input class="userName" id="userName" name="userName" type="text" placeholder="Username" value="${userInfoOfCookie.userName}"  /><label id="errorUserName" class="checkErrorMsgIsNull" >${errorFields.get("errorUserName")}</label><br/>
          <i class="login_form_username_ico"></i>
        </div>
        
        <div class="login_form_password">
          <input class="password" id="password" name="password" type="password" placeholder="Password" value="${userInfoOfCookie.password}"  /><label id="errorPassword" class="checkErrorMsgIsNull">${errorFields.get("errorPassword")}</label><br/>
          <i class="login_form_password_ico"></i>
        </div>
        
        <div class="login_image">
          <a href="javascript:login()"><img src="<%=request.getContextPath()%>/static/images/BTN_Login_35x300.png" style="width:266px;height:30px" /></a>
        </div>
        
        <div class="login_remeber_me">
          <img id="unselected" style="cursor: pointer;" src="<%=request.getContextPath()%>/static/images/ICN_Unselected_15x15.png" onclick="changeImg()"/>&nbsp;remeber me &nbsp;&nbsp;&nbsp;&nbsp; <a href="" style="text-decoration:none;color: #FFF " >Forgot password</a>
        </div>

        <input type="hidden" id="userCookie" name="userCookie" value="${userCookie}"/>
        
        <input type="hidden" name="remeberPWD" id="remeberPWD" />
        
        </form>
    </div>
  </div>
</body>
</html>
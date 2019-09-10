var url = "static/images/";
window.onload = function() {
    checkErrorMsg();
    var img = document.getElementById("unselected");
    var userCookie = document.getElementById("userCookie").value;
    if (userCookie) {
        img.src = url + "ICN_Selected_15x15.png";
    }else {
        img.src = url + "ICN_Unselected_15x15.png";
    }
    getRemeberHidden(img.src);
}
function getRemeberHidden(imgSrc) {
    var remeberPWD = document.getElementById("remeberPWD");
    if (imgSrc.substring(imgSrc.indexOf("static")) == url + "ICN_Selected_15x15.png") {
        remeberPWD.value = "remeberMe";
    } else {
        remeberPWD.value = "noRemeberMe";
    }
}
function changeImg(information) {
    var img = document.getElementById("unselected");
    var imgSrc = img.src;
    // split pictrue from "static" to end
    var imgSrcPart = imgSrc.substring(imgSrc.indexOf("static"));
    var remeberPWD = document.getElementById("remeberPWD");
    if (imgSrcPart == url + "ICN_Unselected_15x15.png" ) {
        //must write as img.src and can't be replaced by imgSrc 
        img.src = url + "ICN_Selected_15x15.png";
        remeberPWD.value = "remeberMe";
    } else {
        img.src = url + "ICN_Unselected_15x15.png";
           remeberPWD.value = "noRemeberMe";
    } 
}
function checkErrorMsg() {
    //default don't remeber password.
    document.getElementById("remeberPWD").value = "noRemeberMe";
    var errorMsg = document.getElementById("errorMsgID").innerHTML;
      if (!errorMsg) {//if not exits value, hide the div of login_errorMsg
        document.getElementById("login_errorMsg").style.visibility = "hidden";
      } else { 
        document.getElementById("login_errorMsg").style.visibility = "visible";
        //check the errorMsg after display this login_errorMsg 
        var errorMsg = document.getElementById("errorMsgID").innerHTML;
        if (errorMsg == "username is not exits!") {
            document.getElementById("errorUserName").innerHTML = "<img src='static/images/ICN_Client_Login_Wrong_20X20.png' />";
        } else {
            document.getElementById("errorPassword").innerHTML = "<img src='static/images/ICN_Client_Login_Wrong_20X20.png' />";
        }
      }
}
function login() {
    var loginFormObj = document.getElementById("loginForm");
    var userName = document.getElementById("userName").value;
    var password = document.getElementById("password").value;
    var isSubmit = true;
    if (!userName) { // The userName field value is null
      document.getElementById("errorUserName").innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png' />";
      isSubmit = false;
    } else {
         document.getElementById("errorUserName").innerHTML = "";
    }
    if (!password) {
      document.getElementById("errorPassword").innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png' />";
      isSubmit = false;
    } else {
         document.getElementById("errorUserName").innerHTML = ""; 
    }
    if (isSubmit) {
      loginFormObj.submit();
    }
}
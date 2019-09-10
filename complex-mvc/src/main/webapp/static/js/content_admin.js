function checkImage() {
      var edit_image = document.getElementsByName("edit_image");
      var number = 0;
      for (var i = 1; i< edit_image.length; i++) {
          var img = edit_image[i].src;
          var imgSrc = img.substring(img.indexOf("static"));
          if (imgSrc == "static/images/ICN_Selected_15x15.png") {
              number++;
          }
          if (number == edit_image.length - 1) {
              edit_image[0].src = "static/images/ICN_Selected_15x15.png";
          }
      }
  }
  
  window.onload = function() {
      var isASC = document.getElementById("isASC").value;
      var orderBy = document.getElementById("orderBy");
      if (isASC == "DESC") {
          orderBy.src = "static/images/ICN_Decrease_10x15.png";
      } else {
          orderBy.src = "static/images/ICN_Increase_10x15.png";
      }
      
      displayDiv();
      var edit_image = document.getElementsByName("edit_image");
      //Add onclick Event for every image.
      for (var i = 0; i < edit_image.length; i++) {
          edit_image[0].onclick = function() {
              var firstSrc = this.src;
              if (firstSrc.substring(firstSrc.indexOf("static")) == "static/images/ICN_Unselected_15x15.png" ) {
                  for (var j = 0; j < edit_image.length; j++) {
                      edit_image[j].src = "static/images/ICN_Selected_15x15.png";
                  }
              } else {
                  for (var j = 0 ; j < edit_image.length; j++) {
                      edit_image[j].src = "static/images/ICN_Unselected_15x15.png";
                  }
              }
          }
          //TODO i? why "i+1" will error
          edit_image[i].onclick = function() {
              var img = this.src;
              var imgSrc = img.substring(img.indexOf("static"));
              if (imgSrc == "static/images/ICN_Unselected_15x15.png") {
                  this.src = "static/images/ICN_Selected_15x15.png";
                  checkImage();
              } else {
                  this.src = "static/images/ICN_Unselected_15x15.png"; 
                  edit_image[0].src = "static/images/ICN_Unselected_15x15.png";
              }
          }
      } 
      
      var content_line = document.getElementsByName("content_line");
      //Add onmouseover and onmouseout Event for content line
      for (var i = 0; i < content_line.length; i++) {
          content_line[i].onmouseover = function() {
              this.style = "background-color:#FFC";
          }
          content_line[i].onmouseout = function() {
              this.style = "background-color:#FFF";
          }
      }
  }
  function displayDiv() {
      var display = document.getElementById("addQuestionSuccess");
      var isAddsuccess = document.getElementById("isAddSuccess");
      if (isAddsuccess.value) {
          display.style = "display:black"
          setTimeout(function() {
          display.style = "display:none";
          isAddsuccess.value = "";
          }, 2000);
      }
  }
  
  function changeColor(questionManager) {
      var questionList = document.getElementById("questionList");
      var createQuestion = document.getElementById("createQuestion");
      if (questionManager == "questionList") {
          questionList.style = "background:#2E4358;color:#FFF";
          createQuestion.style = "background:#FFF;color:#2E4358";
      }
      if (questionManager == "createQuestion") {
          createQuestion.style = "background:#2E4358;color:#FFF";
          questionList.style = "background:#FFF;color:#2E4358";
      }
  }
  
  function changeDiv(type) {
      var questionManagement = document.getElementById("contentAdmin_foot_content_id");
      var examManagement = document.getElementById("ExamManagement");
      var question = document.getElementById("question");
      var exam = document.getElementById("exam");
      if (type == "question") { // block-->show   none-->hidden
          examManagement.style.display = "none";
          questionManagement.style.display = "block";
          question.style = "background-color: #D2DAE3;";
          exam.style = "background-color: #FFF;";
      } else if (type == "exam") {
          questionManagement.style.display = "none";
          examManagement.style.display = "block";
          exam.style = "background-color: #D2DAE3;";
          question.style = "background-color: #FFF;";
      }
  }
  function search() {
      var searchForm = document.getElementById("searchForm");
      var isASC = document.getElementById("isASC").value;
      var orderBy = document.getElementById("orderBy");
      if (form_keyword) { //Not is null or ""
          var pageSize = document.getElementById("pageSize").value;
          var url = "lazySearch.action?pageSize=" + pageSize + "&isASC" + isASC;
          searchForm.action = url;
          searchForm.submit();
      }
  }
  function deleteQuestion() {
      var sureDelete = document.getElementById("sureDelete");
      sureDelete.style = "display:black";
      //Get all of images
      var edit_image = document.getElementsByName("edit_image");
      //Get all of hidden form
      var ids = {};
      var index = 0;
      var hidden = document.getElementsByName("questionDBId");
      for (var i = 1;i < edit_image.length; i++) {
          var img = edit_image[i].src;
          var imgSrc = img.substring(img.indexOf("static"));
          for (var j = 0;j < hidden.length; j++) {
              if ((i-1) == j && imgSrc == "static/images/ICN_Selected_15x15.png") {
                  ids[index++] = hidden[j].value;
              }
              continue;
          }
      }
      var json = "";
      for (var i = 0 ; i < index; i++) {
          json = json + ids[i] + ",";
      }
      json = json.substring(0,json.lastIndexOf(","));
      
      var form = document.getElementById("deleteForm");
      form.method = "post";
      form.action = "deleteQuestion.action?ids=" + json;
      form.submit(); 
      
  }
  function editQuestion(questionId) {
      if (questionId) { //not is null and "" ==> true
          window.location.href = "updateQuestionOption.action?action=update&questionId=" + questionId;
      }
  }
  function goToCreateQuestion() {
      window.location.href='question.action?action=create';
  }
  function checkDelete(isDelete) {
      var sureDelete = document.getElementById("sureDelete");
      var edit_image = document.getElementsByName("edit_image");
      var selectedImage = 0;
      for (var i = 0 ; i < edit_image.length; i++) {
          var img = edit_image[i].src; //TODO When to use "this" and when to use array objects?
          var imgSrc = img.substring(img.indexOf("static"));
          if (imgSrc == "static/images/ICN_Selected_15x15.png") {
              selectedImage++;
          }
      }
      if (selectedImage == 0) {
          return;
      }
      
      sureDelete.style = "display:block";
       if (isDelete == "sure") {
          deleteQuestion();
      } else if (isDelete == "no") {
          sureDelete.style = "display:none";
      } 
  }
  function pagination(currentPage) {
      var lazySearchKeyWord = document.getElementById("lazySearchKeyWord").value;
      var pageSize = document.getElementById("pageSize").value;
      var isASC = document.getElementById("isASC").value;
      var img = document.getElementById("orderBy");
      var src = img.src.substring(img.src.indexOf("static"));
      if (!currentPage) {// Undefined ==> false
          currentPage = 1;
      }
      if (currentPage == "goImage") {//Goto appoint page and page size 
          var goImage = document.getElementById("pageInput").value;
          if (goImage) {
              var url = "question.action?currentPage =" + goImage + "&pageSize=" + pageSize;
              if (lazySearchKeyWord) {
                  url = "lazySearch.action?currentPage =" + goImage + "&pageSize=" + pageSize + "&formKeyWord=" + lazySearchKeyWord;
              }
              window.location.href = url + "&isASC=" + isASC;
          }
          return;
      } 
      var url = "question.action?currentPage=" + currentPage + "&pageSize=" + pageSize;
      if (lazySearchKeyWord) { //If input's content is not null and "", It will begin search of lazy.
          url = "lazySearch.action?currentPage=" + currentPage + "&pageSize=" + pageSize + "&formKeyWord=" + lazySearchKeyWord;
      }
      window.location.href = url + "&isASC=" + isASC;
  }
  function goToQuestionList() {
      window.location.href = "question.action";
  }
  function viewQuestionDetail(questionId) {
      if (questionId) {
          window.location.href = "viewQuestionDetail.action?action=viewQuestionDetail&questionId=" + questionId;
      }
  }
  function orderby() {
      var isASC = document.getElementById("isASC");
      var orderBy = document.getElementById("orderBy");
      var currentPage = document.getElementById("currentPage").value;
      if (isASC.value == "ASC") {
          isASC.value = "DESC";
      } else {
          isASC.value = "ASC";
      }
      pagination(currentPage);
  }

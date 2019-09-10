window.onload = function() {
	displayDiv();
    var input = document.getElementsByTagName("input");
    var textarea = document.getElementsByTagName("textarea")[0];
    var questionError = document.getElementById("questionError");
    textarea.onfocus = function() {
        textarea.nextSibling.innerHTML = "";
    }
    textarea.onblur = function() {
        if (textarea.value) {
            textarea.nextSibling.innerHTML = "";
        }
        if (!textarea.value) {
            textarea.nextSibling.innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png'/>";
        }
    }
    
    
    for (var i = 0; i < input.length; i++) {
        input[i].onfocus = function() {
            this.nextSibling.innerHTML = "";
        }
        input[i].onblur = function() {
            if (this.value) {
                this.nextSibling.innerHTML = "";
            }
            if (!this.value) {
                this.nextSibling.innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png'/>";
            }
        }
    }
}
function goBack() {
    window.history.back(-1);
}
function createQuestion() {
    var isFllow = true;
    var input = document.getElementsByTagName("input");
    var textarea = document.getElementsByTagName("textarea")[0];
    if (!textarea.value) {
        textarea.nextSibling.innerHTML = "<img src='static/images/ICN_Client_Login_Wrong_20X20.png'/>";
        isFllow = false;
    }
    for (var i = 1; i < input.length; i++) {
        //input[i].nextSibling ==> find the next element after this.
        if (!input[i].value) {//If it is not value ==>is null or ""
            input[i].nextSibling.innerHTML = "<img src='static/images/ICN_Client_Login_Wrong_20X20.png'/>";
            isFllow = false;
        }
    }
    if (isFllow) {
        var form = document.getElementById("questionForm");
        form.submit();
    }
}

function displayDiv() {
    var display = document.getElementById("createQuestionMsg");
    var isCreateQuestion = document.getElementById("isCreateQuestion");
    if (isCreateQuestion.value) {
        display.style = "display:black"
        setTimeout(function() {
        display.style = "display:none";
        isCreateQuestion.value = "";
        }, 2000);
    }
}
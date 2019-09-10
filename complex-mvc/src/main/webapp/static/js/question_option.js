window.onload = function() {
    var input = document.getElementsByTagName("input");
    var textarea = document.getElementsByTagName("textarea")[0];
    var questionError = document.getElementById("questionError");
    textarea.onfocus = function() {
        textarea.nextSibling.innerHTML = "";
    }
    textarea.onblur = function() {
        if (textarea.value){
            textarea.nextSibling.innerHTML = "";
        }
        if (!textarea.value){
            textarea.nextSibling.innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png'/>";
        }
    }
    for (var i = 0; i < input.length; i++) {
        input[i].onfocus = function() {
            this.nextSibling.innerHTML = "";
        }
        input[i].onblur = function() {
            if (this.value){
                this.nextSibling.innerHTML = "";
            }
            if (!this.value){
                this.nextSibling.innerHTML = "<img src = 'static/images/ICN_Client_Login_Wrong_20X20.png'/>";
            }
        }
    }
}
function goBack() {
    window.history.back(-1);
}
function editQuestion() {
    var editQuestionForm = document.getElementById("editQuestionForm");
    editQuestionForm.action = "questionOption.action";
    editQuestionForm.submit();
    editImage.src = "static/images/BTN_Edit_30x150.png"
}
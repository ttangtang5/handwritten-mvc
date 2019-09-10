function goBack() {
    window.history.back(-1);
}
function editQuestion() {
    var questionId = document.getElementById("questionDBID").value;
    if (questionId) { //not is null and "" ==> true
        window.location.href = "updateQuestionOption.action?action=update&questionId=" + questionId;
    }
}
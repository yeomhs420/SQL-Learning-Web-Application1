// https://cofs.tistory.com/m/184
jQuery.fn.serializeObject = function() {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function() {
                    obj[this.name] = this.value;
                });
            }
        }
    } catch (e) {
        alert(e.message);
    } finally {}

    return obj;
};

var limitTime=5;
var timeoutId;
var target = $('#submit-btn');

function startTimeout() {
    target.attr("disabled", true);
    timeoutId=setInterval(printCurrentDate, 1000);
}
function printCurrentDate() {
    if(--limitTime>0) target.html("<span style='color:red;'>"+limitTime+"초 후 다시 제출 가능</span>");
    else {
        target.html("답안 제출");
        clearInterval(timeoutId);
        limitTime=5;
        target.attr("disabled", false);
    }
}

$('#answer-form').submit(function() {
    startTimeout();
    var serializedFormData = $('#answer-form').serializeObject();
    console.log(serializedFormData)
    $.ajax({
        url:"http://localhost:8080/test/grading",
        data: JSON.stringify(serializedFormData),
        type: "POST",
        dataType: "json",
        contentType : "application/json"
    })
    .done(function(json) {
        console.log(json);
    })
    .fail(function(xhr, status, errorThrown){
        alert("요청 실패");
    })
    return false;
});
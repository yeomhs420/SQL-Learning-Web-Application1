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
    $('#result-container').show();
    $('html, body').animate({scrollTop: $('#result-container').offset().top}, 200);
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
        var questionList = json.questionList;
        $('#correct-count').text("맞은 개수 : "+json.correctCount+"/"+json.questionList.length);
        if(json.correctCount===json.questionList.length) alert("축하합니다! 테스트를 통과했습니다!");
        for(var i=0;i<questionList.length;i++) {
            var question = questionList[i];
            var result = question.sqlResult;
            if(question.isCorrect) {
                $('#result'+(i+1)+' .correct').show();
                $('#result'+(i+1)+' .incorrect').hide();
            }
            else {
                $('#result'+(i+1)+' .incorrect').show();
                $('#result'+(i+1)+' .correct').hide();
            }
            $('#user-answer-'+(i+1)).text(question.userAnswer);
            $('#result'+(i+1)+' .result-table > tbody').empty();
            if(question.errorMsg!=null) $('#result'+(i+1)+' .result-table > tbody:last').append('<tr><td style="border:none">'+question.errorMsg+'</td></tr>');
            if(result!=null) {
                for(var r=0;r<result.length;r++) {
                    var rowStr="<tr>";
                    if(r==0) rowStr="<tr style=\"background-color:#EEEEEE\">";
                    for(var c=0;c<result[r].length;c++) rowStr+="<td>"+result[r][c]+"</td>";
                    rowStr+="</tr>"
                    $('#result'+(i+1)+' .result-table > tbody:last').append(rowStr);
                }
            }
        }
    })
    .fail(function(xhr, status, errorThrown){
        alert("요청 실패");
    })
    return false;
});

$(document).ready(function() {
    $('#result-container').hide();
    $('.correct').hide();
    $('.incorrect').hide();
    for(var i=0;i<17;i++) $("#test-status>div:first").appendTo(".comment-header:eq("+i+")");
});
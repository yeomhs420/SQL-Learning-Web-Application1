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

$('#answer-form').submit(function() {
    var serializedFormData = $('#answer-form').serializeObject();
    console.log(serializedFormData.unit)
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


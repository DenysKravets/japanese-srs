$(window).on("load", function() {
    //wanikani input
    var textInput = $(".wanakana-input").get()[0];
    console.log(textInput);
    wanakana.bind(textInput)

    var vocab_counter = 0;
    var word;
    var reading;
    var meanings = [];
    var id;
    function change_vocab() {
        word = document.review_data[vocab_counter].word;
        reading = document.review_data[vocab_counter].reading;
        meanings = document.review_data[vocab_counter].meanings;
        id = document.review_data[vocab_counter].id;
        $("#word").html(word);
        vocab_counter++;
    }
    change_vocab(vocab_counter);

    function sendIsSuccess(id, isSuccess) {
        var url = "http://localhost:8080/review/vocab/done?id="+id+"&isSuccess=" + isSuccess;
        $.ajax({
            type: "POST",
            url: url,
            success: function(data) {
                if(data) {
                    console.log("success");
                }
            },
            contentType: "text/plain",
            async: true
        });
    }
    
    $("#button").on("click", function(){

        var userReading = $("#reading").val();
        var userMeaning = $("#meaning").val();
        console.log(userMeaning, userReading, word, meanings, reading);

        var readingCorrect = false;
        var meaningCorrect = false;
        if(userReading == reading) {
            readingCorrect = true;
        }
        if(meanings.includes(userMeaning)) {
            meaningCorrect = true;
        }
        if(readingCorrect && meaningCorrect) {
            sendIsSuccess(id, true);
            if(vocab_counter == document.review_data.length) {
                alert("Nice job, " + document.review_data.length + " vocabs done! Returning to Main Page.");
                window.location.href = "http://localhost:8080/index";
                return null;
            }
            change_vocab();
            $("#reading").focus();
            $("#reading").val("");
            $("#meaning").val("");
            //Send request
        }
        if(readingCorrect == false && meaningCorrect == false) {
            sendIsSuccess(id, false);
            alert("Reading and meaning are wrong!");
            $("#reading").focus();
            $("#reading").val("");
            $("#meaning").val("");
        } else if(readingCorrect == false) {
            sendIsSuccess(id, false);
            alert("Reading is wrong!");
            $("#reading").focus();
            $("#reading").val("");
        } else if(meaningCorrect == false) {
            sendIsSuccess(id, false);
            alert("Meaning is wrong!");
            $("#meaning").focus();
            $("#meaning").val("");
        }

    });

});
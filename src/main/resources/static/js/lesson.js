(function() {
    document.getElementById("done").onclick = function() {
        const url = "http://localhost:8080/lesson/done";
        var list = $(".vocab_id");
        const ids = [];
        for (var i = 0; i < list.length; i++) {
           ids.push(list[i].innerText);
        }
        data = {
            array: ids
        }
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            success: function(data) {
                if(data) {
                    console.log(data);
                    window.location.href = "http://localhost:8080/index";
                }
            },
            contentType: "application/json",
            async: true
        });
    };

    document.getElementById("next-lesson").onclick = function() {
        const url = "http://localhost:8080/lesson/done";
        var list = $(".vocab_id");
        const ids = [];
        for (var i = 0; i < list.length; i++) {
           ids.push(list[i].innerText);
        }
        data = {
            array: ids
        }
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            success: function(data) {
                if(data) {
                    console.log(data);
                    document.location.reload();
                }
            },
            contentType: "application/json",
            async: true
        });
    };
})();
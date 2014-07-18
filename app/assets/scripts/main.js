$(function() {

    $("#myform").submit(function(e) {
        e.preventDefault();
        console.log(new Date());
        var form_data = $(this).serializeArray();

        var jsonify = function(form_data) {
            var my_data = {};
            $.map(form_data, function(e) {
                my_data[e.name] = e.value;
            });
            if (my_data.date) {
                my_data.date = new Date(my_data.date).getTime(); // always send time, in timeMillis to server
            } else {
                delete(my_data.date);
            }
            return JSON.stringify(my_data);
        };


        var request_url = window.location.origin + '/create';
        var data = jsonify(form_data);
        console.log(data);

        $.ajax({
            url: request_url,
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: data

        }).done(function(msg) {

            if (Recaptcha !== 'undefined' || Recaptcha !== null) {
                Recaptcha.destroy();
            }
        }).fail(function(request, status, error) {
            console.log("REQUEST => " + request);
            console.log("STATUS => " + status);
            console.log("ERROR => " + error);

        });

    });

    var showRecaptcha = function() {
        var publicKey = "6Ld9CfcSAAAAAPxFOct3u1CMxs9W1vdrW--z8g5Y";
        Recaptcha.create(publicKey, "recaptcha", {
            theme: "red",
            callback: Recaptcha.focus_response_field
        });
    }(); // end showRecaptcha

});

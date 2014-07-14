$(function() {

    $("#myform").submit(function(e) {
        e.preventDefault();
        console.log(new Date());
        var form_data = $(this).serializeArray();

        var jsonify = function(form_data){
            var my_data = {};
            $.map(form_data, function(e) {
                my_data[e.name] = e.value;
            });
            if(my_data.date !== 'undefined')
                my_data.date = new Date(my_data.date).getTime(); // always send time, in timeMillis to server
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
            // window.location.replace(msg.path);
        }).fail(function(request, status, error) {
            console.log("REQUEST => " + request);
            console.log("STATUS => " + status);
            console.log("ERROR => " + error);

        });
    });
});

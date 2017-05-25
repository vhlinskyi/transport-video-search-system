$(function() {
    $('#sign_up_btn').on("click", function(){

        var registrationData = {
            name: $('#username').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            matchingPassword: $('#repeat_password').val()
        };
        sendRegistrationRequest(registrationData);
        $(this).prop('disabled', true);
    });
});

function sendRegistrationRequest(registrationData) {

    $.ajax({
        url: '/account-service/',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(registrationData),
        success: function (data) {
            console.log("CREATED ACCOUNT:");
            console.log(data);
            $('#sign_up_btn').prop('disabled', false);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $('#sign_up_btn').prop('disabled', false);
            if (xhr.status == 400) {
                alert("Sorry, account with the same name already exists.");
            } else {
                alert("An error during account creation. Please, try again.");
            }
        }
    });
}
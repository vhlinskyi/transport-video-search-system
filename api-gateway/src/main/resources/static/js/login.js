$(function() {
    $('#login_btn').on("click", function(){

        $('#login_btn').prop("disabled", true);
        login();
        $('#login_btn').prop("disabled", false);
    });
});

function login() {


    var username = $("input[id='email']").val();
    var password = $("input[id='password']").val();

    if (requestOauthToken(username, password)) {

        console.log("TOKEN:");
        console.log(getOauthTokenFromStorage());

    } else {
        alert("Something went wrong. Please, check your credentials");
    }
}

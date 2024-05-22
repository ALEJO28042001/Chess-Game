function login() {
    let userData = {
        username: document.getElementsByName('username')[0].value,
        password: document.getElementsByName('password')[0].value,
    };

    $.ajax({
        url: "/usuarios/login",
        type: "POST",
        data: JSON.stringify(userData),
        contentType: "application/json",
        dataType: "json",
        success: function(response) {
            console.log("Success");
            console.log(response);
            console.log(response.username);
            // Verificar si la respuesta tiene el campo 'username'
            if (response && response.username) {
                // Construir la URL con el nombre de usuario como parámetro
                let username = response.username;
                setCookie("token",JSON.stringify(response));
                window.location.replace("/dashboard.html?username=" + encodeURIComponent(username));
            }
        },
        error: function(xhr, status, error) {
            alert("Username or password invalid!")
        }
    });
}
function setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

$(document).ready(function(){
    if(getCookie("token")!=""){
        let token=getCookie("token");
        let user=JSON.parse(token);
        let username=user.username;
        window.location.replace("/dashboard.html?username=" + encodeURIComponent(username));

    }
});
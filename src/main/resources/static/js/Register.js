

function register() {
    let userData = {
        username: document.getElementsByName('username')[0].value,
        password: document.getElementsByName('password')[0].value,
    };

    $.ajax({
        url: "/usuarios/register",
        type: "POST",
        data: JSON.stringify(userData),
        contentType: "application/json",
        dataType: "json",
        success: function(response) {
            alert("User Registered Successfully!")
            console.log("Success");
            console.log(response);
            console.log(response.username);
            // Verificar si la respuesta tiene el campo 'username'
            if (response && response.username) {
                // Construir la URL con el nombre de usuario como parámetro
                let username = response.username;
                window.location.href = "/login.html";
            } else {
                console.log("Error: La respuesta del servidor no contiene el campo 'username'");
            }
        },
        error: function(xhr, status, error) {
            console.log("Error: " + error);
        }
    });
}
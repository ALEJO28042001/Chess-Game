var hasGameStarted = false;
var checkCounter = 0;
let board = null;
function coloring(){
    const color = document.querySelectorAll('.box')
    color.forEach(color =>{
        getId = color.id;
        arr = Array.from(getId);
        arr.shift();
        aside = eval(arr.pop());
        aup = eval(arr.shift());
        a = aside + aup;

        if(a % 2 == 0){
            color.style.backgroundColor = 'rgb(181 136 99)';
        }

        if(a % 2 !== 0){
            color.style.backgroundColor = 'rgb(240 217 181)';
        }
    })
}


function getResponse(x, y) {

    if(!hasGameStarted){
        hasGameStarted = true;
    }

    if(board == null){
        console.log("Nulo");
        let userData = {
            x:x,
            y:y,
        }
        $.ajax({
            url: "/board/newGame",
            type: "GET",
            data: userData,
            dataType: "json",
            success: function(response) {
                console.log("Success")
                paintPieces(response.positions);
                checkJaque(response.banderaJaque, response.hayGanador);
                setPlayerInTurn(response.playerInTurn);
                board = response;
            },
            error: function(xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    }

    else{
        let userData = {
            x: x,
            y: y,
            board: board // Agrega la variable board al objeto userData
        };

        $.ajax({
            url: "/board/move",
            type: "POST", // Cambia el tipo de solicitud a POST
            data: JSON.stringify(userData), // Convierte el objeto a una cadena JSON
            contentType: "application/json", // Especifica que el tipo de contenido es JSON
            dataType: "json",
            success: function(response) {
                console.log("Success");
                console.log(response.positions);
                paintPieces(response.positions);
                checkJaque(response.banderaJaque, response.hayGanador);
                setPlayerInTurn(response.playerInTurn);
                board = response; // Asigna la respuesta a la variable board
            },
            error: function(xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    }


}

function paintPieces(positions) {
    positions.forEach(function(row, x) {
        row.forEach(function(position, y) {
            let id = "b" + x + 0 + y;
            let element = document.getElementById(id);

            if (position !== null) {
                element.innerHTML = '<img src="images/' + position.color + '_' + position.name + '.png">';
            } else {
                element.innerHTML = '';
            }
        });
    });


}

function setRefreshButton(){
}

// To solve the "Check!" alert when the player being attacked sets the first piece
function checkJaque(banderaJaque, hayGanador){

    if(hayGanador){
        alert("CheckMate!!");
        return;
    }

    if (banderaJaque === false){
        checkCounter +=1;
    }

    if ((checkCounter === 1) && (banderaJaque === false)){
        alert("Check!");
    }

    if(checkCounter ===1 && banderaJaque === true){
        checkCounter = 0;
    }

    if (checkCounter === 2){
        checkCounter = 0;
    }
}

function setPlayerInTurn(playerInTurn){
    let element = document.getElementById("tog");
    element.innerHTML = playerInTurn + "'s Turn";
}

function saveGame(){

    let userData = {logs: board.logs}

    $.ajax({
        url: "/board/save",
        type: "POST", // Cambia el tipo de solicitud a POST
        data: JSON.stringify(userData), // Convierte el objeto a una cadena JSON
        contentType: "application/json", // Especifica que el tipo de contenido es JSON
        dataType: "json",
        success: function(response) {
            console.log("Success Saving");
            board = response; // Asigna la respuesta a la variable board
        },
        error: function(xhr, status, error) {
            console.log("Error: " + error);
        }
    });

}

function getParameterByName(id) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(id);
}

$(document).ready(function() {
    const id = getParameterByName('id');
    console.log(id);

    $.ajax({
        url: "/board/loadGame?id=" + id,
        type: "GET",
        success: function(response) {
            // La función que se ejecutará cuando la petición sea exitosa
            console.log("Success");
            console.log(response.positions);
            paintPieces(response.positions);
            checkJaque(response.banderaJaque, response.hayGanador);
            setPlayerInTurn(response.playerInTurn);
            board = response;
        },
        error: function(xhr, status, error) {
            // La función que se ejecutará si hay un error en la petición
            console.error("Error en la petición:", status, error);
            // Aquí puedes manejar el error de alguna manera
        }
    });
});

coloring();
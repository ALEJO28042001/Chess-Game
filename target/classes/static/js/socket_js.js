const url = "http://localhost:8080";
let stompClient;
let gameId;

function create_game(){
    console.log("connecting to the game");
    let socket = new SockJS(url + "gameplay");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame){
        console.log("connected to frame: " + frame);
        stompClient.subscribe("/topic/game-progress")
    })

}

function connectToSpecificGame(){

}
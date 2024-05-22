package com.springChess.springChess.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springChess.springChess.model.Board;
import com.springChess.springChess.model.MoveRequest;
import com.springChess.springChess.model.Position;
import com.springChess.springChess.model.Utils;
import com.springChess.springChess.model.entities.Game;
import com.springChess.springChess.repository.GameRepository;
import com.springChess.springChess.service.BoardService;
import com.springChess.springChess.service.GameService;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private GameService gameService;
    @Autowired
    private BoardService boardService;

    private final ObjectMapper objectMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public BoardController(ObjectMapper objectMapper, SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = objectMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/newGame")
    public ResponseEntity<Board> newGame(){
        return ResponseEntity.ok(boardService.newGame());
    }


    @PostMapping("/move")
    public Board movePiece(@RequestBody MoveRequest moveRequest) throws JsonProcessingException {
        int x = moveRequest.getX();
        int y = moveRequest.getY();
        JsonNode nodeBoard = moveRequest.getBoard();
        
        String myPlayer = moveRequest.getPlayerName();        
        Board board = objectMapper.treeToValue(nodeBoard, Board.class);
        
        if (moveRequest.getGameId()!=-1)
        {
        	Game savedGame = gameService.getGame(moveRequest.getGameId());          
        	savedGame.asignPlayerColor(myPlayer);
        	String playerTurn = savedGame.returnPlayerColor(myPlayer);
            board.positionSet(new Position(x, y),playerTurn);
            simpMessagingTemplate.convertAndSend("/topic/game-progress/" + moveRequest.getGameId(), board);
            savedGame.setLogs(board.getLogs());
            gameService.saveGame(savedGame);
        }
        else
        {
            board.positionSet(new Position(x, y),board.getPlayerInTurn());
        }        

        return board;
    }

    @PostMapping("/save")
    public Game saveGame(@RequestBody JsonNode requestBody){
        Game game = new Game();
        String logs = requestBody.get("logs").asText();
        game.setLogs(logs);
        
        
        gameService.saveGame(game);
        return game;
    }

    @GetMapping("/all")
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

    @GetMapping("/loadGame")
    public ResponseEntity<Board> getGame(Long id){
        if(id == -1){
            return ResponseEntity.ok(boardService.newGame());
        }
        System.out.println(id);
        Game savedGame = gameService.getGame(id);
        System.out.println(savedGame.getBlackPlayer()+"Black");

        System.out.println(savedGame.getWhitePlayer()+"White");
        Board board = Utils.createGame();
        board = Utils.updateBoard(board, savedGame);
        
        return ResponseEntity.ok(board);
    }


}

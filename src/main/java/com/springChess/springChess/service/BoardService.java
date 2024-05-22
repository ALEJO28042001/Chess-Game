package com.springChess.springChess.service;

import com.springChess.springChess.model.Board;
import com.springChess.springChess.model.Position;
import com.springChess.springChess.model.Utils;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    public Board newGame(){
        return Utils.createGame();
    }

    public Board setPosition(Board board, int x, int y){
        board.positionSet(new Position(x, y),board.getPlayerInTurn());
        return board;
    }

}

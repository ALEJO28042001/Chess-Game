package com.springChess.springChess.model;

import com.springChess.springChess.model.entities.Game;

import java.util.ArrayList;

public class Utils {

    public static Board createGame(){
        Board board = new Board();

        // White pieces
        board.placePiece(new Piece("Rook"  , "White"), 0,0);
        board.placePiece(new Piece("Knight", "White"), 0, 1);
        board.placePiece(new Piece("Bishop", "White"), 0, 2);
        board.placePiece(new Piece("Queen" , "White"), 0, 3);
        board.placePiece(new Piece("King"  , "White"), 0, 4);
        board.placePiece(new Piece("Bishop", "White"), 0, 5);
        board.placePiece(new Piece("Knight", "White"), 0, 6);
        board.placePiece(new Piece("Rook"  , "White"), 0, 7);
        for (int i = 0; i < 8; i++) {
            board.placePiece(new Piece("Pawn", "White"), 1, i);
        }

        // Black pieces
        board.placePiece(new Piece("Rook"  , "Black"), 7, 0);
        board.placePiece(new Piece("Knight", "Black"), 7, 1);
        board.placePiece(new Piece("Bishop", "Black"), 7, 2);
        board.placePiece(new Piece("Queen" , "Black"), 7, 3);
        board.placePiece(new Piece("King"  , "Black"), 7, 4);
        board.placePiece(new Piece("Bishop", "Black"), 7, 5);
        board.placePiece(new Piece("Knight", "Black"), 7, 6);
        board.placePiece(new Piece("Rook"  , "Black"), 7, 7);
        for (int i = 0; i < 8; i++) {
            board.placePiece(new Piece("Pawn", "Black"), 6, i);
        }

        board.setBlackKing(new Position(7,4));
        board.setWhiteKing(new Position(0,4));

        return board;
    }

    public static Board updateBoard(Board board, Game game){
        ArrayList<Position> positions = parsePositions(game.getLogs());
        for (Position position: positions){
            board.positionSet(position,board.getPlayerInTurn());
        }
        board.setBlackPlayer(game.getBlackPlayer());
        board.setWhitePlayer(game.getWhitePlayer());
        return board;
    }

    public static ArrayList<Position> parsePositions(String positionsString) {
        String[] positionPairs = positionsString.split(";");
        ArrayList<Position> positions = new ArrayList<>();

        for (String pair : positionPairs) {
            String[] parts = pair.split(",");
            if (parts.length == 2) {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                positions.add(new Position(row, col));
            }
        }

        return positions;
    }

}

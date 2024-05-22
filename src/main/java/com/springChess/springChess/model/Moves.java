package com.springChess.springChess.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class Moves {

    private final Map<String, BiConsumer<Position, Piece[][]>> moveCalculations;
    private List<Position> possibleMoves = new ArrayList<>();

    public Moves() {
        moveCalculations = new HashMap<>();
        moveCalculations.put("Pawn", this::calculatePawnMoves);
        moveCalculations.put("Knight", this::calculateKnightMoves);
        moveCalculations.put("Rook", this::calculateRookMoves);
        moveCalculations.put("Bishop", this::calculateBishopMoves);
        moveCalculations.put("Queen", this::calculateQueenMoves);
        moveCalculations.put("King", this::calculateKingMoves);
        // Add other pieces as needed
    }

    public List<Position> calculatePieceMoves(Position position, Piece[][] positions) {
        Piece piece = positions[position.getRow()][position.getCol()];

        if (piece == null) {
            System.out.println("No piece found at the source position.");
            return new ArrayList<>();
        }
        moveCalculations.getOrDefault(piece.name, (p, pos) -> {}).accept(position, positions);
        System.out.println("Entró"+this.possibleMoves);
        return this.possibleMoves;

    }


    public boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Method to calculate possible moves for a Pawn
    public void calculatePawnMoves(Position position, Piece[][] positions) {
        int row = position.getRow();
        int col = position.getCol();
        List<Position> possibleMoves = new ArrayList<>();
        Piece piece = positions[row][col];

        int direction = piece.color.equals("White") ? 1 : -1;

        // Forward move
        int newRow = row + direction;
        Position newPosition= new Position(newRow, col);

        if (isValidPosition(newPosition) && positions[newRow][col] == null) {
            possibleMoves.add(newPosition);
        }

        // Diagonal capture moves
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            newPosition = new Position(newRow, captureCol);
            if (isValidPosition(newPosition) && positions[newRow][captureCol] != null
                    &&!positions[newRow][captureCol].color.equals(piece.color)) {
                possibleMoves.add(newPosition);
            }
        }

        newRow = newRow + direction;
        newPosition= new Position(newRow, col);

        if (isValidPosition(newPosition) && positions[newRow][col] == null && (row == 1 || row == 6)) {
            possibleMoves.add(newPosition);
        }

        this.possibleMoves=possibleMoves;
    }

    // Method to calculate possible moves for a Knight
    public void calculateKnightMoves(Position position, Piece[][] positions) {
        int row = position.getRow();
        int col = position.getCol();
        Piece piece = positions[row][col];
        List<Position> possibleMoves = new ArrayList<>();
        // Implement the logic for calculating possible moves for the Knight
        int[][] knightMoves = Constants.KNIGHT_MOVES;

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            Position newPosition = new Position(newRow, newCol);
            if (isValidPosition(newPosition) &&
                    (positions[newRow][newCol] == null || !positions[newRow][newCol].color.equals(piece.color))) {
                possibleMoves.add(newPosition);
            }
        }
        this.possibleMoves=possibleMoves;
    }

    // Method to calculate possible moves for a Rook
    public void calculateRookMoves(Position position, Piece[][] positions) {
        int row = position.getRow();
        int col = position.getCol();
        Piece piece = positions[row][col];
        List<Position> possibleMoves = new ArrayList<>();

        // Implement the logic for calculating possible moves for the Rook

        for (int newCol = col - 1; newCol >= 0; newCol--) {
            if (positions[row][newCol] == null) {
                possibleMoves.add(new Position(row, newCol));
            } else {
                if (!positions[row][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(row, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }
        for (int newCol = col + 1; newCol < 8; newCol++) {
            if (positions[row][newCol] == null) {
                possibleMoves.add(new Position(row, newCol));
            } else {
                if (!positions[row][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(row, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }

        // Check moves in the same column
        for (int newRow = row - 1; newRow >= 0; newRow--) {
            if (positions[newRow][col] == null) {
                possibleMoves.add(new Position(newRow, col));
            } else {
                if (!positions[newRow][col].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, col));
                }
                break;  // Stop checking further in this direction
            }
        }
        for (int newRow = row + 1; newRow < 8; newRow++) {
            if (positions[newRow][col] == null) {
                possibleMoves.add(new Position(newRow, col));
            } else {
                if (!positions[newRow][col].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, col));
                }
                break;  // Stop checking further in this direction
            }
        }


        this.possibleMoves=possibleMoves;
    }

    // Method to calculate possible moves for a Bishop
    public void calculateBishopMoves(Position position, Piece[][] positions) {
        int row = position.getRow();
        int col = position.getCol();
        Piece piece = positions[row][col];
        List<Position> possibleMoves = new ArrayList<>();
        // Implement the logic for calculating possible moves for the Bishop


        for (int newRow = row - 1, newCol = col - 1; newRow >= 0 && newCol >= 0; newRow--, newCol--) {
            if (positions[newRow][newCol] == null) {
                possibleMoves.add(new Position(newRow, newCol));
            } else {
                if (!positions[newRow][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }

        // Upper right diagonal
        for (int newRow = row - 1, newCol = col + 1; newRow >= 0 && newCol < 8; newRow--, newCol++) {
            if (positions[newRow][newCol] == null) {
                possibleMoves.add(new Position(newRow, newCol));
            } else {
                if (!positions[newRow][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }

        // Lower left diagonal
        for (int newRow = row + 1, newCol = col - 1; newRow < 8 && newCol >= 0; newRow++, newCol--) {
            if (positions[newRow][newCol] == null) {
                possibleMoves.add(new Position(newRow, newCol));
            } else {
                if (!positions[newRow][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }

        // Lower right diagonal
        for (int newRow = row + 1, newCol = col + 1; newRow < 8 && newCol < 8; newRow++, newCol++) {
            if (positions[newRow][newCol] == null) {
                possibleMoves.add(new Position(newRow, newCol));
            } else {
                if (!positions[newRow][newCol].color.equals(piece.color)) {
                    possibleMoves.add(new Position(newRow, newCol));
                }
                break;  // Stop checking further in this direction
            }
        }

        this.possibleMoves=possibleMoves;
    }

    // Method to calculate possible moves for a Queen
    public void calculateQueenMoves(Position position, Piece[][] positions) {
        List<Position> possibleMoves = new ArrayList<>();
        // Implement the logic for calculating possible moves for the Queen

        // Check horizontal and vertical moves (rook-like moves)
        calculateRookMoves(position,positions);
        possibleMoves=this.possibleMoves;
        // Check diagonal moves (bishop-like moves)
        calculateBishopMoves(position,positions);
        possibleMoves.addAll(this.possibleMoves);

        this.possibleMoves=possibleMoves;
    }

    // Method to calculate possible moves for a King
    public void calculateKingMoves(Position position, Piece[][] positions) {
        int row = position.getRow();
        int col = position.getCol();
        String color = positions[row][col].getColor();
        List<Position> possibleMoves = new ArrayList<>();

        // Define all possible king move offsets

        int[][] kingMoves = Constants.KING_MOVES;

        for (int[] move : kingMoves) {
            int newRow = position.getRow() + move[0];
            int newCol = position.getCol() + move[1];

            if (isValidPosition(new Position(newRow, newCol)) &&
                    (positions[newRow][newCol] == null || !positions[newRow][newCol].color.equals(color))) {
                possibleMoves.add(new Position(newRow, newCol));
            }
        }

        this.possibleMoves=possibleMoves;
    }

    public List<Position> findMyPieces(String color, Piece[][] positions)
    {
        List<Position> myPieces = new ArrayList<>();
        for (int i=0;i<8;i++)
        {
            for (int j=0;j<8;j++)
            {
                //TODO hacer esto más bonito con Optional
                if(positions[i][j] != null && positions[i][j].color.equals(color))
                {
                    myPieces.add((new Position(i, j)));
                }
            }
        }
        return myPieces;
    }

    public List<Position> calculatePlayerMoves(String color, Piece[][] positions)
    {
        List<Position> possibleMoves = new ArrayList<>();
        this.possibleMoves= possibleMoves;
        for (int i=0;i<8;i++)
        {
            for (int j=0;j<8;j++)
            {
                //TODO hacer esto más bonito con Optional
                if(positions[i][j] != null && positions[i][j].color.equals(color))
                {
                    calculatePieceMoves(new Position(i, j),positions);
                    possibleMoves.addAll(this.possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    public boolean jaque(Position kingPosition, List<Position> enemiAttacList)
    {
        for (Position p: enemiAttacList){
            if(p.equals(kingPosition)){
                return true;
            }
        }
        return false;
    }

    public List<Position> getPossibleMoves()
    {
        return this.possibleMoves;
    }

    public boolean containsPosition(Position p)
    {
        boolean a = false;

        for (Position position : this.possibleMoves) {
            if(position.equals(p))
                a = true;
        }
        return a;
    }

}

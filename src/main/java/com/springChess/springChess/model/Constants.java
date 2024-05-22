package com.springChess.springChess.model;

public class Constants {

    public static final int[][] KNIGHT_MOVES = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
    public static final int[][] KING_MOVES = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public static final int[][] WHITE_PAWN_MOVES = {{+1,-1},{+1,0},{+1,+1},{+2,0}};

    public static final int[][] BLACK_PAWN_MOVES = {{-1,-1},{-1,0},{-1+1},{-2,0}};

}

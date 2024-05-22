package com.springChess.springChess.model;

public class Piece {

    public String name;
    public String color;

    public Piece(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIconKey(){
        return color + "_" + name;
    }

    @Override
    public String toString() {
        return "Piece [name=" + name + ", color=" + color + "]";
    }

    

}

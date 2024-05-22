package com.springChess.springChess.model.entities;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String logs;

    private String blackPlayer="";
    private String whitePlayer="";

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

	public String getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(String blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public String getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}
	
	public void asignPlayerColor(String playerName) {
		if(this.whitePlayer.equals(""))
			setWhitePlayer(playerName);
    	else if(this.blackPlayer.equals("") && !this.whitePlayer.equals(playerName))
			setBlackPlayer(playerName);
    	else
    		System.out.println("Players already set or incorrect player");
	}
    
    public String returnPlayerColor(String playerName) {
    	if(playerName.equals(this.whitePlayer))
    		return "White";
    	else if(playerName.equals(this.blackPlayer))
    		return "Black";
    	return "Invalid";
    }

	@Override
	public String toString() {
		return "Game [id=" + id + ", logs=" + logs + ", blackPlayer=" + blackPlayer + ", whitePlayer=" + whitePlayer
				+ "]";
	}
    
    
	
}


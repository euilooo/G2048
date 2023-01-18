package game;

/*
Name: Olina Wang
Course: ICS4U
Date: June 17, 2021
Summary: This class resembles a square board object that contains movable tiles
*/

public class Board {
	//fields
	protected Tile [][] tiles;
	protected Tile [][] merged; 
	private int dimen; 
	protected static int score; 
	
	/**
	   * construcor
	   * @param none
	   * @return void
	   */
	public Board() {
		dimen = 0;
		score = 0;
	}
	
	
	/**
	   * constructor
	   * @param size: int - dimension of the board
	   * @return none
	   */
	public Board(int size) {
        dimen = size; 
        score = 0; 
		tiles = new Tile[dimen][dimen];
		addRandomTile();
		addRandomTile();
		System.out.println("board dimen: "+ dimen);
	}
	
	
	/**
	   * accessor method
	   * @param none
	   * @return int - score of the game.
	   */
	public int getScore(){
        return score;
    }

	
	/**
	   * this method add a tile with value of 4 or 2 to an empty spot
	   * @param none
	   * @return void.
	   */
	public void addRandomTile(){
       	int row = 0, col = 0; 
       	double rand;
       	do {
       		row = (int)( Math.random() * dimen );
               col = (int)( Math.random() * dimen );
               rand = Math.random();
       	} while (tiles[row][col] != null);
           
  
        if ( rand < 0.5 ){
            tiles[row][col] = new Tile( 4 );
        } else {
        	tiles[row][col] = new Tile( 2 );
        }
    }
	
    
	/**
	   * this method calls the moveVertical method 
	   * moving the tiles upwards
	   * @param none
	   * @return boolean - if the positions have shifted.
	   */
	public boolean moveUp() {
    	boolean moved = false;
		merged = new Tile [dimen][dimen];
    	for ( int col = 0; col < dimen; col++ ){
        	//limit =0; 	
            for ( int row = 1; row < dimen; row++ ){
                if ( tiles[row][col] != null) {// && row>=limit ){
                	if (moved) {
                    	moveVertical( row, col, -1 ); 
                	} else {
                		moved = moveVertical( row, col, -1 ); 
                	}
                }
            }
    	}
    	return moved; 
    }
	
    
	/**
	   * this method calls the moveVertical method 
	   * moving the tiles downwards
	   * @param none
	   * @return boolean - if the positions have shifted.
	   */
    public boolean moveDown() {
		boolean moved = false;
		merged = new Tile [dimen][dimen];
		for ( int col = 0; col <dimen; col++ ){
        	//limit = 3;
            for ( int row = dimen-2; row >=0; row-- ){
                if ( tiles[row][col] != null) {//&& row <=limit){
                	if (moved) {
                		moveVertical( row, col, 1 );
                	} else {
                		moved = moveVertical( row, col, 1 );
                	}
                }
            }
        }
		return moved; 
	}
	
	
    /**
	   * this method calls the moveHorizontal method 
	   * moving the tiles left
	   * @param none
	   * @return boolean - if the positions have shifted.
	   */
	public boolean moveLeft() {
		boolean moved = false; 
		merged = new Tile [dimen][dimen];
		for ( int row = 0; row < dimen; row++ ){
			//limit = 0; 
            for ( int col = 1; col < dimen; col++ ){
                if ( tiles[row][col] != null) {//&& col>limit) {
                	if (moved) {
                        moveHorizontal( row, col, -1 );
                	} else {
                		moved =  moveHorizontal( row, col, -1 );
                	}
                }
            }
        }
		return moved; 
	}
	
	
	/**
	   * this method calls the moveHorizontal method 
	   * moving the tiles right
	   * @param none
	   * @return boolean - if the positions have shifted.
	   */
	public boolean moveRight() {
		boolean moved = false; 
		merged = new Tile [dimen][dimen];
		for ( int row = 0; row < dimen; row++ ){
            //limit = 3;  
			for ( int col = dimen-2; col >=0; col-- ){
                if ( tiles[row][col] != null) { //&& col<limit) {
                	if (moved) {
                		moveHorizontal( row, col, 1 );
                	} else {
                		moved = moveHorizontal( row, col, 1 );
                	}
                }
            }
        }
		return moved; 
	}

	
	/**
	   * this method moves the individual tiles vertically
	   * @param row: int - x position of the tile
	   * @param col: int - y position of the tile
	   * @param dir: int - direction of movement
	   * @return boolean - if the positions have shifted.
	   */
	public boolean moveVertical(int row, int col, int dir) {
		//go one row forward at a time, if null, take one step, recursive loop, util no moveleft
		if (row+dir<0||row+dir>dimen-1) {
			return false; 
		}
		if (tiles [row+dir][col]==null) { 
			tiles [row+dir][col]=tiles[row][col];
			tiles[row][col]=null;
			moveVertical(row+dir, col, dir);
			return true;
		} else if (tiles [row+dir][col].getValue()==tiles[row][col].getValue()) {
			if (G2048.toMove&&merged [row+dir][col]==null) {
				int points = tiles[row][col].getValue()*2; 
				tiles [row+dir][col].setValue(points);
				tiles[row][col]=null;
				merged [row+dir][col]=tiles [row+dir][col];
				score +=points; 
			}
			return true;
		} 
		return false; 	
	}   
	
	/**
	   * this method moves the individual tiles horizontally
	   * @param row: int - x position of the tile
	   * @param col: int - y position of the tile
	   * @param dir: int - direction of movement
	   * @return boolean - if the positions have shifted.
	   */
	public boolean moveHorizontal(int row, int col, int dir) {
		if (col+dir<0||col+dir>dimen-1) {
			return false; 
		}
		if (tiles [row][col+dir]==null) {
			tiles [row][col+dir]=tiles[row][col];
			tiles[row][col]=null;
			moveHorizontal(row, col+dir, dir);
			return true;
		} else if (tiles [row][col+dir].getValue()==tiles[row][col].getValue()) {
			if (G2048.toMove&&merged [row][col+dir]==null) {
				int points = tiles[row][col].getValue()*2; 
				tiles [row][col+dir].setValue(points);
				tiles[row][col]=null;
				merged [row][col+dir]=tiles [row][col+dir];
				score +=points;
			}
			return true;
		}
		return false; 
	}
	
	
}

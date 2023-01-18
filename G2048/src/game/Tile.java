package game;

/*
Name: Olina Wang
Course: ICS4U
Date: June 17, 2021
Summary: This class represent a numbered, coloured tile object
	 references: 
 	 colour table: https://rosettacode.org/wiki/2048#Java
*/
 
import java.awt.Color;

public class Tile{

	//fields
	private int value; 
	private final Color[] colorTable = {
            new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3),
            new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
            new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
            new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)};
	private Color colour;
	
	
	/**
	   * constructor
	   * @param none
	   * @return void
	   */
	public Tile() {
		value = 0; 
		//determine colour of the tile using a math Logarithmic function
		colour = colorTable[(int) (Math.log(value) / Math.log(2)) + 1];
	}
	
	
	/**
	   * constructor
	   * @param num: int - value of tile
	   * @return void
	   */
	public Tile(int num) {
		value = num;
		//determine colour of the tile using a math Logarithmic function
		colour = colorTable[(int) (Math.log(value) / Math.log(2)) + 1];
	}
	
	
	/**
	   * accessor method
	   * @param none
	   * @return int - value of tile
	   */
	public int getValue() {
		return value; 
	}
	
	
	/**
	   * this method changes the value of the tile
	   * @param num: int - the new value of the tile
	   * @return void
	   */
	public void setValue(int num) {
		value = num; 
		if (value>=2048) {
			colour = Color.red; 
		}else {
			//determine colour of the tile using a math Logarithmic function
			colour = colorTable[(int) (Math.log(value) / Math.log(2)) +1];

		}
	}

	
	/**
	   * accessor method
	   * @param none
	   * @return Color - color of tile
	   */
	public Color getColour() {
		return colour;
	}
}

	


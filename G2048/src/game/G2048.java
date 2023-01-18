package game;
/*
Name: Olina Wang
Course: ICS4U
Date: June 17, 2021
Summary: This class deals with the GUI of the program and checks the state of the game 
	 references: 
	 inserting pictures: https://stackoverflow.com/questions/22240328/how-to-draw-a-gif-animation-in-java
	 sound effect: https://www.youtube.com/watch?v=3q4f6I5zi2w
*/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

public class G2048 extends JFrame{

	//fields
	private Board board;
	private int dimen; 
	private int width, height, size, xcord, ycord; 
	protected static boolean toMove; 
	private boolean won; 
	private Image imgIcon;
	private int highestScore; 
	public enum State {
        prep, running, won, extension, lost
    }
	private State gamestate = State.prep; 
	final Color[] colorTable = {
            new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3),
            new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
            new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
            new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)};
	
	
	/**
	   * This is the constructor, initialize fields
	   * @param none
	   * @return void
	   */
	public G2048(int size) {
		setSize(900, 700); 
		getContentPane().setBackground(new Color(0xFAF8EF));
		getContentPane().setLayout(null);
        setTitle("2048");
        setVisible(true);  
        dimen = size;

        // Add window listener.
        addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        //add mouse listen to start game
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                	start();
                	won = false;
                    repaint();
            }
        });
        //add key listener to responds to user's direction commands
        addKeyListener(new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	              boolean moved = false; 
	              boolean check = true; 
	              switch (e.getKeyCode()) {
                  case KeyEvent.VK_UP: 
                	  toMove = true;
                	  moved = board.moveUp();
                	  check = true;
                	  break;
                  case KeyEvent.VK_DOWN: 
                	  toMove = true;
                	  moved = board.moveDown(); 
                	  check = true;
                	  break;
                  case KeyEvent.VK_LEFT: 
                	  toMove = true;
                	  moved = board.moveLeft();
                	  check = true;
                	  break;
                  case KeyEvent.VK_RIGHT: 
                	  toMove = true;
                	  moved = board.moveRight();
                	  check = true;
                	  break;
                  case KeyEvent.VK_ENTER:
                	  gamestate = State.running; 
                	  start();
                	  check =false;
              }
	              if (moved&&check) {
	            	  board.addRandomTile(); 
	            	  if (gamestate == State.running && !won) {
	            		  checkWin();
	            	  }
	              } else if (!moved&&check) {
	            	  checkLost(); 
	              }
              repaint();
	          }
	   });
    }		

	
	/**
	   * This method is used to contruct the GUI
	   * @param g: Graphics
	   * @return void
	   */
	public void paint( Graphics g )
    {
		//set up cordinates
		super.paint( g );
        Graphics2D g2 = (Graphics2D)g;
        width = getFieldWidth();
		height = getFieldHeight();
		size = width-100; 
		if (width>height) {
			size = height-100;
		}
		FontMetrics fm = g.getFontMetrics();
		xcord = width/2-size/2;
	    ycord = height/2-size/2+60;
		
        //set basic colours for all gamestates
        g.setColor(new Color(0xBBADA0));
        g.fillRoundRect(xcord, ycord, size, size, 15, 15);
       	g.setColor(new Color(0xFFEBCD));
        g.fillRoundRect(xcord+size/20, 
        				ycord+size/20, 
        				size-size/10, size-size/10, 7, 7);
      
        //interface when waiting to start the game
        if (gamestate==State.prep) { 
            
        	g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, size/4));
            fm = g.getFontMetrics();
            g.drawString("2048", width/2-fm.stringWidth("2048")/2, ycord+size*2/5);
            g.setFont(new Font("SansSerif", Font.PLAIN, size/25));
            g.setColor(Color.black);
            fm = g.getFontMetrics();
            g.drawString( "Click to Start", 
            			  width/2-fm.stringWidth("Click to Start")/2, 
            			  ycord + 2* size/3 );
            g.drawString( "Use Arrow Keys to move", 
            			  width/2-fm.stringWidth("Use Arrow Keys to move")/2, 
            			  ycord+ 3*size/4 );
            board = new Board();
        } else if (gamestate == State.running) { //interface when game is in progress
        	g.setColor(new Color(0xBBADA0));
            g.fillRoundRect(xcord, ycord, size, size, 15, 15);
        	for ( int i = 0; i < dimen; i++ )
            {
                for ( int j = 0; j < dimen; j++ )
                {
                	
                	if (board.tiles[i][j]!=null)
                		drawTile( g2, i,j );
                	else {
                		g.setColor(new Color(0xCDC1B4));
                		int tilesize = (size-size/10)/dimen; 
                        g.fillRoundRect(xcord+size/28 + j * (tilesize+tilesize/20), 
                        				ycord+size/28 + i * (tilesize+tilesize/20), 
                        				tilesize, tilesize, 7, 7);
                	}
                }
            }
        } else if ( gamestate == State.lost){ // when there are no available moves left
        	g.setColor( Color.gray );
            g.fillRoundRect(xcord+size/20, 
		    				ycord+size/20, 
		    				size-size/10, size-size/10, 7, 7);
            g.setColor(Color.black);
            g.setFont(new Font("SansSerif", Font.PLAIN, size/30));
            fm = g.getFontMetrics();
            String str = "Click to restart";
            g.drawString( str, width/2-fm.stringWidth(str)/2, ycord+4*size/5 );
            g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, size/6));
            fm = g.getFontMetrics();
            str = "GAME";
            g.drawString(str , width/2-fm.stringWidth(str)/2, ycord+ 2*size/5);
            str = "OVER";
            g.drawString(str , width/2-fm.stringWidth(str)/2, ycord+ 2*size/5+fm.getAscent());

            	try {
					imgIcon = ImageIO.read(new URL("https://freepngimg.com/thumb/fail_stamp/5-2-fail-stamp-png-image-thumb.png"));
					g.drawImage(imgIcon, width/2-imgIcon.getWidth(this)/2, ycord+size/2, this);
            	} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
            
        } else if (gamestate ==State.won) { //when player creates their first 2048 tile
        	g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.PLAIN, size/40));
        	fm = g.getFontMetrics();
        	g.drawString("You made it!", width/2-fm.stringWidth("you made it!")/2, ycord + size/2);
        	g.drawString("Press ENTER to continue", width/2-fm.stringWidth("Press ENTER to continue")/2, ycord + 4*size/7);

        	g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, size/8));
            fm = g.getFontMetrics();
            g.drawString("CONGRATS!", width/2-fm.stringWidth("CONGRATS!")/2, ycord + 2* size/5);
			try {
				imgIcon = ImageIO.read(new URL("https://media.tenor.com/images/52752f66df186ee1934902da52e5e170/tenor.gif"));
				g.drawImage(imgIcon, width/2-imgIcon.getWidth(this)/2, ycord+size/2+size/10, this);
			} catch (MalformedURLException e1) {
				System.out.println("error");
				e1.printStackTrace();
			} catch (IOException e1) {
				System.out.println("error");
				e1.printStackTrace();
			}
        }  
        //show current score and high score
        g.setFont(new Font("SansSerif", Font.PLAIN, size/40));
        g.setColor(Color.black);
        fm = g.getFontMetrics();
        g.drawString( "Score: " + board.getScore(), xcord+size/10, ycord-size/35 );
		try {
			checkHighscore();
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
        g.drawString( "Highest Score: " + highestScore,
             xcord + size - fm.stringWidth("Highest Score: " + highestScore)-size/10,ycord-size/35 );
    }
	
	
	/**
	   * This method is used for the draw individual tiles
	   * @param g: Graphics2D
	   * @param r: int - row that the tile is in
	   * @param c: int - column that the tile is in
	   * @return void
	   */
	public void drawTile(Graphics2D g, int r, int c) {
		int value = board.tiles[r][c].getValue();
        g.setColor(board.tiles[r][c].getColour());
        int tilesize = (size-size/10)/dimen; 
        g.fillRoundRect(xcord+size/28 + c * (tilesize+tilesize/20), 
        				ycord+size/28 + r * (tilesize+tilesize/20), 
        				tilesize, tilesize, 7, 7);
      
        //record value of tile
        String s = String.valueOf(value); 
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, size/40));
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int dec = fm.getDescent();
        int x = xcord+size/28 + c * (tilesize+tilesize/20) + (tilesize - fm.stringWidth(s)) / 2;
        int y = ycord+size/28 + r * (tilesize+tilesize/20) + (asc + (tilesize - (asc + dec)) / 2);
        g.drawString(s, x, y);
    }

	
	/**
	   * This method is used start the game 
	   * change music accordingly
	   * @param none
	   * @return void
	   */
	public void start() {

		if (gamestate != State.running) {
            gamestate = State.running;
            board = new Board(dimen);
        }
	}
	
	
	/**
	   * accessor method
	   * @param none
	   * @return int - field width of the window.
	   */
	public int getFieldWidth() {
		return getContentPane().getBounds().width;
	}
	
	
	/**
	   * accessor method
	   * @param none
	   * @return int - field height of the window.
	   */
	public int getFieldHeight() {
		return getContentPane().getBounds().height;
	}
	
	
	/**
	   * This method is used to check the high score. 
	   * reads from the txt file, and compares with current score
	   * @param none
	   * @return void.
	   */
	public void checkHighscore() throws IOException {
		highestScore = 0; 
		File textFile = new File ("HighScore.txt");
		//make a file if doesn't currently exist
		if (!textFile.exists()) {
			textFile.createNewFile();
		}
		//read scores from file
		Scanner sc = new Scanner(textFile);
		while (sc.hasNextInt()){
			int nextscore = sc.nextInt();
			if (nextscore > highestScore)
				highestScore = nextscore;
		}
		sc.close();
		if (highestScore <=board.getScore()) {
			highestScore = board.getScore(); 
		}
	}
	
	
	/**
	   * This method is used for checking if player has lost
	   * if the board is full and there are no available moves left
	   * if player has lost, record their score in txt file
	   * change music accordingly
	   * @param g: none
	   * @return boolean - whether the current board is full
	   */
	public boolean checkLost() {
		boolean full = true;
		for (int row = 0; row< dimen; row++) {
			for (int col = 0; col < dimen; col++) {
				if (board.tiles[row][col]==null) {//there is an empty spot
					full = false;
				}
			}
		}
		if (full) {//board is full
			toMove = false; 
			//there are no available moves
			if (!board.moveUp()&&!board.moveDown()&&!board.moveLeft()&&!board.moveRight()) { 
				gamestate = State.lost; 


				//record score in txt file
				Writer writer = null; 
				try {
					writer = new FileWriter("HighScore.txt", true);
				} catch (IOException e){
					e.printStackTrace();
				}
				
				//save user's name and score into the file
				PrintWriter output = new PrintWriter (writer);
				output.print(board.getScore()+"\n");
				output.close();
			}
		}
		return full;
	}
	
	
	/**
	   * This method is used for check whether player has reached 2048
	   * change music accordingly
	   * @param none
	   * @return boolean - whether the player has won the game
	   */
	public boolean checkWin() {
		boolean win = false;
		
		for (int row  = 0; row < dimen; row++) {
			for (int col = 0; col < dimen; col++) {
				if (board.tiles [row][col]!= null && board.tiles [row][col].getValue()==2048) {
					win = true; 
					this.won = true;
					break;
				}
			}
		}
		
		if (win) {
			gamestate = State.won;

		}
		return win; 
	}
}

package game;
/*
Name: Olina Wang
Course: ICS4U
Date: June 17, 2021
Summary: This class introduces the game to users and allows for difficulty selection
	 reference: 
	 2048 rules: https://levelskip.com › puzzle › How-to-play-2048
	 JCheckBox: https://www.javatpoint.com/java-jcheckbox
	 grid bag layout: https://www.youtube.com/watch?v=ZipG38DJJK8
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

	private static int size = 4;
	private static JCheckBox cb3,cb4,cb5;  

	/**
	   * this method creates a JFrame that asks for user's peference
	   * calls on G2048 basedon user's choice
	   * @param none
	   * @return void
	   */
	public static void createSetup(){
		JFrame f = new JFrame("Set Up"); 
		f.setSize(550,400);
		JPanel p = new JPanel(new GridBagLayout()); 
		p.setBackground(new Color(0xFAF8EF));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets (15,0,35,0);
		JLabel l1 = new JLabel("Before we start the game, Let's first get a few things set up...");
		l1.setFont(new Font("MS Sans Serif", Font.BOLD, 15));
		l1.setForeground(Color.RED);		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth=6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(l1,gbc);	
		
        JLabel l2 = new JLabel("Choose a Game Layout:");
		l2.setFont(new Font("MS Sans Serif", Font.BOLD, 13));
		gbc.insets = new Insets (0,0,0,0);
        gbc.gridx = 0;
		gbc.gridy = 1;
		p.add(l2,gbc);
		
		//hint
        Box box = Box.createHorizontalBox();
        ImageIcon icon = createImageIcon("info.png");
        Image image = icon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);
        box.add(new JLabel(icon));
        box.add(new JLabel("Hint: smaller dimensions are more difficult!"));
        gbc.gridx = 0;
		gbc.gridy = 2;        
		p.add(box,gbc);
	    
		//checkboxes
		cb3 =new JCheckBox("3x3");
		gbc.insets = new Insets (0,0,5,40);
		gbc.gridx = 0;
		gbc.gridy = 3;        
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(cb3,gbc);
		cb3.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JCheckBox cb = (JCheckBox) event.getSource();
		        if (cb.isSelected()) {
		            size =3; 
		        } else {
		            // check box is unselected, do something else
		        }
		    }
		});
		cb4 = new JCheckBox("4x4");
        gbc.gridx = 1;
		gbc.gridy = 3;        
        p.add(cb4,gbc);
        cb4.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JCheckBox cb = (JCheckBox) event.getSource();
		        if (cb.isSelected()) {
		            size =4; 
		        } 
		    }
		});
        cb5 = new JCheckBox("5x5");
        gbc.gridx = 2;
		gbc.gridy = 3;        
        p.add(cb5,gbc);	
        cb5.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JCheckBox cb = (JCheckBox) event.getSource();
		        if (cb.isSelected()) {
		            size =5; 
		        } 
		    }
		});
        //Jbutton
        JButton b = new JButton("Next>>");
		gbc.insets = new Insets (15,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 4;
		p.add(b,gbc); 
        b.addActionListener(new ActionListener(){          
			public void actionPerformed(ActionEvent arg0) {
				new G2048(size);
				f.dispose(); 
			}
        });
		f.add(p);
		f.setVisible(true);
	}
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Main.class.getResource(path);

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
    
    /**
	   * this method isthe main method
	   * gives instructions to the game
	   * @param 
	   * @return void
	   */
	public static void main (String [] args) {
		JFrame f = new JFrame("Welcome to 2048"); 
		f.setSize(550,400);
		JPanel p = new JPanel(new GridBagLayout()); 
		p.setBackground(new Color(0xFAF8EF));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets (15,0,5,0);
		JLabel l1 = new JLabel("Instructions:");
		l1.setFont(new Font("MS Sans Serif", Font.BOLD, 25));
		l1.setForeground(Color.RED);		
		gbc.gridx = 0;
		gbc.gridy = 0;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		p.add(l1,gbc);

		JLabel l2 = new JLabel("The rules are simple. You just need to move the tiles! ");
		gbc.insets = new Insets (0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		p.add(l2,gbc);

		JLabel l3 = new JLabel("Every time you move one, another tile pops up in a random manner anywhere in the box. ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		p.add(l3,gbc);

		JLabel l4 = new JLabel("When two tiles with the same number on them collide with one another as you move them, ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		p.add(l4,gbc);

		JLabel l5 = new JLabel("they will merge into one tile with the sum of the numbers written on them initially.");
		gbc.gridx = 0;
		gbc.gridy = 4;
		p.add(l5,gbc);

		JLabel l6 = new JLabel("The goal of this game is to reach 2048!");
		l6.setFont(new Font("MS Sans Serif", Font.BOLD, 15));
		l6.setForeground(Color.RED);
		gbc.insets = new Insets (15,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 7;
		p.add(l6,gbc);

		JLabel l7 = new JLabel("If you think that is too easy, I have a challenge for you!");
		gbc.insets = new Insets (0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 8;
		p.add(l7,gbc);

		JLabel l8 = new JLabel("Aim for numbers higher than 2048.");
		gbc.gridx = 0;
		gbc.gridy = 9;
		p.add(l8,gbc);

		JLabel l10 = new JLabel("Yes, it is possible that you could end up with a number higher than 2048 in this game.");
		gbc.gridx = 0;
		gbc.gridy = 10;
		p.add(l10,gbc);
        //box
		Box box = Box.createHorizontalBox();
		ImageIcon icon = createImageIcon("2048-logo.png");
        Image image = icon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);
        box.add(new JLabel(icon));
        box.add(new JLabel("        "));
        //button
		JButton b = new JButton("Next>>");
		b.addActionListener(new ActionListener(){          
			public void actionPerformed(ActionEvent arg0) {
	            createSetup();
	            f.dispose();
			}
        });
		gbc.insets = new Insets (15,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 12;
		box.add(b);
		p.add(box,gbc);

		f.add(p);
		f.setVisible(true);
		
	}
}

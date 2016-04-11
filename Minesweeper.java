import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Minesweeper extends JFrame{
	
	private JPanel GamePanel, panel;
	private Field[][] field;
	private JButton button;
	private static int numOfBombs = 10;
	
	public static void main( String[] args ){
		
		new Minesweeper(8);
	}// End of MAIN
	
	public Minesweeper( int numOfFields ){
		
		/* ----- SETTING UP THE FRAME ----- */
		
		this.setSize(480, 450);
		this.setTitle("Minesweeper");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setResizable(false);
		
		/* -------- GENERATE BUTTON ------- */
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension( 400, 50 ));
		
		button = new JButton( "Generate" );
		button.setPreferredSize(new Dimension( 110, 35 ));
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		
		ListenForButton buttonListener = new ListenForButton();
		button.addActionListener(buttonListener);
		
		panel.add(button);
		
		/* -------------------------------- */
		
		GamePanel = new JPanel();
		GamePanel.setPreferredSize(new Dimension( 480, 400 ));
		
		/* -------------------------------- */
		
		/* ------------ FIELDS ------------ */
		
		field = new Field[numOfFields][numOfFields];
		
		ListenForMouse mouseListener = new ListenForMouse();
		
		for( int i = 0; i < numOfFields; i++ )
			for( int j = 0; j < numOfFields; j++ ){
			
				field[i][j] = new Field();
				field[i][j].setState( Field.FieldState.EMPTY );
				GamePanel.add(field[i][j]);
				field[i][j].addMouseListener(mouseListener);
			}
		
		/* -------------------------------- */
		
		button.setFocusable(false);
		this.add(panel);
		this.add(GamePanel);
		this.setVisible(true);
		
	}// End of CONSTRUCTOR
	
	private class ListenForMouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			for( int i = 0; i < 8; i++ )
				for( int j = 0; j < 8; j++ ){
					if( e.getModifiers() == InputEvent.BUTTON1_MASK && e.getSource() == field[i][j] ){
						
						switch( field[i][j].getState() ){
						
							case NUMBER: 
								field[i][j].setText(field[i][j].getMark()); 
								break;
							case EMPTY: 
								field[i][j].setBackground(Color.LIGHT_GRAY); 
								break;
							case BOMB: 
								field[i][j].setText(field[i][j].getMark());
								
								for( int a = 0; a < 8; a++ )
									for( int b = 0; b < 8; b++ )
										field[a][b].setText(field[a][b].getMark());
								
								JOptionPane.showMessageDialog(Minesweeper.this, "Boom :(", "Bomb", JOptionPane.ERROR_MESSAGE);
								clearTable(field, 8);
								break;
							case FLAG:
								break;
						}
					}// End of LEFT_BUTTON
					
					if( e.getModifiers() == InputEvent.BUTTON3_MASK && e.getSource() == field[i][j] ){
						
						//field[i][j].setState(Field.FieldState.FLAG);
						//field[i][j].setMark("F");
						//field[i][j].setText(field[i][j].getMark());
						//stateChecker(field, 8);
						
					}// End of RIGHT_BUTTON
			
				}// End of j for loop
			
		}// End of mouseClicked

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}// End of ListenForMouse
	
	private class ListenForButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if( e.getSource() == button ){
				
				// Clearing colors
				for( int i = 0; i < 8; i++ )
					for( int j = 0; j < 8; j++ )
						field[i][j].setForeground(Color.BLACK);
				
				clearTable( field, 8 );
				
				generateFields(field, 8);
			}// End of button
			
			
			
		}// End of actionPerformed
		
		
	}// End of ListenForButton
	
	private static void generateFields( Field[][] field, int numOfFields ){
		
		/* ------------ BOMBS ------------- */
		
		Dimension[] sameRandoms = new Dimension[10];
		
		Arrays.fill(sameRandoms, new Dimension( -1, -1 ));
		
		int x, y;
		
		for( int i = 0; i < 10; i++ ){
			
			do{
				
			x = (int)( Math.random() * numOfFields );
			y = (int)( Math.random() * numOfFields );
			
			}while( isTaken(x, y, sameRandoms) );
			
			sameRandoms[i].width = x;
			sameRandoms[i].height = y;
			
			field[x][y].setState(Field.FieldState.BOMB);
			
			System.out.println(x + " " + y);
		}
		
		System.out.println();
		
		/* -------------------------------- */
		
		/* ----------- NUMBERS ------------ */
		
		int num = 0;
		
		for( int i = 0; i < numOfFields; i++ ){
			for( int j = 0; j < numOfFields; j++ ){
				
				if( field[i][j].getState() == Field.FieldState.BOMB )
					continue;
				
				if( fieldExist(i-1, j-1) && field[i-1][j-1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i-1, j) && field[i-1][j].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i-1, j+1) && field[i-1][j+1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i, j-1) && field[i][j-1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i, j+1) && field[i][j+1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i+1, j-1) && field[i+1][j-1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i+1, j) && field[i+1][j].getState() == Field.FieldState.BOMB )
					num++;
				
				if( fieldExist(i+1, j+1) && field[i+1][j+1].getState() == Field.FieldState.BOMB )
					num++;
				
				if( num == 0 )
					field[i][j].setState(Field.FieldState.EMPTY);
				
				else{
					
					field[i][j].setState(Field.FieldState.NUMBER);
					field[i][j].setNumber(num);
					field[i][j].setNumberColor();
				}
				
				num = 0;
				
			}
			
		}
		
		/* -------------------------------- */
		
		stateChecker( field, numOfFields );
		
	}// End of generateFields
	
	public static boolean isTaken( int x, int y, Dimension[] d ){
		
		boolean taken = false;
		
		for( Dimension dim: d ){
			
			if( x == dim.width && y == dim.height )
				taken = true;
		}
		
		return taken;
		
	}// End of isTaken
	
	public static void stateChecker( Field[][] field, int numOfFields ){
		
		for( int i = 0; i < numOfFields; i++ ){
			
			for( int j = 0; j < numOfFields; j++ ){
				
				switch( field[i][j].getState() ){
				
					case BOMB : 
						field[i][j].setMark("X"); 
						break;
					case NUMBER : 
						field[i][j].setMark("" + field[i][j].getNumber()); 
						break;
					case EMPTY : 
						field[i][j].setMark(""); 
						break;
					case FLAG : 
						field[i][j].setMark("F");
						field[i][j].setNumber(-1);
						break;
				}
			}
		}
		
	}// End of stateChecker
	
	public static void clearTable( Field[][] field, int numOfFields ){
		
		for( int i = 0; i < numOfFields; i++ ){
					
			for( int j = 0; j < numOfFields; j++ ){
						
				field[i][j].setState(Field.FieldState.EMPTY);
				field[i][j].setBackground(Color.WHITE);
				field[i][j].setText("");
			}
		}
		
	}// End of clearTable
	
	public static boolean fieldExist( int x, int y ){
		
		boolean check = true;
		
		if( x < 0 || x > 7 )check = false;
		if( y < 0 || y > 7 )check = false;
		
		return check;
		
	}// End of fieldExist
	
	
}// End of CLASS
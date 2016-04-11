import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Field extends JButton{
	
	public static enum FieldState{
		
		EMPTY,
		NUMBER,
		BOMB,
		FLAG
	}
	
	public FieldState state = FieldState.EMPTY;
	private int number = -1;
	private String mark = "";
	
	public Field(){
		
		this.setPreferredSize(new Dimension(50, 40));
		this.setBackground(Color.WHITE);
	}
	
	public FieldState getState(){
		
		return this.state;
	}
	
	public void setState( FieldState state ){
		
		this.state = state;
	}
	
	public void setNumber( int number ){
		
		this.number = number;
	}
	
	public int getNumber(){
		
		return this.number;
	}
	
	public void setNumberColor(){
		
		switch( this.number ){
		
			case -1: this.setForeground(Color.BLACK); break;
			case 1: this.setForeground(Color.BLUE); break;
			case 2: this.setForeground(new Color(50, 205, 50)); break;
			case 3: this.setForeground(Color.RED); break;
			case 4: this.setForeground(new Color(128, 128, 0)); break;
		}
		
		if( state == FieldState.BOMB )
			this.setForeground(Color.BLACK);
	}
	
	public void setMark( String mark ){
		
		this.mark = mark;
	}
	
	public String getMark(){
		
		return this.mark;
	}
}
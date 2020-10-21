package model;

public class Grid {
	//Atributes
	private char letter;
	private int number;
	private Grid nextGrid;
	private Grid previousGrid;
	private Grid upGrid;
	private Grid downGrid;
	//Methods
	public Grid() {
		nextGrid=null;
		previousGrid=null;
		upGrid=null;
		downGrid=null;
	}
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Grid getNextGrid() {
		return nextGrid;
	}
	public void setNextGrid(Grid nextGrid) {
		this.nextGrid = nextGrid;
	}
	public Grid getPreviousGrid() {
		return previousGrid;
	}
	public void setPreviousGrid(Grid previousGrid) {
		this.previousGrid = previousGrid;
	}
	public Grid getUpGrid() {
		return upGrid;
	}
	public void setUpGrid(Grid upGrid) {
		this.upGrid = upGrid;
	}
	public Grid getDownGrid() {
		return downGrid;
	}
	public void setDownGrid(Grid downGrid) {
		this.downGrid = downGrid;
	}
	
}

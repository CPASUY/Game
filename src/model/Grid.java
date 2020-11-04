package model;

public class Grid {
	//Atributes
	private int number;
	private char letter;
	private int numCol;
	private boolean addmirror;
	private char mirror;
	private Grid nextGrid;
	private Grid previousGrid;
	private Grid upGrid;
	private Grid downGrid;
	private String box;
	//Methods
	/**Grid
	 * @param n!=null
	 * @param l!=null
	 * @param num!=null
	 */
	public Grid(int n,char l,int num) {
		number=n;
		letter=l;
		mirror=' ';
		nextGrid=null;
		previousGrid=null;
		upGrid=null;
		downGrid=null;
		numCol=num;
		addmirror=false;
		box="[ ]";
	}
	public boolean getAddmirror() {
		return addmirror;
	}
	public void setAddmirror(boolean ad) {
		addmirror = ad;
	}
	public String getBox() {
		return box;
	}
	public void setBox(String box) {
		this.box = box;
	}
	public int getNumCol() {
		return numCol;
	}
	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public char getLetter() {
		return letter;
	}
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public char getMirror() {
		return mirror;
	}
	public void setMirror(char mirror) {
		this.mirror = mirror;
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

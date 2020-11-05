package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GridManagement {
	//Constants
	/*The rute file of the serializable file
	 */
		public static final String PLAYERS_FILE_NAME="data/players.bbd";
	//Atributes
	private UserGame root;
	private Grid first;
	//Methods
	/**GridManagement
	 */
	public GridManagement() {
		first=null;
		try {
			loadRoot();
		}catch(IOException io) {
			System.err.println("Information could not be loaded");
		} catch (ClassNotFoundException cf) {
			cf.printStackTrace();
		}
	}
	public Grid getFirst() {
		return first;
	}
	/**addPlayer
     *Method to add player
     * @param n!=null
     * @param score!=null
     * @param r!=null
     * @param c!=null
     * @param m!=null
     * */
	public void addPlayer(String n,int score,int r,int c,int m) throws IOException {
		UserGame us=new UserGame(n,score,r,c,m);
		if(root== null) {
			root = us;
			saveRoot();
		}else {
			addPlayer(root,us);
		}
	}
	/**addPlayer
     *Method to add player
     * @param c!=null
     * @param u!=null
     * */
	public void addPlayer(UserGame c,UserGame u) throws IOException {
		if(u.getScore()<=c.getScore()	&&	c.getL()==null) {
			c.setL(u);
			u.setF(c);
		}else if(u.getScore()>c.getScore( ) && c.getR()==null) {
			c.setR(u);
			u.setF(c);
		}else {
			if(u.getScore()<=c.getScore() && c.getL()!= null) {
				addPlayer(c.getL(),u);
			}else {
				addPlayer(c.getR(),u);
			}
		}
		saveRoot();
	}
	/**create
     *Method to create the first column of the matrix
     * @param row!=null
     * @param column!=null
     * @param cont!=null
     * @param numCol!=null
     * @param m!=null
     * */
	public void create(int row,int column,int cont,int numCol,int m) {
		if(row>0) {
			Grid ng=new Grid(cont,(char)65,numCol);
			if(first==null) {
				first=ng;
				createRows(cont,first,column,1,numCol+1);
				create(row-1,column,cont+1,numCol,m);
			}
			else{
				Grid current=findNext(first);
				if(current.getDownGrid()==null) {
					createRows(cont,ng,column,1,numCol+1);
					current.setDownGrid(ng);
					ng.setUpGrid(current);
					linkRows(current,current.getDownGrid(),column,1);
					create(row-1,column,cont+1,numCol,m);
				}
			}
		}
	}
	/**findNext
     *Method to find the next celd of one Grid object
     * @param g!=null
     * @return Grid g
     * */
	private Grid findNext(Grid g) {
		if(g.getDownGrid()==null) {
			return g;
		}
		else {
			return findNext(g.getDownGrid());
		}
	}
	/**createRows
     *Method to create the rows of the matrix
     * @param cont!=null
     * @param g!=null
     * @param c!=null
     * @param contCol!=null
     * @param numCol!=null
     * */
	private void createRows(int cont,Grid g,int c,int contCol,int numCol) {
		if(c>1) {
			int col=65+contCol;
			Grid ng=new Grid(cont,(char)col,numCol);
			if(g.getNextGrid()==null) {
				g.setNextGrid(ng);
				ng.setPreviousGrid(g);
				createRows(cont,ng,c-1,contCol+1,numCol+1);
			}
			else {
				Grid current=g.getNextGrid();
				createRows(cont,current,c,contCol,numCol+1);
			}	
		}
	}
	/**linkRows
     *Method to link the rows up and down of the matrix
     * @param g1!=null
     * @param g2!=null
     * @param col!=null
     * @param cont!=null
     * */
	private void linkRows(Grid g1,Grid g2,int col,int cont) {
		if(cont<col) {
		Grid current=g1.getNextGrid();
		Grid temp=g2.getNextGrid();
		current.setDownGrid(temp);
		temp.setUpGrid(current);
		linkRows(current,temp,col,cont+1);
		}
	}
	/**createRandom(
     *Method to create random numbers for choosing the position of the matrix
     * @param r!=null
     * @param c!=null
     * @param m!=null
     * @param cont!=null
     * */
	public void createRandom(int r,int c,int m,int cont) {
		if(cont<=m) {
			int numRow= (int) (Math.random() * r) + 1;
			int numCol= (int) (Math.random() * c) + 1;
			Grid g =searchRow(numRow,numCol,1,first);
			if(g.getAddmirror()==false) {
			addMirror(g);
			createRandom(r,c,m,cont+1);
			}
			else {
				createRandom(r,c,m,cont);
			}
		}
	}
	/**searchRow
     *Method to search the row numbers of the matrix
     * @param nr!=null
     * @param nc!=null
     * @param cont!=null
     * @param g!=null
     * @return Grid g
     * */
	private Grid searchRow(int nr,int nc,int cont,Grid g) {
		Grid newg=null;
		if(cont<nr) {
			Grid current=g.getDownGrid();
			return searchRow(nr,nc,cont+1,current);
		}
		else {
			newg=searchCol(nc,g,1);
		}
		return newg;
	}
	/**searchCol
     *Method to search the column numbers of the matrix
     * @param nc!=null
     * @param g!=null
     * @param cont!=null
     * @return Grid g
     * */
	private Grid searchCol(int nc,Grid g,int cont) {
		if(cont<nc) {
			Grid current=g.getNextGrid();
			return searchCol(nc,current,cont+1);
		}
		return g;
	}
	/**addMirror
     *Method to add the mirrors in the the matrix
     * @param g!=null
     * */
	public void addMirror(Grid g) {
		int r = (int)(Math.random()*2);
		if(r==0) {
			r=47;
		}
		else {
			r=92;
		}
		g.setMirror((char)r);
		g.setAddmirror(true);
	}
	/**showPlay
     *Method to show the the matrix
     * @param m!=null
     * @param r!=null
     * @param c!=null
     * @param contR!=null
     * @param contC!=null
     * @return String msg
     * */
	public String showPlay(String m,int r,int c,int contR,int contC) {
		String msg="";
		msg=parcialMatrix(first,m,r,c,contR,contC);
		return msg;
	}
	/**parcialMatrix
     *Method to show the rows of the matrix
     * @param g!=null
     * @param m!=null
     * @param r!=null
     * @param c!=null
     * @param contR!=null
     * @param contC!=null
     * @return String m
     * */
	public String parcialMatrix(Grid g,String m,int r,int c,int contR,int contC) {
		if(contR<=r) {
			String line=showRows(g,m,c,contC)+"\n";
			m+=line;
			return parcialMatrix(g.getDownGrid(),line,r,c,contR+1,contC);
		}
		return m;
	}
	/**showRows
     *Method to show the columns of the matrix
     * @param g!=null
     * @param matrix!=null
     * @param c!=null
     * @param contC!=null
     * @return String matrix
     * */
	public String showRows(Grid g,String matrix,int c,int contC) {
		if(contC<=c) {
			String line=g.getBox()+" ";
			matrix+=line;
			Grid current=g.getNextGrid();
			return showRows(current,matrix,c,contC+1);
		}
		return matrix;
	}
	/**searchR
     *Method to search a row wiht the number and the letter
     *@param r!=null
     * @param c!=null
     * @param cont!=null
     * @param g!=null
     * @return Grid g
     * */
	private Grid searchR(int r,char c,int cont,Grid g) {
		Grid newg=null;
		if(cont<r) {
			Grid current=g.getDownGrid();
			return searchR(r,c,cont+1,current);
		}
		else {
			newg=searchC(c,g);
		}
		return newg;
	}
	/**searchC
     *Method to search a column wiht the number and the letter
     * @param c!=null
     * @param g!=null
     * @return Grid g
     * */
	private Grid searchC(char c,Grid g) {
		if(g.getLetter()==c) {
			return g;
		}
		else {
			Grid current=g.getNextGrid();
			return searchC(c,current);	
		}
	}
	/**clasiffy
     *Method to clasiffy the movement of the celd
     * @param r!=null
     * @param s!=null
     * @param p!=null
     * */
	public void classify(int r,char s,char p) {
		Grid exit=null;
		Grid g=searchR(r,s,1,first);
		if(p==' ') {
			if(g.getNextGrid()==null ) {
				p='H';
				exit=goPrev(g);
			}
			else if( g.getPreviousGrid()==null) {
				p='H';
				exit=goNext(g);
			}
			else if(g.getUpGrid()==null) {
				p='V';
				exit=goDown(g);
			}
			else {
				p='V';
				exit=goUp(g);
			}
		}
		else {
			if(p=='V') {
				if(g.getDownGrid()==null) {
					exit=goUp(g);
				}
				else if( g.getUpGrid()==null) {
					exit=goDown(g);
				}
			}
			else {
				if(g.getPreviousGrid()==null) {
					exit=goNext(g);
				}else {
					exit=goPrev(g);
				}
			}
		}
		if(exit==g) {
			exit.setBox("[M]");
		}else {
			g.setBox("[S]");
			exit.setBox("[E]");
		}
	}
	/**goNext
     *Method to see which direction it takes if it finds a mirror, moving next
     * @param g!=null
     * @return Grid g
     * */
	public Grid goNext(Grid g) {
		Grid ng=null;
		if(g.getAddmirror()==false) {
			ng=moveNext(g);
			if(ng.getMirror()=='/') {
				if(ng.getUpGrid()!=null) {
					ng=goUp(ng.getUpGrid());
				}
				else {
					return ng;
				}
			}
			else if(ng.getMirror()=='\\'){
				if(ng.getDownGrid()!=null) {
					ng=goDown(ng.getDownGrid());
				}
			else {
					return ng;
				}
			}
			else {
					return ng;
				}
		}
		else {
			if(g.getMirror()=='/') {
				if(g.getUpGrid()!=null) {
					ng=goUp(g.getUpGrid());
				}
				else {
					return g;
				}
			}
			else {
				if(g.getDownGrid()!=null) {
					ng=goDown(g.getDownGrid());
				}
			else {
					return g;
				}
			}
		}
		return ng;
	}
	/**goPrev
     *Method to see which direction it takes if it finds a mirror, moving previous
     * @param g!=null
     * @return Grid g
     * */
	public Grid goPrev(Grid g) {
		Grid ng=null;
		if(g.getAddmirror()==false) {
			ng=movePrev(g);
			if(ng.getMirror()=='/') {
				if(ng.getDownGrid()!=null) {
					ng=goDown(ng.getDownGrid());
				}
				else {
					return ng;
				}
			}
			else if(ng.getMirror()=='\\'){
				if(ng.getUpGrid()!=null) {
					ng=goUp(ng.getUpGrid());
				}
			else {
					return ng;
				}
			}
			else {
					return ng;
				}
			}
		else {
			if(g.getMirror()=='/') {
				if(g.getDownGrid()!=null) {
					ng=goDown(g.getDownGrid());
				}
				else {
					return g;
				}
			}
			else {
				if(g.getUpGrid()!=null) {
					ng=goUp(g.getUpGrid());
				}
			else {
					return g;
				}
			}
		}
		return ng;
	}
	/**goUp
     *Method to see which direction it takes if it finds a mirror, moving up
     * @param g!=null
     * @return Grid g
     * */
	public Grid goUp(Grid g) {
		Grid ng=null;
		if(g.getAddmirror()==false) {
			ng=moveUp(g);
			if(ng.getMirror()=='/') {
				if(ng.getNextGrid()!=null) {
					ng=goNext(ng.getNextGrid());
				}
				else {
					return ng;
				}
			}
			else if(ng.getMirror()=='\\'){
				if(ng.getPreviousGrid()!=null) {
					ng=goPrev(ng.getPreviousGrid());
				}
			else {
					return ng;
				}
			}
			else {
					return ng;
				}
			}
		else {
			if(g.getMirror()=='/') {
				if(g.getNextGrid()!=null) {
					ng=goNext(g.getNextGrid());
				}
				else {
					return g;
				}
			}
			else {
				if(g.getPreviousGrid()!=null) {
					ng=goPrev(g.getPreviousGrid());
				}
			else {
					return g;
				}
			}
		}
		return ng;
	}
	/**goDown
     *Method to see which direction it takes if it finds a mirror, moving down
     * @param g!=null
     * @return Grid g
     * */
	public Grid goDown(Grid g) {
		Grid ng=null;
		if(g.getAddmirror()==false) {
			ng=moveDown(g);
			if(ng.getMirror()=='/') {
				if(ng.getPreviousGrid()!=null) {
					ng=goPrev(ng.getPreviousGrid());
				}
				else {
					return ng;
				}
			}
			else if(ng.getMirror()=='\\'){
				if(ng.getNextGrid()!=null) {
					ng=goNext(ng.getNextGrid());
				}
			else {
					return ng;
				}
			}
			else {
					return ng;
				}
			}
		else {
			if(g.getMirror()=='/') {
				if(g.getPreviousGrid()!=null) {
					ng=goPrev(g.getPreviousGrid());
				}
				else {
					return g;
				}
			}
			else {
				if(g.getNextGrid()!=null) {
					ng=goNext(g.getNextGrid());
				}
			else {
					return g;
				}
			}
		}
		return ng;
	}
	/**movePrev
     *Method to move next in the matrix
     * @param g!=null
     * @return Grid g
     * */
	public Grid moveNext(Grid g) {
		Grid ng=null;
		if(g.getNextGrid()==null || g.getAddmirror()==true) {
			ng=g;
			return ng;
		}
		else {
			ng=moveNext(g.getNextGrid());
			return ng;
		}
	}
	/**movePrev
     *Method to move up in the matrix
     * @param g!=null
     * @return Grid g
     * */
	public Grid moveUp(Grid g) {
		Grid ng=null;
		if(g.getUpGrid()==null || g.getAddmirror()==true ) {
			ng=g;
			return ng;
		}
		else {
			ng=moveUp(g.getUpGrid());
			return ng;
		}
	}
	/**movePrev
     *Method to move backwards in the matrix
     * @param g!=null
     * @return Grid g
     * */
	public Grid movePrev(Grid g) {
		Grid ng=null;
		if(g.getPreviousGrid()==null || g.getAddmirror()==true ) {
			ng=g;
			return ng;
		}
		else {
			ng=movePrev(g.getPreviousGrid());
			return ng;
		}
	}
	/**movePrev
     *Method to move down in the matrix
     * @param g!=null
     * @return Grid g
     * */
	public Grid moveDown(Grid g) {
		Grid ng=null;
		if(g.getDownGrid()==null || g.getAddmirror()==true ) {
			ng=g;
			return ng;
		}
		else {
			ng=moveDown(g.getDownGrid());
			return ng;
		}
	}
	/**findMirror(
     *method to find mirrors
     * @param num!=null
     * @param letter!=null
     * @param inclination!=null
     * @return bolean find
     * */
	public boolean findMirror(int num,char letter,String inclination,boolean find) {
		boolean accert=false;
		boolean mirror=false;
		Grid g=searchR(num,letter,1,first);
		char m=' ';
		if(inclination.equals("L")) {
			m='\\';
		}else {
			m='/';
		}
		if(g.getAddmirror()==true) {
			accert=true;
			if(g.getMirror()==m) {
				mirror=true;
			}
		}
		if(accert && mirror) {
			if(g.getMirror()=='/') {
				g.setBox("[/]");
				find=true;
			}
			else{
				g.setBox("[\\]");
				find=true;
			}
		}
		else if(accert && mirror==false) {
			g.setBox("[*]");
		}
		else{
			g.setBox("[X]");
		}
		return find;
	}
	/**eraseIntents
     *method to show good attempts 
     * @param r!=null
     * @param c!=null
     * @param contC!=null
     * @param contR!=null
     * */
	public void eraseIntents(int r,int c,int contC,int contR) {
		intentsR(first,r,c,contC,contR);
	}
	/**intentsR
     *method to show good attempts in rows
     * @param g!=null
     * @param r!=null
     * @param c!=null
     * @param contC!=null
     * @param contR!=null
     * */
	private void intentsR(Grid g,int r, int c, int contC, int contR) {
		if(contR<=r) {
			intentsC(g,c,contC);
			intentsR(g.getDownGrid(),r,c,contC,contR+1);
		}
	}
	/**intentsC
     *method to show good attempts in columns
     * @param g!=null
     * @param c!=null
     * @param contC!=null
     * */
	private void intentsC(Grid g, int c,int contC) {
		if(contC<=c) {
			if(g.getBox().equals("[/]") || g.getBox().equals("[\\]")) {
				intentsC(g.getNextGrid(),c,contC+1);
			}
			else {
				g.setBox("[ ]");
				intentsC(g.getNextGrid(),c,contC+1);
			}
		}
	}
	/**showCheat(
     * Method to clean the the matrix
     * @param m!=null
     * @param r!=null
     * @param c!=null
     * @param contC!=null
     * @param contR!=null
     * */
	public String showCheat(String m,int r,int c,int contC,int contR) {
		String msg="";
		msg=showCheatR(first,m,r,c,contC,contR);
		return msg;
	}
	/**showCheatR
     * Method to show the ubication of mirrors for 3 seconds
     * @param g!=null
     * @param m!=null
     * @param r!=null
     * @param c!=null
     * @param contC!=null
     * @param contR!=null
     * */
	private String showCheatR(Grid g,String m,int r, int c, int contC, int contR) {
		if(contR<=r) {
			String line=showCheatC(g,m,c,contC)+"\n";
			m+=line;
			return showCheatR(g.getDownGrid(),line,r,c,contC,contR+1);
		}
		return m;
	}
	/**fshowCheatC
     * Method to show the ubication of mirrors for 3 seconds
     * @param g!=null
     * @param c!=null
     * @param contC!=null
     * */
	private String showCheatC(Grid g,String m, int c,int contC) {
		if(contC<=c) {
			if(g.getMirror()=='/') {
				String line="[/]"+" ";
				m+=line;
				return showCheatC(g.getNextGrid(),m,c,contC+1);
			}
			else if(g.getMirror()=='\\') {
				String line="[\\]"+" ";
				m+=line;
				return showCheatC(g.getNextGrid(),m,c,contC+1);
			}
			else {
				String line="[ ]"+" ";
				m+=line;
				return showCheatC(g.getNextGrid(),m,c,contC+1);
			}
		}
		return m;
	}
	/**saveRoot
     * Method to save data players scores
     */
	public void saveRoot() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLAYERS_FILE_NAME));
		oos.writeObject(root);
		oos.close();
	}
	/**loadRoot
     * Method to load data players scores
     */
	public void loadRoot() throws IOException, ClassNotFoundException{	
		File f=new File(PLAYERS_FILE_NAME);
		if(f.exists()) {
			ObjectInputStream ois= new ObjectInputStream(new FileInputStream(f));
			root=(UserGame)ois.readObject();
			ois.close();
		}
	}
	/**scoreInorden
     * Method to sort the scores inorden
     * @return String scores
     * */
	public String scoreInorden() {
		return scoreInorden(root,1);
	}
	/**scoreInorden
     * Method to sort the scores inorden
     * @param current!=null
     * @return String scores
     * */
	public String scoreInorden(UserGame current,int n) {
		String scores = "";
		if(current!=null) {
			scores += scoreInorden(current.getR(),n+1);
			scores += n+". "+current.getNickname() +"--> "+" Score: " + current.getScore() + " Rows: "+current.getRows()+" Columns: "+current.getColumn()+" Mirrors: "+current.getMirror()+ "\n";
			scores += scoreInorden(current.getL(),n+1);
		}
		return scores;
	}
}

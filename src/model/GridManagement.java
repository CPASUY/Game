package model;

public class GridManagement {
	//Constants
		public static final String PLAYERS_FILE_NAME="data/players.bbd";
	//Atributes
	private UserGame root;
	private Grid first;
	//Methods
	public GridManagement() {
		first=null;
	}
	public Grid getFirst() {
		return first;
	}
	public void addPlayer(UserGame u) {
		if(root== null) {
			root = u;
		}else {
			addPlayer(root,u);
		}
	}
	public void addPlayer(UserGame c,UserGame u) {
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
	}
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
	private Grid findNext(Grid g) {
		if(g.getDownGrid()==null) {
			return g;
		}
		else {
			return findNext(g.getDownGrid());
		}
	}
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
	private void linkRows(Grid g1,Grid g2,int col,int cont) {
		if(cont<col) {
		Grid current=g1.getNextGrid();
		Grid temp=g2.getNextGrid();
		current.setDownGrid(temp);
		temp.setUpGrid(current);
		linkRows(current,temp,col,cont+1);
		}
	}
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
	private Grid searchCol(int nc,Grid g,int cont) {
		if(cont<nc) {
			Grid current=g.getNextGrid();
			return searchCol(nc,current,cont+1);
		}
		return g;
	}
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
	public String showPlay(String m,int r,int c,int contR,int contC) {
		String msg="";
		msg=parcialMatrix(first,m,r,c,contR,contC);
		return msg;
	}
	public String parcialMatrix(Grid g,String m,int r,int c,int contR,int contC) {
		if(contR<=r) {
			String line=showRows(g,m,c,contC)+"\n";
			m+=line;
			return parcialMatrix(g.getDownGrid(),line,r,c,contR+1,contC);
		}
		return m;
	}
	public String showRows(Grid g,String matrix,int c,int contC) {
		if(contC<=c) {
			String line=g.getBox()+" "+g.getMirror();
			matrix+=line;
			Grid current=g.getNextGrid();
			return showRows(current,matrix,c,contC+1);
		}
		return matrix;
	}
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
	private Grid searchC(char c,Grid g) {
		if(g.getLetter()==c) {
			return g;
		}
		else {
			Grid current=g.getNextGrid();
			return searchC(c,current);	
		}
	}
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
		g.setBox("[S]");
		exit.setBox("[E]");
	}
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
		return ng;
	}
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
		return ng;
	}
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
		return ng;
	}
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
		return ng;
	}
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
}

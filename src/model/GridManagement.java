package model;

public class GridManagement {
	//Atributes
	private Grid first;
	//Methods
	public GridManagement() {
		first=null;
	}
	public Grid getFirst() {
		return first;
	}
	public void create(int row,int column,int cont,int numCol,int m) {
		Grid ng=new Grid(cont,(char)65,numCol);
		if(row>0) {
			if(first==null) {
				first=ng;
				createRows(cont,first,column,1,numCol);
				create(row-1,column,cont+1,numCol,m);
			}
			else{
				Grid current=first;
				if(current.getDownGrid()==null) {
					createRows(cont,ng,column,1,numCol);
					current.setDownGrid(ng);
					ng.setUpGrid(current);
					linkRows(current,current.getDownGrid(),column,1);
					create(row-1,column,cont+1,numCol,m);
				}
				else {
					current=current.getDownGrid();
					create(row,column,cont,numCol,m);
				}
			}
		}
		aleatory(row,column,m,1);
	}
	private void createRows(int cont,Grid g,int c,int contCol,int numCol) {
		if(c>1) {
			int col=65+contCol;
			Grid ng=new Grid(cont,(char)col,numCol);
			if(g.getNextGrid()==null) {
				g.setNextGrid(ng);
				ng.setPreviousGrid(g);
				createRows(cont,ng,c-1,contCol+1,numCol);
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
	private void aleatory(int r,int c,int m,int cont) {
		if(cont<=m) {
			int numRow= (int) (Math.random() * r) + 1;
			int numCol= (int) (Math.random() * c) + 1;
			Grid g =searchRow(numRow,numCol,1,first);
			mirror(g);
			aleatory(r,c,m,cont+1);
		}
	}
	private Grid searchRow(int nr,int nc,int cont,Grid g) {
		Grid newg=null;
		if(cont<nr) {
			Grid current=g.getDownGrid();
			searchRow(nr,nc,cont+1,current);
		}
		else {
			newg=searchCol(nc,g,1);
		}
		return newg;
	}
	private Grid searchCol(int nc,Grid g,int cont) {
		if(cont<nc) {
			Grid current=g.getNextGrid();
			searchCol(nc,current,cont+1);
		}
		return g;
	}
	public void mirror(Grid g) {
		int r = (int)(Math.random()*2);
		if(r==0) {
			r=47;
		}
		else {
			r=92;
		}
		g.setMirror((char)r);
	}
	public String parcialMatrix(String m,int c,int r,int col,int conC) {
		String msg=showMatrix(first,m,c,r,col,conC);
		return msg;
	}
	public String showMatrix(Grid g,String matrix,int contR,int r, int c,int contC) {
		if(contR<=r) {
			if(contC<=c) {
				if(g.getNextGrid()==null) {
					String m=g.getBox()+"\n";
					showMatrix(g,m,contR+1,r,c,contC+1);
				}
				else {
					matrix+=g.getBox()+" ";
					Grid current=g.getNextGrid();
					showMatrix(current,matrix,contR+1,r,c,contC+1);
				}
			}
		}
		return matrix;
	}
}

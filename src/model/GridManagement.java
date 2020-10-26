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
	public Grid create(int row,int column,int cont,int numCol,int m) {
		Grid ng=new Grid(cont,(char)65,numCol);
		if(row>0) {
			if(first==null) {
				first=ng;
				System.out.println(first.getNumber()+"/"+first.getLetter());
				createRows(cont,first,column,1,numCol);
				System.out.println(first.getNextGrid().getNumber()+"/"+first.getNextGrid().getLetter());
				create(row--,column,cont++,numCol,m);
			}
			else{
				Grid current=first;
				if(current.getDownGrid()==null) {
					createRows(cont,ng,column,1,numCol);
					current.setDownGrid(ng);
					ng.setUpGrid(current);
					linkRows(current,current.getUpGrid(),column,1);
					create(row--,column,cont++,numCol,m);
				}
				else {
					current=current.getDownGrid();
					create(row,column,cont,numCol,m);
				}
			}
		}
		aleatory(row,column,m,1);
		return ng;
	}
	private void createRows(int cont,Grid g,int c,int contCol,int numCol) {
		if(c>1) {
			int col=65+contCol;
			Grid ng=new Grid(cont,(char)col,numCol+1);
			if(g.getNextGrid()==null) {
				g.setNextGrid(ng);
				ng.setPreviousGrid(g);
				createRows(cont,ng,c--,contCol++,numCol);
				
			}
			else {
				Grid current=g.getNextGrid();
				createRows(cont,current,c,contCol,numCol);
			}	
		}
	}
	private void linkRows(Grid g1,Grid g2,int col,int cont) {
		if(cont<col) {
		Grid current=g1.getNextGrid();
		Grid temp=g2.getNextGrid();
		current.setDownGrid(temp);
		temp.setUpGrid(current);
		linkRows(current,temp,col,cont++);
		}
	}
	private void aleatory(int r,int c,int m,int cont) {
		if(cont<m) {
			int numRow= (int) (Math.random() * r) + 1;
			int numCol= (int) (Math.random() * c) + 1;
			searchRow(numRow,numCol,1,first);
			aleatory(r,c,m,cont++);
		}
	}
	private void searchRow(int nr,int nc,int cont,Grid g) {
		if(cont<nr) {
			Grid current=g.getNextGrid();
			searchRow(nr,nc,cont++,current);
		}
		else {
			searchCol(nc,g,1);
		}
	}
	private void searchCol(int nc,Grid g,int cont) {
		if(cont<nc) {
			Grid current=g.getNextGrid();
			searchCol(nc,current,cont++);
		}
		else {
			int r = (int)(Math.random()*2);
			if(r==0) {
				r=47;
			}
			else {
				r=92;
			}
			g.setMirror((char)r);
		}
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
					showMatrix(g,m,contR++,r,c,contC++);
				}
				else {
					matrix+=g.getBox()+" ";
					Grid current=g.getNextGrid();
					showMatrix(current,matrix,contR,r,c,contC++);
				}
			}
		}
		return matrix;
		
	}
}

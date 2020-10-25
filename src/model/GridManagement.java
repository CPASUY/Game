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
	public Grid create(int row,int column,int cont) {
		Grid ng=new Grid(cont,(char)65);
		if(row<0) {
			if(first==null) {
				first=ng;
				createRows(cont,first,column,1);
				create(row--,column,cont++);
			}
			else{
				Grid current=first;
				if(current.getDownGrid()==null) {
					createRows(cont,ng,column,1);
					current.setDownGrid(ng);
					ng.setUpGrid(current);
					linkRows(current,current.getUpGrid());
					create(row--,column,cont++);
				}
				else {
					current=current.getDownGrid();
					create(row,column,cont);
				}
			}
		}
		return ng;
	}
	private void createRows(int cont,Grid g,int c,int contCol) {
		if(c>1) {
			int col=65+contCol;
			Grid ng=new Grid(cont,(char)col);
			if(g.getNextGrid()==null) {
				g.setNextGrid(ng);
				ng.setPreviousGrid(g);
				createRows(cont,ng,c--,contCol++);
				
			}
			else {
				Grid current=g.getNextGrid();
				createRows(cont,current,c,contCol);
			}	
		}
	}
	private void linkRows(Grid g1,Grid g2) {
		
	}
}

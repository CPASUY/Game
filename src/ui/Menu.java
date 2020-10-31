package ui;
import java.util.Scanner;

import exceptions.NumbersColumnException;
import model.GridManagement;
public class Menu {
	private Scanner sc;
	private GridManagement gm;
	
	public Menu() {
		sc = new Scanner(System.in);
		createManagement();
	}
	public void startMenu(){
		String menu = getMenuText();
		int option;
		System.out.println(menu);
		option = readOption();
		executeOperation(option);
	}
	private String getMenuText() {
		String menu;
		menu  = "==============================\n";
		menu += "      LASER BEAM\n";
		menu += "==============================\n";
		menu += "1. Start game\n";
		menu += "2. Leaderboard \n";
		menu += "3. Exit game";
		return menu;
	}
	private int readOption()  {
		int op;
		op = Integer.parseInt(sc.nextLine());
		return op;
	}
	private void executeOperation(int option)  {
		switch(option) {
			case 1: 
				try{
					playGame();  
				}catch(NumbersColumnException nce) {
					System.out.println("The number of columns entered is greater than allowed");
				}
			break;
			case 2: showLeaderboard(); break;
			case 3: exitProgram();  break;
			default: break;
		}
		startMenu();
	}
	public void createManagement() {
		gm=new GridManagement();
	}
	private void exitProgram() {
		sc.close();
	}
	private void playGame() throws NumbersColumnException  {
		int r=0;
		int c=0;
		int m=0;
		System.out.println("Nickname Rows Columns Mirrors");
		String line=sc.nextLine();;
		String parts[]=line.split(" ");
		String n=parts[0];
		r=Integer.parseInt(parts[1]);
		c=Integer.parseInt(parts[2]);
		m=Integer.parseInt(parts[3]);
		if(c>26) {
			throw new NumbersColumnException();
		}
		gm.create(r,c,1,1,m);
		play(r,c);
	}
	private void play(int r, int c) {
		String msg=gm.parcialMatrix(" ",0,r,c,0);
		System.out.println(msg);
		String play=sc.nextLine();
		if(play.equals("menu") ) {
			
		}
		else {
			int n=0;
			char letter=' ';
			char pos=' ';
			int l=play.length();
			if(play.charAt(l-1)=='H' || play.charAt(l-1)=='V') {
				pos=play.charAt(l-1);
				letter=play.charAt(l-2);
				String num=play.substring(0,l-2);
				n=Integer.parseInt(num);
				gm.classify(r, letter,pos);
			}
			else {
				letter=play.charAt(l-1);
				String num=play.substring(0,l-1);
				n=Integer.parseInt(num);
				gm.classify(r, letter,pos);
				}
			play(r,c);
		}
	}
	private void showLeaderboard() {
		
	}
}

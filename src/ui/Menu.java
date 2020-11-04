package ui;
import java.io.IOException;
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
	public void startMenu()  {
		String menu = getMenuText();
		int option;
		System.out.println(menu);
		option = readOption();
		try {
			executeOperation(option);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	private void executeOperation(int option) throws IOException  {
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
	private void playGame() throws NumbersColumnException, IOException  {
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
		gm.createRandom(r,c,m,1);
		int s=play(r,c,1,m,n,0);
		int score=s*r*c*m;
		gm.addPlayer(n,score,r,c,m);
		System.out.println("You score is "+score);
	}
	private int play(int r, int c,int cont,int m,String name,int score) throws IOException {
		if(cont==1) {
			String msg=gm.showPlay("",r,c,1,1);
			System.out.println(name+":"+" "+m+" mirrors remaining");
			System.out.print(msg);
			return play(r,c,cont+1,m,name,score);
		}
		String play=sc.nextLine();
		int s=play.length();
		if(play.equals("menu")) {
			cont=cont+1;
			gm.finishAttemp(r, c, 1, 1);
			gm.saveRoot();
			return score;
		}
		else if(play.charAt(s-1)=='L' || play.charAt(s-1)=='R') {
			String lr=play.substring(s-1);
			char let=play.charAt(s-2);
			int num=Integer.parseInt(play.substring(1,s-2));
			boolean find=false;
			boolean found=gm.findMirror(num,let,lr,find);
			if(found) {
				m=m-1;
				score=score+3;
			}
			System.out.println(name+":"+" "+m+" mirrors remaining");
			String msg=gm.showPlay("",r,c,1,1);
			System.out.println(msg);
			gm.eraseIntents(r, c,1,1);
			if(m!=0) {
				return play(r,c,cont+1,m,name,score+1);
			}
			else {
				gm.finishAttemp(r, c, 1, 1);
				gm.saveRoot();
				return score;
			}
		}
		else {
			int n=0;
			char letter=' ';
			char pos=' ';
			if(play.charAt(s-1)=='H' || play.charAt(s-1)=='V') {
				pos=play.charAt(s-1);
				letter=play.charAt(s-2);
				String num=play.substring(0,s-2);
				n=Integer.parseInt(num);
				gm.classify(n, letter,pos);
				System.out.println(name+":"+" "+m+" mirrors remaining");
				String msg=gm.showPlay("",r,c,1,1);
				System.out.print(msg);
				gm.eraseIntents(r,c,1,1);
				return play(r,c,cont+1,m,name,score+1);
			}
			else {
				letter=play.charAt(s-1);
				String num=play.substring(0,s-1);
				n=Integer.parseInt(num);
				gm.classify(n, letter,pos);
				String msg=gm.showPlay("",r,c,1,1);
				System.out.println(name+":"+" "+m+" mirrors remaining");
				System.out.print(msg);
				gm.eraseIntents(r,c,1,1);
				return play(r,c,cont+1,m,name,score+1);
			}
		}
	}
	private void showLeaderboard() {
		System.out.println("\n"+gm.scoreInorden());
	}
}

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
	/**startMenu
     * Method for start menu
     */
	public void startMenu()  {
		clearConsole();
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
	/**getMenuText
     * Method for get the menu text
     * @return String menu
     */
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
	/**readOption(
     * Method for read option
     */
	private int readOption()  {
		int op;
		op = Integer.parseInt(sc.nextLine());
		return op;
	}
	/**executeOperation
     * Method for execute operation
     * @param option!=null
     */
	private void executeOperation(int option) throws IOException  {
		switch(option) {
			case 1: 
				try{
					playGame(); 
					startMenu();
				}catch(NumbersColumnException nce) {
					System.out.println("The number of columns entered is greater than allowed");
				}
			break;
			case 2: showLeaderboard();startMenu(); break;
			case 3: exitProgram();  break;
			default: break;
		}
	}
	/**createManagement
     * Method for create management
     */
	public void createManagement() {
		gm=new GridManagement();
	}
	/**exitProgram
     * Method for exit program
     */
	private void exitProgram() {
		System.out.println("Remember that you can play as many times as you want crack");
		System.out.println("Ciao");
	}
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");
	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	}
	/**playGame
     * Method for play the game
     */
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
		int s=play(r,c,1,m,n,0,1);
		int score=s*r*c*m;
		gm.addPlayer(n,score,r,c,m);
		System.out.println("You score is "+score);
	}
	/**play
     * Method for play 
     */
	private int play(int r, int c,int cont,int m,String name,int score,int cheat) throws IOException {
		clearConsole();
		if(cont==1 ) {
			String msg=gm.showPlay("",r,c,1,1);
			System.out.println(name+":"+" "+m+" mirrors remaining");
			System.out.print(msg);
			System.out.println("Do you want to use your cheat mode? You will only have this help once");
			System.out.println("Write 1 for yes, 2 for no");
			int o=Integer.parseInt(sc.nextLine());
			if(o==1) {
				cheatMode(r, c, cont, m, name, score, cheat);
			}
			return play(r,c,cont+1,m,name,score,cheat-1);
		}
		String play=sc.nextLine();
		int s=play.length();
		if(play.equals("menu")) {
			cont=cont+1;
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
			System.out.flush();
			gm.eraseIntents(r, c,1,1);
			if(m!=0) {
				clearConsole();
				return play(r,c,cont+1,m,name,score+1,cheat);
			}
			else {
				clearConsole();
				System.out.println("CONGRATULATIONS! You win the game");
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
				clearConsole();
				gm.eraseIntents(r,c,1,1);
				return play(r,c,cont+1,m,name,score+1,cheat);
			}
			else {
				letter=play.charAt(s-1);
				String num=play.substring(0,s-1);
				n=Integer.parseInt(num);
				gm.classify(n, letter,pos);
				String msg=gm.showPlay("",r,c,1,1);
				System.out.println(name+":"+" "+m+" mirrors remaining");
				System.out.print(msg);
				clearConsole();
				gm.eraseIntents(r,c,1,1);
				return play(r,c,cont+1,m,name,score+1,cheat);
			}
		}
	}
	/**showLeaderboar
     * Method for show showLeaderboar
     */
	private void showLeaderboard() {
		System.out.println("SCORES");
		System.out.println("=============");
		System.out.println("\n"+gm.scoreInorden());
	}
	private void cheatMode(int r,int c,int cont,int m,String name,int score,int cheat) throws IOException {
		long startTime = System.currentTimeMillis();
		String msg=gm.showCheat("",r,c,1,1);
		System.out.println(msg);
		while(System.currentTimeMillis()-startTime<3000) {
		}
	}
}

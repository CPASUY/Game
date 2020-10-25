package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.GridManagement;
public class Menu {
	private BufferedReader br;
	private BufferedWriter bw;
	private final static int EXIT_OPTION = 3;
	private GridManagement gm;
	
	public Menu() throws IOException {
		br=new BufferedReader(new InputStreamReader(System.in));
		bw=new BufferedWriter(new OutputStreamWriter(System.out));
		createManagement();
	}
	public void startMenu() throws IOException {
		String menu = getMenuText();
		int option;
		do {
			System.out.println(menu);
			option = readOption();
			executeOperation(option);
		}while(option!=EXIT_OPTION);
	}
	private String getMenuText() {
		String menu;
		menu  = "==============================\n";
		menu += "      LASER BEAM\n";
		menu += "==============================\n";
		menu += "1. Start game\n";
		menu += "2. Leaderboard \n";
		menu += "3. Exit game\n";
		return menu;
	}
	private int readOption() throws NumberFormatException, IOException {
		int op;
		op = Integer.parseInt(br.readLine());
		return op;
	}
	private void executeOperation(int option) throws IOException  {
		switch(option) {
			case 1: playGame();   break;
			case 2: showLeaderboard(); break;
			case 3: exitProgram();   break;
			default: break;
		}
	}
	public void createManagement() throws IOException{
		gm=new GridManagement();
	}
	private void exitProgram() throws IOException {
		br.close();
		bw.close();
	}
	private void playGame() throws IOException {
		String n="";
		int r=0;
		int c=0;
		int m=0;
		bw.write("Nickname Rows Columns Mirrors");
		String line=br.readLine();
		String parts[]=line.split(" ");
		n=parts[0];
		r=Integer.parseInt(parts[1]);
		c=Integer.parseInt(parts[2]);
		m=Integer.parseInt(parts[3]);
		gm.organize(r,c,m);
	}
	private void showLeaderboard() {
		
	}
}

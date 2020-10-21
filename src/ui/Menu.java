package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class Menu {
	public static BufferedReader br;
	public static BufferedWriter bw;
	private final static int EXIT_OPTION = 3;
	
	public Menu() {
		br=new BufferedReader(new InputStreamReader(System.in));
		bw=new BufferedWriter(new OutputStreamWriter(System.out));
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
		menu += "1. Star game\n";
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
	private void exitProgram() throws IOException {
		br.close();
		bw.close();
	}
	private void playGame() {
		
	}
	private void showLeaderboard() {
		
	}
}

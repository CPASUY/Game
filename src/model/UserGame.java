package model;

import java.io.Serializable;
public class UserGame implements Serializable {
	//Constants
	private static final long serialVersionUID = 1;
	//Atributes
	private String nickname;
	private int score;
	private int rows;
	private int column;
	private int mirror;
	private UserGame l;
	private UserGame f;
	private UserGame r;
	//Methods
	/**UserGame
	 * @param name!=null
	 * @param s!=null
	 * @param n!=null
	 * @param m!=null
	 * @param k!=null
	 */
	public UserGame(String name, int s,int n,int m,int k) {
		nickname = name;
		score = s;
		rows=n;
		column=m;
		mirror=k;
	}
	public int getRows() {
		return rows;
	}
	public int getColumn() {
		return column;
	}
	public int getMirror() {
		return mirror;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String n) {
		nickname = n;
	}
	public int getScore() {
		return score;
	}
	public UserGame getL() {
		return l;
	}
	public void setL(UserGame left) {
		l = left;
	}
	public UserGame getF() {
		return f;
	}
	public void setF(UserGame father) {
		f = father;
	}
	public UserGame getR() {
		return r;
	}
	public void setR(UserGame right) {
		r = right;
	}
}

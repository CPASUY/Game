package model;

import java.io.Serializable;

public class UserGame implements Serializable {
	//Constants
	private static final long serialVersionUID = 1;
	//Atributes
	private String nickname;
	private int score;
	private UserGame l;
	private UserGame f;
	private UserGame r;
	//Methods
	public UserGame(String n, int s) {
		nickname = n;
		score = s;
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
	public void setScore(int s) {
		score = s;
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

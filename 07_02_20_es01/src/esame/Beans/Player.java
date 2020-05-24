package esame.Beans;

import java.io.Serializable;

public class Player implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String nome;
	public String password;
	public char chr;
	public boolean notify;
	
	public Player(String nome, String password, char chr) {
		super();
		this.nome = nome;
		this.password = password;
		this.chr = chr;
		notify=false;
	}
	
	public void toNotify() {
		notify=true;
	}
	
	public void disableNotify() {
		notify=false;
	}
	
	
}

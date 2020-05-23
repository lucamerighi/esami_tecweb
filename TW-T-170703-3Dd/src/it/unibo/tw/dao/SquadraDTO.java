package it.unibo.tw.dao;

import java.io.Serializable;


public class SquadraDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idS;
	private String nome, torneo, allenatore;
	
	public SquadraDTO(){}

	public int getIdS() {
		return idS;
	}

	public void setIdS(int idS) {
		this.idS = idS;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTorneo() {
		return torneo;
	}

	public void setTorneo(String torneo) {
		this.torneo = torneo;
	}

	public String getAllenatore() {
		return allenatore;
	}

	public void setAllenatore(String allenatore) {
		this.allenatore = allenatore;
	}

}

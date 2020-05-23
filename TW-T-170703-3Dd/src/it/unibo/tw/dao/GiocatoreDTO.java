package it.unibo.tw.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GiocatoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// ---------------------------
	
	private int idG;
	private String cf, cognome, nome;
	private int eta;
	private List<SquadraDTO> squadre = new ArrayList<SquadraDTO>();
	
	
	// --- constructor ----------
	
	public GiocatoreDTO() {
	}


	public int getIdG() {
		return idG;
	}


	public void setIdG(int idG) {
		this.idG = idG;
	}


	public String getCf() {
		return cf;
	}


	public void setCf(String cf) {
		this.cf = cf;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getEta() {
		return eta;
	}


	public void setEta(int eta) {
		this.eta = eta;
	}


	public List<SquadraDTO> getSquadre() {
		return squadre;
	}


	public void setSquadre(List<SquadraDTO> squadre) {
		this.squadre = squadre;
	}
	

	
	

}

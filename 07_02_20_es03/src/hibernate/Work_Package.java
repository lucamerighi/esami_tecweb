package hibernate;

import java.io.Serializable;

public class Work_Package implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomeWp;
	private String Titolo;
	private String Descrizione;
	private Progetto progetto;
	
	public Work_Package(String nomeWp, String titolo, String descrizione, Progetto pr) {
		super();
		this.nomeWp = nomeWp;
		Titolo = titolo;
		Descrizione = descrizione;
		this.progetto=pr;
		
	}
	
	
	public Progetto getProgetto() {
		return progetto;
	}


	public void setProgetto(Progetto progetto) {
		this.progetto = progetto;
	}


	public String getNomeWp() {
		return nomeWp;
	}
	public void setNomeWp(String nomeWp) {
		this.nomeWp = nomeWp;
	}
	public String getTitolo() {
		return Titolo;
	}
	public void setTitolo(String titolo) {
		Titolo = titolo;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	
	
	
	

	
	
}

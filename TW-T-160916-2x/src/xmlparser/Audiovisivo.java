package xmlparser;

import java.util.ArrayList;



public class Audiovisivo {
	
	private String nomeReg, cognomeReg, titolo, nazion, lingua, colore, durata, tipologia, formato, disponib, collocazione;
	private int anno;
	
	public Audiovisivo(String nomeReg, String cognomeReg, String titolo, String nazion, String lingua, String colore,
			String durata, String tipologia, String formato, String disponib, String collocazione, int anno) {
		super();
		this.nomeReg = nomeReg;
		this.cognomeReg = cognomeReg;
		this.titolo = titolo;
		this.nazion = nazion;
		this.lingua = lingua;
		this.colore = colore;
		this.durata = durata;
		this.tipologia = tipologia;
		this.formato = formato;
		this.disponib = disponib;
		this.collocazione = collocazione;
		this.anno = anno;
	}
	
	public String getNomeReg() {
		return nomeReg;
	}

	public void setNomeReg(String nomeReg) {
		this.nomeReg = nomeReg;
	}

	public String getCognomeReg() {
		return cognomeReg;
	}

	public void setCognomeReg(String cognomeReg) {
		this.cognomeReg = cognomeReg;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public Audiovisivo() {}
	
	
	public void stampa() {
		System.out.println("AUDIOVISIVO:");
		System.out.println("NOME REGISTA:"+ nomeReg +" "+ cognomeReg);
		System.out.println("TITOLO:"+ titolo);
		System.out.println("COLORE:"+ colore);
		System.out.println();
	} 
	
	
}

package hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Progetto implements Serializable{
	/**
}
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codiceProgetto;
	private String nomeProgetto;
	private int year;
	private int durata;
	private Set<Work_Package> workPackage = new HashSet<Work_Package>(0);
	
	public Progetto(String codiceProgetto, String nomeProgetto, int year, int durata) {
		super();
		this.codiceProgetto = codiceProgetto;
		this.nomeProgetto = nomeProgetto;
		this.year = year;
		this.durata = durata;
	}
	
	public void add(Work_Package wp) {
		workPackage.add(wp);
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public Set<Work_Package> getWorkPackage() {
		return workPackage;
	}

	public void setWorkPackage(Set<Work_Package> workPackage) {
		this.workPackage = workPackage;
	}
	

}

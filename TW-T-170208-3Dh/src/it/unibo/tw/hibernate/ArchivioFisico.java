package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ArchivioFisico implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idArch;
	private String codArch, nome, descrizione;
	private Date dataCreazione;
	
	private Set<MaterialeFisico> materiali= new HashSet<MaterialeFisico>(0);
	
	public ArchivioFisico(){}

	public int getIdArch() {
		return idArch;
	}

	public void setIdArch(int idArch) {
		this.idArch = idArch;
	}

	public String getCodArch() {
		return codArch;
	}

	public void setCodArch(String codArch) {
		this.codArch = codArch;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Set<MaterialeFisico> getMateriali() {
		return materiali;
	}

	public void setMateriali(Set<MaterialeFisico> materiali) {
		this.materiali = materiali;
	}

	

}

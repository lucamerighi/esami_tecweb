package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MaterialeFisico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	// ---------------------------
	
	private int idMat;
	private String  codMat, nome, descrizione;
	private Date dataCreaz;
	private ArchivioFisico archivio;
	
	private Set<OggettoDigitale> oggetti= new HashSet<OggettoDigitale>(0);

	// --- constructor ----------
	
	public MaterialeFisico() {
	}

	public int getIdMat() {
		return idMat;
	}

	public void setIdMat(int idMat) {
		this.idMat = idMat;
	}

	public String getCodMat() {
		return codMat;
	}

	public void setCodMat(String codMat) {
		this.codMat = codMat;
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

	public Date getDataCreaz() {
		return dataCreaz;
	}

	public void setDataCreaz(Date dataCreaz) {
		this.dataCreaz = dataCreaz;
	}

	public ArchivioFisico getArchivio() {
		return archivio;
	}

	public void setArchivio(ArchivioFisico archivio) {
		this.archivio = archivio;
	}

	public Set<OggettoDigitale> getOggetti() {
		return oggetti;
	}

	public void setOggetti(Set<OggettoDigitale> oggetti) {
		this.oggetti = oggetti;
	}





}

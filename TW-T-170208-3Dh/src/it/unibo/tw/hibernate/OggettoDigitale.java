package it.unibo.tw.hibernate;

import java.io.Serializable;
import java.util.Date;


public class OggettoDigitale implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOgg;
	private String nome, formato, codOgg;
	private Date dataDigit;
	private MaterialeFisico mat;
	
	public OggettoDigitale() {}

	public int getIdOgg() {
		return idOgg;
	}

	public void setIdOgg(int idOgg) {
		this.idOgg = idOgg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getCodOgg() {
		return codOgg;
	}

	public void setCodOgg(String codOgg) {
		this.codOgg = codOgg;
	}

	public Date getDataDigit() {
		return dataDigit;
	}

	public void setDataDigit(Date dataDigit) {
		this.dataDigit = dataDigit;
	}

	public MaterialeFisico getMat() {
		return mat;
	}

	public void setMat(MaterialeFisico mat) {
		this.mat = mat;
	}

	
	
	
}

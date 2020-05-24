package beans;

public class Prodotto {
	

	String id;
	String nome;
	String codice;
	Double prezzo;
	
	public Prodotto(String id, String nome, String codice, Double prezzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.codice = codice;
		this.prezzo = prezzo;
	}

	public Prodotto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	
	
	
}

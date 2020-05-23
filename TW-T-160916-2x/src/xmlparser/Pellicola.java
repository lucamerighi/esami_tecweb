package xmlparser;

public class Pellicola {
	
	private String regia, titolo, stato;
	private int tipo;
		
	public Pellicola() {
	}
		
		
	
	public String getRegia() {
		return regia;
	}



	public void setRegia(String regia) {
		this.regia = regia;
	}



	public String getTitolo() {
		return titolo;
	}



	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}



	public String getStato() {
		return stato;
	}



	public void setStato(String stato) {
		this.stato = stato;
	}



	public int getTipo() {
		return tipo;
	}



	public void setTipo(int tipo) {
		this.tipo = tipo;
	}



	public void stampaPellicola() {
		System.out.println("PELLICOLA:");
		System.out.println("REGIA:"+ regia);
		System.out.println("TITOLO:"+ titolo);
		System.out.println("STATO:"+ stato);
		System.out.println("TIPO:"+ tipo);
		System.out.println();
	}
}

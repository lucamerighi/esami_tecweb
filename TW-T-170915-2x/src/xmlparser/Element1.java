package xmlparser;

import java.util.ArrayList;



public class Element1 {
	
	private String nomeProprio, cognome, nazion;
	private ArrayList<String> secNome = new ArrayList<String>(); 
	private ArrayList<Element2> indirizzi= new ArrayList<Element2>();
	private ArrayList<String> email = new ArrayList<String>();
	private ArrayList<String> note = new ArrayList<String>();
	private ArrayList<String> numtel = new ArrayList<String>();
	
	public Element1() {
		
	}
	
	public void stampa() {
		System.out.println("NUOVO CONTATTO:");
		System.out.println("NOME:"+ nomeProprio + stampaLista(secNome) +" "+ cognome);
		for(Element2 i : indirizzi) {
			System.out.println(i.stampaEl2());
		}
	if(!numtel.isEmpty()) System.out.println("NUMERI TELEFONO: " + stampaLista(numtel));
	if(!email.isEmpty()) System.out.println("EMAIL: " + stampaLista(email));
	if(!nazion.equals("")) System.out.println("NAZIONALITA': " + nazion);
	if(!note.isEmpty()) System.out.println("NOTE: " + stampaLista(note));
	System.out.println();
	} 
	
	//Stamapare una lista di stringhe
	static String stampaLista(ArrayList<String> l) {
		String res="";
		for(String el : l) res+=" "+ el;
		return res;
	}
	
	//Aggiungere elemento a lista
	public void addInd(Element2 ind) {
		this.indirizzi.add(ind);
	}
	
	
}

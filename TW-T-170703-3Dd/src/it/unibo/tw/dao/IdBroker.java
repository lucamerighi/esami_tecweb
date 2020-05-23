package it.unibo.tw.dao;

public interface IdBroker {
	public int newId(String seq, String tab);
	
	public void newSeq(String seq);
}

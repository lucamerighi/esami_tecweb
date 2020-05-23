package it.unibo.tw.dao;


import java.util.List;

public interface GiocatoreDAO {

	// --- CRUD -------------

	public void create(GiocatoreDTO g);

	public GiocatoreDTO read(int id);

	public boolean update(GiocatoreDTO g);

	public boolean delete(int id);
	
	public List<GiocatoreDTO> findAll();

	
	// ----------------------------------

	// ----------------------------------

	
	public boolean createTable();

	public boolean dropTable();

}

package it.unibo.tw.dao;


public interface SquadraDAO {
	// --- CRUD -------------

		public void create (SquadraDTO piatto);

		public SquadraDTO read(int id);

		public boolean update(SquadraDTO piatto);

		public boolean delete(int id);
		
		public boolean createTable();

		public boolean dropTable();
		
		
}

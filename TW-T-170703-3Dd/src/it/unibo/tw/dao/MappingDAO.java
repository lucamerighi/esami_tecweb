package it.unibo.tw.dao;

import java.util.List;

public interface MappingDAO {
	// --- CRUD -------------

			public void create (int idG, int idS);

			//public S_CMappingDTO read(int stid, int couid);

			//public boolean update(S_CMappingDTO mapp);

			public boolean delete(int idG, int idS);

			
			// ----------------------------------

			public List<String> getAllenatoriFromGiocatore(int idG);
			
			// public List<RistoranteDTO> getRistByPiatto(String nomepiatto);

			// ----------------------------------

			
			public boolean createTable();

			public boolean dropTable();

			
}

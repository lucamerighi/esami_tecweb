package it.unibo.tw.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DAOTest {
	
	public static final int DAO = DAOFactory.DB2;
	
	public static SquadraDTO createSquadraDTO(String nome, String torneo, String allenatore) {
		SquadraDTO s = new SquadraDTO();
		s.setNome(nome);
		s.setTorneo(torneo);
		s.setAllenatore(allenatore);
		return s;
	}

	
	public static void main(String[] args) {
		
		// Student
		
		DAOFactory daoFactoryInstance = DAOFactory.getDAOFactory(DAO);
		GiocatoreDAO giocatoreDAO = daoFactoryInstance.getRistoranteDAO();
		giocatoreDAO.dropTable();
		giocatoreDAO.createTable();
		
		
		SquadraDAO squadraDAO = DAOFactory.getDAOFactory(DAO).getPiattoDAO();
		squadraDAO.dropTable();
		squadraDAO.createTable();
		
		MappingDAO mappingDAO = DAOFactory.getDAOFactory(DAO).getMappingDAO();
		mappingDAO.dropTable();
		mappingDAO.createTable();
		
		
		
		SquadraDTO s1 = createSquadraDTO("bologna", "er", "rossi");
		squadraDAO.create(s1);
		
		SquadraDTO s2 = createSquadraDTO("milano", "lomb", "gialli");
		squadraDAO.create(s2);
		
		SquadraDTO s3 = createSquadraDTO("parma", "er", "verdi");
		squadraDAO.create(s3);


		GiocatoreDTO g = new GiocatoreDTO();
		g.setCf("A!JDU");
		g.setNome("nino");
		g.setCognome("pino");
		g.setEta(21);
		giocatoreDAO.create(g);
		mappingDAO.create(g.getIdG(), s3.getIdS());
		mappingDAO.create(g.getIdG(), s1.getIdS());
		mappingDAO.create(g.getIdG(), s2.getIdS());

		g = new GiocatoreDTO();
		g.setCf("JDIW73");
		g.setNome("LINO");
		g.setCognome("BINO");
		g.setEta(22);
		giocatoreDAO.create(g);
		mappingDAO.create(g.getIdG(), s3.getIdS());
		mappingDAO.create(g.getIdG(), s2.getIdS());

		
		File f= new File("Pallacanestro.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new PrintWriter(f));
			
			List<GiocatoreDTO> gioc = giocatoreDAO.findAll();
			
			for(GiocatoreDTO giocatore: gioc) {
				bw.append("\nLISTA ALLENATORI DI " + giocatore.getNome()+" " + giocatore.getCognome()+ ": \n");
				List<SquadraDTO> sq = giocatore.getSquadre();
				for(SquadraDTO s: sq) {
					bw.append(s.getAllenatore()+"\n");
				}
			}
		bw.close();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

}

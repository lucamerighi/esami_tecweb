package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.unibo.tw.dao.SquadraDTO;
import it.unibo.tw.dao.MappingDAO;
import it.unibo.tw.dao.GiocatoreDTO;

public class DB2MappingDAO implements MappingDAO {
	
	Logger logger = Logger.getLogger( getClass().getCanonicalName() );

	static final String TABLE = "mapping";

	// -------------------------------------------------------------------------------------

	static final String IDS = "idS";
	static final String IDG = "idG";

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
		static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " + 
					IDS +", "+IDG+") " +
				"VALUES (?,?) "
			;
	
	// SELECT * FROM table WHERE idcolumn = ?;
		static String read_by_id = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + IDS + " = ? " +
				"AND " + IDG + " = ? " 
			;
		
		// DELETE FROM table WHERE idcolumn = ?;
		static String delete = 
			"DELETE " +
				"FROM " + TABLE + " " +
				"WHERE " + IDS + " = ? " +
				"AND " + IDG + " = ? " 
			;

		// SELECT * FROM table;
		static String query = 
			"SELECT * " +
				"FROM " + TABLE + " "
			;

		// -------------------------------------------------------------------------------------
	
			

		// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
		static String create = 
			"CREATE " +
				"TABLE " + TABLE +" ( " +
					IDG + " INT NOT NULL REFERENCES "+ DB2GiocatoreDAO.TABLE + ", " +
					IDS + " INT NOT NULL REFERENCES "+ DB2SquadraDAO.TABLE + ", " +
					"PRIMARY KEY ("+ IDS+ "," + IDG+") " +
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
		
	
		//----------------------------
	
		static String find_allenatore = 
			"SELECT " + DB2SquadraDAO.ALLENATORE +
			" FROM "+ TABLE + " RP, "+ DB2SquadraDAO.TABLE + " p " +
			" WHERE p."+  DB2SquadraDAO.ID + "= RP.idG AND RP." +  IDG +" = ? "
			;
		
	
	@Override
	public void create (int idG, int idS) {
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2MappingDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idS);
			prep_stmt.setInt(2, idG);
			
			prep_stmt.executeUpdate();
		
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
			//result = new Long(-2);
		}
		
		
	}

	/*@Override
	public S_CMappingDTO read(int stid, int couid) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		S_CMappingDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( stid < 0 || couid < 0)  {
			logger.warning("read(): cannot read an entry with a negative id");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(read_by_id);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, stid);
			prep_stmt.setInt(2, couid);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				S_CMappingDTO entry = new S_CMappingDTO();
				entry.setIdStudent(rs.getInt(IDR));
				entry.setIdCourse(rs.getInt(IDP));
				result = entry;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("read(): failed to retrieve entry with id = " + stid+": "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
*/


	@Override
	public boolean delete(int idG, int idS) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		//if ( code == null || code < 0 )  {
		if ( idS < 0 || idG <0)  {
			logger.warning("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2MappingDAO.delete);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idS);
			prep_stmt.setInt(2, idG);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("delete(): failed to delete entry with id = " + idS+", "+ idG +": "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public List<String> getAllenatoriFromGiocatore(int idG) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		List<String> result = new ArrayList<String>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( idG <0 ){
			logger.warning("idG is invalid");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2MappingDAO.find_allenatore);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, idG);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			while ( rs.next() ) {
				result.add( rs.getString(DB2SquadraDAO.ALLENATORE) );
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("find(): failed to retrieve entry with id = " + idG + ": "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}
	
	/*@Override
	public List<RistoranteDTO> findStudentByCourse(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				List<RistoranteDTO> result = new ArrayList<RistoranteDTO>();
				// --- 2. Controlli preliminari sui dati in ingresso ---
				if ( id <0 ){
					logger.warning("findStudentByCourse(): id course is invalid");
					return result;
				}
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					PreparedStatement prep_stmt = conn.prepareStatement(DB2MappingDAO.find_rist);
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					prep_stmt.clearParameters();
					prep_stmt.setInt(1, id);
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					ResultSet rs = prep_stmt.executeQuery();
					// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
					while ( rs.next() ) {
						RistoranteDTO entry = new RistoranteDTO();
						entry.setId(rs.getInt(DB2RistoranteDAO.ID));
						entry.setFirstName(rs.getString(DB2RistoranteDAO.FIRSTNAME));
						entry.setLastName(rs.getString(DB2RistoranteDAO.LASTNAME));
						long secs = rs.getDate(DB2RistoranteDAO.BIRTHDATE).getTime();
						entry.setBirthDate(new java.util.Date(secs));
						result.add( entry );
					}
					// --- e. Rilascia la struttura dati del risultato
					rs.close();
					// --- f. Rilascia la struttura dati dello statement
					prep_stmt.close();
				}
				// --- 5. Gestione di eventuali eccezioni ---
				catch (Exception e) {
					logger.warning("find(): failed to retrieve entry with id = " + id + ": "+e.getMessage());
					e.printStackTrace();
				}
				// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
				finally {
					Db2DAOFactory.closeConnection(conn);
				}
				// --- 7. Restituzione del risultato (eventualmente di fallimento)
				return result;
	}
*/
	@Override
	public boolean createTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(DB2MappingDAO.create);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("createTable(): failed to create table '"+TABLE+"': "+e.getMessage());
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	@Override
	public boolean dropTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		// n.d.
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			Statement stmt = conn.createStatement();
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// n.d.
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			stmt.execute(DB2MappingDAO.drop);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto a posto.
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("dropTable(): failed to drop table '"+TABLE+"': "+e.getMessage());
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}


}

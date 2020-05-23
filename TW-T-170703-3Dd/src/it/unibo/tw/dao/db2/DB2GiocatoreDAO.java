package it.unibo.tw.dao.db2;


import it.unibo.tw.dao.GiocatoreDAO;
import it.unibo.tw.dao.GiocatoreDTO;
import it.unibo.tw.dao.IdBroker;
import it.unibo.tw.dao.SquadraDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementazione di query jdbc su una versione hqldb della tabella entry
 * 
 * La scrittura dei metodi DAO con JDBC segue sempre e comunque questo schema:
 * 
	// --- 1. Dichiarazione della variabile per il risultato ---
	// --- 2. Controlli preliminari sui dati in ingresso ---
	// --- 3. Apertura della connessione ---
	// --- 4. Tentativo di accesso al db e impostazione del risultato ---
	// --- 5. Gestione di eventuali eccezioni ---
	// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
	// --- 7. Restituzione del risultato (eventualmente di fallimento)
 * 
 * Inoltre, all'interno di uno statement JDBC si segue lo schema generale
 * 
	// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
	// --- b. Pulisci e imposta i parametri (se ve ne sono)
	// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
	// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
	// --- e. Rilascia la struttura dati del risultato
	// --- f. Rilascia la struttura dati dello statement
 * 
 * @author pisi79
 *
 */
public class DB2GiocatoreDAO implements GiocatoreDAO {

	Logger logger = Logger.getLogger( getClass().getCanonicalName() );

	// === Costanti letterali per non sbagliarsi a scrivere !!! ============================
	
	static final String TABLE = "giocatori";

	// -------------------------------------------------------------------------------------

	static final String ID = "idG";
	static final String CF = "cf";
	static final String NOME = "nome";
	static final String COGNOME = "cognome";
	static final String ETA = "eta";
	
	// == STATEMENT SQL ====================================================================

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
	static final String insert = 
		"INSERT " +
			"INTO " + TABLE + " ( " + 
				 CF+ ", "+ NOME+", "+COGNOME+", "+ETA+" " +
			") " +
			"VALUES (?,?,?,?) "
		;
	
	static final String read_id = 
			"SELECT "+ ID +
					" FROM " + TABLE + " " +
					"WHERE " + CF + " = ? "
				;
	
	// SELECT * FROM table WHERE idcolumn = ?;
	static String read_by_id = 
		"SELECT * " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;

	// SELECT * FROM table WHERE stringcolumn = ?;
	static String read_all = 
		"SELECT * " +
		"FROM " + TABLE + " ";
	
	static String find_squadre = 
			"SELECT s.* " +
			"FROM " + DB2SquadraDAO.TABLE +" s, " + DB2MappingDAO.TABLE+" m "+
			" WHERE s." +  DB2SquadraDAO.ID +"=m." + DB2MappingDAO.IDS + " AND m." + DB2MappingDAO.IDG+"= ? ";
	
	// DELETE FROM table WHERE idcolumn = ?;
	static String delete = 
		"DELETE " +
			"FROM " + TABLE + " " +
			"WHERE " + ID + " = ? "
		;

	// UPDATE table SET xxxcolumn = ?, ... WHERE idcolumn = ?;
	static String update = 
		"UPDATE " + TABLE + " " +
			"SET " + 
			CF + " = ?, " +
				NOME + " = ?, " +
				COGNOME + " = ?, " +
				ETA + " = ?, " +
			"WHERE " + ID + " = ? "
		;



	// -------------------------------------------------------------------------------------

	// CREATE entrytable ( code INT NOT NULL PRIMARY KEY, ... );
	static String create = 
		"CREATE " +
			"TABLE " + TABLE +" ( " +
				ID + " INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
				CF + " VARCHAR(50) NOT NULL, " +
				NOME + " VARCHAR(50), " +
				COGNOME + " VARCHAR(50), " +
				ETA + " INT, " +
				"CONSTRAINT cod UNIQUE ("+CF+")" +
			") "
		;
	
	static String drop = 
		"DROP " +
			"TABLE " + TABLE + " "
		;
	
	// === METODI DAO =========================================================================
	
	/**
	 * C
	 */
	public void create(GiocatoreDTO r) {

		Connection conn = Db2DAOFactory.createConnection();
		
		try {
			
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2GiocatoreDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
		
			prep_stmt.setString(1, r.getCf());
			prep_stmt.setString(2, r.getNome());
			prep_stmt.setString(3, r.getCognome());
			prep_stmt.setInt(4, r.getEta());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d.
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
			
			IdBroker idb= new IdBrokerDB2();
			r.setIdG(idb.newId(ID,TABLE));

		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
			//result = new Long(-2);
		}

	}

	// -------------------------------------------------------------------------------------

	/**
	 * R
	 */
	public GiocatoreDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		GiocatoreDTO result = null;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( id < 0 )  {
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
			prep_stmt.setInt(1, id);
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			if ( rs.next() ) {
				GiocatoreDTO entry = new GiocatoreDTO();
				entry.setIdG(rs.getInt(ID));
				entry.setNome(rs.getString(NOME));
				entry.setCognome(rs.getString(COGNOME));
				entry.setEta(rs.getInt(ETA));
				entry.setCf(rs.getString(CF));
				result = entry;
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
			prep_stmt = conn.prepareStatement(find_squadre);
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
			rs = prep_stmt.executeQuery();
			while ( rs.next() ) {
				SquadraDTO entry = new SquadraDTO();
				entry.setIdS(rs.getInt(DB2SquadraDAO.ID));
				entry.setNome(rs.getString(DB2SquadraDAO.NOME));
				entry.setTorneo(rs.getString(DB2SquadraDAO.TORNEO));
				entry.setAllenatore(rs.getString(DB2SquadraDAO.ALLENATORE));
				result.getSquadre().add(entry);
			}
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("read(): failed to retrieve entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	/**
	 * U
	 */
	public boolean update(GiocatoreDTO r) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( r == null )  {
			logger.warning( "update(): failed to update a null entry");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2GiocatoreDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(5, r.getIdG());
			prep_stmt.setString(1, r.getCf());
			prep_stmt.setString(2, r.getNome());
			prep_stmt.setString(3, r.getCognome());
			prep_stmt.setInt(4, r.getEta());
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			prep_stmt.executeUpdate();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. qui devo solo dire al chiamante che e' andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("insert(): failed to update entry: "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	/**
	 * D
	 */
	public boolean delete(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		//if ( code == null || code < 0 )  {
		if ( id < 0 )  {
			logger.warning("delete(): cannot delete an entry with an invalid id ");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2GiocatoreDAO.delete);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(1, id);
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
			logger.warning("delete(): failed to delete entry with id = " + id+": "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

	// -------------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------

	/**
	 * Creazione della table
	 */
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
			stmt.execute(create);
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			// n.d. Qui devo solo dire al chiamante che è andato tutto liscio
			result = true;
			// --- e. Rilascia la struttura dati del risultato
			// n.d.
			// --- f. Rilascia la struttura dati dello statement
			stmt.close();
			//IdBroker idb = new IdBrokerDB2();
			//idb.newSeq("sequenza_idG");
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

	// -------------------------------------------------------------------------------------

	/**
	 * Rimozione della table
	 */
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
			stmt.execute(drop);
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

	@Override
	public List<GiocatoreDTO> findAll() {
		List<GiocatoreDTO> result = new ArrayList<GiocatoreDTO>();
		// --- 2. Controlli preliminari sui dati in ingresso ---
		
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(read_all);
			PreparedStatement prep_stmt2 = conn.prepareStatement(find_squadre);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
			ResultSet rs = prep_stmt.executeQuery();
			// --- d. Cicla sul risultato (se presente) pe accedere ai valori di ogni sua tupla
			while ( rs.next() ) {
				GiocatoreDTO entry = new GiocatoreDTO();
				entry.setIdG(rs.getInt(ID));
				entry.setNome(rs.getString(NOME));
				entry.setCognome(rs.getString(COGNOME));
				entry.setEta(rs.getInt(ETA));
				entry.setCf(rs.getString(CF));
				
				
				
				prep_stmt2.clearParameters();
				prep_stmt2.setInt(1, entry.getIdG());
				ResultSet rs2 = prep_stmt2.executeQuery();
				while ( rs2.next() ) {
					SquadraDTO entry2= new SquadraDTO();
					entry2.setIdS(rs2.getInt(DB2SquadraDAO.ID));
					entry2.setNome(rs2.getString(DB2SquadraDAO.NOME));
					entry2.setTorneo(rs2.getString(DB2SquadraDAO.TORNEO));
					entry2.setAllenatore(rs2.getString(DB2SquadraDAO.ALLENATORE));
					entry.getSquadre().add(entry2);
				}
				result.add(entry);
				rs2.close();
			}
			// --- e. Rilascia la struttura dati del risultato
			rs.close();
			
			// --- f. Rilascia la struttura dati dello statement
			prep_stmt.close();
			prep_stmt2.close();
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("read(): failed to retrieve entry: "+e.getMessage());
			e.printStackTrace();
		}
		// --- 6. Rilascio, SEMPRE E COMUNQUE, la connessione prima di restituire il controllo al chiamante
		finally {
			Db2DAOFactory.closeConnection(conn);
		}
		// --- 7. Restituzione del risultato (eventualmente di fallimento)
		return result;
	}

}
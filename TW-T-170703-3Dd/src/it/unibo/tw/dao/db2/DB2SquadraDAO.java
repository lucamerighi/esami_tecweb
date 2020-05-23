package it.unibo.tw.dao.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import it.unibo.tw.dao.SquadraDAO;
import it.unibo.tw.dao.SquadraDTO;
import it.unibo.tw.dao.GiocatoreDTO;
import it.unibo.tw.dao.IdBroker;

public class DB2SquadraDAO implements SquadraDAO {
	Logger logger = Logger.getLogger( getClass().getCanonicalName() );

	static final String TABLE = "squadre";

	// -------------------------------------------------------------------------------------

	static final String ID = "idS";
	static final String NOME = "nome";
	static final String TORNEO = "torneo";
	static final String ALLENATORE = "allenatoe";

	// INSERT INTO table ( id, name, description, ...) VALUES ( ?,?, ... );
		static final String insert = 
			"INSERT " +
				"INTO " + TABLE + " ( " + 
				 NOME+", "+TORNEO+ ", "+ALLENATORE+") " +
				"VALUES (?,?,?) "
			;
		
		static final String read_id = 
				"SELECT "+ ID +
						" FROM " + TABLE + " " +
						"WHERE " + NOME + " = ? AND "+ TORNEO+ "=?";
					;
	
	// SELECT * FROM table WHERE idcolumn = ?;
		static String read_by_id = 
			"SELECT * " +
				"FROM " + TABLE + " " +
				"WHERE " + ID + " = ? "
			;
		
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
					NOME + " = ?, " +
					TORNEO + " = ?, " +
					ALLENATORE + " = ?, " +
				"WHERE " + ID + " = ? "
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
					ID + " INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
					NOME + " VARCHAR(50) NOT NULL, " +
					TORNEO + " VARCHAR(50) NOT NULL, " +
					ALLENATORE + " VARCHAR(50), " +
					"CONSTRAINT na UNIQUE ("+NOME+","+ TORNEO+") " +
				") "
			;
		
		static String drop = 
			"DROP " +
				"TABLE " + TABLE + " "
			;
		
	
		
	@Override
	public void create(SquadraDTO p) {
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			
			
			
			PreparedStatement prep_stmt = conn.prepareStatement(DB2SquadraDAO.insert);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
		
			prep_stmt.setString(1,p.getNome());
			prep_stmt.setString(2,p.getTorneo());
			prep_stmt.setString(3,p.getAllenatore());
			prep_stmt.executeUpdate();
		
			prep_stmt.close();
			
			IdBroker idb= new IdBrokerDB2();
			p.setIdS(idb.newId(ID, TABLE));
			
		}
		// --- 5. Gestione di eventuali eccezioni ---
		catch (Exception e) {
			logger.warning("create(): failed to insert entry: " + e.getMessage());
			e.printStackTrace();
			//result = new Long(-2);
		}
		
	}

	@Override
	public SquadraDTO read(int id) {
		// --- 1. Dichiarazione della variabile per il risultato ---
				SquadraDTO result = null;
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
						SquadraDTO entry = new SquadraDTO();
						entry.setIdS(rs.getInt(ID));
						entry.setNome(rs.getString(NOME));
						entry.setTorneo(rs.getString(TORNEO));
						entry.setAllenatore(rs.getString(ALLENATORE));
						result = entry;
					}
					// --- e. Rilascia la struttura dati del risultato
					rs.close();
					// --- f. Rilascia la struttura dati dello statement
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

	@Override
	public boolean update(SquadraDTO p) {
		boolean result = false;
		// --- 2. Controlli preliminari sui dati in ingresso ---
		if ( p == null )  {
			logger.warning( "update(): failed to update a null entry");
			return result;
		}
		// --- 3. Apertura della connessione ---
		Connection conn = Db2DAOFactory.createConnection();
		// --- 4. Tentativo di accesso al db e impostazione del risultato ---
		try {
			// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
			PreparedStatement prep_stmt = conn.prepareStatement(DB2SquadraDAO.update);
			// --- b. Pulisci e imposta i parametri (se ve ne sono)
			prep_stmt.clearParameters();
			prep_stmt.setInt(4, p.getIdS());
			prep_stmt.setString(1,p.getNome());
			prep_stmt.setString(2,p.getTorneo());
			prep_stmt.setString(3,p.getAllenatore());
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

	@Override
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
			PreparedStatement prep_stmt = conn.prepareStatement(DB2SquadraDAO.delete);
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


	@Override
	public boolean createTable() {
		// --- 1. Dichiarazione della variabile per il risultato ---
				boolean result = false;
				// --- 2. Controlli preliminari sui dati in ingresso ---
				// n.d.
				// --- 3. Apertura della connessione ---
				Connection conn = Db2DAOFactory.createConnection();
				
				//IdBroker idb = new IdBrokerDB2();
				//idb.newSeq("sequenza_idS");
				
				// --- 4. Tentativo di accesso al db e impostazione del risultato ---
				try {
					// --- a. Crea (se senza parametri) o prepara (se con parametri) lo statement
					Statement stmt = conn.createStatement();
					// --- b. Pulisci e imposta i parametri (se ve ne sono)
					// n.d.
					// --- c. Esegui l'azione sul database ed estrai il risultato (se atteso)
					stmt.execute(DB2SquadraDAO.create);
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
					stmt.execute(DB2SquadraDAO.drop);
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

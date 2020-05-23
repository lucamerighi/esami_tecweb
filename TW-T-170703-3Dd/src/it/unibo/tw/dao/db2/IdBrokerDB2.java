package it.unibo.tw.dao.db2;

import java.sql.*;

import javax.persistence.PersistenceException;

import it.unibo.tw.dao.IdBroker;

public class IdBrokerDB2 implements IdBroker {
	
	public int newId(String seq, String tab) throws PersistenceException {
		int newId = -1;
		ResultSet result = null;
		PreparedStatement statement = null;
		Connection connection = Db2DAOFactory.createConnection();
		try {
			String sqlQuery = "SELECT MAX(" + seq +") AS nuovoId FROM " + tab;
			statement = connection.prepareStatement(sqlQuery);
			result = statement.executeQuery();
			if (result.next()) newId = result.getInt("nuovoId");
			else throw new PersistenceException("invalid id");
		} catch(SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		finally {
			try {
				if (statement != null) statement.close();
				if (connection!= null) connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return newId;
	}

	@Override
	public void newSeq(String seq) {
		ResultSet result = null;
		PreparedStatement statement = null;
		Connection connection = Db2DAOFactory.createConnection();
		try {
			String sequenceQuery = "CREATE SEQUENCE " + seq +
					" AS INTEGER" + 
					" START WITH 1" + 
					" INCREMENT BY 1" + 
					" MINVALUE 0" + 
					" MAXVALUE 9999999" + 
					" NO CYCLE";
			
			statement = connection.prepareStatement(sequenceQuery);
			statement.executeUpdate();

		} catch(SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		finally {
			try {
				if (statement != null) statement.close();
				if (connection!= null) connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return;
	}
}
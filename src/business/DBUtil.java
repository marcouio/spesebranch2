package business;

import java.sql.Connection;
import java.util.Date;

import testo.UtilText;
import db.ConnectionPool;
import db.UtilDb;

@Deprecated
public class DBUtil {

	// per connessione da jar
	// public static String URL = "jdbc:sqlite:./GestioneSpese.sqlite";
	// per connessione per workspace
	private static Connection connection = null;

	public static Connection getConnection() throws Exception {
		connection = ConnectionPool.getSingleton().getConnection();
		return connection;
	}

	/**
	 * Metodo per chiudere una connessione al database
	 */
	public static void closeConnection() {
		try {
			ConnectionPool.getSingleton().chiudiOggettiDb(connection);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
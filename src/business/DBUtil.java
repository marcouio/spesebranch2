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

	/**
	 * Dato un campo, ne valuta la lunghezza. Se e' piu' corto della dimensione
	 * inserita come parametro aggiunge campi vuoti, altrimenti tronca
	 * aggiungendo uno spazio finale.
	 * 
	 * @param campo
	 * @param dimensione
	 * @deprecated
	 * @return String
	 */
	@Deprecated
	public static String creaStringStessaDimensione(final String campo, final int dimensione) {
		return UtilText.creaStringStessaDimensione(campo, dimensione);
	}

	// CONVERSIONE FORMATO STRINGA --> DATA

	/**
	 * Il metodo da la possibilit� di convertire una stringa in una data,
	 * passandogli il formato in cui verr� � presentata nella stringa
	 * 
	 * @param date
	 * @param format
	 * @return Date
	 * @deprecated
	 */
	@Deprecated
	public static Date stringToDate(final String date, final String format) {
		return UtilDb.stringToDate(date, format);
	}

	// CONVERSIONE FORMATO DATA --> STRINGA

	/**
	 * Trasforma una data in una stringa seguendo il formato specificato
	 * 
	 * @param date
	 * @param format
	 * @deprecated
	 * @return String
	 */
	@Deprecated
	public static String dataToString(final Date date, final String format) {
		return UtilDb.dataToString(date, format);
	}

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
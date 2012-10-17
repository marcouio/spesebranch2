package business;

import db.ConnectionPool;

public class ConnectionClass extends ConnectionPool{

	public static String URL = "jdbc:sqlite:" + Database.DB_URL;
	public static final String USR = "root";
	public static final String PWD = "marco";
	public static final String DRIVERCLASSNAME = "org.sqlite.JDBC";
	@Override
	protected String getPassword() {
		return "marco";
	}

	@Override
	protected String getUser() {
		return "root";
	}

	@Override
	protected String getDriver() {
		return DRIVERCLASSNAME;
	}

	@Override
	protected String getDbUrl() {
		return URL;
	}

	@Override
	protected int getNumeroConnessioniDisponibili() {
		return 1;
	}

}

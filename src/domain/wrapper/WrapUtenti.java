package domain.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.Entrate;
import domain.IUtenti;
import domain.SingleSpesa;
import domain.Utenti;

public class WrapUtenti extends Observable implements IDAO, IUtenti {

	public static final String NOME_TABELLA = "utenti";
	public static final String ID = "idUtente";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NOME = "nome";
	public static final String COGNOME = "cognome";
	
	private Utenti utente;
	private IDAO genericDao = UtilityDAO.getDaoByTipo(Utenti.class);

	public WrapUtenti() {
		utente = new Utenti();
	}
	
	@Override
	public Object selectById(int id) throws Exception {
		try {
			return genericDao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	@Override
	public ArrayList<?> selectAll() throws Exception {
		try {
			return (ArrayList<?>) genericDao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	public Utenti utenteLogin(String username, String password) throws Exception {
		String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + USERNAME + " = '" + username + "' AND " + PASSWORD
				+ "='" + password + "'";
		try {
			Connection cn = ConnectionPool.getSingleton().getConnection();
			Utenti utente = new Utenti();

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				utente.setIdUtente(rs.getInt(1));
				utente.setUsername(rs.getString(2));
				utente.setPassword(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return utente;

	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		try {
			return genericDao.insert(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean delete(int id) throws Exception {
		try {
			return genericDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		try {
			return genericDao.update(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean deleteAll() throws Exception {
		try {
			return genericDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	public Utenti selectByUserAndPass(String user, String pass) throws Exception {
		String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + USERNAME + " = '" + user + "' AND " + PASSWORD
				+ "='" + pass + "'";
		Utenti utente =null;
		try {
			Connection cn = ConnectionPool.getSingleton().getConnection();

			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				utente = new Utenti();
				utente.setIdUtente(rs.getInt(1));
				utente.setNome(rs.getString(2));
				utente.setCognome(rs.getString(3));
				utente.setUsername(rs.getString(4));
				utente.setPassword(rs.getString(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return utente;

	}

	@Override
	public int getIdUtente() {
		return utente.getIdUtente();
	}

	@Override
	public void setIdUtente(int idUtente) {
		utente.setIdUtente(idUtente);
	}

	@Override
	public String getPassword() {
		return utente.getPassword();
	}

	@Override
	public void setPassword(String password) {
		utente.setPassword(password);
	}

	@Override
	public String getUsername() {
		return utente.getUsername();
	}

	@Override
	public void setUsername(String username) {
		utente.setUsername(username);
	}

	@Override
	public Set<Entrate> getEntrates() {
		return utente.getEntrates();
	}

	@Override
	public void setEntrates(Set<Entrate> entrates) {
		utente.setEntrates(entrates);
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return utente.getSingleSpesas();
	}

	@Override
	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		utente.setSingleSpesas(singleSpesas);
	}

	@Override
	public void setNome(String nome) {
		utente.setNome(nome);
	}

	@Override
	public String getNome() {
		return utente.getNome();
	}

	@Override
	public void setCognome(String cognome) {
		utente.setCognome(cognome);
	}

	@Override
	public String getCognome() {
		return utente.getCognome();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return utente;
	}
	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}
	
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

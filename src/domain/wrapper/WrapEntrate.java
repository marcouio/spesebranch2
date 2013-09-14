package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.AltreUtil;
import business.ControlloreSpese;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.Entrate;
import domain.IEntrate;
import domain.Utenti;

public class WrapEntrate extends Observable implements IEntrate, IDAO {

	public static final String NOME_TABELLA = "entrate";
	public static final String ID = "idEntrate";
	public static final String DATA = "data";
	public static final String DATAINS = "dataIns";
	public static final String NOME = "nome";
	public static final String INEURO = "inEuro";
	public static final String DESCRIZIONE = "descrizione";
	public static final String FISSEOVAR = "Fisse_o_Var";
	public static final String IDUTENTE = "idUtente";
	public static final String IDENTRATE = "idEntrate";

	public static final String IMPORTANZA_FISSE = "Fisse";
	public static final String IMPORTANZA_VARIABILI = "Variabili";
	
	private final Entrate entrate;
	IDAO genericDao = UtilityDAO.getDaoByTipo(Entrate.class);

	public WrapEntrate() {
		entrate = new Entrate();
	}

	@Override
	public Object selectById(final int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			return genericDao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
		
	}

	public Vector<Object> selectAllForUtente() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		final Vector<Object> entrate = new Vector<Object>();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final String sql = "SELECT * FROM " + WrapEntrate.NOME_TABELLA + " WHERE " + WrapEntrate.IDUTENTE + " = " + utente.getIdUtente();
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				final Entrate entrata = new Entrate();
				entrata.setIdEntrate(rs.getInt(1));
				entrata.setDescrizione(rs.getString(2));
				entrata.setFisseoVar(rs.getString(3));
				entrata.setInEuro(rs.getDouble(4));
				entrata.setData(rs.getString(5));
				entrata.setNome(rs.getString(6));
				entrata.setUtenti(utente);
				entrata.setDataIns(rs.getString(8));
				entrate.add(entrata);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return entrate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> selectAll() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			return (ArrayList<Object>) genericDao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	@Override
	public boolean insert(final Object oggettoEntita) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	public boolean delete(final int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	public boolean update(final Object oggettoEntita) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
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
	public boolean deleteAll() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			return genericDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 * 
	 * @param numEntry
	 * @return Vector<Entrate>
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Vector<Entrate> movimentiEntrateFiltrati(final String dataDa, final String dataA, final String nome, final Double euro, final String categoria) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Vector<Entrate> entrate = null;
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getIdUtente();
		}

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + WrapEntrate.NOME_TABELLA + " WHERE " + WrapEntrate.IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA)) {
			sql.append(" AND " + WrapEntrate.DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa)) {
			sql.append(" AND " + WrapEntrate.DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(" AND " + WrapEntrate.NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(" AND " + WrapEntrate.INEURO + " = " + euro);
		}
		if (categoria != null) {
			sql.append(" AND " + WrapEntrate.FISSEOVAR + " = '" + categoria + "'");
		}
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql.toString());
			entrate = new Vector<Entrate>();
			while (rs.next()) {
				final Entrate e = new Entrate();
				e.setData(rs.getString(5));
				e.setDescrizione(rs.getString(2));
				e.setFisseoVar(rs.getString(3));
				e.setIdEntrate(rs.getInt(1));
				e.setInEuro(rs.getDouble(4));
				e.setNome(rs.getString(6));
				e.setUtenti(utente);
				e.setDataIns(rs.getString(8));
				entrate.add(e);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return entrate;

	}

	// metodo che restituisce le ultime dieci entrate
	/**
	 * Permette di ottenere un vettore con un numero di entita' entrate inserito
	 * come parametro
	 * 
	 * @param numEntry
	 * @return Vector<Entrate>
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Vector<Entrate> dieciEntrate(final int numEntry) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Vector<Entrate> entrate = null;
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getIdUtente();
		}

		final String sql = "SELECT * FROM " + WrapEntrate.NOME_TABELLA + " where " + WrapEntrate.DATA + " BETWEEN '" + Impostazioni.getAnno() + "/01/01" + "'" + " AND '"
				+ Impostazioni.getAnno() + "/12/31" + "'" + " AND " + WrapEntrate.IDUTENTE + " = " + idUtente + " ORDER BY " + WrapEntrate.ID + " desc limit 0," + numEntry;
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			entrate = new Vector<Entrate>();
			while (rs.next()) {
				final Entrate e = new Entrate();
				e.setData(rs.getString(5));
				e.setDescrizione(rs.getString(2));
				e.setFisseoVar(rs.getString(3));
				e.setIdEntrate(rs.getInt(1));
				e.setInEuro(rs.getDouble(4));
				e.setNome(rs.getString(6));
				e.setUtenti(utente);
				e.setDataIns(rs.getString(8));
				entrate.add(e);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return entrate;

	}

	/**
	 * Cancella l'ultima entita' "Entrate" inserita
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public boolean DeleteLastEntrate() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		boolean ok = false;
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final String sql = "SELECT * FROM " + WrapEntrate.NOME_TABELLA + " WHERE " + WrapEntrate.IDUTENTE + " = " + ((Utenti) ControlloreSpese.getSingleton().getUtenteLogin()).getIdUtente()
					+ " ORDER BY " + WrapEntrate.DATAINS + " DESC";

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				final String sql2 = "DELETE FROM " + WrapEntrate.NOME_TABELLA + " WHERE " + WrapEntrate.ID + "=?";
				final PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, rs.getInt(1));
				ps.executeUpdate();
				ok = true;

			}

		} catch (final Exception e) {
			e.printStackTrace();
			ControlloreSpese.getLog().severe("Operazione non riuscita: " + e.getMessage());
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
	}

	@Override
	public String getData() {
		return entrate.getData();
	}

	@Override
	public void setData(final String data) {
		entrate.setData(data);
	}

	@Override
	public String getDescrizione() {
		return entrate.getDescrizione();
	}

	@Override
	public void setDescrizione(final String descrizione) {
		entrate.setDescrizione(descrizione);
	}

	@Override
	public String getFisseoVar() {
		return entrate.getFisseoVar();
	}

	@Override
	public void setFisseoVar(final String FisseoVar) {
		entrate.setFisseoVar(FisseoVar);
	}

	@Override
	public int getIdEntrate() {
		return entrate.getIdEntrate();
	}

	@Override
	public void setIdEntrate(final int idEntrate) {
		entrate.setIdEntrate(idEntrate);
	}

	@Override
	public double getInEuro() {
		return entrate.getInEuro();
	}

	@Override
	public void setInEuro(final double inEuro) {
		entrate.setInEuro(inEuro);
	}

	@Override
	public String getNome() {
		return entrate.getNome();
	}

	@Override
	public void setNome(final String nome) {
		entrate.setNome(nome);
	}

	@Override
	public Utenti getUtenti() {
		return entrate.getUtenti();
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		entrate.setUtenti(utenti);
	}
	
	@Override
	public int getIdUtente() {
		return entrate.getIdUtente();
	}

	@Override
	public void setIdUtente(int idUtente) {
		entrate.setIdUtente(idUtente);
		
	}

	@Override
	public void setDataIns(final String date) {
		entrate.setDataIns(date);
	}

	@Override
	public String getDataIns() {
		return entrate.getDataIns();
	}

	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return entrate;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

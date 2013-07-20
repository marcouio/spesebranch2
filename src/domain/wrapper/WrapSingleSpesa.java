package domain.wrapper;

import grafica.componenti.alert.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Vector;

import view.impostazioni.Impostazioni;
import business.AltreUtil;
import business.ControlloreSpese;
import business.DBUtil;
import business.cache.CacheCategorie;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.CatSpese;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.Utenti;

public class WrapSingleSpesa extends Observable implements IDAO, ISingleSpesa {

	public static final String NOME_TABELLA = "single_spesa";
	public static final String ID = "idSpesa";
	public static final String DATA = "Data";
	public static final String DATAINS = "dataIns";
	public static final String IDCATEGORIE = "idCategorie";
	public static final String INEURO = "inEuro";
	public static final String DESCRIZIONE = "descrizione";
	public static final String NOME = "nome";
	public static final String IDUTENTE = "idUtente";

	private final SingleSpesa uscita;
	private IDAO genericDao = UtilityDAO.getDaoByTipo(SingleSpesa.class);
	
	public WrapSingleSpesa() {
		uscita = new SingleSpesa();
	}

	@Override
	public Object selectById(final int id) {
		try {
			return genericDao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return null;
	}

	public Vector<Object> selectAllForUtente() {
		final Vector<Object> uscite = new Vector<Object>();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		final Map<String, AbstractOggettoEntita> mappaCategorie = CacheCategorie.getSingleton().getAllCategorie();
		try {
			final Connection cn = DBUtil.getConnection();
			final String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + IDUTENTE + " = " + utente.getIdUtente();
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				final CatSpese categoria = (CatSpese) mappaCategorie.get(Integer.toString(rs.getInt(5)));

				final SingleSpesa uscita = new SingleSpesa();
				uscita.setIdSpesa(rs.getInt(1));
				uscita.setData(rs.getString(2));
				uscita.setInEuro(rs.getDouble(3));
				uscita.setDescrizione(rs.getString(4));
				uscita.setCatSpese(categoria);
				uscita.setNome(rs.getString(6));
				uscita.setUtenti(utente);
				uscita.setDataIns(rs.getString(8));
				uscite.add(uscita);
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return uscite;

	}

	public static void main(final String[] args) {
		final WrapSingleSpesa wrap = new WrapSingleSpesa();
		wrap.selectAll();
	}

	@Override
	public ArrayList<Object> selectAll() {
		try {
			return (ArrayList<Object>) genericDao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return null;
	}

	@Override
	public boolean insert(final Object oggettoEntita) {
		try {
			return genericDao.insert(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return false;
	}

	@Override
	public boolean delete(final int id) {
		try {
			return genericDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return false;
	}

	@Override
	public boolean update(final Object oggettoEntita) {
		try {
			return genericDao.update(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return false;
	}

	@Override
	public boolean deleteAll() {
		try {
			return genericDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection();
		}
		return false;
	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 * 
	 * @param dieci
	 * @return Vector<SingleSpesa>
	 */
	public Vector<SingleSpesa> movimentiUsciteFiltrate(final String dataDa, final String dataA, final String nome, final Double euro, final String catSpese) {
		Vector<SingleSpesa> sSpesa = null;

		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		int idUtente = 0;
		if (utente != null) {
			idUtente = utente.getIdUtente();
		}

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + NOME_TABELLA + " WHERE " + IDUTENTE + " = " + idUtente);
		if (AltreUtil.checkData(dataDa) && AltreUtil.checkData(dataA) && dataDa != null && dataA != null) {
			sql.append(" AND " + DATA + " BETWEEN '" + dataDa + "'" + " AND '" + dataA + "'");
		} else if (AltreUtil.checkData(dataDa) && dataDa != null) {
			sql.append(" AND " + DATA + " = '" + dataDa + "'");
		}
		if (nome != null) {
			sql.append(" AND " + NOME + " = '" + nome + "'");
		}
		if (euro != null) {
			sql.append(" AND " + INEURO + " = " + euro);
		}
		if (catSpese != null && Integer.parseInt(catSpese) != 0) {
			sql.append(" AND " + IDCATEGORIE + " = " + Integer.parseInt(catSpese));
		}
		try {
			final Connection cn = DBUtil.getConnection();

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql.toString());
			sSpesa = new Vector<SingleSpesa>();
			while (rs.next()) {
				final CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(rs.getString(5));
				final SingleSpesa ss = new SingleSpesa();
				ss.setIdSpesa(rs.getInt(1));
				ss.setData(rs.getString(2));
				ss.setInEuro(rs.getDouble(3));
				ss.setDescrizione(rs.getString(4));
				ss.setNome(rs.getString(6));
				ss.setCatSpese(categoria);
				ss.setDataIns(rs.getString(8));
				ss.setUtenti(utente);
				sSpesa.add(ss);
			}
		} catch (final Exception e) {
			ControlloreSpese.getLog().severe("Operazione non eseguita: " + e.getMessage());
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return sSpesa;

	}

	/**
	 * Permette di ottenere un vettore con un numero di entita' SingleSpesa
	 * inserito come parametro
	 * 
	 * @param dieci
	 * @return Vector<SingleSpesa>
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SingleSpesa> dieciUscite(final int dieci) {
		 try {
			return (ArrayList<SingleSpesa>) genericDao.selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Cancella l'ultima entita' "SingleSpesa" inserita
	 */
	public boolean deleteLastSpesa() {
		boolean ok = false;
		try {
			final Connection cn = DBUtil.getConnection();
			final String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + WrapSingleSpesa.IDUTENTE + " = " + ((Utenti) ControlloreSpese.getSingleton().getUtenteLogin()).getIdUtente()
			+ " ORDER BY " + DATAINS + " DESC";

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				SingleSpesa ss = new SingleSpesa();
				ss = new SingleSpesa();
				ss.setIdSpesa(rs.getInt(1));

				final String sql2 = "DELETE FROM " + NOME_TABELLA + " WHERE " + ID + "=?";
				final PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, ss.getIdSpesa());
				ps.executeUpdate();
				ok = true;
			} else {
				Alert.segnalazioneErroreWarning("Non ci sono uscite per l'utente loggato");
			}

		} catch (final Exception e) {
			e.printStackTrace();
			ControlloreSpese.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public String getData() {
		return uscita.getData();
	}

	@Override
	public void setData(final String Data) {
		uscita.setData(Data);
	}

	@Override
	public String getDescrizione() {
		return uscita.getDescrizione();
	}

	@Override
	public void setDescrizione(final String descrizione) {
		uscita.setDescrizione(descrizione);
	}

	@Override
	public int getIdSpesa() {
		return uscita.getIdSpesa();
	}

	@Override
	public void setIdSpesa(final int idSpesa) {
		uscita.setIdSpesa(idSpesa);
		uscita.setIdEntita(Integer.toString(idSpesa));
	}

	@Override
	public double getInEuro() {
		return uscita.getInEuro();
	}

	@Override
	public void setInEuro(final double d) {
		uscita.setInEuro(d);
	}

	@Override
	public String getNome() {
		return uscita.getNome();
	}

	@Override
	public void setNome(final String nome) {
		uscita.setNome(nome);
	}

	@Override
	public CatSpese getCatSpese() {
		return uscita.getCatSpese();
	}

	@Override
	public void setCatSpese(final CatSpese catSpese) {
		uscita.setCatSpese(catSpese);
	}

	@Override
	public Utenti getUtenti() {
		return uscita.getUtenti();
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		uscita.setUtenti(utenti);
	}

	@Override
	public void setDataIns(final String dataIns) {
		uscita.setDataIns(dataIns);
	}

	@Override
	public String getDataIns() {
		return uscita.getDataIns();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return uscita;
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

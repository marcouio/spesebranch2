package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;

import business.ControlloreSpese;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.INote;
import domain.Note;
import domain.Utenti;

public class WrapNote extends Observable implements IDAO, INote {

	public static final String NOME_TABELLA = "note";
	public static final String NOME = "nome";
	public static final String DATA = "data";
	public static final String DATAINS = "dataIns";
	public static final String DESCRIZIONE = "descrizione";
	public static final String ID = "idNote";
	public static final String IDUTENTE = "idUtente";
	
	private final Note note;
	
	private IDAO genericDao = UtilityDAO.getDaoByTipo(Note.class);

	public WrapNote(final Note nota) {
		this.note = nota;
	}

	public WrapNote() {
		note = new Note();
	}

	@Override
	public String getData() {
		return note.getData();
	}

	@Override
	public void setData(final String _data_) {
		note.setData(_data_);
	}

	@Override
	public String getDataIns() {
		return note.getDataIns();
	}

	@Override
	public void setDataIns(final String _dataIns_) {
		note.setDataIns(_dataIns_);
	}

	@Override
	public String getDescrizione() {
		return note.getDescrizione();
	}

	@Override
	public void setDescrizione(final String _descrizione_) {
		note.setDescrizione(_descrizione_);
	}

	@Override
	public int getIdNote() {
		return note.getIdNote();
	}

	@Override
	public void setIdNote(final int _idNote_) {
		note.setIdNote(_idNote_);
	}

	@Override
	public int getIdUtente() {
		return note.getIdUtente();
	}

	@Override
	public void setIdUtente(final int _idUtente_) {
		note.setIdUtente(_idUtente_);
	}

	@Override
	public String getNome() {
		return note.getNome();
	}

	@Override
	public void setNome(final String _nome_) {
		note.setNome(_nome_);
	}

	@Override
	public void setUtenti(final Utenti utenti) {
		note.setUtenti(utenti);
	}

	@Override
	public Utenti getUtenti() {
		return note.getUtenti();
	}

	@Override
	public Object selectById(final int id) throws Exception {
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

	@Override
	public boolean insert(final Object oggettoEntita) throws Exception {
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
	public boolean delete(final int id) throws Exception {
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
	public boolean update(final Object oggettoEntita) throws Exception   {
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

	/**
	 * Cancella l'ultima entita' "Note" inserita
	 * @throws Exception 
	 */
	public boolean DeleteLastNote() throws Exception {
		boolean ok = false;
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();
			final String sql = "SELECT * FROM " + NOME_TABELLA + " WHERE " + IDUTENTE + " = " + ((Utenti) ControlloreSpese.getSingleton().getUtenteLogin()).getIdUtente() + " ORDER BY "
				+ DATAINS + " DESC";

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				final String sql2 = "DELETE FROM " + NOME_TABELLA + " WHERE " + ID + "=?";
				final PreparedStatement ps = cn.prepareStatement(sql2);
				ps.setInt(1, rs.getInt(1));
				ps.executeUpdate();
				ok = true;

			}

		} catch (final Exception e) {
			e.printStackTrace();
			ControlloreSpese.getLog().severe("Operazione non eseguita: " + e.getMessage());
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return ok;
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
	public AbstractOggettoEntita getEntitaPadre()
			throws Exception {
		return note;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

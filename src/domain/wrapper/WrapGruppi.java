package domain.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import business.DBUtil;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.CatSpese;
import domain.Gruppi;
import domain.IGruppi;

public class WrapGruppi extends Observable implements IDAO, IGruppi {

	public static final String NOME_TABELLA = "Gruppi";
	public static final String ID = "idGruppo";
	public static final String NOME = "nome";
	private final Gruppi gruppo;
	private IDAO genericDao = UtilityDAO.getDaoByTipo(Gruppi.class);
	
	public WrapGruppi() {
		gruppo = new Gruppi();
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
		} finally {
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

	public Gruppi selectByNome(final String nome) {

		try {
		final Connection cn = DBUtil.getConnection();
		final String sql = "SELECT * FROM " + WrapGruppi.NOME_TABELLA + " WHERE " + WrapGruppi.NOME + " = \"" + nome + "\"";

		Gruppi gruppo = null;


			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				gruppo = new Gruppi();
				gruppo.setIdGruppo(rs.getInt(1));
				gruppo.setNome(rs.getString(2));
				gruppo.setDescrizione(rs.getString(3));
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
		return gruppo;
	}

	@Override
	public String getDescrizione() {
		return gruppo.getDescrizione();
	}

	@Override
	public void setDescrizione(final String descrizione) {
		gruppo.setDescrizione(descrizione);
	}

	@Override
	public int getIdGruppo() {
		return gruppo.getIdGruppo();
	}

	@Override
	public void setIdGruppo(final int idGruppo) {
		gruppo.setIdGruppo(idGruppo);
	}

	@Override
	public String getNome() {
		return gruppo.getNome();
	}

	@Override
	public void setNome(final String nome) {
		gruppo.setNome(nome);
	}

	@Override
	public Set<CatSpese> getCatSpeses() {
		return gruppo.getCatSpeses();
	}

	@Override
	public void setCatSpeses(final Set<CatSpese> catSpeses) {
		gruppo.setCatSpeses(catSpeses);
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
		return gruppo;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
package domain.wrapper;

import java.util.ArrayList;
import java.util.Observable;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.IRisparmio;
import domain.Risparmio;

public class WrapRisparmio extends Observable implements IDAO,IRisparmio{

	public static final String NOME_TABELLA = "risparmio";
	public static final String ID = "idRisparmio";
	public static final String PERCSULTOT = "PercSulTotale";
	
	private Risparmio risparmio;
	IDAO genericDao = UtilityDAO.getDaoByTipo(Risparmio.class);
	
	public WrapRisparmio() {
		risparmio = new Risparmio();
	}
	
	@Override
	public Object selectById(int id) throws Exception {
		try {
			return genericDao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		} finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		try {
			return genericDao.insert(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
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
		} finally{
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
		} finally{
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
		} finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return risparmio;
	}

	@Override
	public int getIdRisparmio() {
		return risparmio.getIdRisparmio();
	}

	@Override
	public void setIdRisparmio(int idRisparmio) {
		risparmio.setIdRisparmio(idRisparmio);
	}

	@Override
	public double getPerSulTotale() {
		return risparmio.getPerSulTotale();
	}

	@Override
	public void setPerSulTotale(double PerSulTotale) {
		risparmio.setPerSulTotale(PerSulTotale);
	}

	@Override
	public String getNome() {
		return risparmio.getNome();
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

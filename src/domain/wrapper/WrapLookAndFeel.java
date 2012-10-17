package domain.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import business.DBUtil;

import command.javabeancommand.AbstractOggettoEntita;

import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.ILookandfeel;
import domain.Lookandfeel;

public class WrapLookAndFeel extends Observable implements IDAO, ILookandfeel {

	public static final String NOME_TABELLA = "lookAndFeel";
	public static final String ID = "idLook";
	public static final String NOME = "nome";
	public static final String VALORE = "valore";
	public static final String USATO = "usato";
	
	private final Lookandfeel lookandfeel;
	private IDAO genericDao = UtilityDAO.getDaoByTipo(Lookandfeel.class);
	
	public WrapLookAndFeel() {
		lookandfeel = new Lookandfeel();
	}

	@Override
	public Object selectById(int id) {
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
	public boolean insert(Object oggettoEntita) {
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
	public boolean delete(int id) {
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
	public boolean update(Object oggettoEntita) {
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

	@Override
	public int getIdLook() {
		return lookandfeel.getIdLook();
	}

	@Override
	public void setIdLook(int idLook) {
		lookandfeel.setIdLook(idLook);
	}

	@Override
	public String getNome() {
		return lookandfeel.getNome();
	}

	@Override
	public void setNome(String nome) {
		lookandfeel.setNome(nome);
	}

	@Override
	public int getUsato() {
		return lookandfeel.getUsato();
	}

	@Override
	public void setUsato(int usato) {
		lookandfeel.setUsato(usato);
	}

	@Override
	public String getValore() {
		return lookandfeel.getValore();
	}

	@Override
	public void setValore(String valore) {
		lookandfeel.setValore(valore);
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
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return lookandfeel;
	}

	@Override
	public Object selectWhere(HashMap<String, String> clausole)
			throws Exception {
		return genericDao.selectWhere(clausole);
	}

}
